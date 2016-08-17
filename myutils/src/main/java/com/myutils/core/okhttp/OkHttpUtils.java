package com.myutils.core.okhttp;

import okhttp3.OkHttpClient;

public class OkHttpUtils {

    // private OkHttpUtils okHttpUtils;

    private volatile static OkHttpClient mInstance = null;


    // 单例加线程锁
    public static OkHttpClient getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = ClientFactory.getOkHttp();
                }
            }
        }
        return mInstance;
    }


    /**
     * 清除cookies
     *
     * @return
     */
    public static void clearCookies() {
        ClientFactory.getCookiesManager().getCookieStore().removeAll();
    }


}
