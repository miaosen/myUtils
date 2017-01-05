package com.gzpykj.vtch.base;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.myutils.base.BaseAppContext;
import com.myutils.base.L;

/**
 * Created by OAIM on 2016/7/28.
 */
public class AppContext extends BaseAppContext {

    @Override
    public void onCreate() {
        super.onCreate();
        L.setIsLog(true);
        //Logger.init("logtag");
        Fresco.initialize(this);
    }
}
