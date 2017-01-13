package com.myutils.core.http.builder;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by OAIM on 2016/8/6.
 */
public class PostBuilder extends BaseBuilder {


    public PostBuilder(String url) {
        super(url);
    }

    @Override
    public Request onRequestSet() {
        //L.i("=========onRequestSet==============");
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (paramMap != null ) {
            for (String key : paramMap.keySet()) {
                formBodyBuilder.add(key.toString(), paramMap.get(key)
                        + "");
            }
        }
        RequestBody requestBody = formBodyBuilder.build();
        request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .build();
        return request;
    }

}
