package com.myutils.core.form;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.myutils.core.RowObject;
import com.myutils.core.json.JSONSerializer;
import com.myutils.base.L;
import com.myutils.utils.JsonUtils;
import com.myutils.utils.RowUtils;

import java.util.Map;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/8/18 10:09
 * @Descrition 数据填充模块
 */
public class Form {

    private Context context;
    private View decorView = null;

    private Map<String, View> viewWithIdName;


    private RowObject row = null;

    private OnFillMessageListener onFillMessageListener;

    /**
     * View的信息最终转成viewWithIdName
     * @param viewWithIdName
     */
    public Form(Map<String, View> viewWithIdName) {
        //init();
        this.viewWithIdName = viewWithIdName;
    }

    public Form(Context context) {
        init();
        this.context = context;
        this.decorView = ViewUtils.getDecorView(context);
        viewWithIdName = ViewUtils.getViewWithIdName(decorView,ViewFilter.getInstance());
    }


    public Form(View view) {
        init();
        viewWithIdName = ViewUtils.getViewWithIdName(view,ViewFilter.getInstance() );
    }

    protected void init() {
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
        if (row != null&&row.size()>0) {
            for (String key : viewWithIdName.keySet()) {
                View view = viewWithIdName.get(key);
                if (view != null) {
                    splitKey(view, key);
                }
            }
        } else {
            L.e("数据源为空！");
        }
    }




    /**
     * 处理id名称，如果有$,就分层填充
     * @param view
     * @param key
     */
    private void splitKey(View view, String key) {
        String value = null;
        if (key.indexOf("$") == -1) {//填充单层数据
            value = row.getString(key);
            //L.i("key================"+key+" value======="+value);
        } else {//填充多层数据
            String[] split = key.split("\\$");
            value = RowUtils.getlayerData(row, split);
        }
        if (TextUtils.isEmpty(value)) {
        } else {
            fillMessage(view, key, value);
        }
    }

    /**
     * 获取要表单的内容
     * @return
     */
    public Map<String, Object> getContentValue() {
        L.i("=========getContentValue=============="+viewWithIdName);
        return ViewUtils.getContentValues(viewWithIdName);
    }


    /**
     * 数据填充到View
     *
     * @param view
     * @param key
     * @param value
     */
    protected void fillMessage(View view, String key, Object value) {
        boolean success = false;
        if (onFillMessageListener != null) {//填充监听
            success = onFillMessageListener.fillMessage(view, key, value);
        }
        if (!success) {
            if (!ViewFilter.getInstance().filter(view)) {
                //success = customFillMessage(view, key, value);
                if (view instanceof FormViewAdpater) {//实现ViewValueAdpater接口的自定义View填充
                    ((FormViewAdpater) view).setValue(value);
                } else if (view instanceof TextView) {// 填充TextView或者TextView的子类
                    ((TextView) view).setText(value.toString());
                } else if (view instanceof RadioGroup) {// 填充RadioGroup
                    RadioGroup rg = (RadioGroup) view;
                    ViewUtils.fillRadioGroup(rg, value.toString());
                } else if (view instanceof CheckBox) {// 填充CheckBox
                    CheckBox cb= (CheckBox) view;
                    ViewUtils.fillCheckBox(cb,value.toString());
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
    // */
    //public boolean customFillMessage(View view, String key, String value) {
    //    return false;
    //}

    /**
     * 数据补充填充接口
     */
    public interface OnFillMessageListener {
        boolean fillMessage(View view, String key, Object value);
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

}
