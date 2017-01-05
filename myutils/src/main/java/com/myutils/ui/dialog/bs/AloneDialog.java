package com.myutils.ui.dialog.bs;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.myutils.R;
import com.myutils.base.AppFactory;
import com.myutils.base.BaseAppContext.OnTrimMemoryListenr;
import com.myutils.base.L;
import com.myutils.core.RowObject;
import com.myutils.ui.view.listview.BaseFillAdapter;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-12-31
 * @Descrition 单例加载弹窗(继承系统弹窗),适合在一个界面同时加载几条数据的情况下使用,避免重复创建
 * (加载弹窗有点慢)
 */
public class AloneDialog extends AlertDialog {

	private static final String TAG="AloneDialog";
	private static AloneDialog instance = null;
	// 左边为提示内容，右边为状态 loadingStatus
	private static Map<String, String> mapMessage;
	private Window window;
	private String loadingStatus = "加载中...";

	private OnRefreshDialog onRefreshDialog = null;

	// 默认View
	private View view;
	//private TextView tips_loading_msg;
	private ListView listView;
	private LinearLayout lnDialogView;
	

	private List<RowObject> rows =null;

	private MAdapter mAdapter;

	private int screenWidth;
	private int screenHeight;
	

	public static AloneDialog getDialog() {
		if (instance == null) {
			instance = new AloneDialog(AppFactory.getAppContext());
			L.i(TAG+"=======AloneDialog==null============");
		}
		return instance;
	}

	private AloneDialog(Context context) {
		super(context, R.style.custom_dialog);
		//退出app时隐藏弹窗
		AppFactory.getAppContext().setOnTrimMemoryListenr(TAG,new OnTrimMemoryListenr() {
			@Override
			public void onTrimMemory() {
				dismiss();
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(rows==null){
			 rows = new ArrayList<RowObject>();
		}
		instance = this;
		initDailog();
		initView();
		onDismiss();
	}

	
	/**
	 * dialog样式
	 */
	private void initDailog() {
		window=getWindow();
		getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//设定为系统级警告，关键
		WindowManager windowManager = window.getWindowManager();
		Display display = windowManager.getDefaultDisplay(); // 为获取屏幕宽、高
		// 窗口宽度
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		// 设置dialog高度等于宽度
//		if (screenWidth > screenHeight) {
//			screenWidth = screenHeight;
//		}
		//设置弹窗大小
		WindowManager.LayoutParams lp = window.getAttributes();
		int w = (int) (screenWidth * 4 / 6);
		lp.width =w;
		lp.height = w/2;
		lp.gravity = Gravity.CENTER;
		window.setAttributes(lp);
	}

	public void initView() {
		view = LayoutInflater.from(AppFactory.getAppContext()).inflate(R.layout.ui_alonedialog,
				null);
		listView = (ListView) view.findViewById(R.id.listView);
		
		mAdapter = new MAdapter(AppFactory.getAppContext(), rows, R.layout.spl_listview_item);
		listView.setAdapter(mAdapter);
		//tips_loading_msg = (TextView) view.findViewById(R.id.tips_loading_msg);
		lnDialogView= (LinearLayout) view.findViewById(R.id.ln_dialog);
		
		//lnDialogView.setLayoutParams(new LinearLayout.LayoutParams(w, w/2));
		setContentView(view);
		
	}

	
	/**
	 * 显示弹窗 
	 * @param tip 提示内容
	 */
	public void show(String tip) {
		//Logger.info(TAG+"   show====");
		if (mapMessage == null) {
			mapMessage = new LinkedHashMap<String, String>();
		}
		if (!isShowing()) {// 显示弹窗
			// tips_loading_msg.setText(tip);
			show();
		} else if (mapMessage.size() > 0) {// 显示弹窗
			// tips_loading_msg.setText(tip);
			show();
		}
		if (mapMessage.containsKey(tip)) {
		} else {
			if (onRefreshDialog != null) {
				onRefreshDialog.refreshDialog(mapMessage);
			} else {
				mapMessage.put(tip, loadingStatus);
				rows=getTipData(mapMessage);
				mAdapter.notifyDataSetChanged();
			}
			mapMessage.put(tip, loadingStatus);
		}
	}
	
	
	private List<RowObject> getTipData(Map<String, String> mapMessage) {
		rows.clear();
		for (String key:mapMessage.keySet()) {
			RowObject row = new RowObject();
			row.put("NAME", key);
			rows.add(row);
		}
		return rows;
	}

	
	
	/**
	 * 移除加载完的提示或者关闭弹窗
	 * @param tip
	 */
	public void dismiss(String tip) {
		if(mapMessage.containsKey(tip)){
			mapMessage.remove(tip);
			mAdapter.notifyDataSetChanged();
		}
		if(mapMessage.size()==0){
			dismiss();
		}
		
	}
	
	
	/**
	 * 消失的时候清除数据
	 */
	public void onDismiss(){
		this.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// 清除全部提示信息
				//Logger.info("OnDismissListener=====");
				mapMessage.clear();
				mAdapter.notifyDataSetChanged();
			}
		});
	}
	
	

	/**
	 * listview适配器
	 * @author OAIM
	 * 
	 */
	class MAdapter extends BaseFillAdapter {

		public MAdapter(Context context, List<RowObject> rows, int layout) {
			super(context, rows, layout);
		}



		@Override
		public void setItem(View convertView, RowObject row, int position,
				 ViewHolder holder) {
			// TODO Auto-generated method stub
			
		}

	}
	
	class MA1dapter extends BaseFillAdapter{

		public MA1dapter(Context context, List<RowObject> rows, int layout) {
			super(context, rows, layout);
			// TODO Auto-generated constructor stub
		}


		@Override
		public void setItem(View convertView, RowObject row, int position,
				 ViewHolder holder) {
			// TODO Auto-generated method stub
			
		}

		
	}


	public interface OnRefreshDialog {
		void refreshDialog(Map<String, String> mapMessage);
	}

	public OnRefreshDialog getOnRefreshDialog() {
		return onRefreshDialog;
	}

	public void setOnRefreshDialog(OnRefreshDialog onRefreshDialog) {
		this.onRefreshDialog = onRefreshDialog;
	}

}
