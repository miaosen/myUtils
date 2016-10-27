package com.myutils.ui.view.listview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.myutils.R;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016年4月8日
 * @Descrition 解决ScrollView嵌套ListView ListView重复刷新的问题,没有重用功能，数据量较少时推荐使用
 * 
 */
public class LinearLayoutForGridView extends LinearLayout {

	private Context context;
	private OnClickListener onClickListener = null;
	private AttributeSet attrs;

	private int column=4;

	public LinearLayoutForGridView(Context context) {
		super(context);
	}

	// 在xml定义时必须引用AttributeSet参数
	public LinearLayoutForGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.attrs=attrs;
		parseAttributes();
		initStyle();
	}

	private void initStyle() {
		setOrientation(LinearLayout.HORIZONTAL);

	}


	/**
	 * 解析从XML传递给视图的属性
	 */
	private void parseAttributes() {
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.SecondsView);
		column = typeArray.getColor(R.styleable.SecondsView_ttextColor,
				4);
		typeArray.recycle();
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
