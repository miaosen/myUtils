package com.myutils.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import android.database.Cursor;

import com.myutils.core.RowObject;
import com.myutils.core.json.JSONSerializer;

public class CursorUtils {
	
	/**
	 * 根据Cursor返回json数据，json格式为JSONArray包含多个JSONObject
	 * @param cursor
	 * @return
	 */
	public static String cursorToJson(Cursor cursor) {
		String str = null;
		JSONArray jsonArray = new JSONArray();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < cursor.getColumnCount(); i++) {
				String name = cursor.getColumnName(i);
				String value = cursor.getString(i);
				if ("".equals(value) || null == value || "null".equals(value)) {
				} else {
					HashMap<String, Object> map = null;
					map = new HashMap<String, Object>();
					map.put(name, value);
					listMap.add(map);
				}
			}
			try {
				JSONObject jsonObj = null;
				jsonObj = new JSONObject();
				for (int j = 0; j < listMap.size(); j++) {
					Entry<String, Object> entry = listMap.get(j).entrySet()
							.iterator().next();
					String strname = entry.getKey();
					String strvalue = listMap.get(j).get(strname) + "";

					jsonObj.put(strname, strvalue);
				}
				jsonArray.put(jsonObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		str = jsonArray.toString();
		//cursor.close();
		return str;
	}
	
	/**
	 * 根据Cursor返回json数据,json格式为JSONArray包含多个JSONObject 
	 * @param cursor
	 * @param strKey
	 * @param strValue
	 * @return
	 */
	public static String cursorToJson(Cursor cursor,String strKey,String strValue) {
		String str = null;
		JSONArray jsonArray = new JSONArray();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < cursor.getColumnCount(); i++) {
				String name = cursor.getColumnName(i);
				String value = cursor.getString(i);
				if ("".equals(value) || null == value || "null".equals(value)) {
				} else {
					HashMap<String, Object> map = null;
					map = new HashMap<String, Object>();
					map.put(name, value);
					listMap.add(map);
				}
			}
			try {
				JSONObject jsonObj = null;
				jsonObj = new JSONObject();
				for (int j = 0; j < listMap.size(); j++) {
					Entry<String, Object> entry = listMap.get(j).entrySet()
							.iterator().next();
					String strname = entry.getKey();
					//String strvalue = listMap.get(j).get(strname) + "";
					String strvalue = entry.getValue()+"";
					jsonObj.put(strname, strvalue);
				}
				jsonObj.put(strKey, strValue);
				jsonArray.put(jsonObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		str = jsonArray.toString();
		//cursor.close();
		return str;
	}
	
	/**
	 *  根据Cursor返回List<RowObject>
	 * @param cursor
	 * @return
	 */
	public static List<RowObject> cursorToRows(Cursor cursor) {
		List<RowObject> rows=JSONSerializer.getRows(cursorToJson(cursor));
		return rows;
	}

}
