package com.myutils.core.form;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.myutils.ui.view.CustomRadioGroup;
import com.myutils.utils.ViewUtils;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OAIM on 2016/9/6.
 */
public class GetUnit {


    private Context context;
    private View decorView = null;

    private Map<String, View> viewWithIdName;

    private ViewFilter viewFilter;


    public GetUnit(Context context) {
        this.context = context;
        this.decorView = ViewUtils.getDecorView(context);
        viewWithIdName = ViewUtils.getViewWithIdName(decorView);
        init();
    }


    public GetUnit(View view) {
        viewWithIdName = ViewUtils.getViewWithIdName(view);
        init();
    }

    public GetUnit( Map<String, View> viewWithIdName) {
        this.viewWithIdName =viewWithIdName;
        init();
    }

    private void init() {
        viewFilter=new ViewFilter();
        viewFilter.setDisableType(TextView.class);
        viewFilter.setDisableType(Button.class);
        viewFilter.setDisableType(AppCompatTextView.class);
        viewFilter.setDisableType(AppCompatButton.class);

    }


    public Map<String, Object> getContentValue() {
        Map<String, Object> contentMap = new LinkedHashMap<String, Object>();
        for (String idName: viewWithIdName.keySet()) {
            View view = viewWithIdName.get(idName);
            if (viewFilter.isContain(idName,view)) {
                if (view instanceof TextView) {//获取TextView或者TextView的子类文字
                    TextView tv = (TextView) view;
                    if (tv.getText()!=null&&!tv.getText().equals("")) {
                        contentMap.put(idName + "", tv.getText() + "");
                    }
                } else if (view instanceof RadioGroup) {
                    RadioGroup rg = (RadioGroup) view;
                    HashMap<String, Object> radioGroupMap = ViewUtils.getRadioGroupMap(rg, null);
                    if (radioGroupMap != null) {
                        contentMap.putAll(radioGroupMap);
                    }
                }else if (view instanceof CustomRadioGroup){
                    CustomRadioGroup rg=(CustomRadioGroup) view;
                    contentMap.put(idName + "",  rg.getValue());
                }
            }
        }
        return contentMap;
    }

    public void setDisableType(List<Class> disableType) {
       viewFilter.setDisableType(disableType);
    }


    /**
     * 设置不需要获取的view
     * @param disableType
     */
    public void setDisableType(Class... disableType) {
        viewFilter.setDisableType(disableType);
    }


    /**
     * 设置不需要获取的view
     * @param idName
     */
    public void enableIdName(String idName){
        viewFilter.enableIdName(idName);
    }




}
