package com.myutils.ui.view.rcview;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.myutils.core.form.ViewUtils;

import java.util.List;


/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/2 11:22
 * @Descrition 为RecyclerView加上加载更多功能，比如listView gridview
 */
public class RefreshRcView extends SwipeRefreshLayout{

    /**
     * listview实例
     */
    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    /**
     *  滑动到最下面时的上拉操作
     */
    private int mTouchSlop=0;
    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;
    /**
     * ListView的加载中footer
     */
    private View mListViewFooter;

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;


    public RefreshRcView(Context context) {
        super(context);
    }

    public RefreshRcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化ListView对象
        if (recyclerView == null) {
            getRecyclerView();
        }
    }

    /**
     * 获取ListView对象
     */
    private void getRecyclerView() {
        int childs = getChildCount();
        if (childs > 0) {
            List<View> allChildViews = ViewUtils.getAllChildViews(this);
            for (int i = 0; i < allChildViews.size(); i++) {
                View childView = getChildAt(i);
                if (childView instanceof RecyclerView) {
                    recyclerView = (RecyclerView) childView;
                    layoutManager=recyclerView.getLayoutManager();
                    // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            // 滚动时到了最底部也可以加载更多
                            if (canLoad()) {
                                loadData();
                            }
                            super.onScrolled(recyclerView, dx, dy);
                        }
                    });
                    i=allChildViews.size();
                }
            }
        }
    }


    /**
     * 触摸事件
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {
        if (layoutManager != null && layoutManager.getItemCount() != 0) {
            if(layoutManager instanceof LinearLayoutManager){
                LinearLayoutManager layout= (LinearLayoutManager) layoutManager;
                return layout.findLastVisibleItemPosition() ==(layout.getItemCount() - 1);
            }else if(layoutManager instanceof GridLayoutManager){
                GridLayoutManager layout= (GridLayoutManager) layoutManager;
                return layout.findLastVisibleItemPosition() ==(layout.getItemCount() - 1);
            }else if(layoutManager instanceof StaggeredGridLayoutManager){
                //TODO
                StaggeredGridLayoutManager layout= (StaggeredGridLayoutManager) layoutManager;
                int lastPosition[]=null;
                lastPosition=  layout.findLastVisibleItemPositions(lastPosition);
                return getMaxInt(lastPosition) ==(layout.getItemCount() - 1);
            }
        }
        return false;
    }

    /**
     * 获取数组的最大值
     * @param args
     * @return
     */
    public static int getMaxInt(int [] args){
        int max=0;
        for (int i = 0; i < args.length; i++) {
            int arg = args[i];
            if(arg>max){
                max=arg;
            }
        }
        return max;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }

    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
             // recyclerView.addFooterView(mAbsListViewFooter);
        } else {
            //   recyclerView.removeFooterView(mAbsListViewFooter);
            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }



    /**
     * 加载更多的监听器
     *
     * @author mrsimple
     */
    public interface OnLoadListener {
        void onLoad();
    }
}
