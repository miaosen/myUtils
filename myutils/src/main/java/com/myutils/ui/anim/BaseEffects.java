package com.myutils.ui.anim;

import android.view.View;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.view.ViewHelper;

/**
 * @Created by gzpykj.com
 * @author wyc
 * @Date 2015-5-19
 * @Descrition 动画效果基础类
 */ 

public abstract  class BaseEffects {

    private static final int DURATION = 1 * 700;

    protected long mDuration =DURATION ;

    private AnimatorSet mAnimatorSet;

    {
        mAnimatorSet = new AnimatorSet();
    }

    protected abstract void setupAnimation(View view);

    public void start(View view) {
        reset(view);
        setupAnimation(view);
        mAnimatorSet.start();
    }
    public void reset(View view) {
        ViewHelper.setPivotX(view, view.getMeasuredWidth() / 2.0f);
        ViewHelper.setPivotY(view, view.getMeasuredHeight() / 2.0f);
    }


    public AnimatorSet getAnimatorSet() {
        return mAnimatorSet;
    }
    
    public void setDuration(long duration) {
        this.mDuration = duration;
    }

}
