package com.myutils.ui.apdater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.myutils.base.AppFactory;
import com.myutils.core.RowObject;
import com.myutils.core.gson.JSONSerializer;
import com.myutils.core.FillMessageUnit;
import com.myutils.utils.ViewUtils;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2015-11-13
 * @Descrition BaseFillListViewAdapter封装,根据布局的id和json中key的字段来填充数据,绑定数据填充模块
 */
public abstract class BaseFillAdapter extends BaseAdapter {

	private Context context;
	// 数据集 List<Map<?,?>>类型
	private List<RowObject> rows;
	// 布局文件
	private int layout;
	// item监听
	private OnItemClickListener onItemClickListener;

	public BaseFillAdapter(Context context, List<RowObject> rows,
			int layout) {
		super();
		this.context = context;
		this.rows = rows;
		this.layout = layout;
	}

	public BaseFillAdapter(Context context, String jsonStr, int layout) {
		super();
		this.context = context;
		this.rows = JSONSerializer.getRows(jsonStr);
		this.layout = layout;
	}

	public BaseFillAdapter(List<RowObject> rows, int layout) {
		super();
		this.context = AppFactory.getAppContext();
		this.rows = rows;
		this.layout = layout;
	}

	public BaseFillAdapter(String jsonStr, int layout) {
		super();
		this.context = AppFactory.getAppContext();
		this.rows = JSONSerializer.getRows(jsonStr);
		this.layout = layout;
	}

	@Override
	public int getCount() {
		if (rows == null) {
			return 0;
		}
		return rows.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(layout, null);
			Map<String, View> allChildViewWithId = ViewUtils.getAllChildViewWithId(convertView);
			holder.setViews(allChildViewWithId);
			holder.setFillMessageUnit(new FillMessageUnit(allChildViewWithId));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//数据填充
		RowObject row = rows.get(position);
		holder.getFillMessageUnit().fillMessageByView(row);
		setItem(convertView, row, position, holder.getViews(), holder);
		convertView.setOnClickListener(new mCick(convertView, row, position));
		return convertView;
	}


	/**
	 * 添加数据
	 * 
	 * @param jsonStr
	 */
	public void addJsonData(String jsonStr) {
		List<RowObject> list = JSONSerializer.getRows(jsonStr);
		if (rows == null) {
			rows = new ArrayList<RowObject>();
		}
		if (list != null) {
			rows.addAll(list);
			notifyDataSetChanged();
		}
	}

	/**
	 * 添加数据
	 * @param list
	 */
	public void addListData(List<RowObject> list) {
		if (rows == null) {
			rows = new ArrayList<RowObject>();
		}
		if (list != null) {
			rows.addAll(list);
			notifyDataSetChanged();
		}
	}

	/**
	 * 清除数据
	 */
	public void clearData() {
		if (rows != null) {
			rows.clear();
			notifyDataSetChanged();
		}
	}

	/**
	 * ItemView初始化
	 */
	public class ViewHolder {
		/**
		 * 所有带id的View
		 */
		private Map<String, View> views = new HashMap<String, View>();

		private Object object;
		
		private FillMessageUnit fillMessageUnit;

		public Map<String, View> getViews() {
			return views;
		}

		public void setViews(Map<String, View> views) {
			this.views = views;
		}

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}

		public FillMessageUnit getFillMessageUnit() {
			return fillMessageUnit;
		}

		public void setFillMessageUnit(FillMessageUnit fillMessageUnit) {
			this.fillMessageUnit = fillMessageUnit;
		}
	}


	/**
	 * item监听
	 *
	 */
	class mCick implements OnClickListener {
		private View convertView;
		private RowObject row;
		private int position;
		/**
		 * 回调 item 监听接口
		 * @param position
		 */
		public mCick(View convertView, RowObject row, int position) {
			this.convertView = convertView;
			this.row = row;
			this.position = position;
		}
		@Override
		public void onClick(View v) {
			if (onItemClickListener != null) {
				onItemClickListener.onItemClick(convertView, row, position);
			}
		}
	}

	/**
	 * item 监听接口
	 */
	public interface OnItemClickListener {
		void onItemClick(View convertView, RowObject row, int position);
	}

	/**
	 * item 自定义
	 * 
	 * @param views
	 * @param holder
	 */
	public abstract void setItem(View convertView, RowObject row, int position,
			Map<String, View> views, ViewHolder holder);

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public List<RowObject> getRows() {
		return rows;
	}

	public void setRows(List<RowObject> rows) {
		this.rows = rows;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
