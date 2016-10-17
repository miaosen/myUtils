package com.gzpykj.vtch.event;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gzpykj.vtch.R;
import com.gzpykj.vtch.base.PagingRcListAct;
import com.myutils.base.AppFactory;
import com.myutils.core.RowObject;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.ui.view.rcview.BaseRcAdapter;

import java.util.Map;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition 活动签到
 */
public class EventSignAct extends PagingRcListAct {


    @Override
    public int setItemLayout() {
        return R.layout.event_list_item;
    }

    @Override
    public UrlInvoker setDataResourse() {
        UrlInvoker ai = AppFactory.creatUrlInvorker("http://api.avatardata.cn/GuoNeiNews/Query?key=124076155abb4e97993a181c949e9de8");
        return ai;
    }

    @Override
    public void setPageItem(BaseRcAdapter.ViewHolder viewHolder, RowObject row, int position) {
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
                // IntentUtils.jump(EventSignAct.this,EventOrderAct.class);
            }
        });

        Button btn_bottom= (Button) viewWithIdName.get("btn_bottom");
        btn_bottom.setText("签到");
    }

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.event_list_act;
    }

    @Override
    public void initView(View decorView) {
        super.initView(decorView);
        baseHeader.setTitle("就诊活动选择");
    }
}
