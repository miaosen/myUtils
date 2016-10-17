package com.myutils.core.form;

import android.view.View;
import android.widget.TextView;

import com.myutils.core.logger.L;

import java.util.LinkedList;
import java.util.List;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/7 11:28
 * @Descrition View过滤器
 */
public class ViewFilter {


    /**
     * 不需要填充的View类型
     */
    private List<Class> disableType = null;

    private List<String> disableIdName = null;

    public ViewFilter(){
        disableType = new LinkedList<Class>();
        //TextView一般不需要获取
        setDisableType(TextView.class);
        disableIdName =new LinkedList<>();
    }


    /**
     * 是否包含过滤的类型
     * @param idName
     * @param view
     * @return
     */
    public boolean isContain(String idName, View view){
        if(disableType.contains(view.getClass())||disableIdName.contains(idName)){
            return false;
        }else {
            return true;
        }
    }



    /**
     * 设置不需要获取的view
     * @param disableType
     */
    public void setDisableType(Class... disableType) {
        for (int i = 0; i < disableType.length; i++) {
            this.disableType.add(disableType[i]);
        }
    }


    public void setDisableType(List<Class> disableType) {
        if (this.disableType == null) {
            this.disableType = new LinkedList<Class>();
        }
        this.disableType.addAll(disableType);
    }

    /**
     * 设置不需要获取的view
     * @param idName
     */
    public void enableIdName(String idName){
        disableIdName.add(idName);
    }


}
