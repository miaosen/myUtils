package com.myutils.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.myutils.core.RowObject;
import com.myutils.core.SerializableRowObject;

public class IntentUtils {
	
	public static void jump(Context context, Class<?> cls){
		Intent in=new Intent(context,cls);
		context.startActivity(in);
	}
	
	
	/**
	 * 添加RowObject对象
	 * @param in
	 * @param row
	 * @param key
	 * @return
	 */
	public static Intent addRow(Intent in,RowObject row,String key){
		SerializableRowObject serializableRowObject = new SerializableRowObject(row);
		Bundle bundle=new Bundle();
		bundle.putSerializable(key, serializableRowObject);
		in.putExtras(bundle);
		return in;
	}
	
	/**
	 * 获取RowObject对象
	 * @param in
	 * @param key
	 * @return
	 */
	public static RowObject getRow(Intent in,String key){
		SerializableRowObject serializableRowObject = (SerializableRowObject) in
				.getSerializableExtra(key);
		if(serializableRowObject!=null){
			return serializableRowObject.getRowObject();
		}else{
			return null;
		}

	}
	

}
