package com.myutilsdemo.ui.filladp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.myutils.core.RowObject;
import com.myutils.ui.view.listview.BaseFillAdapter;
import com.myutils.core.annotation.ViewInject;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;
import com.myutilsdemo.ui.view.RefreshAbsListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/8/26 14:24
 * @Descrition 自动填充适配器
 */
public class BaseApdaterAct extends BaseAct {

    @ViewInject
    ListView lv;

    MAdpter mAdpter;

    List<RowObject> rows;


    @ViewInject
    RefreshAbsListView pullToRefresh;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.ui_filladp_base_act;
    }

    @Override
    public void initView(View decorView) {
        rows=new ArrayList<>();
        mAdpter=new MAdpter(BaseApdaterAct.this,rows,R.layout.ui_filladp_base_item);
        lv.setAdapter(mAdpter);
        // 加载监听器
        pullToRefresh.setOnLoadListener(new RefreshAbsListView.OnLoadListener() {

            @Override
            public void onLoad() {

                Toast.makeText(BaseApdaterAct.this, "load", Toast.LENGTH_SHORT).show();

                pullToRefresh.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        initData();
                        // 加载完后调用该方法
                        pullToRefresh.setLoading(false);
                    }
                }, 1500);

            }
        });
    }

    @Override
    public void initData() {

        for (int i = 0; i < 15; i++) {
            RowObject row=new RowObject();
            row.put("name","第"+i+"项");
            rows.add(row);
        }
        mAdpter.notifyDataSetChanged();
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
