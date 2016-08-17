package com.myutils.core.db;


import java.util.HashMap;
import java.util.Map;

import com.myutils.base.AppFactory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-12-30
 * @Descrition 每个数据库单例并加入同步锁的DbUtils获取帮助类
 */
public class DbOpenHelp extends SQLiteOpenHelper {

	private static DbOpenHelp mInstance;
	
	/**
	 * 用applictation
	 */
	private static Context context=AppFactory.getAppContext();
	
	//不同数据库单例
	private static Map<String,DbOpenHelp> mapInstance=new HashMap<String, DbOpenHelp>();

	public synchronized static DbOpenHelp getInstance(String dbName,String dbPath, int visition) {
		if (mapInstance.containsKey(dbName)){
			mInstance=mapInstance.get(dbName);
		}else{
			mInstance = new DbOpenHelp(context,dbName,dbPath,visition);
			mapInstance.put(dbName, mInstance);
		}
		return mInstance;
	};

	private DbOpenHelp(Context context,String dbName,String dbPath, int visition) {
		super(new DbPathCustom(context,dbPath), dbName, null, visition);
	}

	// 回调函数，第一次创建时才会调用此函数，创建一个数据库
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("Create Database===", db.getPath());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
