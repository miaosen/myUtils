package com.myutilsdemo.core.okhttp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.myutils.base.AppFactory;
import com.myutils.core.okhttp.callback.ActionResult;
import com.myutils.core.json.JSONSerializer;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.core.annotation.ViewInject;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @CreateDate 2016/8/6 14:45
 * @Descrition Okhttp示例
 */
public class OkhttpAct extends BaseAct {


    @ViewInject
    LinearLayout ln;
    @ViewInject
    Button button;
    @ViewInject
    EditText ed;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.core_okhttp_act;
    }

    @Override
    public void initView(View view) {
        //ln= (LinearLayout) findViewById(R.id.ln);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(1);
            }
        });
    }


    @Override
    public void initData() {

    }

    @Override
    public void click(View v) {

    }

    private void getData(final int size) {

        for (int i = 0; i < size; i++) {

            //分页  http://localhost:8080/farseev2/jsp/test/test_list.jsp?renderMode=json&returnType=data&pageNum=1&pageSize=3

            UrlInvoker ai = AppFactory.creatUrlInvorker("http://192.168.1.193:8080/farseev2/jsp/test/test_action.jsp?del");
             //ai.setWay("get");
            ai.addParam("mainid","297eb07258d0251e0158d0283e4e0009");
           // ai.addParam("password", "密码"+i);
            //ai.addParam("pageSize", "10");
            ai.setCallback(new StringCallBack() {
                @Override
                public void onSuccess(ActionResult result) {
//                    EditText tv = new EditText(getContext());
//                    tv.setText("第 " + j + " 条" + JSONSerializer.toJson(result) + "");
//                    ln.addView(tv);
                    ed.setText(JSONSerializer.toJson(result.getResponseJsonText()));
                }
            });
            ai.invoke();
        }

    }


}
