package com.myutils.base;

import android.annotation.SuppressLint;
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.myutils.core.logger.L;

import java.util.HashMap;
import java.util.Map;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-12-13
 * @Descrition app全局Context
 */
@SuppressLint("NewApi")
public class BaseAppContext extends Application {

	private final static String TAG = "BaseAppContext";

	private static BaseAppContext instance = null;

	public static BaseAppContext getAppContext() {
		return instance;
	}

	/**
	 * 应用退出监听集合,慎用,注意内存问题
	 */
	private Map<String, OnTrimMemoryListenr> mapOnTrimMemoryListenr;
	/**
	 * 应用退出监听
	 */
	private OnTrimMemoryListenr onTrimMemoryListenr;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		L.i("====Application初始化成功！=====");
		// UncaughtExceptionHandler.getInstance().initConfig(this);
		Fresco.initialize(this);

	}
	

	@Override
	public void onTerminate() {
		// 程序终止的时候执行
		L.i("====程序终止======onTerminate");
		super.onTerminate();
	}

	

	@Override
	public void onLowMemory() {
		// 低内存的时候执行
		L.i("====程序内存低======onLowMemory");
		super.onLowMemory();
	}

	@Override
	public void onTrimMemory(int level) {
		// 程序在内存清理的时候执行?(还没被清理)
		// 后退app ，程序被转移到系统缓存
		L.i("====程序被推到后台======onTrimMemory");
		if (mapOnTrimMemoryListenr != null && mapOnTrimMemoryListenr.size() > 0) {
			for (String key : mapOnTrimMemoryListenr.keySet()) {
				onTrimMemoryListenr = mapOnTrimMemoryListenr.get(key);
				onTrimMemoryListenr.onTrimMemory();
			}
		}
		super.onTrimMemory(level);
	}





	public interface OnTrimMemoryListenr {
		void onTrimMemory();
	}

	/**
	 * 退出应用监听
	 * 
	 * @param name
	 * @param onTrimMemoryListenr
	 */
	public void setOnTrimMemoryListenr(String name,
			OnTrimMemoryListenr onTrimMemoryListenr) {
		if (mapOnTrimMemoryListenr == null) {
			mapOnTrimMemoryListenr = new HashMap<String, OnTrimMemoryListenr>();
		}
		mapOnTrimMemoryListenr.put(name, onTrimMemoryListenr);
	}


}
