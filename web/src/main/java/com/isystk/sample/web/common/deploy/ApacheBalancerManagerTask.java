package com.isystk.sample.web.common.deploy;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ApacheBalancerManagerTask {

	private static final String RETRY_COUNT = "5";

	private static final String RETRY_WAIT = "5000";

	/**
	 * @param args
	 *            args[0] --- バランサマネージャURL args[1] --- クラスタ args[2] ---
	 *            (有効にする)true/(無効にする)false args[3]～ --- ワーカ名
	 * @throws Exception
	 */
	public static void main(String[] args) {

		try {
			System.out.println(getJobStartMsg(ApacheBalancerManagerTask.class));
			if (args == null || args.length < 4) {
				System.out.println("引数不足、第一引数にバランサマネージャURL、第二引数にクラスター名、第三引数に有効/無効を指定した上、第四引数以降ワーカをご指定ください。");
				return;
			}
			// 定数設定
			Properties prop = new Properties();
			prop.setProperty("balancer.manager", args[0]);
			prop.setProperty("balancer.name", args[1]);
			prop.setProperty("balancer.retry.count", ApacheBalancerManagerTask.RETRY_COUNT);
			prop.setProperty("balancer.retry.wait", ApacheBalancerManagerTask.RETRY_WAIT);

			// 作業対象設定
			List<BalancerManagerConfig> lConfig = new ArrayList<BalancerManagerConfig>();
			BalancerManagerConfig bmc = new BalancerManagerConfig("Balancer Manager", prop);
			lConfig.add(bmc);

			// バランサマネージャ利用
			for (int idx = 3; idx < args.length; idx++) {
				switchBalancer(lConfig, args[idx], Boolean.parseBoolean(args[2]));
			}

			System.out.println(getJobFinishMsg(ApacheBalancerManagerTask.class));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(getJobAbortMsg(ApacheBalancerManagerTask.class));
		}
	}

	/**
	 * 指定されたワーカーのステータスを切り替える.
	 * 
	 * @param balancerManager
	 * @param workerName
	 * @throws Exception
	 */
	private static void switchBalancer(List<BalancerManagerConfig> balancers, String workerName, boolean enable)
			throws Exception {
		for (BalancerManagerConfig balancer : balancers) {
			boolean ret = false;
			for (int i = balancer.retryCount; i >= 0; i--) {
				if (ret)
					continue;
				if (enable) {
					System.out.println(balancer.name + ":" + balancer.balancerName + "[" + workerName + "]を有効にします。");
				} else {
					System.out.println(balancer.name + ":" + balancer.balancerName + "[" + workerName + "]を無効にします。");
				}
				ret = ApacheBalancerManagerUtil.changeWorkerStatus(balancer.managerUrl, balancer.balancerName,
						workerName, enable);
				if (!ret) {
					if (i > 0) {
						System.out.println("切り替えに失敗しました。リトライします。残り" + i);
						Thread.sleep(balancer.retryWait);
					} else {
						throw new Exception("バランサの切り替えが失敗しました.");
					}
				}
			}
		}
	}

	/**
	 * FQCNパッケージよりクラス名を取得する<br/>
	 * 
	 * @param strFqcnName
	 *            FQCNパッケージ名
	 * @return String クラス名
	 */
	private static final String cutFqcnPackage(final String strFqcnName) {

		if (strFqcnName != null) {
			int intLastIndex = strFqcnName.lastIndexOf(".");

			if (-1 < intLastIndex) {
				return strFqcnName.substring(intLastIndex + 1);
			}
		}

		return strFqcnName;
	}

	/**
	 * ジョブ開始メッセージを返す<br/>
	 * 
	 * @param clazz
	 *            Class
	 * @return String 開始メッセージ
	 */
	private static final String getJobStartMsg(final Class clazz) {
		return "=== " + cutFqcnPackage(clazz.getName()) + " started. ===";
	}

	/**
	 * ジョブ正常終了メッセージを返す<br/>
	 * 
	 * @param clazz
	 *            Class
	 * @return String 正常終了メッセージ
	 */
	private static final String getJobFinishMsg(final Class clazz) {
		return "=== " + cutFqcnPackage(clazz.getName()) + " finished. ===";
	}

	/**
	 * ジョブ異常終了メッセージを返す<br/>
	 * 
	 * @param clazz
	 *            Class
	 * @return String 異常終了メッセージ
	 */
	private static final String getJobAbortMsg(final Class clazz) {
		return "=== " + cutFqcnPackage(clazz.getName()) + " aborted. ===";
	}

}
