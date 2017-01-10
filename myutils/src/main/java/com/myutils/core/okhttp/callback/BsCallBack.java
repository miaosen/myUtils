package com.myutils.core.okhttp.callback;

import com.myutils.base.L;
import com.myutils.core.JSONResult;
import com.myutils.core.okhttp.HandlerQueue;
import com.myutils.ui.dialog.LoadingDialog;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/9 16:30
 * @Descrition 数据回调适配器，扩展获取网络和本地结果回调接口
 */
public abstract class BsCallBack implements Callback {

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (response.isSuccessful()) {
            final String result = response.body().string();
            HandlerQueue.onResultCallBack(new Runnable() {
                @Override
                public void run() {
                    onSuccess(new ActionResult(result));
                }
            });
        }
    }

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

    public abstract void onSuccess(JSONResult result);


}
