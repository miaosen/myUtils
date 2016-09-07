package com.myutilsdemo.ui.view.rcview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.myutils.base.AppFactory;
import com.myutils.core.ActionResult;
import com.myutils.core.RowObject;
import com.myutils.core.logger.L;
import com.myutils.core.okhttp.ActionInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.UIHelper;
import com.myutils.core.annotation.ViewInject;
import com.myutils.ui.dialog.MsgDialog;
import com.myutils.ui.view.rcview.BaseRcAdapter;
import com.myutils.ui.view.rcview.GridViewLine;
import com.myutils.ui.view.rcview.ItemTouchCallback;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;
import com.myutilsdemo.ui.view.RefreshRecyclerView;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition
 */
public class RcViewAct extends BaseAct {


    @ViewInject
    RecyclerView rc_view;

    @ViewInject
    RefreshRecyclerView pullToRefresh;

    BaseRcAdapter rcAdapter;

    List<RowObject> rows = new LinkedList<RowObject>();

    MsgDialog msgDialog;
    WebView webView;

    int page = 1;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.ui_view_recycleview;
    }

    @Override
    public void initView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(RcViewAct.this);
        //GridLayoutManager layoutManager = new GridLayoutManager(RcViewAct.this, 4);
        //StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        //设置布局管理器
        rc_view.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        //开启拖动、移除动画
        ItemTouchCallback callback = new ItemTouchCallback() {
            @Override
            public boolean isLongPressDragEnabled() {
                // 长按拖拽打开
                return true;
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rc_view);

        //设置Adapter
        rcAdapter = new BaseRcAdapter(rows, R.layout.ui_view_review_item) {
            @Override
            public void setItem(ViewHolder viewHolder, RowObject row, int position) {
                //if (position % 5 == 0) {
                //    if (position % 6 == 0) {
                //        viewHolder.itemView.setBackgroundColor(Color.parseColor("#00ff00"));
                //        viewHolder.setText(R.id.name, "飞机上几分几分几分片区及聘请键盘");
                //        viewHolder.itemView.findViewById(R.id.name).setOnClickListener(new View.OnClickListener() {
                //            @Override
                //            public void onClick(View v) {
                //                UIHelper.toast("TextView 监听");
                //            }
                //        });
                //    } else {
                //        viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                //    }
                //} else {
                //    viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                //}
            }
        };
        rcAdapter.setOnItemClickListener(new BaseRcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRcAdapter.ViewHolder viewHolder, RowObject row, int position) {

                getNew(row.getString("url"));
            }
        });
        rc_view.setAdapter(rcAdapter);
        //设置增加或删除条目的动画
        rc_view.setItemAnimator(new DefaultItemAnimator());
        //设置分隔线
        rc_view.addItemDecoration(new GridViewLine(RcViewAct.this));
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rows.clear();
                page = 1;
                getData("refresh");

            }
        });
        pullToRefresh.setOnLoadListener(new RefreshRecyclerView.OnLoadListener() {
            @Override
            public void onLoad() {
                getData("load");

            }
        });
        pullToRefresh.setRefreshing(true);
        getData("refresh");


        msgDialog=new MsgDialog();
        msgDialog.setTitle("你选择了");
    }

    private void getNew(String url) {
        //WebView webView=new WebView(RcViewAct.this);
        //WebSettings wSet = webView.getSettings();
        //wSet.setJavaScriptEnabled(true);
        //webView.loadUrl(url);
        //msgDialog.setContentView(webView);
        //msgDialog.show(getSupportFragmentManager(),"news");
        ActionInvoker ai = AppFactory.creatActionInvorker(url);
        ai.setDialog("loading...");
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(ActionResult result) {
                webView=new WebView(RcViewAct.this);
                WebSettings wSet = webView.getSettings();
                wSet.setJavaScriptEnabled(true);
                msgDialog.setContentView(webView);
                webView.loadData(result.getText(),"text/html","utf-8");
                webView.setBackgroundColor(Color.parseColor("#00ffff"));
                msgDialog.show(getSupportFragmentManager(),"news");
                //msgDialog.setMsg(result.getText());
               // UIHelper.toast("你选择了 " + result.getText());
            }
        });
        ai.invoke();

    }

    private void getData( final String type) {
        ActionInvoker ai = AppFactory.creatActionInvorker("http://api.avatardata.cn/GuoNeiNews/Query?key=124076155abb4e97993a181c949e9de8&rows=20");
        ai.addParam("page", page);
        ai.setDialog("loading...");
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(ActionResult result) {
                RowObject row = result.getRow();
                List<RowObject> resultRows = row.getRows("result");
                rows.addAll(resultRows);
                if(type.equals("refresh")){
                    rcAdapter.notifyDataSetChanged();
                    pullToRefresh.setRefreshing(false);

                }else{
                    rcAdapter.notifyDataSetChanged();
                    pullToRefresh.setLoading(false);
                    page=page+1;
                }

            }
        });
        ai.invoke();
    }

    @Override
    public void click(View v) {

    }

    @Override
    public void initData() {

    }
}
