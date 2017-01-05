package com.myutils.core.okhttp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.myutils.core.ActionResult;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2017-01-04  14:34
 * @Descrition
 */

public class DataOperation extends View {



    String url;

    //获取数据成功后提示
    String successMsg;
    //获取数据失败后提示
    String failMsg;

    ActionResult result;

    public DataOperation(Context context) {
        super(context);
    }

    public DataOperation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setVisibility(View.GONE);



    }
}
