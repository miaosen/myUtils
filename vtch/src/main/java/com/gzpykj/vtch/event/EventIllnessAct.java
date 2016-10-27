package com.gzpykj.vtch.event;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gzpykj.vtch.R;
import com.gzpykj.vtch.base.Global;
import com.gzpykj.vtch.base.PagingRcListAct;
import com.myutils.base.AppFactory;
import com.myutils.core.ActionResult;
import com.myutils.core.RowObject;
import com.myutils.core.logger.L;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.ui.view.rcview.BaseRcAdapter;
import com.myutils.utils.IntentUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition 就诊活动选择
 */
public class EventIllnessAct extends PagingRcListAct {


    @Override
    public int setItemLayout() {
        return R.layout.event_illness_list_item;
    }

    @Override
    public UrlInvoker setDataResourse() {
        UrlInvoker ai= Global.creatActionInvorker("vhDiseaseAction","getList");
        return ai;
    }

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
                Intent in=new Intent(EventIllnessAct.this,EventListAct.class);
                in.putExtra("DISEASEID",row.getString("MAINID"));
                startActivity(in);
            }
        });
        SimpleDraweeView draweeView = (SimpleDraweeView) viewWithIdName.get("simpleDraweeView");
        Uri uri = Uri.parse(Global.getProjectPath()+row.getString("DISEASEPHOTO"));
        L.i("uri============="+uri);
        draweeView.setImageURI(uri);
    }

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.event_list_act;
    }

    @Override
    public void initView(View decorView) {
        super.initView(decorView);
        baseHeader.setTitle("按病种预约");
        mRefresh.setOnLoadListener(null);
    }

    ///**
    // * 结果回调
    // * @param result
    // * @param type 刷新还是加载更多
    // */
    //protected void onResultCallBack(ActionResult result, String type) {
    //    RowObject row = result.getRow();
    //    if (result.isSuccess()) {
    //        List<RowObject> resultRows = row.getFilePaths("data");
    //        if (resultRows != null) {
    //            rcAdapter.hideFooter();
    //            rows.addAll(resultRows);
    //            if (type.equals("refresh")) {
    //                rcAdapter.notifyDataSetChanged();
    //                mRefresh.setRefreshing(false);
    //            } else {
    //                rcAdapter.notifyDataSetChanged();
    //            }
    //            pageIndex = pageIndex + 1;
    //        } else {
    //            rcAdapter.getTipLayout().notData("没有更多数据！");
    //        }
    //        mRefresh.setLoading(false);
    //    }
    //}
}
