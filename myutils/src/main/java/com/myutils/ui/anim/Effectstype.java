package com.myutils.ui.anim;



/**
 * @Created by gzpykj.com
 * @author wyc
 * @Date 2015-5-19
 * @Descrition 动画效果类型枚举类
 */


public enum  Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects=null;
	try {
		bEffects = effectsClazz.newInstance();
	} catch (ClassCastException e) {
		throw new Error("不能初始化动画实例");
	} catch (InstantiationException e) {
		throw new Error("不能初始化动画实例");
	} catch (IllegalAccessException e) {
		throw new Error("不能初始化动画实例");
	}
	return bEffects;
    }
}
