package com.myutils.core.json;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.myutils.core.RowObject;

public class GsonUtils {

	/**
	 * gson反射为RowObject时自定义解析数据
	 * @return
	 */
	public static JsonDeserializer<RowObject> rowObjectJsonDeserializer() {
		JsonDeserializer<RowObject> jsonDeserializer = new JsonDeserializer<RowObject>() {
			public RowObject deserialize(JsonElement jsonElement, Type typeOfT,
					JsonDeserializationContext context)
					throws JsonParseException {
				RowObject resultMap = new RowObject();
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				resultMap=jsonObjectToRowObject(resultMap,null,jsonObject);
				return resultMap;
			}
		};
		return jsonDeserializer;
	}

	/**
	 * JsonObject转RowObject
	 * 
	 * @param resultMap
	 * @param jsonObject
	 * @param key 
	 * @return
	 */
	public static RowObject jsonObjectToRowObject(RowObject resultMap, String key,
			JsonObject jsonObject) {
		RowObject row = new RowObject();
		Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
		for (Map.Entry<String, JsonElement> entry : entrySet) {
			JsonElement je = entry.getValue();
			if (je.isJsonObject()) {
				jsonObjectToRowObject(row,entry.getKey(), je.getAsJsonObject());
			}else if (je.isJsonArray()) {
				JsonArrayToListRowObject(row,entry.getKey(), je.getAsJsonArray());
			} else {
				//TODO value的取值
				if(!je.isJsonNull()){
					row.put(entry.getKey(), je.getAsString());
				}
			}
		}
		if(key==null||key.equals("")){
			resultMap.putAll(row);
		}else{
			resultMap.put(key,row);
		}
		return resultMap;
	}
	
	/**
	 * JsonArray转list<RowObject>
	 * @param resultMap
	 * @param JsonArray
	 * @param key 
	 * @return
	 */
	public static void JsonArrayToListRowObject(RowObject resultMap, String key,
			JsonArray JsonArray) {
		LinkedList<Object> rows = new LinkedList<Object>();
		for (int i = 0; i < JsonArray.size(); i++) {
			JsonElement je = JsonArray.get(i);
			RowObject row = new RowObject();
			if (je.isJsonObject()) {
				rows.add(jsonObjectToRowObject(row,null,je.getAsJsonObject()));
			}else if (je.isJsonArray()) {
				JsonArrayToListRowObject(row,null, je.getAsJsonArray());
			}else {//是字符串
				if(!je.isJsonNull()){
				rows.add(je.getAsString());
				}
			}
		}
		resultMap.put(key, rows);
	}


}
