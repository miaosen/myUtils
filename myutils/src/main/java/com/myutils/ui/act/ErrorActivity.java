//package com.myutils.ui.act;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.myutils.R;
//import com.myutils.base.BaseActivity;
//import com.myutils.ui.T;
//
//public class ErrorActivity extends BaseActivity{
//
//
//	private Context context;
//	private Button btn_exit;
//	private Button btn_reset;
//	private Button btn_return;
//
//	private String errorMsg;
//
//	private TextView tv_errormsg;
//
//
//
//	@Override
//	public void initConfig() {
//		setContentView(R.layout.error);
//		errorMsg=getIntent().getStringExtra("msg");
//		context=this;
//	}
//
//	@Override
//	public void initView() {
//		btn_reset=(Button) findViewById(R.id.btn_reset);
//		btn_reset.setOnClickListener(this);
//		btn_exit=(Button) findViewById(R.id.btn_exit);
//		btn_exit.setOnClickListener(this);
//		btn_return=(Button) findViewById(R.id.btn_return);
//		btn_return.setOnClickListener(this);
//		tv_errormsg=(TextView) findViewById(R.id.tv_errormsg);
//		tv_errormsg.setText("错误信息 :\n"+errorMsg);
//	}
//
//	@Override
//	public void click(View v) {
//		if(v==btn_reset){
//			//启动主activity并结束当前activity
//        	Intent i =getPackageManager()
//        	        .getLaunchIntentForPackage(getPackageName());
//        	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        	startActivity(i);
//        	finish();
//		}else if(v==btn_exit){
//			finish();
//		}else if(v==btn_return){
//			T.show("功能待开发！");
//		}
//	}
//
//
//}
