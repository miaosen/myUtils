package com.myutils.core.okhttp.callback;

import com.myutils.base.L;
import com.myutils.core.okhttp.HandlerQueue;
import com.myutils.ui.dialog.LoadingDialog;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/9 16:30
 * @Descrition 回调适配器 扩展okhttp3 的Callback接口
 */
public abstract class CallBackAdapter implements Callback {

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
                onFail(call,e);
            }
        });

    }

    /**
     * Ui线程
     * @param call
     * @param e
     */
    protected void onFail(Call call, IOException e) {

    }

    public abstract void setLoadingDialog(LoadingDialog loadingDialog);


}
