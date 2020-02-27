package com.isystk.sample.web.common.util;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.Discovery;
import org.openid4java.discovery.html.HtmlResolver;
import org.openid4java.discovery.xri.XriDotNetProxyResolver;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.server.RealmVerifierFactory;
import org.openid4java.util.HttpFetcherFactory;

import com.isystk.sample.common.exception.SystemException;

public class OpenidManagerUtil {
    public static ConsumerManager _manager;

    public static ConsumerManager getManager() {
	if (_manager == null) {
	    try {
//		    SSLContext ctx = SSLContext.getInstance("TLS");
//		    X509TrustManager tm = new X509TrustManager() {
//			public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//			}
		//
//			public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
//			}
		//
//			public X509Certificate[] getAcceptedIssuers() {
//			    return null;
//			}
//		    };
//		    ctx.init(null, new TrustManager[] { tm }, null);

		SSLContext ctx = SSLContext.getInstance("SSL");

		// set up a TrustManager that trusts everything
		ctx.init(null, new TrustManager[] { new X509TrustManager() {
		    public X509Certificate[] getAcceptedIssuers() {
			System.out.println("getAcceptedIssuers =============");
			return null;
		    }

		    public void checkClientTrusted(X509Certificate[] certs, String authType) {
			System.out.println("checkClientTrusted =============");
		    }

		    public void checkServerTrusted(X509Certificate[] certs, String authType) {
			System.out.println("checkServerTrusted =============");
		    }
		} }, new SecureRandom());

		_manager =
		    new ConsumerManager(new RealmVerifierFactory(new YadisResolver(new HttpFetcherFactory(ctx))), new Discovery(new HtmlResolver(
			new HttpFetcherFactory(ctx)), new YadisResolver(new HttpFetcherFactory(ctx)), new XriDotNetProxyResolver(
			new HttpFetcherFactory(ctx))), new HttpFetcherFactory(ctx));

	    } catch (Exception ex) {
		throw new SystemException(ex);
	    }
	}

	return _manager;
    }
}
