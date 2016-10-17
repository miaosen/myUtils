package com.myutilsdemo.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.myutils.base.AppFactory;
import com.myutils.core.ActionResult;
import com.myutils.core.RowObject;
import com.myutils.core.annotation.ViewInject;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.view.listview.BaseFillAdapter;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OAIM on 2016/9/8.
 */
public class MyRrefreshAct extends BaseAct {



    @ViewInject
    ListView lv;

    MAdpter mAdpter;

    List<RowObject> rows;



    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.ui_view_myrefresh;
    }

    @Override
    public void initView(View decorView) {
        rows=new ArrayList<>();
        mAdpter=new MAdpter(MyRrefreshAct.this,rows,R.layout.ui_view_review_item);
        lv.setAdapter(mAdpter);
    }

    @Override
    public void initData() {
        UrlInvoker ai = AppFactory.creatUrlInvorker("http://api.avatardata.cn/GuoNeiNews/Query?key=124076155abb4e97993a181c949e9de8");
        ai.addParam("page", 50);
        //ai.setDialog("loading...");
        ai.addParam("rows",10);
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(ActionResult result) {
                RowObject row = result.getRow();
                List<RowObject> resultRows = row.getRows("result");
                rows.addAll(resultRows);
                mAdpter.notifyDataSetChanged();
                // 加载完后调用该方法
            }
        });
        ai.invoke();
    }

    @Override
    public void click(View v) {

    }


    class MAdpter extends BaseFillAdapter {

        public MAdpter(Context context, List<RowObject> rows, int layout) {
            super(context, rows, layout);
        }

        @Override
        public void setItem(View convertView, RowObject row, int position, ViewHolder holder) {

        }
    }




}
