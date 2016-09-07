package com.myutils.ui.view.listview;

/**
 * Created by OAIM on 2016/1/5.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


public class GridViewForScrollView extends GridView {
    public GridViewForScrollView(Context context) {
        super(context);

    }

    public GridViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}