package com.myutilsdemo.ui.view.listview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.myutils.base.AppFactory;
import com.myutils.core.okhttp.callback.ActionResult;
import com.myutils.core.RowObject;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.view.listview.BaseFillAdapter;
import com.myutils.core.annotation.ViewInject;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;

import java.util.ArrayList;
import java.util.List;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/8/26 14:24
 * @Descrition 自动填充适配器
 */
public class ListViewAct extends BaseAct {

    @ViewInject
    ListView lv;

    MAdpter mAdpter;

    List<RowObject> rows;

    @ViewInject
    RefreshAbsListView pullToRefresh;

    View footerView;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.ui_filladp_base_act;
    }

    @Override
    public void initView(View decorView) {
        rows=new ArrayList<>();
        mAdpter=new MAdpter(ListViewAct.this,rows,R.layout.ui_view_review_item);
        footerView = LayoutInflater.from(ListViewAct.this).inflate(R.layout.ui_view_listview_footer, null, false);
        lv.addFooterView(footerView);
        lv.setAdapter(mAdpter);
        // 加载监听器
        pullToRefresh.setOnLoadListener(new RefreshAbsListView.OnLoadListener() {
            @Override
            public void onLoad() {
                lv.addFooterView(footerView);
                initData();

            }
        });
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdpter.clearData();
                initData();
            }
        });
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
                RowObject row = result.getAsRow();
                List<RowObject> resultRows = row.getRows("result");
                if(rows!=null){
                    rows.addAll(resultRows);
                    mAdpter.notifyDataSetChanged();
                    // 加载完后调用该方法
                    pullToRefresh.setLoading(false);
                    pullToRefresh.setRefreshing(false);
                    lv.removeFooterView(footerView);
                }

            }
        });
        ai.invoke();

    }

    @Override
    public void click(View v) {

    }


    class MAdpter extends BaseFillAdapter{

        public MAdpter(Context context, List<RowObject> rows, int layout) {
            super(context, rows, layout);
        }

        @Override
        public void setItem(View convertView, RowObject row, int position, ViewHolder holder) {

        }
    }
}
