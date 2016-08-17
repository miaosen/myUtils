package com.myutils.ui.view.header;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myutils.R;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-11-2
 * @Descrition 通用头部初始化模块 注意布局文件id要和initHeader()中的id相对应,需要下面一些id 整个头部view布局id:
 *             rl_head (View) 标题id: title (TextView) 标题左边id: left_btn (Button)
 *             标题右边id: right_btn (Button)
 */
public class BaseHeader {

	public Context context;

	private View header;
	private Button headerLeft;
	private Button headerRight;
	private TextView title;

	private boolean isHeaderEnable = false;

	public BaseHeader(Context context) {
		this.context = context;
		header = ((Activity) context).findViewById(R.id.rl_head);
		initHeader();
	}

	public BaseHeader(Context context, View view) {
		this.context = context;
		header = view.findViewById(R.id.rl_head);
		initHeader();
	}

	public BaseHeader(Activity activity) {
		this.context = activity;
		header = activity.findViewById(R.id.rl_head);
		initHeader();
	}

	public BaseHeader(Activity activity, View view) {
		this.context = activity;
		header = activity.findViewById(R.id.rl_head);
		initHeader();
	}

	/**
	 * 初始化
	 */
	private void initHeader() {
		if (header == null) {
			
		} else {
			isHeaderEnable = true;
			headerLeft = (Button) header.findViewById(R.id.left_btn);
			headerRight = (Button) header.findViewById(R.id.right_btn);
			title = (TextView) header.findViewById(R.id.title);
		}
	}

	/**
	 * 设置左边问题
	 * 
	 * @param name
	 */
	public BaseHeader setLeft(String name) {
		headerLeft.setText(name);
		return this;
	}

	/**
	 * 隐藏左边
	 */
	public BaseHeader invisibleLeft() {
		headerLeft.setVisibility(View.INVISIBLE);
		return this;
	}

	public BaseHeader setLeftFinish() {
		headerLeft.setOnClickListener(new mClick());
		return this;
	}

	public BaseHeader setRight(String name) {
		headerRight.setText(name);
		return this;
	}

	public BaseHeader setRightFinish() {
		headerRight.setOnClickListener(new mClick());
		return this;
	}

	public BaseHeader setTitle(String name) {
		title.setText(name);
		return this;
	}

	class mClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			((Activity) context).finish();
		}
	}

	public View getHeader() {
		return header;
	}

	public void setHeader(View header) {
		this.header = header;
	}

	public Button getHeaderLeft() {
		return headerLeft;
	}

	public void setHeaderLeft(Button headerLeft) {
		this.headerLeft = headerLeft;
	}

	public Button getHeaderRight() {
		return headerRight;
	}

	public void setHeaderRight(Button headerRight) {
		this.headerRight = headerRight;
	}

	public TextView getTitle() {
		return title;
	}

	public void setTitle(TextView title) {
		this.title = title;
	}

	public boolean isHeaderEnable() {
		return isHeaderEnable;
	}

	public void setHeaderEnable(boolean isHeaderEnable) {
		this.isHeaderEnable = isHeaderEnable;
	}

}
