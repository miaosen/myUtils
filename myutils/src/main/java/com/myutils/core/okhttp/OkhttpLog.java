package com.myutils.core.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/9 16:52
 * @Descrition 日志打印
 */ 
public class OkhttpLog implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return response;
    }

    private void logForRequest(Request request) {
       //com.myutils.core.logger.Logger.d request.url();
    }
}
