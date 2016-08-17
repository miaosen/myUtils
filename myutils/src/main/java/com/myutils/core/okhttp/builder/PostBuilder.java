package com.myutils.core.okhttp.builder;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by OAIM on 2016/8/6.
 */
public class PostBuilder extends BaseBuilder{


    public PostBuilder(String url) {
        super(url);
    }

    @Override
    public Request onRequestSet() {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (paramMap == null || paramMap.size() == 0) {
            request = new Request.Builder().url(url).build();
        } else {
            for (String key : paramMap.keySet()) {
                formBodyBuilder.add(key.toString(), paramMap.get(key)
                        + "");
            }
            RequestBody requestBody = formBodyBuilder.build();
            request = new Request.Builder().url(url).post(requestBody)
                    .build();
        }
        return request;
    }

}
