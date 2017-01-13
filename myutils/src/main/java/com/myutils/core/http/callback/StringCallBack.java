package com.myutils.core.http.callback;


import com.myutils.core.ResultCallBack;
import com.myutils.core.http.HandlerQueue;

import java.io.IOException;

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
