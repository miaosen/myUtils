package com.gzpykj.vtch.base;

import com.myutils.base.AppConfig;
import com.myutils.base.AppFactory;
import com.myutils.core.http.UrlInvoker;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/19 15:34
 * @Descrition
 */ 
public class Global {

    private static AppConfig appConfig = AppFactory.getAppConfig();

    /**
     * 创建http异步请求
     * @param actionClass
     * @return
     */
    public static UrlInvoker creatActionInvorker(String actionClass,Object actionName){
        String url=getProjectPath()+appConfig.getACTION_PREFIX()+"/"+actionClass+"/"+actionName;
        UrlInvoker ai=new UrlInvoker(url);
        return ai;
    }


    /**
     * 创建http异步请求
     * @return
     */
    public static String getProjectPath(){
        String url="http://"+appConfig.getHOST()+":"+appConfig.getPORT()+"/"+appConfig.getCONTEXT_PATH()+"/";
        return url;
    }
}
