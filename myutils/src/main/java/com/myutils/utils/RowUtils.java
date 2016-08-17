package com.myutils.utils;

import java.util.ArrayList;
import java.util.List;

import com.myutils.core.logger.L;
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
	 * 获取RowObject深层数据,比如args为["aa","bb"],则返回row下的aaRowObject对象中的key为bb的值
	 * 
	 * @param row
	 * @param args
	 * @return
	 */
	public static Object getlayerData(RowObject row, String[] args) {
		Object value = null;
		if (args.length < 2) {
			return value;
		} else {
			RowObject rowNew = new RowObject();
			rowNew.putAll(row);
			for (int i = 0; i < args.length; i++) {
				if (rowNew.get(args[i]) != null) {
					if (i == args.length - 1) {
						value = rowNew.get(args[i]);
					} else {
						L.i("i==========="+i);
						if(rowNew.get(args[i]) instanceof RowObject){
							rowNew = rowNew.getRow(args[i]);
						}
					}
				} else {
					i = args.length;
				}
			}
			return value;
		}
	}

}
