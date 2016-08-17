//package com.myutils.ui.apdater;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.TextView;
//import android.widget.ToggleButton;
//
//import com.myutils.base.AppFactory;
//import com.myutils.base.L;
//import com.myutils.data.RowObject;
//import com.myutils.data.gson.JSONSerializer;
//import com.myutils.unit.ResourceUnit;
//import com.myutils.utils.RowUtils;
//
///**
// * @Created by gzpykj.com
// * @author zms
// * @Date 2015-11-13
// * @Descrition BaseFillListViewAdapter封装,根据布局的id和json中key的字段来填充数据,绑定数据填充模块
// */
//public abstract class BaseFillListViewAdapter extends BaseAdapter {
//
//	private Context context;
//	// 数据集 List<Map<?,?>>类型
//	private List<RowObject> rows;
//	// 布局文件
//	private int layout;
//	// item监听
//	private OnItemClickListener onItemClickListener;
//
//	public BaseFillListViewAdapter(Context context, List<RowObject> rows,
//			int layout) {
//		super();
//		this.context = context;
//		this.rows = rows;
//		this.layout = layout;
//	}
//
//	public BaseFillListViewAdapter(Context context, String jsonStr, int layout) {
//		super();
//		this.context = context;
//		this.rows = JSONSerializer.getRows(jsonStr);
//		this.layout = layout;
//	}
//
//	public BaseFillListViewAdapter(List<RowObject> rows, int layout) {
//		super();
//		this.context = AppFactory.getAppContext();
//		this.rows = rows;
//		this.layout = layout;
//	}
//
//	public BaseFillListViewAdapter(String jsonStr, int layout) {
//		super();
//		this.context = AppFactory.getAppContext();
//		this.rows = JSONSerializer.getRows(jsonStr);
//		this.layout = layout;
//	}
//
//	@Override
//	public int getCount() {
//		if (rows == null) {
//			return 0;
//		}
//		return rows.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return null;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return 0;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		Map<String, View> holderViews;
//		if (convertView == null) {
//			holder = new ViewHolder();
//			holderViews = new HashMap<String, View>();
//			convertView = LayoutInflater.from(context).inflate(layout, null);
//			Map<String, View> views = holder.getViews();
//			addViewsToHolder(views, rows.get(position),convertView,null);
//
//			Map<String, Integer> viewIds = new HashMap<String, Integer>();
//			viewIds = setConvertView(viewIds);
//			if (viewIds != null && viewIds.size() > 0) {
//				for (String key : viewIds.keySet()) {
//					View view = convertView.findViewById(viewIds.get(key));
//					holderViews.put(key, view);
//				}
//			}
//			initViewHolder(holder, convertView);
//			holder.setHolderViews(holderViews);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//			holderViews = holder.getHolderViews();
//			// Log.i("CosmeticsCheckItemAdapter", "===" + position + "===");
//		}
//		RowObject row = rows.get(position);
//		// 数据填充
//		for (String id : holder.getViews().keySet()) {
//			View view = holder.getViews().get(id);
//			String value = "";
//			String[] split = id.split("\\$");
//			L.i("value========长度" + split.length+"    id==="+id);
//			if (split.length < 2) {// 单层数据填充
//				value = row.get(id) + "";
//				viewToSetText(view, value);
//				L.i("value========" + value);
//			} else if (split.length >= 2) {// 多层数据填充（嵌套在里面row）
//				value = RowUtils.getlayerData(row, split) + "";
//				L.i("value========大于2：" + value);
//				viewToSetText(view, value);
//			}
//			L.info("id====" + id + "   value===" + value + "   i======="
//					+ position);
//
//		}
//		setItem(convertView, row, position, holderViews, holder);
//		convertView.setOnClickListener(new mCick(convertView, row, position));
//		return convertView;
//	}
//
//	private void addViewsToHolder(Map<String, View> views, RowObject rowObject, View convertView,String prefix) {
//
//		for (String idName : rowObject.keySet()) {
//			View view=null;
//			if(prefix==null){
//			}else{
//				idName=prefix+"$"+idName;
//			}
//
//			view = convertView.findViewById(ResourceUnit
//						.getIdByName(idName));
//			L.i("idName===="+idName+"    view==="+view);
//			if (canFillType(view)) {
//				views.put(idName, view);
//			}
//
//			Object object = rowObject.get(idName);
//			if(object instanceof RowObject){
//				addViewsToHolder(views, (RowObject) rowObject.get(idName),convertView,idName);
//			}
//		}
//	}
//
//	/**
//	 * 初始化ViewHolder
//	 *
//	 * @param holder
//	 * @param row
//	 * @param convertView
//	 */
//	public void initViewHolder(ViewHolder holder, View convertView) {
//
//	}
//
//	/**
//	 * ItemView初始化
//	 *
//	 * @param views
//	 * @return
//	 */
//	public abstract Map<String, Integer> setConvertView(
//			Map<String, Integer> views);
//
//	/**
//	 * 添加数据
//	 *
//	 * @param jsonStr
//	 */
//	public void addJsonData(String jsonStr) {
//		// TODO 只支持jsonObject类型
//		List<RowObject> list = JSONSerializer.getRows(jsonStr);
//		if (rows == null) {
//			rows = new ArrayList<RowObject>();
//		}
//		if (list != null) {
//			rows.addAll(list);
//			notifyDataSetChanged();
//		}
//	}
//
//	/**
//	 * 添加数据
//	 *
//	 * @param jsonStr
//	 */
//	public void addListData(List<RowObject> list) {
//		if (rows == null) {
//			rows = new ArrayList<RowObject>();
//		}
//		if (list != null) {
//			rows.addAll(list);
//			notifyDataSetChanged();
//		}
//	}
//
//	/**
//	 * 清除数据
//	 *
//	 * @param jsonStr
//	 */
//	public void clearData() {
//		if (rows != null) {
//			rows.clear();
//			notifyDataSetChanged();
//		}
//	}
//
//	/**
//	 * ItemView初始化
//	 */
//	public class ViewHolder {
//		// private List<RowObject> rows;
//		/**
//		 * 数据填充的View
//		 */
//		private Map<String, View> views = new HashMap<String, View>();
//		/**
//		 * 不需要数据填充的View
//		 */
//		private Map<String, View> holderViews = new HashMap<String, View>();
//
//		private Object object;
//
//		public Map<String, View> getViews() {
//			return views;
//		}
//
//		public void setViews(Map<String, View> views) {
//			this.views = views;
//		}
//
//		public Map<String, View> getHolderViews() {
//			return holderViews;
//		}
//
//		public void setHolderViews(Map<String, View> holderViews) {
//			this.holderViews = holderViews;
//		}
//
//		public Object getObject() {
//			return object;
//		}
//
//		public void setObject(Object object) {
//			this.object = object;
//		}
//	}
//
//	/**
//	 * 选择所需填充的控件类型， 默认填充TextView、EditText类型,如需改变可以重写该方法但该View必须有setText()方法
//	 *
//	 * @param view
//	 * @return
//	 */
//	public boolean canFillType(View view) {
//		boolean canfill = false;
//		if (view instanceof TextView || view instanceof EditText) {
//			canfill = true;
//		}
//		return canfill;
//	}
//
//	/**
//	 * 设置文字
//	 *
//	 * @param view
//	 * @param text
//	 */
//	public void viewToSetText(View view, String text) {
//		if (canFillType(view)) {
//			if (view instanceof TextView) {
//				((TextView) view).setText(text);
//			} else if (view instanceof EditText) {
//				((EditText) view).setText(text);
//			} else if (view instanceof Button) {
//				((Button) view).setText(text);
//			} else if (view instanceof CheckBox) {
//				((CheckBox) view).setText(text);
//			} else if (view instanceof RadioButton) {
//				((RadioButton) view).setText(text);
//			} else if (view instanceof ToggleButton) {
//				((ToggleButton) view).setText(text);
//			} else if (customViewToSetText(view, text)) {
//
//			} else {
//				L.info("没有找到" + view.getClass().getName()
//						+ "的类型，自定义view类型请重写customViewToSetText方法");
//			}
//		}
//	}
//
//	/**
//	 * 自定义View填充数据
//	 *
//	 * @param view
//	 * @param text
//	 * @return
//	 */
//	public boolean customViewToSetText(View view, String text) {
//		return false;
//	}
//
//	/**
//	 * item监听
//	 *
//	 * @author OAIM
//	 *
//	 */
//	class mCick implements OnClickListener {
//		private View convertView;
//		private RowObject row;
//		private int position;
//
//		/**
//		 * 回调 item 监听接口
//		 *
//		 * @param position
//		 */
//		public mCick(View convertView, RowObject row, int position) {
//			this.convertView = convertView;
//			this.row = row;
//			this.position = position;
//		}
//
//		@Override
//		public void onClick(View v) {
//			if (onItemClickListener != null) {
//				onItemClickListener.onItemClick(convertView, row, position);
//			}
//		}
//	}
//
//	/**
//	 * item 监听接口
//	 */
//	public interface OnItemClickListener {
//		void onItemClick(View convertView, RowObject row, int position);
//	}
//
//	/**
//	 * item 自定义
//	 *
//	 * @param views
//	 * @param holder
//	 */
//	public abstract void setItem(View convertView, RowObject row, int position,
//			Map<String, View> views, ViewHolder holder);
//
//	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//		this.onItemClickListener = onItemClickListener;
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
//	public Context getContext() {
//		return context;
//	}
//
//	public void setContext(Context context) {
//		this.context = context;
//	}
//
//}
