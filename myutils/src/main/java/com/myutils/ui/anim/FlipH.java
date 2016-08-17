package com.myutils.ui.anim;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;


/**
 * @Created by gzpykj.com
 * @author wyc
 * @Date 2015-5-24
 * @Descrition 翻转动画
 */

public class FlipH  extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "rotationY", -90, 0).setDuration(mDuration)

        );
    }
}