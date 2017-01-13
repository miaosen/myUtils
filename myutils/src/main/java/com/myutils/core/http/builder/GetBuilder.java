package com.myutils.core.http.builder;

import com.myutils.base.L;

import okhttp3.Request;

/**
 * get请求方式
 */
public class GetBuilder extends BaseBuilder {


    public GetBuilder(String url) {
        super(url);
    }

    @Override
    public Request onRequestSet() {
        if (paramMap == null || paramMap.size() == 0) {
            L.i("参数为空");
        } else {
            for (String key : paramMap.keySet()) {
                url = url + "&" + key.toString() + "="
                        + paramMap.get(key).toString();
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .build();
        return request;
    }

}
