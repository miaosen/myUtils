package com.myutils.core.okhttp.callback;


import com.myutils.core.ActionResult;
import com.myutils.core.okhttp.HandlerQueue;
import com.myutils.ui.dialog.LoadingDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @CreateDate 2016/8/7 20:01
 * @Descrition 文本回调
 */
public abstract class StringCallBack extends CallBackAdapter {


    private LoadingDialog loadingDialog;


    public StringCallBack() {

    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
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

    public abstract void onSuccess(ActionResult result);

    @Override
    public void setLoadingDialog(LoadingDialog loadingDialog) {
        this.loadingDialog = loadingDialog;
    }
}
