package com.myutils.ui.view.rcview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.myutils.base.BaseActivity;
import com.myutils.core.ActionResult;
import com.myutils.core.RowObject;
import com.myutils.core.annotation.ViewInject;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.UIHelper;
import com.myutils.ui.view.LoadingTipLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition 就诊活动选择
 */

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/10/18 11:44
 * @Descrition 
 */
public abstract class PagingRcListAct extends BaseActivity {


    @ViewInject
    public RecyclerView rc_view;

    @ViewInject
    public RefreshRcView mRefresh;

    public RcAdapterWithFooter rcAdapter;

    public List<RowObject> rows = new LinkedList<RowObject>();


    //页码参数
    public String pageIndexText = "pageNum";
    //页数参数
    public String pageSizeText = "pageSize";
    //页码
    public int pageIndex = 1;
    //分页大小
    public int pageSize = 10;

    //数据源
    public UrlInvoker ai;


    public abstract int setItemLayout();

    public abstract UrlInvoker setDataResourse();

    public abstract void setPageItem(BaseRcAdapter.ViewHolder viewHolder, RowObject row, int position);


    @Override
    public void initView(View view) {
        initRcView();
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rows.clear();
                rcAdapter.notifyDataSetChanged();
                pageIndex = 1;
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


    public void initRcView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(PagingRcListAct.this);
        //设置布局管理器
        rc_view.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        rcAdapter = new RcAdapterWithFooter(rows, setItemLayout()) {
            @Override
            public void setItem(ViewHolder viewHolder, RowObject row, int position) {
                super.setItem(viewHolder, row, position);
                setPageItem(viewHolder, row, position);

            }
        };
        rc_view.setAdapter(rcAdapter);
    }


    /**
     * 获取数据
     *
     * @param type
     */
    public void getData(final String type) {
        if (type.equals("refresh")) {
            rcAdapter.hideFooter();
        } else {
            rcAdapter.showFooter();
        }
        if (ai == null) {
            ai = setDataResourse();
        }
        ai.addParam(pageIndexText, pageIndex);
        ai.addParam(pageSizeText, pageSize);
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(ActionResult result) {
                onResultCallBack(result, type);
            }
        });
        ai.invoke();
    }

    public void refresh(){
        rows.clear();
        getData("refresh");
    }

    /**
     * 结果回调
     *
     * @param result
     * @param type   刷新还是加载更多
     */
    protected void onResultCallBack(ActionResult result, String type) {
        RowObject row = result.getRow();
        if (result.isSuccess()) {
            List<RowObject> resultRows = row.getRows("data");
            if (resultRows != null && resultRows.size() > 0) {
                rcAdapter.hideFooter();
                rows.addAll(resultRows);
                if (type.equals("refresh")) {
                    rcAdapter.notifyDataSetChanged();
                } else {
                    rcAdapter.notifyDataSetChanged();
                }
                pageIndex = pageIndex + 1;
            } else {
                if (type.equals("refresh")) {
                    UIHelper.toast("没有数据！");
                }
                LoadingTipLayout tipLayout = rcAdapter.getTipLayout();
                if (tipLayout != null) {
                    rcAdapter.getTipLayout().notData("没有更多数据！");
                    rcAdapter.hideFooter(1);
                }
            }
            mRefresh.setRefreshing(false);
            mRefresh.setLoading(false);
        }
    }

    @Override
    public void click(View v) {

    }

    @Override
    public void initData() {

    }

    public String getPageIndexText() {
        return pageIndexText;
    }

    public void setPageIndexText(String pageIndexText) {
        this.pageIndexText = pageIndexText;
    }

    public String getPageSizeText() {
        return pageSizeText;
    }

    public void setPageSizeText(String pageSizeText) {
        this.pageSizeText = pageSizeText;
    }
}
