package com.myutils.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.myutils.R;
import com.myutils.base.AppFactory;

public class T{
	
	/**
	 * 显示时间  默认LENGTH_SHORT 2秒 (LENGTH_LONG3.5秒)
	 * @param text 
	 */
	public static void show(String text) {
		Toast.makeText(AppFactory.getAppContext(), text,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示时间
	 * @param text 提示文字
	 * @param time 显示时长
	 */
	public static void show(String text,int time) {
		Toast.makeText(AppFactory.getAppContext(), text,
				time).show();
	}
	
	
	public static void custom(String tip) {
		LayoutInflater inflater = LayoutInflater.from(AppFactory
				.getAppContext());
		View layout = inflater.inflate(R.layout.ui_toast,
				null);
		TextView tvTip = (TextView) layout.findViewById(R.id.tip);
		tvTip.setText(tip);
		tvTip.setTextColor(Color.parseColor("#33475f"));
		Toast toast = new Toast(AppFactory.getAppContext());
//		toast.setGravity(Gravity.CENTER,0,0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

}
