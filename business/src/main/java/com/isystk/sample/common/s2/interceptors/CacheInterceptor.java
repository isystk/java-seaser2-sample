package com.isystk.sample.common.s2.interceptors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.hotdeploy.HotdeployBehavior;
import org.seasar.framework.container.hotdeploy.HotdeployFilter;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.container.hotdeploy.HotdeployUtil.Rebuilder;
import org.seasar.framework.container.impl.S2ContainerBehavior;
import org.seasar.framework.util.ClassLoaderUtil;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.tiger.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.anotation.Cache;
import com.isystk.sample.common.exception.SystemException;

/**
 * キャッシュを実施するインターセプター。当該メソッドには@Cacheアノテーションによる設定が必要で、メソッド呼び出し時に適用される。
 * キャッシュのヒットの条件として
 * 、同じオブジェクトに対する、同じメソッドを、同じ引数で呼び出すことが必要。それ以外は初めてのパターンとして、新たにキャッシュに追加する。
 * また、システムのメモリが少なくなった際には自動でメモリを開放する。また、 @Cacheアノテーションには、キャッシュ時間を指定する必要がある。
 * なお、このインターセプターは同じCacheCustomizerによってコンポーネントごとに作成されることを想定している。
 * 
 * @author iseyoshitaka
 */
public class CacheInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 7907571627704115100L;

	private static final Logger logger = LoggerFactory.getLogger(CacheInterceptor.class);

	public ConcurrentHashMap<CacheInfo, Result> conditionAndResultList = new ConcurrentHashMap<CacheInfo, Result>();

	private ComponentDef componentDef;

	public CacheInterceptor(ComponentDef componentDef) {
		this.componentDef = componentDef;
		CacheInterceptorManager.getSingleton().addCacheInterceptor(this);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		// キャッシュマネージャによるキャッシュ更新化のためのスレッドでなく、またキャッシュが見つかった場合にはキャッシュを使用する
		if (Thread.currentThread() != CacheInterceptorManager.SINGLETON.thread) {
			Pair<Boolean, Object> findCache = findCacheAndRemoveExpire(invocation);
			if (findCache.getFirst()) {
				return findCache.getSecond();
			}
		}

		// キャッシュがない場合には、メソッドを呼び出してキャッシュを作成する
		Object proceed = invocation.proceed();
		createCache(invocation.getMethod(), invocation.getArguments(), proceed);
		return proceed;
	}

	/**
	 * キャッシュを検索する
	 * 
	 * @param invocation
	 *            メソッド呼び出し
	 * @return キャッシュがヒットしたかどうかと、キャッシュがヒットした場合の値のペア
	 */
	private Pair<Boolean, Object> findCacheAndRemoveExpire(MethodInvocation invocation) {
		long currentTimeMillis = System.currentTimeMillis();

		CacheInfo targetInfo = new CacheInfo();
		targetInfo.methodName = invocation.getMethod().getName();
		targetInfo.arguments = invocation.getArguments();

		Result res = conditionAndResultList.get(targetInfo);

		Pair<Boolean, Object> result;
		if (res != null) {
			Object cacheResult = res.result.get();
			if (HotdeployUtil.isHotdeploy()) {
				cacheResult = toObject((byte[]) cacheResult);
			}

			if (cacheResult != null || res.isResultNull) {
				result = new Pair<Boolean, Object>(true, cacheResult);

				res.touch = currentTimeMillis; // キャッシュを使ったタイミングを保持
			} else { // メモリの不足によりキャッシュが捨てられた場合
				result = new Pair<Boolean, Object>(false, null);
			}
		} else {
			result = new Pair<Boolean, Object>(false, null); // キャッシュが見つからなかった場合
		}

		return result;
	}

	/**
	 * オブジェクトをバイト配列に変換する
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	private byte[] toByteArray(Object obj) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();
		} catch (IOException e) {
			throw new SystemException(e);
		}

		return baos.toByteArray();
	}

	/**
	 * オブジェクトをバイト配列に変換する
	 * 
	 * @param cacheResult
	 * @return
	 * @throws IOException
	 */
	private Object toObject(byte[] byteArray) {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Class<?> rebuilderClass = ClassLoaderUtil.loadClass(loader, HotdeployUtil.REBUILDER_CLASS_NAME);
			Rebuilder rebuilder = (Rebuilder) ClassUtil.newInstance(rebuilderClass);
			return rebuilder.deserialize(byteArray);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * キャッシュを作成する
	 * 
	 * @param invocation
	 * @param result
	 */
	private CacheInfo createCache(Method method, Object[] arguments, Object result) {

		CacheInfo info = new CacheInfo();
		info.methodName = method.getName();
		info.arguments = arguments;

		Result res = new Result();
		if (HotdeployUtil.isHotdeploy()) {
			result = toByteArray(result);
		}

		res.result = new SoftReference<Object>(result);
		res.isResultNull = result == null;
		res.cacheCreate = System.currentTimeMillis();
		res.expire = res.cacheCreate + getCacheTime(method);
		res.autoReload = getAutoReload(method);
		if (res.autoReload) {
			res.invocationMethodName = method.getName();
			res.invocationMethodArgsType = method.getParameterTypes();
		}
		res.autoReloadInvalidTime = getAutoReloadInvalidTime(method);
		res.touch = res.cacheCreate;

		conditionAndResultList.put(info, res);

		return info;
	}

	private Integer getCacheTime(Method method) {
		return method.getAnnotation(Cache.class).time();
	}

	private Boolean getAutoReload(Method method) {
		return method.getAnnotation(Cache.class).autoReload();
	}

	private Long getAutoReloadInvalidTime(Method method) {
		return (long) method.getAnnotation(Cache.class).autoReloadInvalidTime();
	}

	public static class Result {
		public long autoReloadInvalidTime;
		public SoftReference<Object> result;
		public boolean isResultNull = false;
		public long expire;
		public boolean autoReload;
		public String invocationMethodName;
		public Class<?>[] invocationMethodArgsType;
		public long cacheCreate;
		public long touch; // キャッシュを最後に使った時間
	}

	public static class CacheInfo {
		public String methodName;
		public Object[] arguments;

		// @Override
		// public int compareTo(CacheInfo o) {
		// CompareToBuilder bld = new CompareToBuilder();
		// bld.append(this.methodName, o.methodName);
		// bld.append(this.arguments, o.arguments); //Comparableを継承していないパラメータだとエラーが出る。
		//
		// return bld.toComparison();
		// }

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof CacheInfo)) {
				return false;
			} else {
				CacheInfo o = (CacheInfo) obj;

				EqualsBuilder bld = new EqualsBuilder();
				bld.append(this.methodName, o.methodName);
				bld.append(this.arguments, o.arguments);
				return bld.isEquals();
			}
		}

		int hashCode = -1;

		@Override
		public int hashCode() {
			if (hashCode == -1) {
				int temp = 31 + this.methodName.hashCode();
				for (Object arg : this.arguments) {
					if (arg != null) {
						temp += arg.hashCode();
					}
				}

				hashCode = temp;
			}

			return hashCode;
		}

		public static boolean compare(CacheInfo cacheInfo, MethodInvocation invocation) {
			String methodName = invocation.getMethod().getName();
			Object[] arguments = invocation.getArguments();
			return // cacheInfo.ths == invocation.getThis() &&
			compare(cacheInfo, methodName, arguments);
		}

		public static boolean compare(CacheInfo cacheInfo, String methodName, Object[] arguments) {
			return cacheInfo.methodName.equals(methodName) && Arrays.deepEquals(cacheInfo.arguments, arguments);
		}

	}

	/**
	 * キャッシュの自動更新を行う。古いキャッシュの削除と、自動更新の対象のキャッシュを新しくする。
	 */
	public void cacheHeartbeat() {
		long currentTimeMillis = System.currentTimeMillis();

		// int startSize = conditionAndResultList.size();
		// int createcount = 0;
		// int removecount = 0;
		// StringBuilder sizeLoop = new StringBuilder();

		for (Entry<CacheInfo, Result> conditionAndResult : conditionAndResultList.entrySet()) {
			try {
				CacheInfo cacheInfo = conditionAndResult.getKey();
				Result res = conditionAndResult.getValue();

				// 有効期限切れを削除する。自動更新でない場合には、有効期限を確認して削除。ただしAutoReloadの対象についてはアクセスが全くない場合にキャッシュを無効にするまでの時間を超えた場合に削除。キャッシュが既にガーベージコレクションされていた場合にも削除。
				if ((res.autoReload == false && res.expire <= currentTimeMillis)
						|| (res.autoReload && currentTimeMillis - res.touch > res.autoReloadInvalidTime)
						|| (res.result.get() == null && res.isResultNull == false)) {
					conditionAndResultList.remove(cacheInfo);
					continue;
				}

				// 自動更新
				if (res.autoReload) {
					if (res.expire <= currentTimeMillis) { // キャッシュの時間を超えていたら自動更新する

						if (HotdeployUtil.isHotdeploy()) { // ホットデプロイ時には、クラスを専用のクラスローダーでロードするようにする。
							synchronized (HotdeployFilter.class) {
								HotdeployBehavior ondemand = (HotdeployBehavior) S2ContainerBehavior.getProvider();
								ondemand.start();
								try {
									reloadNewCache(cacheInfo, res);
								} finally {
									ondemand.stop();
								}
							}
						} else {
							reloadNewCache(cacheInfo, res);
						}

						// createcount++;
					}
				}

			} catch (Throwable t) {
				// void
			}
		}

		// java.util.Iterator<Map.Entry<CacheInfo, Result>> iter =
		// conditionAndResultList.entrySet().iterator();
		// while (iter.hasNext()) {
		// Map.Entry<CacheInfo, Result> temp = iter.next();
		//
		// System.out.println("C: " + temp.getKey().methodName + " - " +
		// temp.getKey().arguments[0]);
		// }
		//
		// int endsize = conditionAndResultList.size();
		//
		// System.out.println("Size: " + startSize + " - " + endsize);
		// System.out.println("createCount: " + createcount);
		// System.out.println("removeCount: " + removecount);
		// System.out.println(sizeLoop.toString());
		// System.out.println("CountOK: " + ((createcount - removecount) == (endsize -
		// startSize)));
		// System.out.println();

	}

	/**
	 * 新しいキャッシュをリロードする
	 * 
	 * @param cacheInfo
	 * @param res
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void reloadNewCache(CacheInfo cacheInfo, Result res)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Object ths = componentDef.getComponent();
		Method method = componentDef.getComponentClass().getMethod(res.invocationMethodName,
				res.invocationMethodArgsType);

		Object proceed = method.invoke(ths, cacheInfo.arguments);
		createCache(method, cacheInfo.arguments, proceed);// キャッシュ作成
	}
}