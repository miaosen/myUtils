package com.myutils.core.okhttp;

import android.text.TextUtils;
import android.util.Log;

import com.myutils.core.logger.L;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @CreateDate 2016/8/9 16:52
 * @Descrition 日志打印
 */
public class OkhttpLog implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        Response clone = logForResponse(response);
        return clone;
    }


    protected void logForRequest(Request request) {
        L.i("完整地址===" + request.url().toString());
    }

    /**
     * 打印返回数据
     *
     * @param response
     */
    protected Response logForResponse(Response response) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    String string = body.string();
                    L.json("返回的数据===" + string);
                    body = ResponseBody.create(mediaType, string);
                    return response.newBuilder().body(body).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
