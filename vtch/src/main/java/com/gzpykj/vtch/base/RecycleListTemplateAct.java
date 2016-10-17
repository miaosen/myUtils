package com.gzpykj.vtch.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gzpykj.vtch.R;
import com.myutils.base.AppConfig;
import com.myutils.base.AppFactory;
import com.myutils.core.ActionResult;
import com.myutils.core.RowObject;
import com.myutils.core.annotation.ViewInject;
import com.myutils.core.logger.L;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.UIHelper;
import com.myutils.ui.view.rcview.BaseRcAdapter;
import com.myutils.ui.view.rcview.GridViewLine;
import com.myutils.ui.view.rcview.RcAdapterWithFooter;
import com.myutils.ui.view.rcview.RefreshRcView;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition recycleView模板
 */
public class RecycleListTemplateAct extends BaseAct {


    @ViewInject
    RecyclerView rc_view;

    @ViewInject
    RefreshRcView mRefresh;

    RcAdapterWithFooter rcAdapter;

    List<RowObject> rows = new LinkedList<RowObject>();


    int page = 1;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        AppConfig appCfg = AppConfig.getInstance();
        String host = appCfg.getHOST();
        L.i("host==========="+host);
        return R.layout.bs_recycle_list_act;

    }

    @Override
    public void initView(View view) {
        initHeader();
        baseHeader.leftFinish();
        baseHeader.setTitle("预约就诊");
        LinearLayoutManager layoutManager = new LinearLayoutManager(RecycleListTemplateAct.this);
        //设置布局管理器
        rc_view.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        rcAdapter = new RcAdapterWithFooter(rows, R.layout.bs_recycle_list_item) ;
        rcAdapter.setOnItemClickListener(new BaseRcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRcAdapter.ViewHolder viewHolder, RowObject row, int position) {
                UIHelper.toast("url==========="+row.getString("title"));
            }
        });
        rc_view.setAdapter(rcAdapter);
        //设置分隔线
        rc_view.addItemDecoration(new GridViewLine(RecycleListTemplateAct.this));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rows.clear();
                rcAdapter.notifyDataSetChanged();
                page = 1;
                getData("refresh");
            }
        });
        mRefresh.setOnLoadListener(new RefreshRcView.OnLoadListener() {
            @Override
            public void onLoad() {
                getData("load");
            }
        });
        mRefresh.setRefreshing(true);
        getData("refresh");
    }


    private void getData( final String type) {
        if(type.equals("refresh")) {
        }else{
            rcAdapter.showFooter();
        }
        UrlInvoker ai = AppFactory.creatUrlInvorker("http://api.avatardata.cn/GuoNeiNews/Query?key=124076155abb4e97993a181c949e9de8");
        if(page==2){
            //page=1001110;
        }
        ai.addParam("page", page);
        //ai.setDialog("loading...");
        ai.addParam("rows",10);
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(ActionResult result) {
                RowObject row = result.getRow();
                List<RowObject> resultRows = row.getRows("result");
                if(resultRows!=null){
                    rcAdapter.hideFooter();
                    rows.addAll(resultRows);
                    if(type.equals("refresh")){
                        rcAdapter.notifyDataSetChanged();
                        mRefresh.setRefreshing(false);
                    }else{
                        rcAdapter.notifyDataSetChanged();
                    }
                    page=page+1;
                }else{
                    rcAdapter.getTipLayout().notData("没有更多数据！");
                }
                mRefresh.setLoading(false);
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
