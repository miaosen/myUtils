package com.gzpykj.vtch.event;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gzpykj.vtch.R;
import com.gzpykj.vtch.base.BaseAct;
import com.myutils.core.RowObject;
import com.myutils.core.annotation.ViewInject;
import com.myutils.core.form.FillUnit;
import com.myutils.utils.IntentUtils;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/10/12 10:35
 * @Descrition 就诊活动详情
 */
public class EventDetailAct extends BaseAct {

    private RowObject rowInfo;

    @ViewInject
   Button btn_sure;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        rowInfo= IntentUtils.getRow(getIntent(),"rowInfo");
        return R.layout.event_detail_act;
    }

    @Override
    public void initView(View decorView) {
        btn_sure.setOnClickListener(this);
        initHeader();
        baseHeader.setTitle("活动详情");
        baseHeader.leftFinish();
        FillUnit fillUnit=new FillUnit(this);
        fillUnit.fill(rowInfo);
    }

    @Override
    public void initData() {

    }

    @Override
    public void click(View v) {
        if(v==btn_sure){
            finish();
        }
    }
}
