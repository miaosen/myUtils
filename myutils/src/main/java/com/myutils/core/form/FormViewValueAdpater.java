package com.myutils.core.form;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2017-01-04  16:42
 * @Descrition View的值填充和获取适配自定义View,自定义View实现此接口可以被表单工具扫描到
 *
 */

public interface FormViewValueAdpater {
    /**
     * 数据填充时调用
     * @param object
     */
    void setValue(Object object);

    /**
     * 数据扫描时调用
     * @return
     */
    Object getValue();

}
