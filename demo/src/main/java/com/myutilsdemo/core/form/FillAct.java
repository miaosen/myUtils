package com.myutilsdemo.core.form;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myutils.base.AppFactory;
import com.myutils.core.okhttp.callback.ActionResult;
import com.myutils.core.form.Form;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/1 15:03
 * @Descrition 数据填充例子
 */
public class FillAct extends BaseAct {

    private Form f;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.core_fillunit_act;
    }

    @Override
    public void initView(View decorView) {
         f=new Form(decorView);
    }

    @Override
    public void initData() {
        UrlInvoker ai = AppFactory.creatUrlInvorker("http://wthrcdn.etouch.cn/weather_mini?citykey=101010100");
        ai.postMode();
        ai.setDialog("百度");
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(ActionResult result) {
                f.setRow(result.getAsRow());
                f.setDisableType(EditText.class);//EditText不填充
                f.setOnFillMessageListener(new Form.OnFillMessageListener() {//自定义填充
                    @Override
                    public boolean fillMessage(View view, String key, String value) {
                        if(key.equals("desc")){
                            TextView tv= (TextView) view;
                            tv.setText("自定义文字");
                            return true;//此方法在默认填充方法前面，所以完成必须返回true;
                        }
                        return false;
                    }
                });
                f.fill();
            }
        });
        ai.invoke();
    }

    @Override
    public void click(View v) {
    }
}
