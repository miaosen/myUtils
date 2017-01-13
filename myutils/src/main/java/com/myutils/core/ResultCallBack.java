package com.myutils.core;

import com.myutils.base.L;
import com.myutils.core.http.HandlerQueue;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2017-01-09  12:11
 * @Descrition
 */

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/9 16:30
 * @Descrition 数据回调适配器，扩展获取网络和本地结果回调接口
 */
public abstract class ResultCallBack implements Callback {



    @Override
    public void onFailure(final Call call, final IOException e) {
        if(e instanceof SocketTimeoutException){
            L.e("连接超时! ",e);
        }else if(e instanceof ConnectException){
            L.e("连接失败! ",e);
        }else {
            L.e("出错了! ",e);
        }
        HandlerQueue.onResultCallBack(new Runnable() {
            @Override
            public void run() {
                onFail(e);
            }
        });

    }

    /**
     * Ui线程
     * @param e
     */
    protected void onFail(Exception e) {

    }

    public abstract void onSuccess(JSONResult result);


}
