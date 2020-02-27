package com.isystk.sample.common.s2.interceptors;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CacheInterceptorを管理するマネージャ。独自のスレッドを持ち、このスレッドによって、古いキャッシュを削除し、
 * 自動更新対象のキャッシュを最新にする。
 * 
 * @author iseyoshitaka
 */
public class CacheInterceptorManager {
    public CopyOnWriteArrayList<CacheInterceptor> cacheInterceptorList = new CopyOnWriteArrayList<CacheInterceptor>();

    public static CacheInterceptorManager SINGLETON = new CacheInterceptorManager();

    public Thread thread;

    public static CacheInterceptorManager getSingleton() {
	return SINGLETON;
    }

    private CacheInterceptorManager() {
	thread = new Thread("CacheInterceptorManagerThread") {
	    @Override
	    public void run() {
		cacheHeartbeat();
	    }

	};
	thread.setDaemon(true);
	thread.setPriority(Thread.MIN_PRIORITY);
	thread.start();
    }

    public void addCacheInterceptor(CacheInterceptor interceptor) {
	cacheInterceptorList.add(interceptor);
    }

    private void cacheHeartbeat() {
	while (true) { // 確実に動作させ続けるために無限ループ＆トライキャッチを実装している。
	    try {
		for (CacheInterceptor cacheInterceptor : cacheInterceptorList) {
		    try {
			cacheInterceptor.cacheHeartbeat();
		    } catch (Throwable t2) {
			// void
		    }
		}

		Thread.sleep(5000);
	    } catch (Throwable t1) {
		// void 
	    }
	}
    }
}