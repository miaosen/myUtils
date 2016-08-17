//package com.myutils.ui.apdater;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//
//import com.myutils.data.RowObject;
//import com.myutils.data.gson.JSONSerializer;
//import com.myutils.core.FillMessageUnit;
//
///**
// * @Created by gzpykj.com
// * @author zms
// * @Date 2015-11-13
// * @Descrition RecycleViewAdapter封装，绑定FillMessageUnit
// */
//public abstract class BaseFillRecycleViewAdapter extends
//		RecyclerView.Adapter<BaseFillRecycleViewAdapter.ViewHolder> {
//
//	// 数据集 List<Map<?,?>>类型
//	private List<RowObject> rows;
//	// 布局文件
//	private int layout;
//	// item监听
//	private OnItemClickListener onItemClickListener;
//
//
//	// item设置
//	// private OnItemLoading onItemLoading;
//
//	public BaseFillRecycleViewAdapter(List<RowObject> rows, int layout) {
//		super();
//		this.rows = rows;
//		this.layout = layout;
//	}
//
//	public BaseFillRecycleViewAdapter(String jsonStr, int layout) {
//		super();
//		this.rows = JSONSerializer.getRows(jsonStr);
//		this.layout = layout;
//	}
//
//	/**
//	 * 添加数据
//	 * @param jsonStr
//	 */
//	public void addJsonData(String jsonStr){
//		//TODO 值支持jsonObject类型
//		List<RowObject> list=JSONSerializer.getRows(jsonStr);
//		if(rows==null){
//			rows=new ArrayList<RowObject>();
//		}
//		if(list!=null){
//			rows.addAll(list);
//			notifyDataSetChanged();
//		}
//	}
//
//	/**
//	 * 添加数据
//	 * @param jsonStr
//	 */
//	public void addListData(List<RowObject> list){
//		if(rows==null){
//			rows=new ArrayList<RowObject>();
//		}
//		if(list!=null){
//			rows.addAll(list);
//			notifyDataSetChanged();
//		}
//	}
//
//	/**
//	 * 清除数据
//	 * @param jsonStr
//	 */
//	public void clearData(){
//		if(rows!=null){
//			rows.clear();
//			notifyDataSetChanged();
//		}
//	}
//
//	@Override
//	public int getItemCount() {
//		if (rows != null) {
//			return rows.size();
//		} else {
//			return 0;
//		}
//	}
//
//	@Override
//	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//		//Context context = viewGroup.getContext();
//		View view = View.inflate(viewGroup.getContext(), layout, null);
//		// 创建一个ViewHolder
//		ViewHolder holder = new ViewHolder(view, i);
//		return holder;
//	}
//
//	/**
//	 * 绑定数据到ViewHolder上
//	 */
//	@Override
//	public void onBindViewHolder(ViewHolder viewHolder, int i) {
//		RowObject row = rows.get(i);
//		viewHolder.fillMessageUnit.fillMessage(row);
//		viewHolder.itemView.setOnClickListener(new mCick(viewHolder, row));
//		setItem(viewHolder, row, i);
//
//	}
//
//	/**
//	 * ItemView初始化
//	 */
//	public class ViewHolder extends RecyclerView.ViewHolder {
//		// private List<RowObject> rows;
////		private Map<String, View> views;
//		private FillMessageUnit fillMessageUnit;
//
//		public ViewHolder(View itemView, int i) {
//			super(itemView);
//			fillMessageUnit=new FillMessageUnit(itemView);
////			if (views == null) {
////				views = new HashMap<String, View>();
////				for (String idName : rows.get(i).keySet()) {
////					View view = itemView.findViewById(GenUtil.getIdByName(
////							idName));
////
////				}
////			}
//		}
//
//	}
//
//	/**
//	 * 选择所需填充的控件类型，
//	 *  默认填充TextView、EditText类型,如需改变可以重写该方法但该View必须有setText()方法
//	 * @param view
//	 * @return
//	 */
////	public boolean canFillType(View view) {
////		boolean canfill = false;
////		if (view instanceof TextView || view instanceof EditText) {
////			canfill = true;
////		}
////		return canfill;
////	}
////
////	/**
////	 * 设置文字
////	 *
////	 * @param view
////	 * @param text
////	 */
////	public void viewToSetText(View view, String text) {
////		if (view instanceof TextView) {
////			((TextView) view).setText(text);
////		} else if (view instanceof EditText) {
////			((EditText) view).setText(text);
////		} else if (view instanceof Button) {
////			((Button) view).setText(text);
////		} else if (view instanceof CheckBox) {
////			((CheckBox) view).setText(text);
////		} else if (view instanceof RadioButton) {
////			((RadioButton) view).setText(text);
////		} else if (view instanceof ToggleButton) {
////			((ToggleButton) view).setText(text);
////		} else if (customViewToSetText(view, text)) {
////
////		} else {
////			Logger.info("没有找到"+view.getClass().getName()+"的类型，自定义view类型请重写customViewToSetText方法");
////		}
////	}
////
////
////	/**
////	 * 自定义View填充数据
////	 * @param view
////	 * @param text
////	 * @return
////	 */
////	public boolean customViewToSetText(View view, String text) {
////		return false;
////	}
//
//	class mCick implements OnClickListener {
//		private ViewHolder viewHolder;
//		private RowObject row;
//
//		/**
//		 * 回调 item 监听接口
//		 */
//		public mCick(ViewHolder viewHolder, RowObject row) {
//			this.viewHolder = viewHolder;
//			this.row = row;
//		}
//
//		@Override
//		public void onClick(View v) {
//			if (onItemClickListener != null) {
//				onItemClickListener.onItemClick(viewHolder, row, v);
//			}
//		}
//	}
//
//	/**
//	 * item 监听接口
//	 */
//	public interface OnItemClickListener {
//		void onItemClick(ViewHolder viewHolder, RowObject row, View itemView);
//	}
//
//	/**
//	 * item 自定义
//	 */
//	public abstract void setItem(ViewHolder viewHolder, RowObject row, int i);
//
//
//
//	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//		this.onItemClickListener = onItemClickListener;
//	}
//
//	/**
//	 * item 自定义 接口方式
//	 */
//	// public interface OnItemLoading{
//	// void setItem(ViewHolder viewHolder, int i);
//	// }
//	// public void setOnItemLoading(OnItemLoading onItemLoading) {
//	// this.onItemLoading = onItemLoading;
//	// }
//
//}
