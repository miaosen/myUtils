package com.gzpykj.vtch.base;

import com.myutils.base.BaseActivity;
import com.myutils.ui.view.header.BaseHeader;

/**
 * Created by OAIM on 2016/8/6.
 */
public abstract class BaseAct extends BaseActivity{

    public BaseHeader baseHeader;

    public void initHeader(){
        baseHeader=new BaseHeader(this);

    }




}
