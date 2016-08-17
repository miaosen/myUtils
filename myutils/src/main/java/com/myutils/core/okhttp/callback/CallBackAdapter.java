package com.myutils.core.okhttp.callback;

import com.myutils.ui.dialog.LoadingDialog;

import okhttp3.Callback;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/9 16:30
 * @Descrition 回调适配器 扩展okhttp3 的Callback接口
 */
public abstract class CallBackAdapter implements Callback {



    public abstract void setLoadingDialog(LoadingDialog loadingDialog);


}
