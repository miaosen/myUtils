package com.gzpykj.vtch.base;

import com.myutils.base.BaseAppContext;
import com.myutils.core.logger.L;

/**
 * Created by OAIM on 2016/7/28.
 */
public class AppContext extends BaseAppContext {

    @Override
    public void onCreate() {
        super.onCreate();

        L.init("logtag");
    }
}
