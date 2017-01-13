package com.myutils.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.annotation.SuppressLint;

import com.myutils.utils.AppUtils;
import com.myutils.utils.FileUtils;


/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/11 12:06
 * @Descrition
 */
@SuppressLint("NewApi")
public class AppConfig {

	private static final String TAG="AppConfig";

	/**
	 * 服务器地址
	 */
	private String HOST;
	/**
	 * 端口号
	 */
	private int PORT;
	/**
	 * 项目名称
	 */
	private String CONTEXT_PATH;
	/**
	 * action名称
	 */
	private String ACTION_PREFIX;
	/**
	 * 版本号
	 */
	private String VERSION;
	/**
	 * 数据库名称
	 */
	private String DB_NAME;
	/**
	 * 数据库路径
	 */
	private String DB_PATH;
	/**
	 * 数据库版本号
	 */
	private String DB_VERSION;
	/**
	 * 是否打印
	 */
	private String LOGGER;
	/**
	 * 打印tag
	 */
	private String LOG_TAG="true";
	/**
	 * 是否保持cookie
	 */
	private String KEEP_COOKIE;

	/**
	 * 存放应用相关文件的跟目录
	 */
	private String APP_DIR_PATH;

	/**
	 * 此字段判断此类是否被回收
	 */
	private static String isAlive=null;

	/**
	 * 单例模式
	 */
	private static AppConfig instance=null;

	private AppConfig() {
		init();
	}

	//TODO 效果待验证
	public static AppConfig getInstance() {
		if(instance==null){
			instance= new AppConfig();
			L.i(TAG+"================AppConfig为空===================");
		}else if(isAlive==null){
			instance= new AppConfig();
			L.i(TAG+"================AppConfig被回收===================");
		}
		return instance;
	}

	private void init() {
        InputStream cfgFile = getCfgFile();
        if(cfgFile!=null){
            try {
                Properties p = new Properties();
                p.load(cfgFile);
                isAlive="true";
                HOST = p.getProperty("host");
                PORT = Integer.valueOf(p.getProperty("port"));
                CONTEXT_PATH = p.getProperty("contextPath");
                ACTION_PREFIX = p.getProperty("actionPrefix");
                VERSION = p.getProperty("version");
                LOG_TAG = p.getProperty("logTag");
                DB_PATH = p.getProperty("dbPath");
                DB_NAME = p.getProperty("db");
                LOGGER = p.getProperty("log");
                //ROWOBJECT_MATCH_CASE = p.getProperty("rowObjectMatchCase");
                KEEP_COOKIE = p.getProperty("keepCookies");
                APP_DIR_PATH = p.getProperty("appDirPath");

            } catch (IOException e) {
                e.printStackTrace();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public static String getHttpRootPath(){
		String url = "http://" +instance. getHOST() + ":"+instance. getPORT() +
				"/" + instance. getCONTEXT_PATH() +
				"/" + instance. getACTION_PREFIX();
		return url;
	}

    public InputStream getCfgFile() {
        InputStream resourceAsStream = AppFactory.getAppContext().getClass().getClassLoader().getResourceAsStream("assets/appConfig.properties");
        return resourceAsStream;
    }

    public String getHOST() {
		return HOST;
	}

	public int getPORT() {
		return PORT;
	}

	public String getCONTEXT_PATH() {
		return CONTEXT_PATH;
	}

	public String getACTION_PREFIX() {
		return ACTION_PREFIX;
	}

	public String getVERSION() {
		return VERSION;
	}

	public String getDB_NAME() {
		return DB_NAME;
	}

	public String getDB_PATH() {
		return DB_PATH;
	}

	public String getDB_VERSION() {
		return DB_VERSION;
	}

	public String getLOGGER() {
		return LOGGER;
	}

	public String getLOG_TAG() {
		return LOG_TAG;
	}

	public String getKEEP_COOKIE() {
		return KEEP_COOKIE;
	}

//	public String getROWOBJECT_MATCH_CASE() {
//		return ROWOBJECT_MATCH_CASE;
//	}

	public void setHOST(String hOST) {
		HOST = hOST;
	}

	public void setPORT(int pORT) {
		PORT = pORT;
	}

	public void setCONTEXT_PATH(String cONTEXT_PATH) {
		CONTEXT_PATH = cONTEXT_PATH;
	}

	public void setACTION_PREFIX(String aCTION_PREFIX) {
		ACTION_PREFIX = aCTION_PREFIX;
	}

	public void setVERSION(String vERSION) {
		VERSION = vERSION;
	}

	public void setDB_NAME(String dB_NAME) {
		DB_NAME = dB_NAME;
	}

	public void setDB_PATH(String dB_PATH) {
		DB_PATH = dB_PATH;
	}

	public void setDB_VERSION(String dB_VERSION) {
		DB_VERSION = dB_VERSION;
	}

	public void setLOGGER(String lOGGER) {
		LOGGER = lOGGER;
	}

	public void setLOG_TAG(String lOG_TAG) {
		LOG_TAG = lOG_TAG;
	}

	public void setKEEP_COOKIE(String kEEP_COOKIE) {
		KEEP_COOKIE = kEEP_COOKIE;
	}

	public String getAPP_DIR_PATH() {
		if(APP_DIR_PATH!=null){
			return FileUtils.getSDCardPath()+APP_DIR_PATH;
		}else{
			return FileUtils.getSDCardPath()+ AppUtils.getAppName();
		}
	}

	public void setAPP_DIR_PATH(String aPP_DIR_PATH) {
		APP_DIR_PATH = aPP_DIR_PATH;
	}

//	public void setROWOBJECT_MATCH_CASE(String rOWOBJECT_MATCH_CASE) {
//		ROWOBJECT_MATCH_CASE = rOWOBJECT_MATCH_CASE;
//	}



}
