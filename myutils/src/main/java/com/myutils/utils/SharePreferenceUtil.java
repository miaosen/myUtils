package com.myutils.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.myutils.base.AppFactory;
/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-1-19
 * @Descrition 基于SharePreference的xml操作工具
 */
public class SharePreferenceUtil {
	
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	/**
	 * 
	 * @param fileName文件名称
	 */
	@SuppressLint("CommitPrefEdits")
	public SharePreferenceUtil(String fileName) {
		sp = AppFactory.getAppContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	
	/**
	 * 保存到xml
	 * @param name 键值
	 * @param value 值
	 */
	public void save(String name,String value){
		editor.putString(name, value);
		editor.commit();
	}
	
	/**
	 * 从xml读取
	 * @param name 键值
	 * @return
	 */
	public String get(String name){
		return sp.getString(name, null);
	}
	
	
	/**
	 * 清除xml内容
	 * @param name 键值
	 * @param value 值
	 */
	public void clear(String name){
		editor.clear();
		editor.commit();
	}
	
}

