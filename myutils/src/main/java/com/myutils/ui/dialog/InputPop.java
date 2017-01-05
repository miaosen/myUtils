package com.myutils.ui.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.myutils.R;
import com.myutils.core.RowObject;
import com.myutils.base.L;
import com.myutils.ui.view.listview.BaseFillAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-3-17
 * @Descrition 下拉菜单
 */
public class InputPop {

	private Context context;
	/** View */
	// 列表项布局id
	private int itemLayout = R.layout.popup_dailog_item;;
	// 主布局id
	private int defaultLayout = R.layout.popup_dailog_view;
	// 弹窗
	private PopupWindow popupWindow;
	// 主布局View
	private View popupView;
	// 适配器
	private DropDownMenuAdapter popListAdapter;
	// 下拉菜单绑定的view
	private View view;


	private ListView lv;
	private TextView  tv_tip;

	// 列表数据源
	private List<RowObject> rows = new LinkedList<RowObject>();


	/**
	 * 列表项布局自定义
	 */
	public OnItemListener onItemListener = null;
	/**
	 * 列表项结果回调
	 */
	public OnUpdateListView onUpdateListView = null;

	/**
	 * @param context
	 * @param view
	 *            下拉菜单绑定的view
	 */
	public InputPop(Context context, View view) {
		this.context = context;
		this.view = view;
	}

	/**
	 *
	 * @param context
	 * @param itemLayout
	 * @param view
	 *            下拉菜单绑定的view
	 */
	public InputPop(Context context, View view, int itemLayout) {
		this.context = context;
		this.itemLayout = itemLayout;
		this.view = view;

	}

	public void init() {
		popupView = LayoutInflater.from(context).inflate(defaultLayout, null,false);
		lv = (ListView) popupView.findViewById(R.id.lv);
		lv.setAdapter(popListAdapter);
		tv_tip =(TextView) popupView.findViewById(R.id.tv_tip);
		popListAdapter = new DropDownMenuAdapter(context, rows, itemLayout);
		view.setOnClickListener(new mClick());
		popListAdapter.setOnItemClickListener(new ItemClick());
	}

	/*
	 * 参数和样式设置完后初始化
	 */
	public void initPop() {
		L.i("弹窗宽度=========" + view.getWidth());
		popupWindow = new PopupWindow(popupView, view.getWidth(),
				LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

	}


	public void updateListView(List<RowObject> rows) {
		if(popupView==null){
			init();
		}
		if(rows!=null){
			this.rows.clear();
			this.rows.addAll(rows);
			if (rows.size() == 0) {
				//getData();
				tv_tip.setVisibility(View.VISIBLE);
			} else {
				tv_tip.setVisibility(View.GONE);
			}
			//popListAdapter.notifyDataSetChanged();
			lv.setAdapter(popListAdapter);
			popListAdapter.notifyDataSetChanged();
			if (onUpdateListView != null) {
				onUpdateListView.onUpdate(rows);
			}
		}


	}


	/**
	 * 隐藏
	 */
	public void dismiss(){
		if(popupWindow.isShowing()){
			popupWindow.dismiss();
		}
	}

	/**
	 * 显示
	 */
	public void show(){
		//popupWindow.setWidth(view.getMeasuredWidth());
		if(!popupWindow.isShowing()){
			popupWindow.showAsDropDown(view);
		}
	}

	class mClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (popupWindow == null) {
				initPop();
			}

			if(popupWindow.getWidth()==0){
				init();
			}
			L.i("mClick=========="+popupWindow.getWidth());
			if (!popupWindow.isShowing()) {
				popupWindow.showAsDropDown(view);
			}
			L.i("mClick=====getCount"+lv.getChildCount());
		}
	}

	/**
	 * 列表项监听
	 *
	 * @author OAIM
	 *
	 */
	class ItemClick implements BaseFillAdapter.OnItemClickListener {
		@Override
		public void onItemClick(View convertView, RowObject row, int position) {
			if (onItemListener != null) {
				onItemListener.onItemClick(convertView, row, position);
			}
		}
	}

	/**
	 * 适配器
	 *
	 * @author OAIM
	 *
	 */
	public class DropDownMenuAdapter extends BaseFillAdapter {

		public DropDownMenuAdapter(Context context, List<RowObject> rows,
				int layout) {
			super(context, rows, layout);
		}

        @Override
        public void setItem(View convertView, RowObject row, int position, ViewHolder holder) {

        }

    }


	public interface OnCustomDataListner {
		/**
		 * 自行将列表数据解析为List<RowObject>
		 *
		 * @return
		 */
		List<RowObject> onCustomData();
	}

	public interface OnUpdateListView {
		/**
		 * /** 获取数据后回调
		 *
		 * @author OAIM
		 */
		void onUpdate(List<RowObject> rows);
	}

	/**
	 * 适配器监听
	 *
	 */
	public interface OnItemListener {
		/**
		 * item样式调整
		 *
		 * @param convertView
		 * @param row
		 * @param position
		 * @param views
		 * @param holder
		 */
		void setItem(View convertView, RowObject row, int position,
				Map<String, View> views, BaseFillAdapter.ViewHolder holder);

		/**
		 * item监听
		 *
		 * @param convertView
		 * @param row
		 * @param position
		 */
		void onItemClick(View convertView, RowObject row, int position);

	}



	public int getItemLayout() {
		return itemLayout;
	}

	public int getDefaultLayout() {
		return defaultLayout;
	}

	public View getView() {
		return view;
	}


	public OnItemListener getOnItemListener() {
		return onItemListener;
	}


	public void setItemLayout(int itemLayout) {
		this.itemLayout = itemLayout;
	}

	public void setDefaultLayout(int defaultLayout) {
		this.defaultLayout = defaultLayout;
	}

	public void setView(View view) {
		this.view = view;
	}

	public void setOnItemListener(OnItemListener onItemListener) {
		this.onItemListener = onItemListener;
	}


	public OnUpdateListView getOnUpdateListView() {
		return onUpdateListView;
	}

	public void setOnUpdateListView(OnUpdateListView onUpdateListView) {
		this.onUpdateListView = onUpdateListView;
	}

	public List<RowObject> getRows() {
		return rows;
	}

	public void setRows(List<RowObject> rows) {
		if(rows!=null){
			this.rows.addAll(rows);
		}
		updateListView(rows);

	}



}
