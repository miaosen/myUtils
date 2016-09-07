package com.myutils.core.form;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.DateButton;
import com.myutils.ui.view.CustomRadioGroup;
import com.myutils.utils.ViewUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by OAIM on 2016/9/6.
 */
public class GetUnit {


    private Context context;
    private View decorView = null;

    private Map<String, View> viewWithIdName;


    /**
     * 不需要获取的View类型
     */
    private List<Class> disableType = null;


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

    private void init() {
        if (this.disableType == null) {
            this.disableType = new LinkedList<Class>();
        }
        //TextView一般不需要获取
        setDisableType(TextView.class);
    }


    public Map<String, Object> getContentValue() {
        Map<String, Object> contentMap = new LinkedHashMap<String, Object>();
        for (String idName: viewWithIdName.keySet()) {
            View view = viewWithIdName.get(idName);
            if (disableType == null || !disableType.contains(view.getClass())) {
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
        this.disableType.addAll(disableType);
    }

    public void setDisableType(Class... disableType) {
        for (int i = 0; i < disableType.length; i++) {
            this.disableType.add(disableType[i]);
        }
    }

}
