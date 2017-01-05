package com.myutilsdemo.ui.view.refresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.myutils.base.L;
import com.myutils.ui.UIHelper;
import com.myutils.utils.DPUtils;
import com.myutilsdemo.R;
import com.myutilsdemo.ui.view.TouchEventHelp;

/**
 * Created by OAIM on 2016/9/8.
 */
public class MyRefresh extends RelativeLayout {

    TouchEventHelp touchEventHelp;

    View mTarget;

    //从按下到离开屏幕的y坐标距离
    float moveSpace = 0;

    //上次按下时Y坐标
    float downY = 0;

    //首次移动距离
    //float moveY = 0;
    //
    //float disanceY = 0;


    //是否上拉
    private boolean isUp = true;

    View headerView;

    View footerView;

    public MyRefresh(Context context) {
        super(context);
        init();
    }

    public MyRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public MyRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        // TODO: 2016/9/8
        headerView = findViewById(R.id.header);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("logtag", "onTouchEvent=====");
        int action = event.getAction();
        //按下
        if (action == MotionEvent.ACTION_DOWN) {
            downY = event.getY();
            // moveY = event.getY();
        }
        //移动
        if (action == MotionEvent.ACTION_MOVE) {
            logicMove(event);
        }
        //放开
        if (action == MotionEvent.ACTION_UP) {
            moveSpace = event.getY() - downY;
            //移动距离大于或者大于50dp就可以移动
            if (DPUtils.px2dip(moveSpace) >= 100) {
                UIHelper.toast("刷新...");
            }
            scrollIn(event);
        }
        return true;
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.i("logtag", "onInterceptTouchEvent=======");
        final int action = MotionEventCompat.getActionMasked(event);
        //
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                logicMove(event);
                return isIntercept(event);
            case MotionEvent.ACTION_UP:
                Log.i("logtag", "onInterceptTouchEvent=====ACTION_UP");
                scrollIn(event);
                break;
        }
        return false;
    }



    private void logicMove(MotionEvent event) {
        float space = event.getY() - downY;
        if (space < 0) {
            isUp = true;
        } else {
            isUp = false;
        }
        Log.i("logtag", "onTouchEvent==ACTION_MOVE===isUp==" + isUp);
        if (!canChildScrollDown()) {
            scrollFooter(space);
        } else if (!canChildScrollUp()) {
            scrollHead(space);
        } else {
           //scrollFooter(space);
        }
        downY = event.getY();
    }

    private boolean isFooterVisible() {
        if(footerView==null){
            footerView = findViewById(R.id.footer);
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) footerView.getLayoutParams();
        int bottomMargin = params.bottomMargin;
        L.i("isFooterVisible====="+bottomMargin);
        return -DPUtils.dip2px(100) < bottomMargin &&bottomMargin< 0;
    }

    private boolean isHeaderVisible() {
        if(headerView==null){
            headerView = findViewById(R.id.header);
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) headerView.getLayoutParams();
        int bottomMargin = params.bottomMargin;
        L.i("isHeaderVisible====="+bottomMargin);
        return -DPUtils.dip2px(100) < bottomMargin &&bottomMargin< 0;
    }

    /**
     * 是否拦截
     *
     * @param event
     */
    private boolean isIntercept(MotionEvent event) {
        if (!canChildScrollDown() && isUp) {
            return false;
        } else if (!canChildScrollUp() &&isHeaderVisible()) {
            return false;
        } else {
            return false;
        }
    }


    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        // if this is a List < L or another view that doesn't support nested
        // scrolling, ignore this request so that the vertical scroll event
        // isn't stolen
        if ((android.os.Build.VERSION.SDK_INT < 21 && mTarget instanceof AbsListView)
                || (mTarget != null && !ViewCompat.isNestedScrollingEnabled(mTarget))) {
            // Nope.
            L.i("requestDisallowInterceptTouchEvent");
        } else {
            super.requestDisallowInterceptTouchEvent(b);
        }
    }


    /**
     * 子View可以向上滚动
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean canChildScrollUp() {

        if (mTarget == null) {
            mTarget = findViewById(R.id.lv);
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                L.i("position=======" + absListView.getLastVisiblePosition());
                return absListView.getChildCount() > 0
                        && ((absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop()));
            } else {

                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    /**
     * 子View可以向上滚动
     *
     * @return
     */
    //TODO
    public boolean canChildScrollDown() {
        if (mTarget == null) {
            mTarget = findViewById(R.id.lv);
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                L.i("position=======" + absListView.getLastVisiblePosition());
                return absListView.getChildCount() > 0
                        && ((absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop()));
            } else {

                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, 1);
        }
    }


    /**
     * 显示头部
     *
     * @param space
     */
    public void scrollHead(float space) {
        if (headerView == null) {
            headerView = findViewById(R.id.header);
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) headerView.getLayoutParams();
        int topMargin = (int) (params.topMargin + space);
        L.i("disanceMoveY==========scrollHead====" + params.topMargin + "   " + space);
        if (topMargin > 0) {
            topMargin = 0;
        }
        if (topMargin < -DPUtils.dip2px(100)) {
            topMargin = -DPUtils.dip2px(100);
        }
        params.setMargins(params.leftMargin, topMargin, params.rightMargin, params.bottomMargin);
        headerView.setLayoutParams(params);


        invalidate();

    }


    /**
     * 显示尾部
     *
     * @param space
     */
    public void scrollFooter(float space) {
        if (footerView == null) {
            footerView = findViewById(R.id.footer);
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) footerView.getLayoutParams();
        int bottomMargin = (int) (params.bottomMargin - space);
        Log.i("logtag", "scrollFooter===bottomMargin=======0===" + params.bottomMargin+"  "+space);
        if (bottomMargin > 0) {
            bottomMargin = 0;
        }
        if (bottomMargin < -DPUtils.dip2px(50)) {
            bottomMargin = -DPUtils.dip2px(50);
        }

        params.bottomMargin=0;
        Log.i("logtag", "bottomMargin=======" + bottomMargin);
        params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, 0);
        footerView.setLayoutParams(params);

        RelativeLayout.LayoutParams mTargetLayoutParams = (RelativeLayout.LayoutParams) mTarget.getLayoutParams();
        int mTargetTopMargin = (int) (mTargetLayoutParams.bottomMargin - space);
        if (mTargetTopMargin > DPUtils.dip2px(50)) {
            mTargetTopMargin = DPUtils.dip2px(50);
        }
        if (mTargetTopMargin < 0) {
            mTargetTopMargin = 0;
        }
        mTargetLayoutParams.setMargins(params.leftMargin, params.topMargin, params.rightMargin,DPUtils.dip2px(100) );
        mTarget.setLayoutParams(mTargetLayoutParams);



    }

    private void scrollIn(MotionEvent event) {
        if (headerView == null) {
            headerView = findViewById(R.id.header);
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) headerView.getLayoutParams();
        int topMargin = -DPUtils.dip2px(100);
        params.setMargins(params.leftMargin, topMargin, params.rightMargin, params.bottomMargin);
        headerView.setLayoutParams(params);
        invalidate();
    }


}
