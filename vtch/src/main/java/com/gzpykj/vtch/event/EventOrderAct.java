package com.gzpykj.vtch.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gzpykj.vtch.R;
import com.gzpykj.vtch.base.BaseAct;
import com.gzpykj.vtch.base.Global;
import com.myutils.core.ActionResult;
import com.myutils.core.GlobalVariable;
import com.myutils.core.RowObject;
import com.myutils.core.annotation.ViewInject;
import com.myutils.core.form.FillUnit;
import com.myutils.core.logger.L;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.UIHelper;
import com.myutils.ui.view.CustomRadioGroup;
import com.myutils.ui.view.listview.BaseFillAdapter;
import com.myutils.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/23 12:06
 * @Descrition 确认预约
 */ 
public class EventOrderAct extends BaseAct {

    @ViewInject
    ListView lv;
    BaseFillAdapter mAdpter;
    List<RowObject> rows=new ArrayList<RowObject>();

    @ViewInject
    Button btn_sure;

    private String activityId;

    private String expertId;

    private String  activityDate;

    private String activityTimeId;

    private RowObject rowInfo;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        Intent intent = getIntent();
        activityId =intent.getStringExtra("activityId");
        expertId=intent.getStringExtra("expertId");
        activityDate=intent.getStringExtra("activityDate");
        rowInfo= IntentUtils.getRow(intent,"rowInfo");
        return R.layout.event_order_act;
    }

    @Override
    public void initView(View decorView) {
        initHeader();
        baseHeader.leftFinish();
        baseHeader.setTitle("确认预约");
        mAdpter=new BaseFillAdapter(EventOrderAct.this,rows,R.layout.event_order_time_item) {
            @Override
            public void setItem(View convertView, RowObject row, int position, ViewHolder holder) {

            }
        };
        lv.setAdapter(mAdpter);
        btn_sure.setOnClickListener(this);
        mAdpter.setOnItemClickListener(new BaseFillAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View convertView, RowObject row, int position) {
                RadioButton rb = (RadioButton) convertView.findViewById(R.id.rb);
                if(rb.isChecked()){
                    rb.setChecked(false);
                    activityTimeId ="";
                }else{
                    rb.setChecked(true);
                    activityTimeId =row.getString("MAINID");
            }

            }
        });
        FillUnit fillUnit=new FillUnit(this);
        fillUnit.fill(rowInfo);
    }

    @Override
    public void initData() {
        UrlInvoker ai= Global.creatActionInvorker("vhTimeDefinitionAction","getListById/"+ activityId);
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(ActionResult result) {
                if(result.isSuccess()){
                    List<RowObject> data = result.getRow().getRows("data");
                   if(data!=null){
                       rows.addAll(data);
                       mAdpter.notifyDataSetChanged();
                   }
                }else{
                    String message = result.getMessage();
                    UIHelper.toast(message);
                }
            }
        });
        ai.invoke();
    }

    @Override
    public void click(View v) {
        if(v==btn_sure){
            submit();
        }
    }

    private void submit() {
        if(activityTimeId!=null){
            UrlInvoker ai= Global.creatActionInvorker("vhActivityPlanAction","appointOlder/");
            ai.setCallback(new StringCallBack() {
                @Override
                public void onSuccess(ActionResult result) {
                    if(result.isSuccess()){
                        String message = result.getMessage();
                        UIHelper.toast(message);
                        finish();
                    }else{
                        String message = result.getMessage();
                        UIHelper.toast(message);
                    }
                    //L.i("result===="+result.getResponseText());
                }
            });
            ai.addParam("activityTimeId", activityTimeId);
            ai.addParam("mainId", activityId);
            ai.addParam("expertId ", expertId);
            ai.addParam("activityDate ", activityDate);
            RowObject olderInfo = GlobalVariable.getRow("olderInfo");
            // L.i(olderInfo.toString()+"   "+olderInfo.getString("MAINID"));
            ai.addParam("olderId",olderInfo.getString("MAINID"));
            ai.invoke();
        }else{
            UIHelper.toast("没有时间段id！");
        }

    }
}
