package com.myutils.core.okhttp.builder;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/6 15:09
 * @Descrition http基础请求体构建器，要求传入Url和参数，构建出请求体
 */
public abstract class BaseBuilder implements RequestBuilder {

    /**
     * 地址
     */
    protected String url = null;
    /**
     * 参数
     */
    protected Map<String, Object> paramMap= new LinkedHashMap<String, Object>();
    /**
     * 请求体
     */
    protected Request request = null;


    public BaseBuilder(String url){
        this.url=url;
    }


    @Override
    public Request getRequest() {
        return onRequestSet();
    }

    public abstract Request onRequestSet() ;

    @Override
    public Map<String, Object> addParam(Map<String, Object> param) {
        paramMap.putAll(param);
        return paramMap;

    }

    @Override
    public Map<String, Object> addParam(String key, Object value) {
        paramMap.put(key,value);
        return paramMap;
    }




}
