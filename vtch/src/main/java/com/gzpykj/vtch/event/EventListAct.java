package com.gzpykj.vtch.event;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gzpykj.vtch.R;
import com.gzpykj.vtch.base.Global;
import com.gzpykj.vtch.base.PagingRcListAct;
import com.myutils.core.RowObject;
import com.myutils.base.L;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.ui.view.rcview.BaseRcAdapter;
import com.myutils.utils.IntentUtils;

import java.util.Map;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition 就诊活动选择（预约）
 */
public class EventListAct extends PagingRcListAct {

    String DISEASEID ;

    String EXPERTID;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        DISEASEID=getIntent().getStringExtra("DISEASEID");
        EXPERTID=getIntent().getStringExtra("EXPERTID");
        return R.layout.event_list_act;
    }

    @Override
    public int setItemLayout() {
        return R.layout.event_list_item;
    }

    @Override
    public UrlInvoker setDataResourse() {
        UrlInvoker ai= Global.creatActionInvorker("vhActivityPlanAction","findList");
        ai.addParam("TYPENO","02");
        ai.addParam("STATUS","2");
        if(!TextUtils.isEmpty(DISEASEID)){
            ai.addParam("DISEASEID",DISEASEID);
        }
        if(!TextUtils.isEmpty(EXPERTID)){
            ai.addParam("EXPERTID",EXPERTID);
        }
        return ai;
    }

    @SuppressLint("NewApi")
    @Override
    public void setPageItem(BaseRcAdapter.ViewHolder viewHolder, final RowObject row, int position) {
        Map<String, View> viewWithIdName = viewHolder.fillUnit.getViewWithIdName();
        View ln_illness = viewWithIdName.get("ln_illness");
        if(position%2==0){
            ln_illness.setBackground(getResources().getDrawable(R.drawable.sel_event_item_bg_green));
        }else{
            ln_illness.setBackground(getResources().getDrawable(R.drawable.sel_event_item_bg_blue));
        }
        ln_illness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(EventListAct.this,EventDetailAct.class);
                IntentUtils.addRow(in,row,"rowInfo");
                startActivity(in);
            }
        });
        SimpleDraweeView draweeView = (SimpleDraweeView) viewWithIdName.get("simpleDraweeView");
        Uri uri = Uri.parse(Global.getProjectPath()+row.getString("EXPERTPHOTO"));
        L.i("uri============="+uri);
        draweeView.setImageURI(uri);
        TextView btn_order = (TextView) viewWithIdName.get("btn_order");
        String status = row.getString("STATUS");
        if(status!=null&&status.equals("2")){
            btn_order.setText("预约");
            btn_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in=new Intent(EventListAct.this,EventOrderAct.class);
                    in.putExtra("activityId",row.getString("MAINID"));
                    in.putExtra("expertId",row.getString("EXPERTID"));
                    in.putExtra("activityDate",row.getString("ACTIVITYDATE"));
                    IntentUtils.addRow(in,row,"rowInfo");
                    startActivity(in);
                    //IntentUtils.jump(EventListAct.this,EventOrderAct.class);
                }
            });
        }else{
            btn_order.setText("预约完成!");
            btn_order.setTextColor(getResources().getColor(R.color.white));
            btn_order.setBackground(new BitmapDrawable());
        }
    }



    @Override
    public void initView(View decorView) {
        super.initView(decorView);
        baseHeader.setTitle("就诊活动选择");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
