package com.myutils.core;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-3-4
 * @Descrition 键值对(key-value)实体类, 注意 : RowObject在json序列化时重写了RowObject的序列化方法，
 *             value的取值只有3种String,RowObject,LinkedList<RowObject>
 */
public class RowObject extends LinkedHashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = 7561153740565098648L;

	public RowObject getRow(String key) {
		RowObject row = (RowObject) get(key);
		if (row == null) {
			return null;
		}
		return row;
	}

	public LinkedList<RowObject> getRows(String key) {
		LinkedList<RowObject> rows = (LinkedList<RowObject>) get(key);
		return rows;
	}

	public List<String> getStringList(String key) {
		LinkedList<String> list = (LinkedList<String>) get(key);
		return list;
	}

	public String getString(String key) {
		Object obj = get(key);
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	public Integer getInteger(String key) {
		Object obj = get(key);
		if (obj == null) {
			return null;
		}
		if (obj instanceof String) {
			return Double.valueOf((String) obj).intValue();
		}
		return (Integer) obj;
	}

}
