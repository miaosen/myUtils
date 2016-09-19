package com.myutils.core.form;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.myutils.core.RowObject;
import com.myutils.core.gson.JSONSerializer;
import com.myutils.core.logger.L;
import com.myutils.utils.JsonUtils;
import com.myutils.utils.RowUtils;
import com.myutils.utils.ViewUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/8/18 10:09
 * @Descrition 数据填充模块
 */
public class FillUnit {

    private Context context;
    private View decorView = null;

    private Map<String, View> viewWithIdName;

    private RowObject row = null;

    private OnFillMessageListener onFillMessageListener;

    private ViewFilter viewFilter;


    public FillUnit(Map<String, View> viewWithIdName) {
        this.viewWithIdName = viewWithIdName;
        init();
    }

    public FillUnit(Context context) {
        this.context = context;
        this.decorView = ViewUtils.getDecorView(context);
        viewWithIdName = ViewUtils.getViewWithIdName(decorView);
        init();
    }


    public FillUnit(View view) {
        viewWithIdName = ViewUtils.getViewWithIdName(view);
        init();
    }


    private void init() {
        viewFilter=new ViewFilter();
    }



    public void fill(RowObject row) {
        this.row = row;
        fill();
    }

    /**
     * json数据填充
     */
    public void fill(String json) {
        if (JsonUtils.isCanToRow(json)) {
            this.row = JSONSerializer.getRow(json);
        }
        fill();
    }


    /**
     * 开始填充
     */
    public void fill() {
        if (row != null) {
            for (String key : viewWithIdName.keySet()) {
                View view = viewWithIdName.get(key);
                if (view != null) {
                    splitKey(view, key);
                }
            }
        } else {
            L.e("填充数据源为空！");
        }
    }


    /**
     * @param view
     * @param key
     */
    private void splitKey(View view, String key) {
        String value = null;
        if (key.indexOf("$") == -1) {////填充单层数据
            value = row.getString(key);

        } else {//填充多层数据
            String[] split = key.split("\\$");
            value = RowUtils.getlayerData(row, split);
        }
        if (value == null || value.equals("") || value.equals("null")) {
        } else {
            fillMessage(view, key, value);
        }
    }


    /**
     * 数据填充到View
     *
     * @param view
     * @param key
     * @param value
     */
    public void fillMessage(View view, String key, String value) {
        boolean success = false;
        if (onFillMessageListener != null) {//填充监听
            success = onFillMessageListener.fillMessage(view, key, value);
        }
        if (!success) {
            Class cls = view.getClass();
            if (viewFilter.isContain(key,view)) {
                success = customFillMessage(view, key, value);
                if (success) {//自定义View填充
                } else if (view instanceof TextView) {// 填充TextView或者TextView的子类
                    ((TextView) view).setText(value);

                } else if (view instanceof RadioGroup) {// 填充RadioGroup
                    RadioGroup rg = (RadioGroup) view;
                    ViewUtils.fillRadioGroup(rg, value);
                } else if (view instanceof CheckBox) {// 填充CheckBox

                }
            }
        }
    }


    /**
     * 自定义View填充
     *
     * @param view
     * @param key
     * @param value
     * @return
     */
    public boolean customFillMessage(View view, String key, String value) {
        return false;
    }

    /**
     * 数据补充填充接口
     */
    public interface OnFillMessageListener {
        boolean fillMessage(View view, String key, String value);
    }


    public void setDisableType(List<Class> disableType) {
        viewFilter .setDisableType(disableType);
    }

    public void setDisableType(Class... disableType) {
        viewFilter.setDisableType(disableType);
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public View getDecorView() {
        return decorView;
    }

    public void setDecorView(View decorView) {
        this.decorView = decorView;
    }

    public Map<String, View> getViewWithIdName() {
        return viewWithIdName;
    }

    public void setViewWithIdName(Map<String, View> viewWithIdName) {
        this.viewWithIdName = viewWithIdName;
    }

    public RowObject getRow() {
        return row;
    }

    public void setRow(RowObject row) {
        this.row = row;
    }

    public OnFillMessageListener getOnFillMessageListener() {
        return onFillMessageListener;
    }

    public void setOnFillMessageListener(OnFillMessageListener onFillMessageListener) {
        this.onFillMessageListener = onFillMessageListener;
    }


}
