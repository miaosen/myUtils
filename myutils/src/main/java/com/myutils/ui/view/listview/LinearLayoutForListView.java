package com.myutils.ui.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016年4月8日
 * @Descrition 解决ScrollView嵌套ListView ListView重复刷新的问题
 * 
 */
public class LinearLayoutForListView extends LinearLayout {

	private Context context;
	private OnClickListener onClickListener = null;

	public LinearLayoutForListView(Context context) {
		super(context);
	}

	// 在xml定义时必须引用AttributeSet参数
	public LinearLayoutForListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}


	//TODO adapter.notifyDataSetChanged();无效
	public void setAdapter(BaseAdapter adapter) {
		int count = adapter.getCount();
		this.removeAllViews();
		for (int i = 0; i < count; i++) {
			View v = adapter.getView(i, null, null);
			v.setOnClickListener(this.onClickListener);
			addView(v, i);
		}
	}
	
	

}
