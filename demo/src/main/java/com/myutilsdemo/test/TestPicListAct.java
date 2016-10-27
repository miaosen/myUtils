package com.myutilsdemo.test;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.myutils.core.ActionResult;
import com.myutils.core.RowObject;
import com.myutils.core.logger.L;
import com.myutils.core.okhttp.UrlInvoker;
import com.myutils.core.okhttp.callback.StringCallBack;
import com.myutils.ui.UIHelper;
import com.myutils.ui.view.LoadingTipLayout;
import com.myutils.ui.view.rcview.BaseRcAdapter;
import com.myutils.ui.view.rcview.PagingRcListAct;
import com.myutils.ui.view.rcview.RcAdapterWithFooter;
import com.myutilsdemo.R;

import java.util.List;
import java.util.Map;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016-10-25  10:10
 * @Descrition
 */

public class TestPicListAct  extends PagingRcListAct{
    @Override
    public int setItemLayout() {
        setPageIndexText("pn");
        return R.layout.test_piclist_item;
    }

    @Override
    public UrlInvoker setDataResourse() {
        UrlInvoker ui=new UrlInvoker("http://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&is=&fp=result&queryWord=%E9%A3%8E%E6%99%AF&cl=2&lm=-1&ie=utf-8&oe=utf-8&st=-1&z=&ic=0&word=%E9%A3%8E%E6%99%AF");
        return ui;
    }

    @Override
    public void setPageItem(BaseRcAdapter.ViewHolder viewHolder, RowObject row, int position) {
        Map<String, View> viewWithIdName = viewHolder.fillUnit.getViewWithIdName();
        SimpleDraweeView draweeView = (SimpleDraweeView) viewWithIdName.get("simpleDraweeView");
        String middleURL = row.getString("middleURL");
        if(middleURL!=null){
            Uri uri = Uri.parse(row.getString("middleURL"));
            L.i("uri============="+uri);
            draweeView.setImageURI(uri);
        }

    }

    /**
     * 获取数据
     *
     * @param type
     */
    @Override
    public void getData(final String type) {
        if (type.equals("refresh")) {
            rcAdapter.hideFooter();
        } else {
            rcAdapter.showFooter();
        }
        if (ai == null) {
            ai = setDataResourse();
        }
        ai.addParam("pn", pageIndex);
        //ai.addParam(pageSizeText, pageSize);
        ai.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(ActionResult result) {
                onResultCallBack(result, type);
            }
        });
        ai.invoke();
    }


    @Override
    public void initRcView() {
        GridLayoutManager layoutManager = new GridLayoutManager(TestPicListAct.this,3);
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

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.test_piclist_act;
    }

    /**
     * 结果回调
     *
     * @param result
     * @param type   刷新还是加载更多
     */
    @Override
    protected void onResultCallBack(ActionResult result, String type) {
        RowObject row = result.getRow();
            List<RowObject> resultRows = row.getRows("data");
            if (resultRows != null && resultRows.size() > 0) {
                rcAdapter.hideFooter();
                rows.addAll(resultRows);
                if (type.equals("refresh")) {
                    rcAdapter.notifyDataSetChanged();
                } else {
                    rcAdapter.notifyDataSetChanged();
                }
                pageIndex = pageIndex + 30;
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
