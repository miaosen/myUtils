package com.myutils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.myutils.base.L;
import com.myutils.core.RowObject;
import com.myutils.core.json.JSONSerializer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

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

	/**
	 * json字符串转对象
	 * @param jsonStr
	 * @return
	 */
	public static RowObject jsonToRow(String jsonStr) {
		RowObject row = new RowObject();
		if (isValidateJson(jsonStr)) {
			if (jsonStr.startsWith("{")) {
				try {
					JSONObject jObject = new JSONObject(jsonStr);
					jsonObjectToRow(row, jObject);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else {
			L.e("json格式错误！");
		}
		return row;
	}

	/**
	 * 判断json字符串是否是JSONArray
	 *
	 * @param jsonStr
	 * @return
	 */
	public static boolean isJSONObject(String jsonStr) {
		if (isValidateJson(jsonStr)) {
			if (jsonStr.startsWith("[")) {
				return true;
			}
		}
		return false;
	}


	/**
	 * JSONArray转List<RowObject>
	 * @param jsonStr
	 * @return
	 */
	public static List<RowObject> jsonToRows(String jsonStr) {
		List<RowObject> rows = new LinkedList<RowObject>();
		if (isJSONArray(jsonStr)) {
			try {
				JSONArray jArray = new JSONArray(jsonStr);
				jsonArrayToRows(rows, jArray);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			L.e("json格式错误！");
		}
		return rows;
	}

	/**
	 * 判断json字符串是否是JSONArray
	 *
	 * @param jsonStr
	 * @return
	 */
	public static boolean isJSONArray(String jsonStr) {
		if (isValidateJson(jsonStr)) {
			if (jsonStr.startsWith("[")) {
				return true;
			}
		}
		return false;
	}

	public static void jsonObjectToRow(RowObject row, JSONObject jObject) {
		try {
			Iterator it = jObject.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				Object value = jObject.get(key);
				if (value instanceof JSONArray) {
					List<RowObject> rows = new LinkedList<RowObject>();
					jsonArrayToRows(rows, (JSONArray) value);
					row.put(key, rows);
				} else if (value instanceof JSONObject) {
					RowObject mRow = new RowObject();
					jsonObjectToRow(mRow, (JSONObject) value);
					row.put(key, mRow);
				} else {
					row.put(key, value);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void jsonArrayToRows(List<RowObject> rows, JSONArray jArray) {
		try {
			for (int i = 0; i < jArray.length(); i++) {
				Object object = jArray.get(i);
				if (object instanceof JSONObject) {
					RowObject row = new RowObject();
					jsonObjectToRow(row, (JSONObject) object);
					rows.add(row);
				} else {
					rows.add((RowObject) object);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


}
