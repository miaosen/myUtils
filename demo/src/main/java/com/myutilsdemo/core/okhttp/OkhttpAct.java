package com.myutilsdemo.core.okhttp;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.myutils.base.AppFactory;
import com.myutils.core.ActionResult;
import com.myutils.core.gson.JSONSerializer;
import com.myutils.core.okhttp.ActionInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.view.annotation.ViewInject;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/8/6 14:45
 * @Descrition Okhttp示例
 */
public class OkhttpAct extends BaseAct{

    @ViewInject
    LinearLayout ln;
    @ViewInject
    Button button;

    @Override
    public void initConfig() {
        setContentView(R.layout.core_act_okhttp);
    }

    @Override
    public void initView() {
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
//        for (int i = 0; i <5 ; i++) {
//            getData(i);
//        }
    }

    @Override
    public void click(View v) {

    }

    private void getData(final int i) {
        ActionInvoker ai = AppFactory.creatActionInvorker("https://github.com/");
        ai.postMode();
        ai.setDialog("百度");
//        ai.addParam("mobileLogin", "true");
//        ai.addParam("username", "thinkGem");
//        ai.addParam("password", "123456");
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(ActionResult result) {
                TextView tv=new TextView(OkhttpAct.this);
                tv.setText("第 "+i+" 条"+JSONSerializer.toJson(result)+"");
                ln.addView(tv);
            }
        });
        ai.invoke();
    }


}
