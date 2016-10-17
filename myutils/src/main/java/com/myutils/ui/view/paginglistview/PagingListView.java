//package com.myutils.ui.view.paginglistview;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//
//import com.myutils.R;
//import com.myutils.base.AppFactory;
//import com.myutils.base.L;
//import com.myutils.data.ActionResult;
//import com.myutils.data.OnResultListener;
//import com.myutils.data.RowObject;
//import com.myutils.data.okhttp.UrlInvoker;
//import com.myutils.ui.apdater.BaseFillListViewAdapter;
//import com.myutils.ui.apdater.BaseFillListViewAdapter.OnItemClickListener;
//import com.myutils.ui.apdater.BaseFillListViewAdapter.ViewHolder;
//import com.myutils.ui.T;
//import com.myutils.ui.view.LoadingTipLayout;
//import com.myutils.core.annotation.ViewInject;
//import com.myutils.ui.view.paginglistview.SwipeRefreshLayout.OnLoadListener;
//import com.myutils.ui.view.paginglistview.SwipeRefreshLayout.OnRefreshListener;
//import com.myutils.utils.ViewUtils;
//
///**
// * @Created by gzpykj.com
// * @author zms
// * @Date 2016-3-15
// * @Descrition 分页列表
// */
//@SuppressLint("ResourceAsColor")
//public class PagingListView extends LinearLayout {
//
//	private Context context;
//
//	// 数据来源，默认网络(internet)、还有本地数据(sqlite)、自定义custom
//	private String dataProvide = "internet";
//	/**
//	 * 网络请求是否为get方式
//	 */
//	private boolean InvorkerWayByGet = false;
//	/**
//	 * 网络请求的地址,默认为调用src下的application.properties配置的服务器地址,true为调用自定义地址
//	 */
//	private boolean InvorkerTypeByCustom = false;
//	// 请求链接
//	private String actionClass;
//	// 分页大小参数名称
//	private String pageSizeParam = "pageSize";
//	// 起始页参数名称
//	private String pageIndexParam = "pageIndex";
//	// 请求参数
//	private HashMap<String, Object> formMap = new HashMap<String, Object>();
//	// 起始页数
//	private int dfpageIndex = 1;
//	// 起始页数
//	private int dfcurrentPageIndex = dfpageIndex - 1;
//	// 起始页数
//	private int pageIndex = dfpageIndex;
//	// 默认起始页数
//	private int currentPageIndex = dfcurrentPageIndex;
//
//	// 分页大小
//	private int pageSize = 10;
//
//	// View
//	@ViewInject
//	private SwipeRefreshLayout swipe_layout;
//	@ViewInject
//	private ListView lv;
//	private int itemLayout;
//	private PagingListAdapter adapter;
//	// 列表数据源
//	private List<RowObject> rows = new LinkedList<RowObject>();
//
//	/** 监听 */
//	/**
//	 * 列表数据解析监听
//	 */
//	public OnDataListner onDataListner = null;
//	/**
//	 * 列表项布局自定义
//	 */
//	public OnItemListener onItemListener = null;
//
//	private LoadingTipLayout tip_header;
//
//	private LoadingTipLayout tip_footer;
//
//
//	public PagingListView(Context context) {
//		super(context);
//		this.context = context;
//
//	}
//
//	public PagingListView(Context context, AttributeSet atbSet) {
//		super(context, atbSet);
//		this.context = context;
//
//	}
//
//	@SuppressLint("InlinedApi")
//	private void initView() {
//		View view = LayoutInflater.from(context).inflate(
//				R.layout.ui_view_paging, null);
//		this.addView(view);
//		ViewUtils.injectAllFields(this, view);
//		tip_header = new LoadingTipLayout(context);
//		tip_footer = new LoadingTipLayout(context);
//		swipe_layout.setColor(android.R.color.holo_blue_bright,
//				android.R.color.holo_green_light,
//				android.R.color.holo_orange_light,
//				android.R.color.holo_red_light);
//		swipe_layout.setOnRefreshListener(new OnRefreshListener() {
//			@Override
//			public void onRefresh() {
//				refresh();
//			}
//		});
//		swipe_layout.setOnLoadListener(new OnLoadListener() {
//			@Override
//			public void onLoad() {
//				pageIndex = currentPageIndex + 1;
//				getData("load");
//			}
//		});
//		adapter = new PagingListAdapter(context, rows, itemLayout);
//		adapter.setOnItemClickListener(new ItemClick());
//		tip_header.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				refresh();
//			}
//		});
//		tip_footer.finish();
//		lv.addHeaderView(tip_header);
//		lv.addFooterView(tip_footer);
//		lv.setAdapter(adapter);
//
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
//	 * 加载列表
//	 */
//	public void loading() {
//		initView();
//		tip_header.loading();
//		getData("load");
//	}
//
//	/**
//	 * 加载列表
//	 */
//	public void refresh() {
//		pageIndex = dfpageIndex;
//		currentPageIndex = dfcurrentPageIndex;
//		getData("refresh");
//	}
//
//	public void getData(final String type) {
//		if (type.equals("refresh")) {
//			swipe_layout.setRefreshing(true);
//		} else {
//			//loadData(rows);
//		}
//		if (dataProvide.equals("internet")) {// 网络
//			UrlInvoker ai = AppFactory
//					.creatUrlInvorker(actionClass);
//			ai.setOnResultListener(new OnDataResult(type));
//			ai.addParam(pageIndexParam, pageIndex);
//			ai.addParam(pageSizeParam, pageSize);
//			ai.addParam(formMap);
//			if (InvorkerWayByGet) {
//				ai.setWay("get");
//			}
//			if (InvorkerTypeByCustom) {
//				ai.invokeUrlForResult();
//			} else {
//				ai.invokeForResult();
//			}
//		} else if (dataProvide.equals("sqlite")) {// 本地数据库
//			// TODO
//
//		} else if (dataProvide.equals("custom")) {// 自定义数据来源
//			// TODO
//
//		}
//	}
//
//	/**
//	 * 网络请求时，设置请求方式为get方式
//	 */
//	private void actionInvokerByGet() {
//		// TODO Auto-generated method stub
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
//		private String type;
//		public OnDataResult(String type) {
//			this.type = type;
//		}
//		@Override
//		public void onResult(ActionResult result) {
//			L.i("PagingListViewDataList====" + result.getResponseText());
//			List<RowObject> rows = new LinkedList<RowObject>();
//			if (onDataListner == null) {
//				// 默认json格式解析 :{"success":false,"data":[],"size":0}
//				if (result.isSuccess()) {
//					RowObject row = result.getRow();
//					rows = row.getRows("data");
//				} else {
//					tip_header.error();
//					lv.addFooterView(tip_header);
//				}/*
//				 * else{ BaseToast.show("获取数据失败 :"+result.getMessage()); }
//				 */
//			} else {
//				// 不符合上面的格式请调此方法自行解析
//				rows = onDataListner.onData(result.getResponseText());
//			}
//			if(rows==null){
//				rows=new LinkedList<RowObject>();
//			}
//			if (type.equals("refresh")) {
//				refresh(rows);
//			} else {
//				loadData(rows);
//			}
//			tip();
//
//
//		}
//	}
//
//	private void tip() {
//		if (rows.size() == 0) {
//			tip_header.notData();
//		} else {
//			tip_header.finish();
//		}
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
//	/**
//	 * 刷新
//	 *
//	 * @param rows
//	 */
//	private void refresh(List<RowObject> rows) {
//		swipe_layout.setRefreshing(false);
//		this.rows.clear();
//		this.rows.addAll(rows);
//		adapter.notifyDataSetChanged();
//		// adapter.setOnItemClickListener(new ItemClick());
//		T.show("刷新成功");
//	}
//
//	/**
//	 * 加载数据
//	 *
//	 * @param rows
//	 */
//	private void loadData(List<RowObject> rows) {
//		tip_header.finish();
//		swipe_layout.setLoading(false);
//		if (rows==null||rows.size() == 0) {
//			//tip_footer.notData("没有更多数据了");
//		} else {
//			if (rows.size()<pageSize) {
//				tip_footer.notData("没有更多数据了");
//			}
//			currentPageIndex = currentPageIndex + 1;
//			this.rows.addAll(rows);
//			adapter.notifyDataSetChanged();
//		}
//		// adapter.setOnItemClickListener(new ItemClick());
//	}
//
//	/**
//	 * 适配器
//	 *
//	 * @author OAIM
//	 *
//	 */
//	public class PagingListAdapter extends BaseFillListViewAdapter {
//
//		public PagingListAdapter(Context context, List<RowObject> rows,
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
//		 * 解析列表数据为List<RowObject>
//		 *
//		 * @return
//		 */
//		List<RowObject> onData(String text);
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
//	public OnDataListner getOnDataListner() {
//		return onDataListner;
//	}
//
//	public OnItemListener getOnItemListener() {
//		return onItemListener;
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
//	public String getPageSizeParam() {
//		return pageSizeParam;
//	}
//
//	public String getPageIndexParam() {
//		return pageIndexParam;
//	}
//
//	public int getPageIndex() {
//		return pageIndex;
//	}
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSizeParam(String pageSizeParam) {
//		this.pageSizeParam = pageSizeParam;
//	}
//
//	public void setPageIndexParam(String pageIndexParam) {
//		this.pageIndexParam = pageIndexParam;
//	}
//
//	public void setPageIndex(int pageIndex) {
//		this.pageIndex = pageIndex;
//		this.dfpageIndex = pageIndex;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public String getActionClass() {
//		return actionClass;
//	}
//
//	public void setActionClass(String actionClass) {
//		this.actionClass = actionClass;
//	}
//
//	public int getItemLayout() {
//		return itemLayout;
//	}
//
//	public void setItemLayout(int itemLayout) {
//		this.itemLayout = itemLayout;
//	}
//
//	public List<RowObject> getRows() {
//		return rows;
//	}
//
//	public void setRows(List<RowObject> rows) {
//		this.rows = rows;
//	}
//
//	/**
//	 * 设置请求的方式为get
//	 *
//	 * @param type
//	 */
//	public boolean isInvorkerWayByGet() {
//		return InvorkerWayByGet;
//	}
//
//	public boolean isInvorkerTypeByCustom() {
//		return InvorkerTypeByCustom;
//	}
//
//	public void setInvorkerWayByGet() {
//		InvorkerWayByGet = true;
//	}
//
//	public void setInvorkerTypeByCustom() {
//		InvorkerTypeByCustom = true;
//	}
//
//}
