package com.myutils.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.myutils.base.AppFactory;
import com.myutils.core.logger.L;
import com.myutils.core.annotation.ViewInject;
import com.myutils.ui.view.date.DateButton;
import com.myutils.core.form.ResourceHold;

public class ViewUtils {

	/**
	 * 获取activity根布局
	 * 
	 * @param context
	 * @return
	 */
	public static ViewGroup getContentView(Context context) {
		ViewGroup viewGroup = (ViewGroup) ((ViewGroup) ((Activity) context)
				.getWindow().getDecorView().findViewById(android.R.id.content))
				.getChildAt(0);
		return viewGroup;
	}

	/**
	 * 获取activity下的view,包括依赖activity的dialog的view
	 * 
	 * @param activity
	 * @return
	 */
	public static View getDecorView(Activity activity) {
		return activity.getWindow().getDecorView();
	}

	public static View getDecorView(Context context) {
		return ((Activity) context).getWindow().getDecorView();
	}

	/**
	 * @note 获取一个View下所有的view
	 */
	public static List<View> getAllChildViews(View view) {
		List<View> allchildren = new ArrayList<View>();
		if (view instanceof ViewGroup) {
			ViewGroup vp = (ViewGroup) view;
			for (int i = 0; i < vp.getChildCount(); i++) {
				View viewchild = vp.getChildAt(i);
				allchildren.add(viewchild);
				allchildren.addAll(getAllChildViews(viewchild));
			}
		}else{
			allchildren.add(view);
		}
		return allchildren;
	}
	
	/**
	 * 获取一个View下所有的id不为空的view,并返回一个map
	 * @param view
	 * @return 以id名称为key的，value为对应的View
	 */
	public static Map<String,View> getViewWithIdName(View view) {
		Map<String,View> allchildren = new LinkedHashMap<String, View>();
		if (view instanceof ViewGroup) {
			ViewGroup vp = (ViewGroup) view;
			if(vp.getId()>0){
				String nameById = ResourceHold.getNameById(vp.getId());
				if(nameById!=null){
					allchildren.put(nameById,vp);
				}
			}
			for (int i = 0; i < vp.getChildCount(); i++) {
				View viewchild = vp.getChildAt(i);
				if(viewchild.getId()>0){
					String nameById = ResourceHold.getNameById(viewchild.getId());
					if(nameById!=null) {
						allchildren.put(nameById, viewchild);
					}
				}
				allchildren.putAll(getViewWithIdName(viewchild));
			}
		}else{
			if(view.getId()>0){
				String nameById = ResourceHold.getNameById(view.getId());
				if(nameById!=null) {
					allchildren.put(nameById, view);
				}
			}
		}
		return allchildren;
	}





	/**
	 * 填充RadioGroup 根据传入的值和RadioButton的值是否对应来选中radiobutton
	 * 
	 * @param rg
	 * @param value
	 *            对应着RadioGroup中RadioButton的text
	 */
	public static void fillRadioGroup(RadioGroup rg, String value) {
		if (rg != null) {
			List<View> rgViews = ViewUtils.getAllChildViews(rg);
			for (int i = 0; i < rgViews.size(); i++) {
				if (rgViews.get(i) instanceof RadioButton) {
					RadioButton rbNew = (RadioButton) rgViews.get(i);
					if (rbNew.getText() != null
							&& rbNew.getText().equals(value)) {
						rbNew.setChecked(true);
					}
				}
			}
		}
	}

	/**
	 * 填充CheckBox 根据传入的值和CheckBox的值是否对应来选中CheckBox
	 * 
	 * @param view
	 *            包含多个checkbox的View
	 * @param value
	 *            格式为"a,b,c"的可以分割的字符串 对应着RadioGroup中RadioButton的text
	 */
	public static void fillCheckBoxOnView(View view, String value) {
		String[] split = value.split(",");
		HashMap<String, Object> map = StringUtils.argsToMap(split);
		if (view != null) {
			List<View> rgViews = ViewUtils.getAllChildViews(view);
			for (int i = 0; i < rgViews.size(); i++) {
				if (rgViews.get(i) instanceof CheckBox) {
					CheckBox cb = (CheckBox) rgViews.get(i);
					if (cb.getText() != null && map.containsKey(cb.getText())) {
						cb.setChecked(true);
					}
				}
			}
		}
	}

	/**
	 * 填充CheckBox 根据传入的值和CheckBox的值是否对应来选中CheckBox
	 * 
	 * @param cb
	 * @param value
	 */
	public static void fillCheckBox(CheckBox cb, String value) {
		if (cb != null) {
			String[] split = value.split(",");
			HashMap<String, Object> map = StringUtils.argsToMap(split);
			if (cb.getText() != null && map.containsKey(cb.getText())) {
				cb.setChecked(true);
			}
		}
	}


	/**
	 * 通过资源文件id获取View
	 * 
	 * @param context
	 * @param layout
	 * @return
	 */
	public static View inflatView(Context context, int layout) {
		View view = LayoutInflater.from(context).inflate(layout, null);
		return view;

	}


	/**
	 * 以RadioGroup的id名称为key，并获取其选中的值为value，返回一个HashMap
	 *
	 * @param rg
	 * @param defaultValue
	 *            没有选中时的默认值
	 * @return
	 */
	public static HashMap<String, Object> getRadioGroupMap(RadioGroup rg, String defaultValue) {
		if (rg != null) {
			if (rg.getId() > 0) {
				String nameById = ResourceHold.getNameById(rg.getId());
				int checkedRadioButtonId = rg.getCheckedRadioButtonId();
				HashMap<String, Object> map = new HashMap<String, Object>();
				if (checkedRadioButtonId > 0) {
					RadioButton rb = (RadioButton) rg.findViewById(checkedRadioButtonId);
					String value = rb.getText() + "";
					map.put(nameById, value);
					return map;
				} else if (defaultValue != null) {
					map.put(nameById, defaultValue);
					return map;
				}
			}
		}
		return null;
	}


	/**
	 * 动态设置gridview高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(GridView listView) {
		// 获取listview的adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		// 固定列宽，有多少列
		int col = 5;// listView.getNumColumns();
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		for (int i = 0; i < listAdapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}

		// 获取listview的布局参数
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// 设置高度
		params.height = totalHeight;
		// 设置margin
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		// 设置参数
		listView.setLayoutParams(params);
	}

}
