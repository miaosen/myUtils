package com.myutils.ui.anim;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * @Created by gzpykj.com
 * @author wyc
 * @Date 2015-5-24
 * @Descrition 底部向上动画
 */


public class SlideBottom extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationY", 300, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration*3/2)

        );
    }
}