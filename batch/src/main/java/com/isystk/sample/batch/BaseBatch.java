package com.isystk.sample.batch;

import java.util.Date;

import javax.persistence.PersistenceException;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.exception.SQLRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isystk.sample.common.exception.SystemException;
import com.isystk.sample.common.util.DateUtils;
import com.isystk.sample.common.util.StringUtils;

/**
 * crontab で起動される単位毎に、このクラスを継承したバッチクラスを作成し main メソッドと execute メソッドを実装してください.
 *
 * @author iseyoshitaka
 */
public abstract class BaseBatch {

	protected static final String DICON = "app.dicon";

	private static final Logger logger = LoggerFactory.getLogger(BaseBatch.class);

	private S2Container container;

	protected final Date batchExecDate;

	/**
	 * バッチ名を取得.
	 *
	 * @return バッチ名
	 */
	protected abstract String getBatchName();

	/**
	 * バッチ処理
	 */
	protected abstract void execute();

	public BaseBatch() {
		this.batchExecDate = DateUtils.getNow();
		logger.info(getBatchName() + " ： batch execute date = {}", DateUtils.toDateFormat(batchExecDate));
	}

	/**
	 * コンストラクタ。
	 */
	public BaseBatch(String batchExecDateStr) {
		if (StringUtils.isNullOrEmpty(batchExecDateStr)) {
			this.batchExecDate = DateUtils.getNow();
		} else {
			Date date = DateUtils.toDateNoSlash(batchExecDateStr);
			if (date == null) {
				throw new SystemException("引数の日付が変換できませんでした。 日付：" + batchExecDateStr);
			}
			this.batchExecDate = date;
		}
		logger.info(getBatchName() + " ： batch execute date = {}", DateUtils.toDateFormat(batchExecDate));
	}

	/**
	 * service や logic のコンポーネントを取得
	 *
	 * @param <E>
	 *            型
	 * @param clazz
	 *            クラス
	 * @return Component
	 */
	@SuppressWarnings("unchecked")
	protected <E> E getComponent(Class<E> clazz) {
		return (E) container.getComponent(clazz);
	}

	/**
	 * 各バッチから呼び出す
	 *
	 * @return 結果
	 */
	protected final Status run() {
		Status result = null;

		String batchName = getBatchName();

		try {
			init();

			long start = System.currentTimeMillis();
			logger.info("{} ： 処理開始", batchName);
			execute();
			logger.info("{} ： 正常終了({} ms)", batchName, System.currentTimeMillis() - start);

			result = Status.SUCCESS;

		} catch (Throwable e) {
			result = Status.ERROR;
			logger.info("{} ： 異常終了", batchName);

			if (e instanceof SQLRuntimeException || e instanceof PersistenceException) {
				logger.error("SQLエラー: " + e.getMessage(), e);
			} else {
				logger.error("未定義の例外: " + e.getMessage(), e);
			}

		} finally {

			destroy();
		}

		return result;
	}

	/**
	 * S2container の初期化
	 */
	private void init() {
		try {
			if (container == null) {
				SingletonS2ContainerFactory.setConfigPath(DICON);
				SingletonS2ContainerFactory.init();
				container = SingletonS2ContainerFactory.getContainer();
			}

		} catch (Exception e) {
			throw new SystemException("Error occurred while initializing S2Container.", e);
		}
	}

	/**
	 * S2container の 破棄
	 */
	private void destroy() {
		if (container != null) {
			container.destroy();
		}
	}
}
