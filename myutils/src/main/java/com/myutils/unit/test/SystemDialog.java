package com.myutils.unit.test;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.myutils.R;
import com.myutils.base.AppFactory;



public class SystemDialog {
	
	
	/**
	 * WindowManager，直接添加view
	 */
	public static void aa(){
		WindowManager wm = (WindowManager) AppFactory.getAppContext().getSystemService("window");
	    WindowManager.LayoutParams para = new WindowManager.LayoutParams();
	    para.height =500;
	    para.width = 300;
	    para.format = 1;
	            
	    para.flags = LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_LAYOUT_IN_SCREEN;
	            
	    para.type = LayoutParams.TYPE_SYSTEM_ALERT;
	    View  mView = LayoutInflater.from(AppFactory.getAppContext()).inflate(
	                    R.layout.ui_alonedialog, null);
	    wm.addView(mView, para);
	}
	
	
	/**
	 * 
	 */
	public static void showBox()
	{
		Context context=AppFactory.getAppContext();
		AlertDialog.Builder dialog=new AlertDialog.Builder(context);
		//dialog.setTitle("提示");
		//dialog.setIcon(android.R.drawable.ic_dialog_info);
		 View  mView = LayoutInflater.from(AppFactory.getAppContext()).inflate(
                 R.layout.ui_alonedialog, null);
		dialog.setView(mView);
		//dialog.setMessage("完成次数: "+String.valueOf(COUNT)+"\n"+"总计次数: "+String.valueOf(TIMES));
//		dialog.setPositiveButton("停止测试",new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//					//点击后跳转到某个Activity
////					Intent result = new Intent(context,xxx.class);
////					result.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////					context.startActivity(result);
//			}
//		});
		AlertDialog mDialog=dialog.create();
		mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//设定为系统级警告，关键
		mDialog.show();
	}	

}
