package com.gzpykj.vtch.base;

import android.view.View;

import com.myutils.ui.view.header.BaseHeader;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition 就诊活动选择
 */
public abstract class PagingRcListAct extends com.myutils.ui.view.rcview.PagingRcListAct {


    public BaseHeader baseHeader;

    public void initHeader(){
        baseHeader=new BaseHeader(this);

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        initHeader();
        baseHeader.leftFinish();
    }


}
