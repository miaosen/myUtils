package com.myutils.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myutils.core.annotation.InjectReader;

/**
 * Created by OAIM on 2016/8/25.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

   private View view;

    boolean isFirstLoad=true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(initConfig(savedInstanceState),container,false);
        InjectReader.injectAllFields(this,view);
        initView(view);
        isFirstLoad=true;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&isFirstLoad) {
            //相当于Fragment的onResume
            initData();
            isFirstLoad=false;
        } else {
            //相当于Fragment的onPause
        }
    }

    public abstract int initConfig(Bundle savedInstanceState);

    public abstract void initView(View view);

    /**
     * 点击事件监听
     *
     * @param v
     */
    public abstract void click(View v);


    /**
     * 获取数据操作或者一些耗时操作，比如获取网络数据，
     * 如果获取网络数据方法放在onCreate里的话，数据获取完成页面才会加载，所以要分离出来
     */
    public abstract void initData();

    @Override
    public void onClick(View v) {
        click(v);
    }

}
