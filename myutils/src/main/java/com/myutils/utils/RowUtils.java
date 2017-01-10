package com.myutils.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.myutils.core.json.JSONSerializer;
import com.myutils.core.RowObject;

/**
 * Created by OAIM on 2016/1/6.
 */
public class RowUtils {

	public static List<RowObject> argToRows(String name, Object[] args) {
		List<RowObject> rows = new ArrayList<RowObject>();
		for (int i = 0; i < args.length; i++) {
			RowObject row = new RowObject();
			row.put(name, args[i]);
			rows.add(row);
		}
		return rows;
	}

	public static List<RowObject> addArg(List<RowObject> rows, String name,
			Object[] args) {
		if (rows != null) {
			if (rows.size() == args.length) {
				for (int i = 0; i < args.length; i++) {
					RowObject row = rows.get(i);
					row.put(name, args[i]);
					// rows.add(row);
				}
			}
			return rows;
		} else {
			return null;
		}
		// List<RowObject> rows=new ArrayList<RowObject>();

	}


	/**
	 * 获取RowObject层级数据,比如aa对象下有key为bb的字段,则返回row下的aa的RowObject对象中的key为bb的值
	 * 
	 * @param row
	 * @param args
	 * @return
	 */
	public static String getlayerData(RowObject row, String[] args) {
		String value = null;
		if (args.length < 2) {
			return value;
		} else {
			//避免引用影响数据源
			RowObject rowNew = new RowObject();
			rowNew.putAll(row);
			for (int i = 0; i < args.length; i++) {
				if (rowNew.get(args[i]) != null) {
					if (i == args.length - 1) {
						value = rowNew.getString(args[i]);
					} else {
						Object obj = rowNew.get(args[i]);
						if(obj!=null&&obj instanceof RowObject){
							rowNew.putAll( (RowObject) obj);
						} else {
							i = args.length;
						}
					}
				} else {
					i = args.length;
				}
			}
			return value;
		}
	}


	/**
	 * 实体类转RowObject
	 * @param obj
	 * @return
     */
	public static RowObject entityToRow(Object obj) {
		String json = JSONSerializer.toJson(obj);
		return JSONSerializer.getRow(json);
	}





}
