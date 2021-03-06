package com.myutils.core.http;

import com.myutils.base.L;
import com.myutils.core.ResultCallBack;
import com.myutils.core.http.builder.GetBuilder;
import com.myutils.core.http.builder.PostBuilder;
import com.myutils.core.http.builder.RequestBuilder;
import com.myutils.ui.T;
import com.myutils.utils.AppUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * @author zms
 * @Created by gzpykj.com
 * @Date 2016-5-24
 * @Descrition 异步获取网络数据
 */
public class UrlInvoker {


    private OkHttpClient client;

    //请求地址
    private String url;

    //请求参数
    private Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
    ;
    //请求体
    private RequestBuilder responseBuilder = null;
    // 默认表单提交方式,决定ResponseBuilder的类型
    private String way = RESQUESTMODE.POST;

    public interface RESQUESTMODE {
        String POST = "post";
        String GET = "get";
    }

    ;


    //回调
    private ResultCallBack callback;

    //打印参数时定位项目调用此次请求的代码位置
    private int logLocationIndex = 2;


    public UrlInvoker(String url) {
        this.url = url;
        client = ClientFactory.getInstance();
    }


    /**
     * 调用请求网络线程
     */
    public void invoke() {
        if (!AppUtils.isConnected()) {
            T.show("网络未连接,请连接网络!");
        } else {
            if (way.equals("post")) {
                responseBuilder = new PostBuilder(url);
            } else {// get方式
                responseBuilder = new GetBuilder(url);
            }
            responseBuilder.addParam(paramMap);
            if (callback != null) {
                Call call = client.newCall(responseBuilder.getRequest());
                call.enqueue(callback);
                logParam();
            }
        }
    }

    /**
     * 打印参数
     */
    private void logParam() {
        if (paramMap.size() > 0) {
            for (String key : paramMap.keySet())
                L.i("key===" + key + "    value===" + paramMap.get(key), logLocationIndex);
        } else {
            L.i("没有参数", logLocationIndex);
        }
    }

    /**
     * 添加参数
     *
     * @param key
     * @param value
     */
    public void addParam(String key, Object value) {
        paramMap.put(key, value);
    }

    /**
     * 添加参数
     *
     * @param map
     */
    public void addParam(Map<String, Object> map) {
        paramMap.putAll(map);
    }

    /**
     * 获取参数
     *
     * @return
     */
    public Map<String, Object> getMap() {
        return paramMap;
    }


    /**
     * 请求方式为get
     */
    public void getMode() {
        way = RESQUESTMODE.GET;
    }

    /**
     * 请求方式为post
     */
    public void postMode() {
        way = RESQUESTMODE.POST;
    }


    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public RequestBuilder getResponseBuilder() {
        return responseBuilder;
    }

    public void setResponseBuilder(RequestBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }


    public ResultCallBack getCallback() {
        return callback;
    }

    public void setCallback(ResultCallBack callback) {
        this.callback = callback;
    }


    public int getLogLocationIndex() {
        return logLocationIndex;
    }

    public void setLogLocationIndex(int logLocationIndex) {
        this.logLocationIndex = logLocationIndex;
    }
}
