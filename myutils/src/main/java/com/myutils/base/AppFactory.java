package com.myutils.base;

import com.myutils.core.http.UrlInvoker;
import com.myutils.utils.AppUtils;
import com.myutils.utils.FileUtils;


public class AppFactory {

	/**
	 * 文件默认保存的基础路径
	 */
	private static String appDir = FileUtils.getSDCardPath()+ AppUtils.getAppName();
	
	
	
	/**
	 * 创建http异步请求
	 * @param actionClass
	 * @return
	 */
	public static UrlInvoker creatUrlInvorker(String actionClass){
		UrlInvoker ai=new UrlInvoker(actionClass);
		return ai;
	}
	
	/**
	 * 应用配置信息
	 * @return
	 */
	public static AppConfig getAppConfig(){
		AppConfig appConfig=AppConfig.getInstance();
		return appConfig;
	}
	
	/**
	 * 获取全局上下文
	 * @return
	 */
	public static BaseAppContext getAppContext(){
		return BaseAppContext.getAppContext();
	}


	public static String getAppDir() {
		return appDir;
	}

	public static void setAppDir(String appDir) {
		AppFactory.appDir = appDir;
	}
}
