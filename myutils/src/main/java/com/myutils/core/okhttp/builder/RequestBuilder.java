package com.myutils.core.okhttp.builder;

import java.util.Map;

import okhttp3.Request;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/6 16:20
 * @Descrition 请求体构建器，自定义请求体需要实现此接口
 */
public interface RequestBuilder {

    /**
     * 请求体
     * @return
     */
    Request getRequest();
    /**
     * 参数
     * @return
     */
    Map<String, Object> addParam(Map<String, Object> param);
    /**
     * 参数
     * @return
     */
    Map<String, Object> addParam(String key, Object value);

    //TODO文件上传
    //Map<String, Object> addFile(String name, Object value);

}
