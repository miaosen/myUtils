package com.myutils.core.db;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.myutils.base.L;
import com.myutils.ui.T;
/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-12-30
 * @Descrition 修改本地数据库默认指定的路径
 */
@SuppressLint("NewApi")
class DbPathCustom extends ContextWrapper {

	//private Context mContext;
	
	
	private String dbPath;

	public DbPathCustom(Context base,String dbPath) {
		super(base);
		//this.mContext = base;
		this.dbPath=dbPath;
	}

	@Override
	//TODO 用FileUtils操作
	public File getDatabasePath(String name) {
		// 判断是否存在sd卡
		boolean sdExist = android.os.Environment.MEDIA_MOUNTED
				.equals(android.os.Environment.getExternalStorageState());
		if (!sdExist) {// 如果不存在,
			L.i("SD卡管理：SD卡不存在，请加载SD卡");
			return null;
		} else {// 如果存在
			// 获取sd卡路径
			String dbDir = android.os.Environment.getExternalStorageDirectory()
					.getPath();
			// 判断目录是否存在，不存在则创建该目录
			File dirFile = new File(dbDir, dbPath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
				T.show( "数据库指定目录不存在，已为你创建一个！");
			}
			// 数据库文件是否创建成功
			boolean isFileCreateSuccess = false;
			// 判断文件是否存在，不存在则创建该文件
			File dbFile = new File(dirFile, name);
			if (!dbFile.exists()) {
				try {
					isFileCreateSuccess = dbFile.createNewFile();// 创建文件
					T.show("数据库不存在，已为你创建一个！");
				} catch (IOException e) {
					L.i("数据库创建失败！");
					e.printStackTrace();
				}
			} else
				isFileCreateSuccess = true;
			// 返回数据库文件对象
			if (isFileCreateSuccess){
				return dbFile;
			}else{
				return null;
			}
		}
	}

	/**
	 * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
	 * @param name
	 * @param mode
	 * @param factory
	 */
	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode,
			SQLiteDatabase.CursorFactory factory) {
		File file=getDatabasePath(name);
		SQLiteDatabase result = null;
		if(file!=null){
			result = SQLiteDatabase.openOrCreateDatabase(
					getDatabasePath(name).getAbsolutePath(), null);
		}
		return result;
	}

	/**
	 * Android 4.0会调用此方法获取数据库。
	 * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String,
	 *      int, android.database.sqlite.SQLiteDatabase.CursorFactory,
	 *      android.database.DatabaseErrorHandler)
	 * @param name
	 * @param mode
	 * @param factory
	 * @param errorHandler
	 */
	@Override
	public SQLiteDatabase openOrCreateDatabase(String name, int mode,
			CursorFactory factory, DatabaseErrorHandler errorHandler) {
		File file=getDatabasePath(name);
		SQLiteDatabase result = null;
		if(file!=null){
			 result = SQLiteDatabase.openDatabase(
						getDatabasePath(name).getAbsolutePath(), factory, 8);
		}
		return result;
	}
}