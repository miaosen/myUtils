package com.myutils.core;

import java.util.HashMap;
import java.util.Map;

import com.myutils.base.L;
import com.myutils.core.json.JSONSerializer;
import com.myutils.utils.JsonUtils;
import com.myutils.utils.SharePreferenceUtils;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-2-19
 * @Descrition 
 *  存放全局的静态变量的map,比如UserInfo,
 *  持久化处理:保存时把变量存放到此类然后再写入xml文件中，读取时先读取此类变量，
 *  如果为空再去读xml中相应的信息，如果获取信息不为空则再次把该变量存放到此类
 *  
 *  注意清除xml文件
 */
public class GlobalVariable {
	
	private static final String TAG = "GlobalVariable";
	/**
	 * 缓存的xml文件名
	 */
	private static final String VARIABLE_PREFS = "variablePrefsFile";
	
	private static SharePreferenceUtils sharePreferenceUtil;
	/**
	 * 存放字符串变量
	 */
	private static Map<String,Object> mapGlobalVariable=new HashMap<String,Object>();
	
	

	/**
	 * 把对象数据转为json放进静态常量中，并保存到xml文件里
	 * @param key
	 * @param object
	 */
	public static void saveObject(String key,Object object){
		if(sharePreferenceUtil==null){
			sharePreferenceUtil=new SharePreferenceUtils(VARIABLE_PREFS);
		}
		String json = JSONSerializer.toJson(object);
		sharePreferenceUtil.save(key, json);
		mapGlobalVariable.put(key, json);
		
	}
	
	/**
	 * 把json数据放进静态常量中，并保存到xml文件里
	 * @param key 
	 * @param strJson json数据
	 */
	public static void saveString(String key,String strJson){
		if(sharePreferenceUtil==null){
			sharePreferenceUtil=new SharePreferenceUtils(VARIABLE_PREFS);
		}
		sharePreferenceUtil.save(key, strJson);
		mapGlobalVariable.put(key, strJson);
	}

	
	/**
	 * 从静态常量map中读取数据，如果为空则返回从xml中读取的数据
	 * @param key
	 * @return json字符串
	 */
	public static String getString(String key){
		if(mapGlobalVariable.get(key)==null){
			if(sharePreferenceUtil==null){
				sharePreferenceUtil=new SharePreferenceUtils(VARIABLE_PREFS);
			}
			if(sharePreferenceUtil.get(key)!=null){
				mapGlobalVariable.put(key, sharePreferenceUtil.get(key));
			}
		}
		return mapGlobalVariable.get(key)+"";
	}
	
	
	
	
//	/**
//	 * 把rowObject转为json数据放进静态常量中，并保存到xml文件里
//	 * @param key 
//	 * @param String json数据
//	 */
//	public static void saveRow(String key,RowObject rowObject){
//		if(sharePreferenceUtil==null){
//			sharePreferenceUtil=new SharePreferenceUtils(VARIABLE_PREFS);
//		}
//		String json = JSONSerializer.toJson(rowObject);
//		sharePreferenceUtil.save(key, json);
//		rowGlobalVariable.put(key, json);
//	}
	
	/**
	 * 从静态常量map中读取数据，返回一个RowObject，如果为空则返回从xml中读取的数据
	 * @param key 
	 * @return json字符串
	 */
	public static RowObject getRow(String key){
		if(mapGlobalVariable.get(key)==null){
			if(sharePreferenceUtil==null){
				sharePreferenceUtil=new SharePreferenceUtils(VARIABLE_PREFS);
			}
			if(sharePreferenceUtil.get(key)!=null){
				if(JsonUtils.isCanToRow(sharePreferenceUtil.get(key))){
					mapGlobalVariable.put(key, sharePreferenceUtil.get(key));
				}else{
					L.i(TAG+"getAsRow key=="+key+" value=="+sharePreferenceUtil.get(key)+"无法转换为RowObject");
				}
			}
		}
		return JSONSerializer.getRow(mapGlobalVariable.get(key)+"");
	}
	
	/**
	 * 从静态常量map中读取数据，返回一个RowObject，如果为空则返回从xml中读取的数据
	 * @param key
	 * @param clazz 需要返回的对象类型
	 * @return json字符串
	 */
	public static Object getObject(String key,Class<?> clazz){
		if(mapGlobalVariable.get(key)==null){
			if(sharePreferenceUtil==null){
				sharePreferenceUtil=new SharePreferenceUtils(VARIABLE_PREFS);
			}
			if(sharePreferenceUtil.get(key)!=null){
				if(JsonUtils.isCanToRow(sharePreferenceUtil.get(key))){
					mapGlobalVariable.put(key, sharePreferenceUtil.get(key));
				}else{
					L.i(TAG+"getAsRow key=="+key+" value=="+sharePreferenceUtil.get(key)+"无法转换为RowObject");
				}
			}
		}
		return JSONSerializer.fromJson(mapGlobalVariable.get(key)+"", clazz);
	}
	
	
	/**
	 * 清除变量和变量的缓存文件
	 */
	public static void  clearPrefs() {
		if(sharePreferenceUtil==null){
			sharePreferenceUtil=new SharePreferenceUtils(VARIABLE_PREFS);
		}
		sharePreferenceUtil.clear(VARIABLE_PREFS);
		mapGlobalVariable.clear();
	}

	public static Map<String, Object> getMapGlobalVariable() {
		return mapGlobalVariable;
	}

	public static void setMapGlobalVariable(Map<String, Object> mapGlobalVariable) {
		GlobalVariable.mapGlobalVariable = mapGlobalVariable;
	}
	
	
	
	
	
	

}
