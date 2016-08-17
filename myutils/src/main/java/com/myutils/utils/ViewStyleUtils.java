package com.myutils.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myutils.R;


public class ViewStyleUtils {
	
	public static int widthDP10;
	
	
	/**
	 * btn左下圆角style
	 * @param context
	 * @param btn
	 */
	public static void setBtnStyLeft(Context context,Button btn){
		LinearLayout.LayoutParams layouttParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		layouttParams.weight = 1;
		btn.setLayoutParams(layouttParams);
		btn.setBackgroundResource(R.drawable.sel_share_blue_lt_left);
		btn.setTextColor(context.getResources().getColor(R.color.blue_deep));
		btn.setText("确定");
		btn.setTextSize(16);
	}
	
	/**
	 * btn右下圆角style
	 * @param context
	 * @param btn
	 */
	public static void setBtnStyRight(Context context,Button btn){
		LinearLayout.LayoutParams layouttParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		layouttParams.weight = 1;
		btn.setLayoutParams(layouttParams);
		btn.setBackgroundResource(R.drawable.sel_share_blue_lt_right);
		btn.setTextColor(context.getResources().getColor(R.color.blue_deep));
		btn.setText("取消");
		btn.setTextSize(16);
	}
	
	/**
	 * btn底部圆角style
	 * @param context
	 * @param btn
	 */
	public static void setBtnStySingle(Context context,Button btn){
		LinearLayout.LayoutParams layouttParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		layouttParams.weight = 1;
		btn.setLayoutParams(layouttParams);
		btn.setBackgroundResource(R.drawable.eg_button_single_selector);
		btn.setTextColor(context.getResources().getColor(R.color.blue_deep));
		btn.setText("确定");
		btn.setTextSize(16);
	}
	
	public static void setIVStyVertical(Context context,ImageView iv){
		LinearLayout.LayoutParams layouttParams = new LinearLayout.LayoutParams(2,LayoutParams.WRAP_CONTENT );
		iv.setLayoutParams(layouttParams);
		iv.setBackgroundColor(context.getResources().getColor(R.color.alertdialog_line));
	}
	
	/**
	 * TV Style 01
	 * @param context
	 * @param tv
	 */
	
	public static void setTVSty01(Context context,TextView tv){
		LinearLayout.LayoutParams layouttParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		layouttParams.setMargins(15, 15, 15, 0);
		tv.setLayoutParams(layouttParams);
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(context.getResources().getColor(R.color.text_color));
		tv.setTextSize(16);
	}

	/**
	 * checkbox style 01
	 * @param context
	 * @param cb
	 */
	public static void setCheckBoxSty01(Context context,CheckBox cb){
		widthDP10 = DPUtils.dip2px(10);
		LinearLayout.LayoutParams layouttParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		cb.setLayoutParams(layouttParams);
		cb.setPadding(widthDP10,widthDP10,widthDP10,widthDP10);
		cb.setTextSize(16);
		cb.setTextColor(context.getResources().getColor(R.color.text_color));
		cb.setButtonDrawable(context.getResources().getDrawable(R.drawable.eg_base_checkbox_style_01));
		
		 
	}
	
	/**
	 * 设置ImageVIew背景
	 * @param context
	 * @param imageView
	 * @param drawable
	 */
	public static void setDots(Context context,ImageView imageView,Drawable drawable){
		imageView.setBackgroundDrawable(drawable);
	}
	
	
}
