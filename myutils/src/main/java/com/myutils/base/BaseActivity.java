package com.myutils.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.myutils.core.annotation.InjectReader;
import com.myutils.utils.ViewUtils;

@SuppressLint("NewApi")
public abstract class BaseActivity extends
        AppCompatActivity implements OnClickListener {

    /**
     * 页面是否加载完成
     */
    public boolean loadComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initConfig(savedInstanceState));
        InjectReader.injectAllFields(this);
        initView(getWindow().getDecorView());
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !loadComplete) {
            loadComplete = true;
            initData();
        }
    }

    /**
     * 初始化配置信息,
     */
    public abstract int initConfig(Bundle savedInstanceState);

    /**
     * 注解读取View后执行
     * @param decorView
     */
    public abstract void initView(View decorView);

    /**
     * 获取数据操作或者一些耗时操作，比如获取网络数据，
     * 如果获取网络数据方法放在onCreate里的话，数据获取完成页面才会加载，所以要分离出来
     */
    public abstract void initData();


    @Override
    public void onClick(View v) {
        click(v);
    }

    /**
     * 点击事件监听
     *
     * @param v
     */
    public abstract void click(View v);


}
