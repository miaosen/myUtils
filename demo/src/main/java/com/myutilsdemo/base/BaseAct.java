package com.myutilsdemo.base;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import com.myutils.base.BaseActivity;

/**
 * Created by OAIM on 2016/8/6.
 */
public abstract class BaseAct extends BaseActivity{


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(com.myutils.R.color.blue));
        }
    }

}
