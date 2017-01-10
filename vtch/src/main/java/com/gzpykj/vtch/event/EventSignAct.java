package com.gzpykj.vtch.event;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gzpykj.vtch.R;
import com.gzpykj.vtch.base.MResult;
import com.gzpykj.vtch.base.PagingRcListAct;
import com.myutils.base.AppFactory;
import com.myutils.core.GlobalVariable;
import com.myutils.core.JSONResult;
import com.myutils.core.ResultCallBack;
import com.myutils.core.RowObject;
import com.myutils.core.json.JSONSerializer;
import com.myutils.core.okhttp.HandlerQueue;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.ActionResult;
import com.myutils.core.okhttp.callback.CustomeCallBack;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.view.rcview.BaseRcAdapter;
import com.myutils.utils.JsonUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static android.R.attr.y;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition 活动签到
 */
public class EventSignAct extends PagingRcListAct {



    @SuppressLint("NewApi")
    @Override
    public void setPageItem(BaseRcAdapter.ViewHolder viewHolder, RowObject row, int position) {
        //Map<String, View> viewWithIdName = viewHolder.fillUnit.getViewWithIdName();
        //View ln_illness = viewWithIdName.get("ln_illness");
        //if(position%2==0){
        //    ln_illness.setBackground(getResources().getDrawable(R.drawable.sel_event_item_bg_green));
        //}else{
        //    ln_illness.setBackground(getResources().getDrawable(R.drawable.sel_event_item_bg_blue));
        //}
        //ln_illness.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        // IntentUtils.jump(EventSignAct.this,EventOrderAct.class);
        //    }
        //});

        //Button btn_bottom= (Button) viewWithIdName.get("btn_bottom");
        //btn_bottom.setText("签到");
    }

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.event_list_act;
    }

    @Override
    public void initView(View decorView) {
        super.initView(decorView);
        baseHeader.setTitle("签到");
        UrlInvoker ai = pagingListRcView.getUik();
        RowObject olderInfo = GlobalVariable.getRow("olderInfo");
        ai.addParam("olderId",olderInfo.getString("MAINID"));
        String url="http://api.avatardata.cn/Cook/List?key=8858c93a7665449784b19a421aba0059&page=2&rows=20";
        pagingListRcView.setUrl(url);
        pagingListRcView.getRefreshRcView().setOnLoadListener(null);
        pagingListRcView.setItemLayout(R.layout.event_sign_item);
        pagingListRcView.setResultCallBack(new CustomeCallBack<MResult>() {
            @Override
            public void onSuccess(JSONResult result) {
                pagingListRcView.onResultCallBack(result);
            }
        });
        pagingListRcView.setPageIndexText("page");
        pagingListRcView.setPageSizeText("rows");
        pagingListRcView.load();
        //UrlInvoker urlInvoker=new UrlInvoker("http://api.avatardata.cn/Cook/List?key=8858c93a7665449784b19a421aba0059&page=2&rows=20");
        //urlInvoker.setCallback(new mCallBack() {
        //    @Override
        //    public void onSuccess(JSONResult result) {
        //        List<RowObject> result1 = result.getAsRow().getRows("result");
        //        pagingListRcView.addList(result1);
        //    }
        //});
        //urlInvoker.invoke();
    }




}
