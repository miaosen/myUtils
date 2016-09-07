package com.myutils.ui.dialog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.myutils.R;
import com.myutils.base.AppFactory;
import com.myutils.core.RowObject;
import com.myutils.ui.view.listview.SimpleFillListAdapter;
import com.myutils.ui.dialog.bs.WindowDialog;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-12-31
 * @Descrition 单例加载弹窗(使用WindowManager),适合在一个界面同时加载几条数据的情况下使用,避免重复创建
 */
public class LoadingDialog extends WindowDialog {
	
	private static final String TAG = "LoadingDialog";
	
	// View
//	private TextView tips_loading_msg;
	private static ListView listView;
	private LinearLayout lnDialogView;
	private static List<RowObject> rows =null;
	private SimpleFillListAdapter mAdapter;
	
	// 左边为提示内容，右边为状态 loadingStatus
	private static Map<String, String> mapMessage=new LinkedHashMap<String, String>();
	private String loadingStatus = "加载中...";

	private OnRefreshDialog onRefreshDialog = null;
	
	private static LoadingDialog instance = null;

	public static LoadingDialog getDialog() {
		if (instance == null) {
			instance = new LoadingDialog();
		}
		return instance;
	}
	
	
	private LoadingDialog() {
		super();
		if(rows==null){
			 rows = new ArrayList<RowObject>();
		}
		if(mAdapter==null){
			mAdapter = new SimpleFillListAdapter(AppFactory.getAppContext(), rows,
					R.layout.spl_listview_item);
		}

	}
	
	
	/**
	 * 初始化View
	 */
	@Override
	public View onCreateView() {
		View view = LayoutInflater.from(AppFactory.getAppContext()).inflate(
				R.layout.ui_alonedialog, null);
		// 返回键监听
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.setOnKeyListener(backlistener);
		// 监听
		view.setOnClickListener(new mClick());
		listView = (ListView) view.findViewById(R.id.listView);
		listView.setAdapter(mAdapter);
		int w = (int) (screenWidth * 4 / 6);
		lnDialogView = (LinearLayout) view.findViewById(R.id.ln_dialog);
		lnDialogView.setLayoutParams(new LinearLayout.LayoutParams(w, w / 2));
		// 覆盖上一层view的监听事件
		lnDialogView.setOnClickListener(new mClick());
		return view;
	}
	/**
	 * view监听事件
	 *
	 * @author OAIM
	 *
	 */
	class mClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {


		}

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
	
	@Override
	public void dismiss() {
		super.dismiss();
		mapMessage.clear();
	}
	
	
	/**
	 * 显示弹窗 
	 * @param tip 提示内容
	 */
	public void show(String tip) {
		if (!isShowing()) {// 显示弹窗
			show();
		} 
		if (mapMessage.containsKey(tip)) {
		} else {
			if (onRefreshDialog != null) {
				onRefreshDialog.refreshDialog(mapMessage);
			} else {
				mapMessage.put(tip, loadingStatus);

				updateTipData(mapMessage);
			}
			mAdapter.notifyDataSetChanged();
		}
	}
	

	private List<RowObject> updateTipData(Map<String, String> mapMessage) {
		rows.clear();
		for (String key:mapMessage.keySet()) {
			RowObject row = new RowObject();
			row.put("NAME", key);
			rows.add(row);
		}
		return rows;
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
