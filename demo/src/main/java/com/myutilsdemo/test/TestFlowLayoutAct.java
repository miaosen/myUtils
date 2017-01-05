package com.myutilsdemo.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.myutils.base.BaseActivity;
import com.myutils.core.annotation.ViewInject;
import com.myutils.ui.view.FlowLayout;
import com.myutilsdemo.R;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016-10-18  15:52
 * @Descrition
 */
public class TestFlowLayoutAct extends BaseActivity {

    @ViewInject
    FlowLayout flowlayout;
    @ViewInject
    Button btn;

    int size =0;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.test_flowlayout02;
    }

    @Override
    public void initView(View decorView) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // TextView button=new TextView(TestFlowLayoutAct.this);
                notifyView();
            }
        });

        //flowlayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
        //    @Override
        //    public void onItemClick(int position) {
        //        UIHelper.toast("你点击了第 "+position+" 项");
        //    }
        //});
    }


    public void notifyView(){
        flowlayout.removeAllViews();
        size = size +1;
        for (int i = 0; i < size; i++) {
            ViewGroup view= (ViewGroup) LayoutInflater.from(TestFlowLayoutAct.this).inflate(R.layout.file_atmview_item,null);
            view.setTag("aaaaaaaaa"+i);
            //LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) view.getChildAt(0).getLayoutParams();
            //L.i("=========notifyView=============="+JSONSerializer.toJson(lp));
            flowlayout.addView(view);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void click(View v) {

    }
}
