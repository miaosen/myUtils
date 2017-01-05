package com.gzpykj.vtch.event;

import android.view.View;

import com.gzpykj.vtch.base.Global;
import com.myutils.core.okhttp.UrlInvoker;

/**
 * Created by OAIM on 2016/10/11.
 */
public class EventHealthListAct extends EventListAct {

    @Override
    public UrlInvoker setDataResourse() {
        UrlInvoker ai= Global.creatActionInvorker("vhActivityPlanAction","findList");
        ai.addParam("TYPENO","01");
        ai.addParam("STATUS","2");
        return ai;
    }



    @Override
    public void initView(View decorView) {
        super.initView(decorView);
        baseHeader.setTitle("健康知识讲座");
    }

}
