package com.myutils.core.okhttp.builder;

import com.myutils.core.logger.L;

import okhttp3.Request;

/**
 * Created by OAIM on 2016/8/6.
 */
public class GetBuilder extends BaseBuilder{


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
        Request request = new Request.Builder().url(url).build();
        return request;
    }

}
