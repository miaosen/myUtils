package com.gzpykj.vtch.main;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gzpykj.vtch.R;
import com.gzpykj.vtch.base.BaseAct;
import com.gzpykj.vtch.base.Global;
import com.gzpykj.vtch.event.EventChooseAct;
import com.gzpykj.vtch.event.EventHealthListAct;
import com.gzpykj.vtch.event.EventRecordAct;
import com.gzpykj.vtch.event.EventSignAct;
import com.myutils.base.L;
import com.myutils.core.JSONResult;
import com.myutils.core.okhttp.callback.ActionResult;
import com.myutils.core.DataHandler;
import com.myutils.core.GlobalVariable;
import com.myutils.core.annotation.ViewInject;
import com.myutils.core.form.Form;
import com.myutils.core.okhttp.ClientFactory;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.UIHelper;
import com.myutils.ui.dialog.MsgDialog;
import com.myutils.ui.dialog.bs.BaseFmDialog;
import com.myutils.utils.IntentUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by OAIM on 2016/9/20.
 */
public class LoginAct extends BaseAct {

    @ViewInject
    Button btn_login;

    @ViewInject
    DataHandler datahandler;

    int num=0;

    @ViewInject
    LinearLayout ln_event_choose,ln_menu,ln_login,ln_event_health,ln_event_record,ln_event_sign_in;


    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.main_login_act;
    }

    @Override
    public void initView(View decorView) {
        //btn_login.setOnClickListener(this);
        ln_event_choose.setOnClickListener(this);
        ln_event_health.setOnClickListener(this);
        ln_event_sign_in.setOnClickListener(this);
        ln_event_record.setOnClickListener(this);
        datahandler.setOnInvokeLisener(new DataHandler.OnInvokeLisener() {
            @Override
            public void onBefore() {
                ClientFactory.clearCookies();
                GlobalVariable.clearPrefs();
            }

            @Override
            public void onSuccess(JSONResult result) {
                if(result.isSuccess()){
                    UIHelper.toast("登陆成功！");
                    GlobalVariable.saveObject("olderInfo",result.getAsRow().getRow("data"));
                   showMenu();
                }else{
                    String message = result.getMessage();
                    L.i("message==="+message);
                    UIHelper.toast(message);
                }
            }

            @Override
            public void onFail(Exception e) {
            }
        });
    }

    @Override
    public void initData() {

    }

    public void login() {
        Form getUnit=new Form(this);
        Map<String, Object> contentValue =getUnit.getContentValue();
        L.i("=========login=============="+contentValue);
        ClientFactory.clearCookies();
        GlobalVariable.clearPrefs();
        UrlInvoker ai= Global.creatActionInvorker("vhOlderAction","olderLogin");
        ai.addParam(contentValue);
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(JSONResult result) {
                if(result.isSuccess()){
                    UIHelper.toast("登陆成功！");
                    GlobalVariable.saveObject("olderInfo",result.getAsRow().getRow("data"));
                   showMenu();
                }else{
                    String message = result.getMessage();
                    L.i("message==="+message);
                    UIHelper.toast(message);
                }
            }
            @Override
            protected void onFail(Exception e) {
                super.onFail(e);
            }
        });
        ai.invoke();
    }

    private void showMenu() {
        ln_login.setVisibility(View.GONE);
        ln_menu.setVisibility(View.VISIBLE);

    }

    @Override
    public void click(View v) {
        if(v==btn_login){
            //showMenu();
            //login();
        }else if(v==ln_event_choose){
            IntentUtils.jump(LoginAct.this, EventChooseAct.class);
        }else if(v== ln_event_health){
            IntentUtils.jump(LoginAct.this, EventHealthListAct.class);
        }else if(v==ln_event_record){
            IntentUtils.jump(LoginAct.this, EventRecordAct.class);
        }else if(v==ln_event_sign_in){
            //UIHelper.toast("现在活动不需要签到！");
            IntentUtils.jump(LoginAct.this, EventSignAct.class);
        }
       // GlobalVariable.getAsRow()
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(ln_menu.getVisibility()==View.VISIBLE){
                showExitDialog();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitDialog() {
        MsgDialog msgDialog=new MsgDialog("提示");
        msgDialog.setMsg("是否退出App！");
        msgDialog.setOnSureListener(new BaseFmDialog.OnSureListener() {
            @Override
            public void sure(BaseFmDialog baseFmDialog) {
                finish();
            }
        });
        msgDialog.show(getSupportFragmentManager(),"loginOut");
    }
}
