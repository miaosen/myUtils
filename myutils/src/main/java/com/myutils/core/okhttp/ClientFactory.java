package com.myutils.core.okhttp;

import okhttp3.OkHttpClient;

import com.myutils.base.AppFactory;
import com.myutils.core.okhttp.cookies.CookiesManager;

import java.util.concurrent.TimeUnit;


/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/7 16:57
 * @Descrition OKhttp处理工厂，
 */ 
public class ClientFactory {
	
	private static CookiesManager cookiesManager = new CookiesManager(AppFactory.getAppContext());

	private static String keepCookies = AppFactory.getAppConfig().getKEEP_COOKIE();

	private volatile static OkHttpClient mInstance = null;


	// 单例加线程锁
	public static OkHttpClient getInstance() {
		if (mInstance == null) {
			synchronized (ClientFactory.class) {
				if (mInstance == null) {
					mInstance = ClientFactory.getOkHttp();
				}
			}
		}
		return mInstance;
	}





	/**
	 * 清除cookies
	 *
	 * @return
	 */
	public static void clearCookies() {
		ClientFactory.getCookiesManager().getCookieStore().removeAll();
	}


	/**
	 * OkHttpClient 配置
	 *
	 * @return
	 */
	public static OkHttpClient getOkHttp() {
		OkHttpClient client = new OkHttpClient();
		//Builder okHttpBuilder = client.newBuilder();
		OkHttpClient.Builder buidler =  client.newBuilder();
		if (keepCookies != null && keepCookies.equals("true")) {
			buidler =  buidler.cookieJar(cookiesManager);
			//Log.i("logtag","keepCookies================");
		}
		// 连接超时60秒
		buidler.connectTimeout(30, TimeUnit.SECONDS).build();
		buidler.readTimeout(30, TimeUnit.SECONDS).build();
		buidler.writeTimeout(30, TimeUnit.SECONDS).build();
		buidler.addInterceptor(new OkhttpLog());
		client=buidler.build();
		return client;
	}

	public static CookiesManager getCookiesManager() {
		return cookiesManager;
	}

	public static void setCookiesManager(CookiesManager cookiesManager) {
		ClientFactory.cookiesManager = cookiesManager;
	}




}
