//package com.myutils.ui.dialog;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.graphics.drawable.BitmapDrawable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import com.myutils.R;
//import com.myutils.base.AppFactory;
//import com.myutils.base.L;
//import com.myutils.data.ActionResult;
//import com.myutils.data.OnResultListener;
//import com.myutils.data.RowObject;
//import com.myutils.data.okhttp.ActionInvoker;
//import com.myutils.ui.apdater.BaseFillListViewAdapter;
//import com.myutils.ui.apdater.BaseFillListViewAdapter.OnItemClickListener;
//import com.myutils.ui.apdater.BaseFillListViewAdapter.ViewHolder;
//
///**
// * @Created by gzpykj.com
// * @author zms
// * @Date 2016-3-17
// * @Descrition 基础下拉菜单
// */
//public class BaseDropDownMenu {
//
//	private Context context;
//	// 数据来源，默认网络(internet)、还有本地(sqlite)、自定义custom
//	private String dataProvide = PROVIDE_INTERNET;
//	/**
//	 * 网络数据来源
//	 */
//	public static final String PROVIDE_INTERNET="internet";
//	/**
//	 * 本地数据来源
//	 */
//	public static final String PROVIDE_SQLITE="sqlite";
//	/**
//	 * 自定义数据来源
//	 */
//	public static final String PROVIDE_CUSTOM="custom";
//
//	// 请求链接
//	private String actionClass;
//	// 请求参数
//	private HashMap<String, Object> formMap = new HashMap<String, Object>();
//
//	/** View */
//	// 列表项布局id
//	private int itemLayout = R.layout.popup_dailog_item;;
//	// 主布局id
//	private int defaultLayout = R.layout.popup_dailog_view;
//	// 弹窗
//	private PopupWindow popupWindow;
//	// 主布局View
//	private View popupView;
//	// 适配器
//	private DropDownMenuAdapter popListAdapter;
//	// 下拉菜单绑定的view
//	private View view;
//
//
//	private ListView lv;
//	private TextView  tv_tip;
//
//	// 列表数据源
//	private List<RowObject> rows = new LinkedList<RowObject>();
//
//	/**
//	 * 列表数据解析
//	 */
//	public OnDataListner onDataListner = null;
//
//	/**
//	 * 列表项布局自定义
//	 */
//	public OnItemListener onItemListener = null;
//	/**
//	 * 列表项结果回调
//	 */
//	public OnUpdateListView onUpdateListView = null;
//
//	/**
//	 * @param context
//	 * @param itemLayout
//	 * @param view
//	 *            下拉菜单绑定的view
//	 */
//	public BaseDropDownMenu(Context context, View view) {
//		this.context = context;
//		this.view = view;
//	}
//
//	/**
//	 *
//	 * @param context
//	 * @param itemLayout
//	 * @param view
//	 *            下拉菜单绑定的view
//	 */
//	public BaseDropDownMenu(Context context, int itemLayout, View view) {
//		this.context = context;
//		this.itemLayout = itemLayout;
//		this.view = view;
//	}
//
//	public void init() {
//		popupView = LayoutInflater.from(context).inflate(defaultLayout, null);
//		lv = (ListView) popupView.findViewById(R.id.lv);
//		lv.setAdapter(popListAdapter);
//		tv_tip =(TextView) popupView.findViewById(R.id.tv_tip);
//		popListAdapter = new DropDownMenuAdapter(context, rows, itemLayout);
//		view.setOnClickListener(new mClick());
//		popListAdapter.setOnItemClickListener(new ItemClick());
//	}
//
//	/*
//	 * 参数和样式设置完后初始化
//	 */
//	private void initPop() {
//		L.info("弹窗宽度=========" + view.getWidth());
//		popupWindow = new PopupWindow(popupView, view.getWidth(),
//				LayoutParams.WRAP_CONTENT);
//		popupWindow.setFocusable(true);
//		popupWindow.setBackgroundDrawable(new BitmapDrawable());
//
//	}
//
//	/**
//	 * 获取数据
//	 *
//	 * @param type
//	 */
//	public void getData() {
//		if (dataProvide.equals(PROVIDE_INTERNET)) {// 网络
//			ActionInvoker ai = AppFactory
//					.creatActionInvorker(actionClass);
//			ai.setOnResultListener(new OnDataResult());
//			ai.setDialog("数据获取中...");
//			ai.addParam(formMap);
//			//TODO
////			ai.invokeForResult();
//		} else if (dataProvide.equals(PROVIDE_SQLITE)) {// 本地数据库
//			// TODO
//		} else if (dataProvide.equals(PROVIDE_CUSTOM)){// 自定义数据来源
//			// TODO
//			updateListView();
//		}
//
//	}
//
//	/**
//	 * 取得数据后回调
//	 *
//	 * @author OAIM
//	 *
//	 */
//	class OnDataResult implements OnResultListener {
//		@Override
//		public void onResult(ActionResult result) {
//			L.info("OnDataResult====" + result.getText());
//			List<RowObject> resultRows = new LinkedList<RowObject>();
//			if (onDataListner == null) {
//				// 默认json格式解析 :{"success":false,"data":[],"size":0}
//				if (result.isSuccess()) {
//					RowObject row = result.getRow();
//					resultRows = row.getRows("data");
//				}
//			} else {
//				// 如果是网络请求或者本地数据库请求时，不符合上面的格式请调此方法自行解析，返回一个rows
//				resultRows = onDataListner.onData(result);
//			}
//			if(resultRows!=null){
//				rows.addAll(resultRows);
//			}
//			updateListView();
//
//		}
//
//	}
//
//
//	private void updateListView() {
//
//		if (rows.size() == 0) {
//			//getData();
//			tv_tip.setVisibility(View.VISIBLE);
//		} else {
//			tv_tip.setVisibility(View.GONE);
//		}
//		L.info("updateListView====rows" + rows.toString());
//		//popListAdapter.notifyDataSetChanged();
//		lv.setAdapter(popListAdapter);
//		popListAdapter.notifyDataSetChanged();
//		if (onUpdateListView != null) {
//			onUpdateListView.onUpdate(rows);
//		}
//
//	}
//
//
//	/**
//	 * 隐藏
//	 */
//	public void dismiss(){
//		if(popupWindow.isShowing()){
//			popupWindow.dismiss();
//		}
//	}
//
//	/**
//	 * 显示
//	 */
//	public void show(){
//		if(!popupWindow.isShowing()){
//			popupWindow.showAsDropDown(view);
//		}
//	}
//
//	class mClick implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			if (popupWindow == null) {
//				initPop();
//			}
//
//			if(popupWindow.getWidth()==0){
//				init();
//			}
//			L.i("mClick=========="+popupWindow.getWidth());
//			if (!popupWindow.isShowing()) {
//				popupWindow.showAsDropDown(view);
//			}
//			L.info("mClick=====getCount"+lv.getChildCount());
//		}
//	}
//
//	/**
//	 * 列表项监听
//	 *
//	 * @author OAIM
//	 *
//	 */
//	class ItemClick implements OnItemClickListener {
//		@Override
//		public void onItemClick(View convertView, RowObject row, int position) {
//			if (onItemListener != null) {
//				onItemListener.onItemClick(convertView, row, position);
//			}
//		}
//	}
//
//	/**
//	 * 适配器
//	 *
//	 * @author OAIM
//	 *
//	 */
//	public class DropDownMenuAdapter extends BaseFillListViewAdapter {
//
//		public DropDownMenuAdapter(Context context, List<RowObject> rows,
//				int layout) {
//			super(context, rows, layout);
//		}
//
//		@Override
//		public Map<String, Integer> setConvertView(Map<String, Integer> views) {
//			if (onItemListener != null) {
//				return onItemListener.findView(views);
//			}
//			return views;
//		}
//
//		@Override
//		public void setItem(View convertView, RowObject row, int position,
//				Map<String, View> views, ViewHolder holder) {
//			if (onItemListener != null) {
//				onItemListener.setItem(convertView, row, position, views,
//						holder);
//			}
//		}
//	}
//
//	public interface OnDataListner {
//		/**
//		 * 自行将列表数据解析为List<RowObject>
//		 *
//		 * @return
//		 */
//		List<RowObject> onData(ActionResult result);
//	}
//
//	public interface OnCustomDataListner {
//		/**
//		 * 自行将列表数据解析为List<RowObject>
//		 *
//		 * @return
//		 */
//		List<RowObject> onCustomData();
//	}
//
//	public interface OnUpdateListView {
//		/**
//		 * /** 获取数据后回调
//		 *
//		 * @author OAIM
//		 */
//		void onUpdate(List<RowObject> rows);
//	}
//
//	/**
//	 * 适配器监听
//	 *
//	 */
//	public interface OnItemListener {
//		/**
//		 * 查找view
//		 *
//		 * @return
//		 */
//		Map<String, Integer> findView(Map<String, Integer> views);
//
//		/**
//		 * item样式调整
//		 *
//		 * @param convertView
//		 * @param row
//		 * @param position
//		 * @param views
//		 * @param holder
//		 */
//		void setItem(View convertView, RowObject row, int position,
//				Map<String, View> views, ViewHolder holder);
//
//		/**
//		 * item监听
//		 *
//		 * @param convertView
//		 * @param row
//		 * @param position
//		 */
//		void onItemClick(View convertView, RowObject row, int position);
//
//	}
//
//	/**
//	 * 添加过滤参数
//	 */
//	public void addParam(String key, Object value) {
//		formMap.put(key, value);
//	}
//
//	/**
//	 * 移除过滤参数
//	 */
//	public void removeParam(String key) {
//		if (formMap.containsKey(key)) {
//			formMap.remove(key);
//		}
//	}
//
//	public String getDataProvide() {
//		return dataProvide;
//	}
//
//	public String getActionClass() {
//		return actionClass;
//	}
//
//	public HashMap<String, Object> getFormMap() {
//		return formMap;
//	}
//
//	public int getItemLayout() {
//		return itemLayout;
//	}
//
//	public int getDefaultLayout() {
//		return defaultLayout;
//	}
//
//	public View getView() {
//		return view;
//	}
//
//	public OnDataListner getOnDataListner() {
//		return onDataListner;
//	}
//
//	public OnItemListener getOnItemListener() {
//		return onItemListener;
//	}
//
//	public void setDataProvide(String dataProvide) {
//		this.dataProvide = dataProvide;
//	}
//
//	public void setActionClass(String actionClass) {
//		this.actionClass = actionClass;
//	}
//
//	public void setFormMap(HashMap<String, Object> formMap) {
//		this.formMap = formMap;
//	}
//
//	public void setItemLayout(int itemLayout) {
//		this.itemLayout = itemLayout;
//	}
//
//	public void setDefaultLayout(int defaultLayout) {
//		this.defaultLayout = defaultLayout;
//	}
//
//	public void setView(View view) {
//		this.view = view;
//	}
//
//	public void setOnDataListner(OnDataListner onDataListner) {
//		this.onDataListner = onDataListner;
//	}
//
//	public void setOnItemListener(OnItemListener onItemListener) {
//		this.onItemListener = onItemListener;
//	}
//
//
//	public OnUpdateListView getOnUpdateListView() {
//		return onUpdateListView;
//	}
//
//	public void setOnUpdateListView(OnUpdateListView onUpdateListView) {
//		this.onUpdateListView = onUpdateListView;
//	}
//
//	public List<RowObject> getRows() {
//		return rows;
//	}
//
//	public void setRows(List<RowObject> rows) {
//		if(rows!=null){
//			this.rows.addAll(rows);
//		}
//
//	}
//
//
//
//}
