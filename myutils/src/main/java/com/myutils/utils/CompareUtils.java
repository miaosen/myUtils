package com.myutils.utils;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-12-29
 * @Descrition 数据比较工具类
 */
public class CompareUtils {
	
	
	/**
	 * 判断viewIds中是否包含该viewId的值
	 * @param viewId
	 * @param viewIds
	 * @return
	 */
	public static boolean containsKey(Integer viewId,Integer... viewIds){
		for (int i = 0; i < viewIds.length; i++) {
			//Logger.info("viewId====="+viewId+"    viewIds======"+viewIds[i]);
			if(viewId.longValue()==viewIds[i].longValue()){
				//Logger.info("=========containsKey============");
				return true;
			}
		}
		return false;
	}
	
	
	

}
