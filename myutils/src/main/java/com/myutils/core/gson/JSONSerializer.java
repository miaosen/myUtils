package com.myutils.core.gson;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.myutils.core.RowObject;

public class JSONSerializer {
	
	public static List<RowObject> getRows(String json) {
		Gson gson = new GsonBuilder().registerTypeAdapter(RowObject.class, GsonUtils.rowObjectJsonDeserializer()).enableComplexMapKeySerialization()
				.serializeNulls().setDateFormat("yyyy-MM-dd")
				.setPrettyPrinting().setVersion(1.0).create();
		return gson.fromJson(json,  new TypeToken<List<RowObject>>() {}.getType());
		
	}
	
	public static RowObject getRow(String json) {
		Gson gson = new GsonBuilder().registerTypeAdapter(RowObject.class, GsonUtils.rowObjectJsonDeserializer()).enableComplexMapKeySerialization().enableComplexMapKeySerialization()
				.serializeNulls().setDateFormat("yyyy-MM-dd")
				.setPrettyPrinting().setVersion(1.0).create();
		return gson.fromJson(json, RowObject.class);
	}
	
	public static String toJson(Object object) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(object);
	}
	
	/**
	 * 把JSON字符串转换为java对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
				.serializeNulls().setDateFormat("yyyy-MM-dd")
				.setPrettyPrinting().setVersion(1.0).create();
		return gson.fromJson(json, clazz);
	}

}
