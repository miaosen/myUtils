package com.myutils.core;

import com.myutils.core.RowObject;

import java.util.List;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2017-01-09  10:48
 * @Descrition 返回的json数据处理接口
 */

public interface JSONResult
{
    /**
     * 转成RowObject
     * @return
     */
     RowObject getAsRow();

     /**
      * 转成ListRowObject
      * @return
      */
     List<RowObject> getAsRows();

    /**
     * 是否获取成功
     * @return
     */
     boolean isSuccess();

    /**
     * 主数据
     * @return
     */
    Object getMainData();


    String getMessage();


}
