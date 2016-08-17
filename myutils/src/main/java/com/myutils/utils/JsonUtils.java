package com.myutils.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.myutils.core.logger.L;
import com.myutils.core.gson.JSONSerializer;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-12-17
 * @Descrition 通用json解析工具
 */
public class JsonUtils {

	/**
	 * 是否提交成功
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isSuccess(String text) {
		boolean success = false;
		try {
			JSONObject jObj = new JSONObject(text);
			String strSuccess = jObj.getString("success");
			if (strSuccess != null && strSuccess.equals("true")) {
				success = true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return success;
	}

	
	/**
	 * 判断字符串是否为json格式
	 * @param json
	 * @return 
	 */
	public static boolean isValidateJson(String json) {
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 判断字符串是否为json格式
	 * @param json
	 * @return 
	 */
	public static boolean isCanToRow(String json) {
		if(isValidateJson(json)){
			try{
				JSONSerializer.getRow(json);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			
		}
		return false;
	}
	
	/**
	 * 判断字符串是否为json格式
	 * @param json
	 * @return 
	 */
	public static boolean isCanToRows(String json) {
		if(isValidateJson(json)){
			try{
				JSONSerializer.getRows(json);
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	
	/**
	 * 获取JSONObject中的字符串
	 * @param json
	 * @return 
	 */
	public static String getString(String json,String name) {
		String data=null;
		try {
			JSONObject jObj = new JSONObject(json);
			 data = jObj.getString(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
		
	}

}
