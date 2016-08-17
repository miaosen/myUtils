package com.myutils.utils;

import java.util.HashMap;

public class StringUtils {
	
	/**
	 * String[]转成value为null的HashMap
	 * @param args
	 * @return
	 */
	public static HashMap<String,Object> argsToMap(String[] args) {
		HashMap<String,Object> map=new HashMap<String, Object>();
		for (int i = 0; i < args.length; i++) {
			map.put(args[i],null);
		}
		return map;
	}
	
	/**
	 * 判断一个数组是否包含某个对象
	 * @param viewId
	 * @param viewIds
	 * @return
	 */
	public static boolean isInArray(Object[] objects,Object object){
		for (int i = 0; i < objects.length; i++) {
			if(object.toString().equals(objects[i])){
				return true;
			}
		}
		return false;
	}

}
