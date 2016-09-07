package com.myutilsdemo.ui.dlg;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myutils.core.RowObject;
import com.myutils.ui.UIHelper;
import com.myutils.ui.view.listview.BaseFillAdapter;
import com.myutils.ui.dialog.InputPop;
import com.myutils.ui.dialog.LoadingDialog;
import com.myutils.ui.dialog.MsgDialog;
import com.myutils.ui.dialog.bs.BaseFmDialog;
import com.myutils.core.annotation.ViewInject;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/8/29 15:59
 * @Descrition 
 */ 
public class DialogAct extends BaseAct {

    @ViewInject
    private Button btn_bs, btn_msg, btn_input_pop;

    @ViewInject
    private Button btn_loading;

    View view;

    TestDialog bsDialog;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.ui_dlg_act;
    }

    @Override
    public void initView(View view) {
        btn_bs.setOnClickListener(this);
        btn_loading.setOnClickListener(this);
        btn_msg.setOnClickListener(this);
        testInputPop();
    }

    @Override
    public void click(View v) {

    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        if (v == btn_msg) {
            msgDialog();
        } else if (v == btn_bs) {
            baseFmDialog();
        } else if (v == btn_loading) {
            loadingDialog();
        } else if (v == btn_input_pop) {
            testInputPop();
        }
    }


    private void loadingDialog() {
        LoadingDialog loadingDialog = LoadingDialog.getDialog();
        loadingDialog.show("信息加载中...");
    }


    private void baseFmDialog() {
        if(bsDialog==null){
            bsDialog = new TestDialog();
        }
        bsDialog.show(getSupportFragmentManager(),"ttt");
    }


    public void msgDialog() {
        MsgDialog msgDialog=null;
        if(msgDialog==null){
            msgDialog = new MsgDialog();
            msgDialog.setTitle("提示")
                    .setMsg("我是内容");
                    //.setCancelable(false);
            msgDialog.setOnSureListener(new BaseFmDialog.OnSureListener() {
                @Override
                public void sure(BaseFmDialog baseFmDialog) {
                    UIHelper.toast("确定");
                    baseFmDialog.dismiss();
                }
            });
        }
        msgDialog.show(getSupportFragmentManager(),"sss");
    }

    public void testInputPop() {
        final InputPop inputPop = new InputPop(this, btn_input_pop, R.layout.ui_filladp_base_item);
        inputPop.setRows(getRows());
        inputPop.setOnItemListener(new InputPop.OnItemListener() {
            @Override
            public void setItem(View convertView, RowObject row, int position, Map<String, View> views, BaseFillAdapter.ViewHolder holder) {
            }

            @Override
            public void onItemClick(View convertView, RowObject row, int position) {
                btn_input_pop.setText(row.getString("name"));
                inputPop.dismiss();
            }
        });
    }


    public List<RowObject> getRows() {
        List<RowObject> rows = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RowObject row = new RowObject();
            row.put("name", "第 " + i + " 项");
            rows.add(row);
        }
        return rows;
    }
}
