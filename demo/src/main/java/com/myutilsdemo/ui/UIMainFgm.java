package com.myutilsdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myutils.core.annotation.ViewInject;
import com.myutils.utils.IntentUtils;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseFgm;
import com.myutilsdemo.core.form.FillAct;
import com.myutilsdemo.core.okhttp.OkhttpAct;
import com.myutilsdemo.ui.dlg.DialogAct;
import com.myutilsdemo.ui.filladp.BaseApdaterAct;
import com.myutilsdemo.ui.view.rcview.RcViewAct;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/1 16:17
 * @Descrition 核心示例主页
 */
public class UIMainFgm extends BaseFgm {

    @ViewInject
    Button dialog,lv,rcv;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.ui_main_fgm;
    }

    @Override
    public void initView(View view) {
        dialog.setOnClickListener(this);
        lv.setOnClickListener(this);
        rcv.setOnClickListener(this);
    }

    @Override
    public void click(View v) {
        if(v==dialog){
            IntentUtils.jump(getContext(), DialogAct.class);
        }else if(v==lv){
            IntentUtils.jump(getContext(), BaseApdaterAct.class);
        }else if(v==rcv){
            IntentUtils.jump(getContext(), RcViewAct.class);
        }
    }

    @Override
    public void initData() {

    }
}
