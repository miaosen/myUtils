package com.myutils.core.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.myutils.base.L;
/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-12-30
 * @Descrition 数据库事务类，支持添加，修改，删除表格操作
 */
public class DbTransaction {

	public SQLiteDatabase db;

	public DbUtils dbUtils;

	public List<TablePty> tablePtys;
	// 事务操作对象，key为动作名称(添加、修改、删除),右边为数据
	private Map<String, List<TablePty>> mapTablePty = new HashMap<String, List<TablePty>>();

	private final String AC_INSERT = "insert";
	private final String AC_UPDATE = "update";
	private final String AC_DELETE = "delete";

	public List<TablePty> insertTablePtys;
	public List<TablePty> updateTablePtys;
	public List<TablePty> deleteTablePtys;

	public DbTransaction(DbUtils dbUtils) {
		this.dbUtils = dbUtils;
		this.db = dbUtils.getDataBase();
	}

	/**
	 * 添加数据事务
	 * 
	 * @return
	 */
	public boolean insert() {
		boolean success = true;
		if (tablePtys != null) {
			try {
				db.beginTransaction();
				for (int i = 0; i < tablePtys.size(); i++) {
					TablePty tablePty = tablePtys.get(i);
					if (tablePty.getValue() != null) {
						long insertSucces = dbUtils.addRow(tablePty.getName(),
								tablePty.getValue());
						if (insertSucces == -1) {
							success = false;
							i = tablePtys.size();
						}
					} else if (tablePty.getListVc() != null) {
						List<ContentValues> listVc = tablePty.getListVc();
						for (int j = 0; j < listVc.size(); j++) {
							long insertSucces = dbUtils.addRow(
									tablePty.getName(), listVc.get(j));
							if (insertSucces == -1) {
								j = listVc.size();
								success = false;
							}
						}
					} else {
						L.i("数据格式不对！");
						success = false;
					}
				}
				if (success) {
					db.setTransactionSuccessful();
				} else {
					L.i("本次所有数据保存不成功！事务回滚");
				}
			} catch (Exception e) {
				e.printStackTrace();
				success = false;
			} finally {
				db.endTransaction();
			}
		} else {
			success = false;
			L.i("无表格数据！");
		}
		return success;
	}

//	/**
//	 * 一个表对应一条数据
//	 * 
//	 * @param name
//	 * @param values
//	 */
//	public void addTableRow(String name, ContentValues values) {
//		if (tablePtys == null) {
//			tablePtys = new ArrayList<TablePty>();
//		}
//		tablePtys.add(new TablePty(name, values));
//	}
//
//	/**
//	 * 一个表对应多条数据
//	 * 
//	 * @param name
//	 * @param values
//	 */
//	public void addTableRows(String name, List<ContentValues> values) {
//		if (tablePtys == null) {
//			tablePtys = new ArrayList<TablePty>();
//		}
//		tablePtys.add(new TablePty(name, values));
//	}

	/**
	 * 在一张表添加一条数据
	 * @param tableName
	 * @param values
	 */
	public void addRow(String tableName, ContentValues values) {
		if (insertTablePtys == null) {
			insertTablePtys = new ArrayList<TablePty>();
		}
		insertTablePtys.add(new TablePty(tableName, values));
		mapTablePty.put(AC_INSERT, insertTablePtys);
	}

	/**
	 * 在一张表添加多条数据
	 * 
	 * @param name
	 * @param values
	 */
	public void addRows(String name, List<ContentValues> values) {
		if (insertTablePtys == null) {
			insertTablePtys = new ArrayList<TablePty>();
		}
		insertTablePtys.add(new TablePty(name, values));
		mapTablePty.put(AC_INSERT, insertTablePtys);
	}

	/**
	 * 更新一张表的一条数据
	 * 
	 * @param tableName
	 * @param values
	 * @param whereClause
	 *            条件
	 * @param whereArgs
	 *            值
	 */
	public void updateRow(String tableName, ContentValues values,
			String whereClause, String[] whereArgs) {
		if (updateTablePtys == null) {
			updateTablePtys = new ArrayList<TablePty>();
		}
		updateTablePtys.add(new TablePty(tableName, values, whereClause,
				whereArgs));
		mapTablePty.put(AC_UPDATE, updateTablePtys);
	}

	/**
	 * 更新一张表的多条数据
	 * 
	 * @param tableName
	 * @param values
	 * @param whereClause
	 *            条件
	 * @param whereArgs
	 *            值
	 */
	public void updateRows(String tableName, List<ContentValues> values,
			String whereClause, String[] whereArgs) {
		if (updateTablePtys == null) {
			updateTablePtys = new ArrayList<TablePty>();
		}
		updateTablePtys.add(new TablePty(tableName, values, whereClause,
				whereArgs));
		mapTablePty.put(AC_UPDATE, updateTablePtys);
	}

	/**
	 * 删除一张表的数据
	 * 
	 * @param tableName
	 *            表名
	 * @param whereClause
	 *            条件
	 * @param whereArgs
	 *            值
	 */
	public void deleteRow(String tableName, String whereClause,
			String[] whereArgs) {
		if (deleteTablePtys == null) {
			deleteTablePtys = new ArrayList<TablePty>();
		}
		deleteTablePtys.add(new TablePty(tableName, whereClause, whereArgs));
		mapTablePty.put(AC_DELETE, deleteTablePtys);
	}

	/**
	 * 事务提交
	 * 
	 * @return
	 */
	//TODO 顺序问题 执行顺序为删除、添加、修改，不能按添加顺序来处理
	public boolean commit() {
		boolean success = true;
		db.beginTransaction();
		try {
			if (mapTablePty.containsKey(AC_DELETE)) {
				if (success) {
					success = delete();
				}
			}
			if (mapTablePty.containsKey(AC_INSERT)) {
				if (success) {
				success = insertTable();
				}
			}
			if (mapTablePty.containsKey(AC_UPDATE)) {
				if (success) {
					success = updateTable();
				}
			}
			
			if (success) {
				db.setTransactionSuccessful();
			} else {
				L.i("本次数据提交不成功! 操作取消");
			}
		} catch (Exception e) {
			 e.printStackTrace();
			//L.e(e);
			success = false;
		} finally {
			db.endTransaction();
		}
		return success;

	}

	/**
	 * 添加数据操作
	 * 
	 * @return
	 */
	private boolean insertTable() {
		List<TablePty> insertPtys = mapTablePty.get(AC_INSERT);
		boolean success = true;
		if (insertPtys != null) {
			for (int i = 0; i < insertPtys.size(); i++) {
				TablePty tablePty = insertPtys.get(i);
				if (tablePty.getValue() != null) {
					long insertSucces = dbUtils.addRow(tablePty.getName(),
							tablePty.getValue());
					if (insertSucces == -1) {
						success = false;
						i = insertPtys.size();
					}
				} else if (tablePty.getListVc() != null) {
					List<ContentValues> listVc = tablePty.getListVc();
					for (int j = 0; j < listVc.size(); j++) {
						long insertSucces = dbUtils.addRow(tablePty.getName(),
								listVc.get(j));
						if (insertSucces == -1) {
							j = listVc.size();
							success = false;
						}
					}
				} else {
					L.i("添加数据操作没有表格数据！");
					success = false;
				}
			}

		} else {
			success = false;
			L.i("添加数据操作没有表格数据！");
		}
		return success;
	}

	/**
	 * 更新数据操作
	 * 
	 * @return
	 */
	private boolean updateTable() {
		List<TablePty> updatePtys = mapTablePty.get(AC_UPDATE);
		boolean success = true;
		if (updatePtys != null) {
			for (int i = 0; i < updatePtys.size(); i++) {
				TablePty tablePty = updatePtys.get(i);
				if (tablePty.getValue() != null) {
					long insertSucces = db.update(tablePty.getName(),
							tablePty.getValue(), tablePty.getWhereClause(),
							tablePty.getWhereArgs());
					if (insertSucces == -1) {
						success = false;
						i = updatePtys.size();
					}
				} else if (tablePty.getListVc() != null) {
					List<ContentValues> listVc = tablePty.getListVc();
					for (int j = 0; j < listVc.size(); j++) {
						long insertSucces = db.update(tablePty.getName(),
								listVc.get(j), tablePty.getWhereClause(),
								tablePty.getWhereArgs());
						if (insertSucces == -1) {
							j = listVc.size();
							success = false;
						}
					}
				} else {
					L.i("更新数据操作没有表格数据！");
					success = false;
				}
			}

		} else {
			success = false;
			L.i("更新数据操作没有表格数据！");
		}
		return success;
	}

	/**
	 * 删除数据操作
	 * 
	 * @return
	 */
	private boolean delete() {
		List<TablePty> deletePtys = mapTablePty.get(AC_DELETE);
		boolean success = true;
		if (deletePtys != null) {
			for (int i = 0; i < deletePtys.size(); i++) {
				TablePty tablePty = deletePtys.get(i);
				long insertSucces = db.delete(tablePty.getName(),
						tablePty.getWhereClause(), tablePty.getWhereArgs());
				if (insertSucces == -1) {
					success = false;
					i = deletePtys.size();
				}
			}
		} else {
			success = false;
			L.i("删除数据操作没有表格数据！");
		}
		return success;
	}

}
