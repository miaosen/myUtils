package com.myutils.core.okhttp;

import okhttp3.OkHttpClient;

import com.myutils.base.AppFactory;
import com.myutils.core.okhttp.cookies.CookiesManager;


/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/7 16:57
 * @Descrition OKhttp处理工厂，
 */ 
public class ClientFactory {
	
	private static CookiesManager cookiesManager = new CookiesManager(AppFactory.getAppContext());

	//private static String keepCookies = AppFactory.getAppConfig().getKEEP_COOKIE();
	
	/**
	 * OKhttp 设置
	 * 
	 * @return
	 */
	public static OkHttpClient getOkHttp() {
		OkHttpClient client = new OkHttpClient();
		//Builder okHttpBuilder = client.newBuilder();
		OkHttpClient.Builder buidler =  client.newBuilder();
		buidler.addInterceptor(new OkhttpLog());
//		if (keepCookies != null && keepCookies.equals("true")) {
//			buidler =  buidler.cookieJar(cookiesManager);
//		}
		client=buidler.build();
		// 连接超时60秒
//		client=client.newBuilder().connectTimeout(40, TimeUnit.SECONDS).build();
//		client=client.newBuilder().readTimeout(50, TimeUnit.SECONDS).build();
//		client=client.newBuilder().writeTimeout(60, TimeUnit.SECONDS).build();
		return client;
	}

	public static CookiesManager getCookiesManager() {
		return cookiesManager;
	}

	public static void setCookiesManager(CookiesManager cookiesManager) {
		ClientFactory.cookiesManager = cookiesManager;
	}




}
