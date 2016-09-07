package com.myutilsdemo.ui.fgm.adp;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.myutilsdemo.base.BaseFgm;

import java.util.List;

/**
 * Created by OAIM on 2016/8/23.
 */
public class PageAdp extends FragmentPagerAdapter {

    private List<BaseFgm> fragments;


    public PageAdp(FragmentManager fm, List<BaseFgm> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }



}
