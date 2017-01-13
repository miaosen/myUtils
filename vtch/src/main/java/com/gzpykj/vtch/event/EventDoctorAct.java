package com.gzpykj.vtch.event;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gzpykj.vtch.R;
import com.gzpykj.vtch.base.Global;
import com.gzpykj.vtch.base.PagingRcListAct;
import com.myutils.core.RowObject;
import com.myutils.ui.view.rcview.BaseRcAdapter;

import java.util.Map;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition 就诊活动选择
 */
public class EventDoctorAct extends PagingRcListAct {



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
                Intent in=new Intent(EventDoctorAct.this,EventListAct.class);
                in.putExtra("EXPERTID",row.getString("MAINID"));
                startActivity(in);
            }
        });

        SimpleDraweeView draweeView = (SimpleDraweeView) viewWithIdName.get("simpleDraweeView");
        Uri uri = Uri.parse(Global.getProjectPath()+row.getString("EXPERTPHOTO"));
        draweeView.setImageURI(uri);
    }

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.event_list_act;
    }

    @Override
    public void initView(View decorView) {
        super.initView(decorView);
        baseHeader.setTitle("按医生预约");
        pagingListRcView.getRefreshRcView().setOnLoadListener(null);
        pagingListRcView.setActionClass("vhExpertAction");
        pagingListRcView.setItemLayout(R.layout.event_doctor_list_item);
        pagingListRcView.setActionName("getList");
        pagingListRcView.load();
    }
}
