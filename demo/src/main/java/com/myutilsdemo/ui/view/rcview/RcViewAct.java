package com.myutilsdemo.ui.view.rcview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.myutils.base.AppFactory;
import com.myutils.core.JSONResult;
import com.myutils.core.RowObject;
import com.myutils.core.annotation.ViewInject;
import com.myutils.core.http.UrlInvoker;
import com.myutils.core.http.callback.StringCallBack;
import com.myutils.ui.UIHelper;
import com.myutils.ui.dialog.MsgDialog;
import com.myutils.ui.view.rcview.BaseRcAdapter;
import com.myutils.ui.view.rcview.GridViewLine;
import com.myutils.ui.view.rcview.PagingListRcView;
import com.myutils.ui.view.rcview.RcAdapterWithFooter;
import com.myutils.ui.view.rcview.RefreshRcView;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;

import java.util.LinkedList;
import java.util.List;

import static com.myutilsdemo.R.id.mRefresh;
import static com.myutilsdemo.R.id.rc_view;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition
 */
public class RcViewAct extends BaseAct {

    @ViewInject
    PagingListRcView paging_list;


    MsgDialog msgDialog;
    WebView webView;


    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.ui_view_recycleview;
    }

    @Override
    public void initView(View view) {

        //开启拖动、移除动画
        //ItemTouchCallback callback = new ItemTouchCallback() {
        //    @Override
        //    public boolean isLongPressDragEnabled() {
        //        // 长按拖拽打开
        //        return true;
        //    }
        //};
        //ItemTouchHelper helper = new ItemTouchHelper(callback);
        //helper.attachToRecyclerView(rc_view);

        RcAdapterWithFooter rcAdapter =paging_list.getAdpRc();
        rcAdapter.setOnItemClickListener(new BaseRcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRcAdapter.ViewHolder viewHolder, RowObject row, int position) {
                UIHelper.toast("url==========="+row.getString("title"));
               // getNew(row.getString("url"));
            }
        });
        //设置增加或删除条目的动画
        //rc_view.setItemAnimator(new DefaultItemAnimator());
        //设置分隔线
        //RecyclerView rc_view=paging_list.getRecycler_view();
        //rc_view.addItemDecoration(new GridViewLine(RcViewAct.this));
        if(msgDialog==null){
            msgDialog=new MsgDialog();
            msgDialog.setTitle("新闻弹窗");
        }
        String url="http://api.avatardata.cn/Cook/List";
        paging_list.setUrl(url);
        UrlInvoker ai = paging_list.getUik();
        ai.addParam("key","8858c93a7665449784b19a421aba0059");
        paging_list.setOnDataAnalysisListener(new PagingListRcView.OnDataAnalysisListener() {
            @Override
            public LinkedList<RowObject> onAnalysis(JSONResult jsonResult) {
                return jsonResult.getAsRow().getRows("result");
            }
        });
        paging_list.setPageIndexText("page");
        paging_list.setPageSizeText("rows");
        paging_list.setPageSize(20);
        paging_list.load();

    }

    private void getNew(String url) {
        UrlInvoker ai = AppFactory.creatUrlInvorker(url);
        //ai.setDialog("loading...");
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(JSONResult result) {
                webView=new WebView(RcViewAct.this);
                WebSettings wSet = webView.getSettings();
                wSet.setJavaScriptEnabled(true);
                msgDialog.setContentView(webView);
                webView.loadData(result.getAsText(),"text/html","utf-8");
                webView.setBackgroundColor(Color.parseColor("#00ffff"));
                msgDialog.show(getSupportFragmentManager(),"news");
                //msgDialog.setMsg(result.getResponseJsonText());
               // UIHelper.toast("你选择了 " + result.getResponseJsonText());
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
