package com.myutilsdemo.ui.view;

import android.util.Log;
import android.view.MotionEvent;

import com.myutils.core.logger.L;
import com.myutils.utils.DPUtils;

/**
 * Created by OAIM on 2016/9/8.
 */
public class TouchEventHelp {

    MotionEvent event;

    //从按下到离开屏幕的坐标距离
    float moveY;
    float moveX;


    public TouchEventHelp(){
    }

    public void analysis(MotionEvent event) {
        int action = event.getAction();
        //按下坐标
        float downX;
        float downY;

        if (action == MotionEvent.ACTION_DOWN) {
            downX = event.getX();
            downY = event.getY();
            L.i("ACTION_DOWN==========" + downY);
            L.i("ACTION_DOWN==========" + DPUtils.px2dip(downY));
        }
        if (action == MotionEvent.ACTION_UP) {
            L.i("ACTION_UP=========="+ event.getY());
            scroll(event);
        }
        if (action == MotionEvent.ACTION_MOVE) {
            Log.i("logtag","ACTION_MOVE=========="+ event.getY());
        }
    }

    private void scroll(MotionEvent event) {
        int disance = DPUtils.px2dip(event.getY());

    }

    public boolean isMoveUp(){
        return false;
    }

    public boolean isMoveDown(){
        return false;
    }


}
