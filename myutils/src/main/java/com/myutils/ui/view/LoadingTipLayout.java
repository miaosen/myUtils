package com.myutils.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myutils.R;
import com.myutils.core.annotation.InjectReader;
import com.myutils.core.annotation.ViewInject;
import com.myutils.utils.ViewUtils;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-5-13
 * @Descrition 加载中提示布局
 */

public class LoadingTipLayout extends FrameLayout{
	
	private Context context;
	
	private FrameLayout viewTip;
	@ViewInject
	private LinearLayout ln_loading;
	@ViewInject
	private LinearLayout ln_error;
	@ViewInject
	private LinearLayout ln_not_data;
	@ViewInject
	private TextView tv_not_data;
	
	
	public LoadingTipLayout(Context context) {
		super(context);
		this.context=context;
		init();
	}

	public LoadingTipLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init();
		
	}

	private void init() {
		if(!isInEditMode()){
		viewTip = (FrameLayout) ViewUtils.inflatView(context, R.layout.ui_view_loadingtip);
		this.addView(viewTip, 0);
		//解决xml预览保存
		InjectReader.injectAllFields(this, viewTip);
		}
	}
	
	/**
	 * 加载中
	 */
	public void loading() {
		tip();
		ln_loading.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 加载中
	 */
	public void notData() {
		tip();
		ln_not_data.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 加载中
	 */
	public void notData(String text) {
		tip();
		tv_not_data.setText(text);
		ln_not_data.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 加载中
	 */
	public void error() {
		 tip();
		ln_error.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏所有布局，显示提示布局
	 */
	private void tip(){
		hideAllView();
		viewTip.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏所有的子类布局
	 */
	public void hideAllView() {
		int childCount = this.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view=this.getChildAt(i);
			view.setVisibility(View.GONE);
		}
		int tipChildCount = viewTip.getChildCount();
		for (int i = 0; i < tipChildCount; i++) {
			View view=viewTip.getChildAt(i);
			view.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 显示所有的子类布局
	 */
	public void showAllView() {
		int childCount = this.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view=this.getChildAt(i);
			view.setVisibility(View.VISIBLE);
		}
		int tipChildCount = viewTip.getChildCount();
		for (int i = 0; i < tipChildCount; i++) {
			View view=viewTip.getChildAt(i);
			view.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 结束
	 */
	public void finish(){
		int childCount = this.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view=this.getChildAt(i);
			view.setVisibility(View.VISIBLE);
		}
		viewTip.setVisibility(View.GONE);
	}


	public void setContext(Context context) {
		this.context = context;
	}

	public FrameLayout getViewTip() {
		return viewTip;
	}

	public void setViewTip(FrameLayout viewTip) {
		this.viewTip = viewTip;
	}

	public LinearLayout getLn_loading() {
		return ln_loading;
	}

	public void setLn_loading(LinearLayout ln_loading) {
		this.ln_loading = ln_loading;
	}

	public LinearLayout getLn_error() {
		return ln_error;
	}

	public void setLn_error(LinearLayout ln_error) {
		this.ln_error = ln_error;
	}

	public LinearLayout getLn_not_data() {
		return ln_not_data;
	}

	public void setLn_not_data(LinearLayout ln_not_data) {
		this.ln_not_data = ln_not_data;
	}

	
	
}
