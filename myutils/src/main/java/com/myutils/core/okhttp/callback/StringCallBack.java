package com.myutils.core.okhttp.callback;


import com.myutils.base.L;
import com.myutils.core.JSONResult;
import com.myutils.core.ResultCallBack;
import com.myutils.core.okhttp.HandlerQueue;
import com.myutils.ui.dialog.LoadingDialog;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @CreateDate 2016/8/7 20:01
 * @Descrition 文本回调
 */
public abstract class StringCallBack extends ResultCallBack {



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

}
