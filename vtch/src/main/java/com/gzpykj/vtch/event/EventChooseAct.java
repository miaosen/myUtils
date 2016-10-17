package com.gzpykj.vtch.event;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gzpykj.vtch.R;
import com.gzpykj.vtch.base.BaseAct;
import com.myutils.core.annotation.ViewInject;
import com.myutils.utils.IntentUtils;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/22 11:19
 * @Descrition 预约就诊
 */
public class EventChooseAct extends BaseAct {

    @ViewInject
    LinearLayout ln_illness,ln_doctor;


    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.event_choose_act;
    }

    @Override
    public void initView(View decorView) {
        initHeader();
        baseHeader.leftFinish();
        baseHeader.setTitle("预约就诊");
        ln_illness.setOnClickListener(this);
        ln_doctor.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void click(View v) {
        if(v==ln_illness){
            IntentUtils.jump(EventChooseAct.this,EventIllnessAct.class);
        }else if(v==ln_doctor){
            IntentUtils.jump(EventChooseAct.this,EventDoctorAct.class);
        }
    }
}
