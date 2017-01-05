package com.myutils.core.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myutils.base.L;

import java.util.Map;
/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-12-30
 * @Descrition 本地数据库操作工具类
 */

//TODO 使用Application的context替换activity的会更好
public class DbUtils {

	public DbOpenHelp dbHelp;
	public SQLiteDatabase db;
//	public Context context = null;
	private final static String TAG = "DbUtils";
	private String dbName;
	private String dbPath;
	private int visition;

	/**
	 * 默认数据库工具，使用src下的配置文件进行创建数据库连接
	 * 
	 */
//	public DbUtils() {
//		AppConfig appConfig = AppFactory.getAppConfig();
////		this.context = context;
//		this.dbName = appConfig.getDB_NAME();
//		this.dbPath = appConfig.getDB_PATH();
//		this.visition = Integer.parseInt(appConfig.getDB_VERSION());
//		getDbOpenHelp();
//		getDataBase();
//	}

	/**
	 * 配置式数据库工具，使用传进来的参数创建数据库连接
	 * 
	 */
	public DbUtils(String dbName, String dbPath, int visition) {
		this.dbName = dbName;
		this.dbPath = dbPath;
		this.visition = visition;
		getDbOpenHelp();
		getDataBase();
	}

	/**
	 * 获取 SQLiteOpenHelper
	 */
	public DbOpenHelp getDbOpenHelp() {
		dbHelp = DbOpenHelp.getInstance(dbName, dbPath, visition);
		return dbHelp;
	}

	/**
	 * 获取 SQLiteDatabase
	 */
	public SQLiteDatabase getDataBase() {
		db = getDbOpenHelp().getReadableDatabase();
		return db;
	}

	/**
	 * 表格数据添加
	 * 
	 * @param tableName
	 *            表名
	 * @param values
	 *            数据
	 */
	public long addRow(String tableName, ContentValues values) {
		long success = 1;
		// 插入数据库中
		if (isExitsTable(tableName)) {
			success = db.insert(tableName, null, values);
		} else {
			success = -1;
		}
		if (success == -1) {
			L.i("表 " + tableName + " 数据添加失败!");
		}
		return success;
	}

	/**
	 * 查询所有数据
	 * 
	 * @param tableName
	 *            要查询的表
	 * @return Cursor资源集合
	 */
	public Cursor query(String tableName) {
		// 获取Cursor
		Cursor c = db
				.query(tableName, null, null, null, null, null, null, null);
		return c;
	}

	/**
	 * 分页查询数据
	 * 
	 * @param tableName
	 *            表名
	 * @param pageIndex
	 *            开始条数
	 * @param pageNum
	 *            条数
	 */
	public Cursor PageQuery(String tableName, String pageIndex, String pageNum) {
		int index = Integer.parseInt(pageIndex) - 1;
		Integer.parseInt(pageNum);
		// 获取Cursor
		String sql = "Select * From " + tableName + " Limit " + pageNum
				+ " Offset " + index;
		Log.i(TAG, sql);
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 */
	public void execSQL(String sql) {
		try {
			db.execSQL(sql);
			//Toast.makeText(context, "执行成功！", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * map数据插入(map转ContentValues)
	 * 
	 * @param tableName
	 * @param map
	 */
	public long insertMapToTable(String tableName, Map<String, Object> map) {
		ContentValues values = new ContentValues();
		if (map != null) {
			for (String key : map.keySet()) {
				values.put(key, map.get(key) + "");
			}
		} else {
			//Toast.makeText(context, "map参数为空！", Toast.LENGTH_SHORT).show();
		}
		long success = addRow(tableName, values);
		return success;
	}

	/**
	 * 
	 * @param tableName
	 * @param term
	 *            key为字段名，value为表名
	 * @param values
	 * @return
	 */
	public Cursor findByParam(String tableName, Map<String, String> term,
			String[] values) {
		String sql="select * from " + tableName ;
		if (term == null) {
			L.i("参数为空!");
		} else {
			String strTerm = " where ";
			int index = 0;
			int count = term.size();
			for (String key : term.keySet()) {
				index = index + 1;
				if (index == count) {
					strTerm = strTerm + " " + key + " " + term.get(key) + "=?";
				} else {
					strTerm = strTerm + " " + key + " " + term.get(key) + "=?"
							+ " and ";
				}
			}
			sql = sql+ strTerm;
			L.i("findByParam sql===" + sql);
		}
		Cursor cursor = db.rawQuery(sql, values);
		return cursor;
	}

	/**
	 * 根据多个字段查找数据库
	 * 
	 * @param tableName
	 *            表名
	 * @param rowName
	 *            字段名称
	 * @param values
	 *            字段对应的值
	 * @return
	 */
	// public Cursor findByParams(String tableName, Map<String, Object> map) {
	// Cursor cursor=null;
	// if (map == null || map.size() == 0) {
	// } else {
	//
	// for (String key : map.keySet()) {
	// String rowName = map.keySet() + "";
	// String values = map.get(key) + "";
	//
	// }
	// cursor = db.rawQuery("select * from " + tableName
	// + " where " + rowName + "=?", new String[] { values });
	// return cursor;
	// }
	// return null;
	// }

	/**
	 * 判断表格是否存在
	 * 
	 * @param tableName
	 *            表名
	 * @return 存在则返回true
	 */
	public boolean isExitsTable(String tableName) {
		boolean exits = false;
		String sql = "SELECT COUNT(*) FROM sqlite_master where type='table' and name='"
				+ tableName + "'";
		// Logger.info(sql);
		// 获取SQLiteDatabase实例
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToNext()) {
			if (cursor.getInt(0) != 0) {
				exits = true;
			} else {
//				Toast.makeText(context, tableName + "表格不存在！", Toast.LENGTH_SHORT)
//						.show();
			}
		}
		return exits;
	}

	

	/**
	 * 创建事务
	 * 
	 * @return
	 */
	public DbTransaction createTrastation() {
		DbTransaction dbTransaction = new DbTransaction(this);
		return dbTransaction;
	}

	/**
	 * 异步查询数据
	 * @return
	 */
	public AsyncFindDb createAsyncFindDb() {
		AsyncFindDb asyncFindDb = new AsyncFindDb(this);
		return asyncFindDb;
	}

}
