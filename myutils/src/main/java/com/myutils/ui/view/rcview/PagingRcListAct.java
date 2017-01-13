package com.myutils.ui.view.rcview;

import android.view.View;

import com.myutils.base.BaseActivity;
import com.myutils.core.RowObject;
import com.myutils.core.annotation.ViewInject;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 10:38
 * @Descrition 就诊活动选择
 */
public abstract class PagingRcListAct extends BaseActivity {


    @ViewInject
    public PagingListRcView pagingListRcView;

    public abstract void setPageItem(BaseRcAdapter.ViewHolder viewHolder, RowObject row, int position);


    @Override
    public void initView(View view) {
        pagingListRcView.getAdpRc().setOnItemDetailListener(new BaseRcAdapter.OnItemDetailListener() {
            @Override
            public void setItem(BaseRcAdapter.ViewHolder viewHolder, RowObject row, int position) {
                setPageItem(viewHolder,row,position);
            }
        });
    }


    @Override
    public void click(View v) {

    }

    @Override
    public void initData() {

    }


}
