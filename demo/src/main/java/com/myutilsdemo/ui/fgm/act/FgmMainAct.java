package com.myutilsdemo.ui.fgm.act;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.myutils.core.annotation.ViewInject;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;
import com.myutilsdemo.base.BaseFgm;
import com.myutilsdemo.core.CoreMainFgm;
import com.myutilsdemo.ui.UIMainFgm;
import com.myutilsdemo.ui.fgm.adp.PageAdp;
import com.myutilsdemo.ui.dlg.DialogAct;
import com.myutilsdemo.ui.filladp.BaseApdaterAct;
import com.myutilsdemo.unit.UnitMainFgm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OAIM on 2016/8/22.
 */
public class FgmMainAct extends BaseAct {

    private Context context;

    @ViewInject
    private TabLayout tabLayout;

    private PageAdp pagerAdp;

    private List<BaseFgm> fragments = new ArrayList<BaseFgm>();

    private String[] menuTitle = new String[]{
            "核心","UI","单元"
    };

    @ViewInject
    ViewPager vpager;

    @ViewInject
    Toolbar toolbar;




    @Override
    public int initConfig(Bundle savedInstanceState) {
        context = this;
        return R.layout.ui_fgm_main_act;
    }

    @Override
    public void initView(View decorView) {
        // toolbar.setTitle("弹窗例子");
        setSupportActionBar(toolbar);


        CoreMainFgm coreMainFgm = new CoreMainFgm();
        fragments.add(coreMainFgm);


        UIMainFgm uiMainFgm = new UIMainFgm();
        fragments.add(uiMainFgm);

        UnitMainFgm unitMainFgm=new UnitMainFgm();
        fragments.add(unitMainFgm);

//        AtmAct atmFgm=new AtmAct();
//        fragments.add(atmFgm);
//
//        RcViewAct recycleViewDemo=new RcViewAct();
//        fragments.add(recycleViewDemo);


        pagerAdp = new PageAdp(getSupportFragmentManager(), fragments);
        vpager.setAdapter(pagerAdp);
        //让ViewPager缓存5个页面
        vpager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(vpager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(getTabItem(i)).setText(menuTitle[i]);
        }



    }

    private View getTabItem(int i) {
        View viewItem = LayoutInflater.from(context).inflate(R.layout.ui_fgm_main_tab_item,null);
        TextView tv= (TextView) viewItem.findViewById(R.id.tv);
        tv.setText(menuTitle[i]);
        ImageButton img_btn= (ImageButton) viewItem.findViewById(R.id.img_btn);
        //img_btn.setImageResource(R.drawable.icon_video);
        return viewItem;
    }


    @Override
    public void initData() {

    }

    @Override
    public void click(View v) {

    }


}
