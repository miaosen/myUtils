package com.myutils.ui.anim;

import android.view.View;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * @Created by gzpykj.com
 * @author wyc
 * @Date 2015-5-24
 * @Descrition 渐渐显示动画
 */

public class FadeIn extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view,"alpha",0,1).setDuration(mDuration)

        );
    }
}
