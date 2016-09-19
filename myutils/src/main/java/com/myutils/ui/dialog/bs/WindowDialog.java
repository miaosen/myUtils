package com.myutils.ui.dialog.bs;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.myutils.base.AppFactory;
import com.myutils.base.BaseAppContext.OnTrimMemoryListenr;
import com.myutils.core.logger.L;
import com.myutils.utils.WindowUtils;
/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-1-24
 * @Descrition 使用WindowManager 添加view建立弹窗，依赖于application,可跨activity使用
 * 
 */

//TODO 横屏监听
public abstract class WindowDialog implements DialogInterface{

	private static final String TAG = "WindowDialog";

	public int screenWidth;
	public int screenHeight;

	private WindowManager windowManager;
	private WindowManager.LayoutParams param;
	private boolean isShowing=false;
	
	private View view=null;


	public WindowDialog() {
		initDisplay();
		// 退出app时隐藏弹窗
		AppFactory.getAppContext().setOnTrimMemoryListenr(TAG,
				new OnTrimMemoryListenr() {
					@Override
					public void onTrimMemory() {
						dismiss();
					}
				});
		
	}
	
	public abstract View onCreateView();

	/**
	 * 初始化屏幕
	 */
	private void initDisplay() {
		windowManager = (WindowManager) AppFactory
				.getAppContext().getSystemService(Context.WINDOW_SERVICE);
		// 窗口宽度
		screenWidth = WindowUtils.getScreenWidth();
		screenHeight = WindowUtils.getScreenHeight();
		param = new WindowManager.LayoutParams();
		param.height = screenHeight;
		param.width = screenWidth;
		param.format = 1;
		param.flags = LayoutParams.FLAG_FULLSCREEN
				| LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		param.type = LayoutParams.TYPE_SYSTEM_ALERT;

		// 设置dialog高度等于宽度
		// if (screenWidth > screenHeight) {
		// screenWidth = screenHeight;
		// }
		// 设置弹窗大小

	}



	/**
	 * 显示
	 */
	public void show() {
		if(view==null){
			view=onCreateView();
		}
		if(!isShowing()){
			windowManager.addView(view, param);
		}
		isShowing=true;
	}
	
	/**
	 * 隐藏
	 */
	@Override
	public void dismiss() {
		if(isShowing()){
			try {
				windowManager.removeView(view);
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		isShowing=false;
	}
	
	

	/**
	 * 点击返回键事件处理
	 */
	public View.OnKeyListener backlistener = new View.OnKeyListener() {
		@Override
		public boolean onKey(View view, int i, KeyEvent keyEvent) {
			if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
				dismiss();
				return true; // 已处理
			}
			return false;
		}
	};

	/**
	 * view监听事件
	 * 
	 * @author OAIM
	 * 
	 */
	class mClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == view) {
				dismiss();
			}
		}

	}

	



	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	
}
