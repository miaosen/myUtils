package com.myutilsdemo.core;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myutils.core.annotation.ViewInject;
import com.myutils.utils.IntentUtils;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseFgm;
import com.myutilsdemo.core.form.FillAct;
import com.myutilsdemo.core.http.OkhttpAct;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/1 16:17
 * @Descrition 核心示例主页
 */
public class CoreMainFgm extends BaseFgm {

    @ViewInject
    Button http,form;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.core_main_fgm;
    }

    @Override
    public void initView(View view) {
        http.setOnClickListener(this);
        form.setOnClickListener(this);
    }

    @Override
    public void click(View v) {
        if(v==http){
            IntentUtils.jump(getContext(), OkhttpAct.class);
        }else if(v==form){
            IntentUtils.jump(getContext(), FillAct.class);
        }
    }

    @Override
    public void initData() {

    }
}
