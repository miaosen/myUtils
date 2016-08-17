package com.myutils.core;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.myutils.core.gson.JSONSerializer;
import com.myutils.ui.toast.T;
import com.myutils.unit.ResourceUnit;
import com.myutils.utils.JsonUtils;
import com.myutils.utils.RowUtils;
import com.myutils.utils.ViewUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-1-20
 * @Descrition View 数据填充模块，支持的数据类型为json,RowObject和list<RowObject>
 */
public class FillMessageUnit {

	private static final String TAG = "FillMessageUtil";
	private Context context;
	private View decorView = null;
	private OnFillMessageListener onFillMessageListener;

	private List<Class> viewType = new ArrayList<Class>();

	/**
	 * 是否填充多层
	 */
	private boolean isFillLayer = false;

	private Map<String, View> allChildViewWithIdName;




	public FillMessageUnit(Map<String, View> allChildViewWithIdName) {
		this.allChildViewWithIdName = allChildViewWithIdName;
	}


	/**
	 * 填充全部的View
	 * 
	 * @param context
	 */
	public FillMessageUnit(Context context) {
		this.context = context;
		this.decorView = ViewUtils.getDecorView(context);
	}

	/**
	 * 填充传入的View
	 * 
	 * @param decorView
	 */
	public FillMessageUnit(View decorView) {
		this.decorView = decorView;
	}

	/**
	 * json数据填充
	 */
	public void fillMessage(String json) {
		/*
		 * if (JsonUtils.isCanToRows(json)) {
		 * fillMessage(JSONSerializer.getRows(json)); } else
		 */if (JsonUtils.isCanToRow(json)) {
			if (JsonUtils.isCanToRow(json)) {
				fillMessage(JSONSerializer.getRow(json));
			}
		}
	}

	/**
	 * RowObject填充数据
	 * 
	 * @param row
	 */
	public void fillMessage(RowObject row) {
		for (String name : row.keySet()) {
			View view = null;
			if (decorView != null) {
				int id = ResourceUnit.getIdByName(name);
				if (id > 0) {
					view = decorView.findViewById(id);
					setMessage(view, name, row.getString(name) + "");
				}
			} else {
				T.show("view为空!");
			}
		}
	}

	/**
	 * RowObject填充数据
	 * 
	 * @param row
	 */
	public void fillMessageByView(RowObject row) {
		if (allChildViewWithIdName != null) {
			for (String name : allChildViewWithIdName.keySet()) {
				View view = allChildViewWithIdName.get(name);
				String[] split = name.split("\\$");
				//L.i("value========长度" + split.length + "     name==="
				//		+ name);
				String value = "";
				if (split.length < 2) {// 单层数据填充
					value = row.get(name) + "";
					if(row.get(name)!=null){
						setMessage(view, name, row.getString(name) + "");
					}
					//L.i("value========" + value);
				} else if (split.length >= 2) {// 多层数据填充（嵌套在里面row）
					value = RowUtils.getlayerData(row, split) + "";
					if(value==null||value.equals("")||value.equals("null")){
					}else{
						setMessage(view, name, value);
					}
					//L.i("value========大于2：" + value);
					
				}
				// setMessage(view, row.getString(name)+"");
			}

		}
	}

	/**
	 * 填充数据
	 * 
	 * @param view
	 * @param value
	 * @param name
	 */
	public void setMessage(View view, String name, String value) {
		if (view != null) {
			if (view instanceof TextView) {// 填充TextView或者TextView的子类
				((TextView) view).setText(value);
			} else if (view instanceof RadioGroup) {// 填充RadioGroup
				// Logger.info("---fill--RadioGroup---" + view.getId()
				// + row.get(name));
				RadioGroup rg = (RadioGroup) view;
				ViewUtils.fillRadioGroup(rg, value);
			} else if (view instanceof CheckBox) {// 填充CheckBox
				CheckBox cb = (CheckBox) view;
				String cbName = ResourceUnit.getNameById(cb.getId());
				if (cbName != null && cbName.startsWith(cbName)) {// 如果CheckBox的id字段名称以服务器返回的key为开始，则开始填充checkbox
					ViewUtils.fillCheckBox(cb, value);
				}
			}
			// 补充填充数据
			if (onFillMessageListener != null) {
				onFillMessageListener.fillMessage(name, value);
			}
			// }
		} else {
			// Logger.info(TAG+" viewToSetText===========view==null");
		}
	}

	/**
	 * 
	 * @param viewType
	 *            要填充的view的类型
	 */
	public void setViewType(Class... viewType) {
		for (int i = 0; i < viewType.length; i++) {
			this.viewType.add(viewType[i]);
		}
	}

	/**
	 * 数据补充填充接口
	 */
	public interface OnFillMessageListener {
		void fillMessage(String idName, String value);
	}

	public OnFillMessageListener getOnFillMessageListener() {
		return onFillMessageListener;
	}

	public void setOnFillMessageListener(
			OnFillMessageListener onFillMessageListener) {
		this.onFillMessageListener = onFillMessageListener;
	}


	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public View getDecorView() {
		return decorView;
	}

	public void setDecorView(View decorView) {
		this.decorView = decorView;
	}

	public boolean isFillLayer() {
		return isFillLayer;
	}

	public void setFillLayer(boolean isFillLayer) {
		this.isFillLayer = isFillLayer;
	}

	public Map<String, View> getAllChildViewWithIdName() {
		return allChildViewWithIdName;
	}

	public void setAllChildViewWithIdName(Map<String, View> allChildViewWithIdName) {
		this.allChildViewWithIdName = allChildViewWithIdName;
	}

	public static String getTag() {
		return TAG;
	}

}
