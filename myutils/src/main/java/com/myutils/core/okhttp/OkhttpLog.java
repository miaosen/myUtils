package com.myutils.core.okhttp;

import android.text.TextUtils;
import android.util.Log;

import com.myutils.core.RowObject;
import com.myutils.core.gson.JSONSerializer;
import com.myutils.core.logger.L;
import com.myutils.utils.JsonUtils;
import com.myutils.utils.RowUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
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
        String method = request.method();
        if("POST".equals(method)){
            RowObject rowbody = RowUtils.entityToRow(request.body());
            List<String> listEncodedNames = rowbody.getStringList("encodedNames");
            List<String> listEncodedValuess = rowbody.getStringList("encodedValues");
            String strParamUrl="";
            for (int i = 0; i <listEncodedNames.size() ; i++) {
                String encodedNames = listEncodedNames.get(i);
                String encodedValues = listEncodedValuess.get(i);
                if(i==0){
                    strParamUrl=strParamUrl+"?"+encodedNames+"="+encodedValues;
                }else{
                    strParamUrl=strParamUrl+"&"+encodedNames+"="+encodedValues;
                }
            }
            L.i("完整的"+request.method()+"请求地址===" + request.url().toString()+strParamUrl);
        }else{
            L.i("完整的"+request.method()+"请求地址===" + request.url().toString());
        }


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
                    //如果是Json，格式化显示
                    if(JsonUtils.isValidateJson(string)){
                        L.json("返回的数据===",string);
                    }else{
                        L.i("返回的数据===" + string);
                    }
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
