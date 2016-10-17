package com.gzpykj.vtch.event;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gzpykj.vtch.R;
import com.gzpykj.vtch.base.Global;
import com.gzpykj.vtch.base.PagingRcListAct;
import com.myutils.base.AppFactory;
import com.myutils.core.ActionResult;
import com.myutils.core.GlobalVariable;
import com.myutils.core.RowObject;
import com.myutils.core.logger.L;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.UIHelper;
import com.myutils.ui.dialog.MsgDialog;
import com.myutils.ui.dialog.bs.BaseFmDialog;
import com.myutils.ui.view.rcview.BaseRcAdapter;
import com.myutils.utils.IntentUtils;

import java.util.Map;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition 就诊活动选择
 */
public class EventRecordAct extends PagingRcListAct {


    @Override
    public int setItemLayout() {
        return R.layout.event_list_item;
    }

    @Override
    public UrlInvoker setDataResourse() {
        UrlInvoker ai= Global.creatActionInvorker("vhActivityAppointAction","findList");
        RowObject olderInfo = GlobalVariable.getRow("olderInfo");
        // L.i(olderInfo.toString()+"   "+olderInfo.getString("MAINID"));
        ai.addParam("olderId",olderInfo.getString("MAINID"));
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
                Intent in=new Intent(EventRecordAct.this,EventDetailAct.class);
                IntentUtils.addRow(in,row,"rowInfo");
                startActivity(in);
            }
        });
        Button btn_order = (Button) viewWithIdName.get("btn_order");
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showCancleOrderDialog(row.getString("MAINID"));
            }
        });
        SimpleDraweeView draweeView = (SimpleDraweeView) viewWithIdName.get("simpleDraweeView");
        Uri uri = Uri.parse(Global.getProjectPath()+row.getString("EXPERTPHOTO"));
        L.i("uri============="+uri);
        draweeView.setImageURI(uri);
        String status = row.getString("STATUS");
        if(status!=null&&status.equals("1")){
            btn_order.setText("取消预约");
            btn_order.setEnabled(true);
        }else{
            btn_order.setText("已取消");
            btn_order.setEnabled(false);
        }

    }

    private void showCancleOrderDialog(final String mainId) {
        MsgDialog msgDialog=new MsgDialog("提示");
        msgDialog.setMsg("是否确定取消预约！");
        msgDialog.setOnSureListener(new BaseFmDialog.OnSureListener() {
            @Override
            public void sure(BaseFmDialog baseFmDialog) {
                cancleOrder(mainId);

            }
        });
        msgDialog.show(getSupportFragmentManager(),"canle");
    }

    /**
     * 取消预约
     */
    private void cancleOrder(String mainId) {
        UrlInvoker ai= Global.creatActionInvorker("vhActivityAppointAction","cancelAppoint");
        ai.addParam("idStr",mainId);
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(ActionResult result) {
                if(result.isSuccess()){
                    UIHelper.toast("取消成功！");
                    finish();
                }else{
                    String message = result.getMessage();
                    UIHelper.toast(message);
                }
            }
        });
        ai.invoke();
    }

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.event_list_act;
    }

    @Override
    public void initView(View decorView) {
        super.initView(decorView);
        mRefresh.setOnLoadListener(null);
        baseHeader.setTitle("预约记录查询");
    }
}
