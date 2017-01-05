package com.myutils.core.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.myutils.utils.StringUtils;

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
		return getViewWithIdName(view,null);
	}
	
	/**
	 * 获取一个View下所有的id不为空的view,并返回一个map
	 * @param view
	 * @param viewFilter 过滤器，不需要的
	 * @return 以id名称为key的，value为对应的View
	 */
	public static Map<String,View> getViewWithIdName(View view,ViewFilter viewFilter) {
		Map<String,View> allchildren = new LinkedHashMap<String, View>();
		if (view instanceof ViewGroup) {
			ViewGroup vp = (ViewGroup) view;
			if (viewFilter!=null||viewFilter.filter(view)) {
			}else {
				if (vp.getId() > 0) {
					String nameById = ResourceHold.getNameById(vp.getId());
					if (nameById != null) {
						allchildren.put(nameById, vp);
					}
				}
			}
			if(vp instanceof FormViewAdpater&&((FormViewAdpater)vp).isScanAsOne){//不扫描里面的View
			}else{
				for (int i = 0; i < vp.getChildCount(); i++) {
					View viewchild = vp.getChildAt(i);
					if (viewFilter!=null||viewFilter.filter(view)) {
					}else {
						if (viewchild.getId() > 0) {
							String nameById = ResourceHold.getNameById(viewchild.getId());
							if (nameById != null) {
								allchildren.put(nameById, viewchild);
							}
						}
					}
					allchildren.putAll(getViewWithIdName(viewchild,viewFilter));
				}
			}
		}else{
			if ((viewFilter!=null||viewFilter.filter(view))&&view.getId()<=0) {
			}else {
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

	public static HashMap<String, Object> getContentValues(View view){
		Map<String, View> viewWithIdName = getViewWithIdName(view);
		return getContentValues(viewWithIdName);
	}


	public static LinkedHashMap<String, Object> getContentValues(Map<String, View> viewWithIdName){
		LinkedHashMap<String, Object> contentMap = new LinkedHashMap<String, Object>();
		for (String idName: viewWithIdName.keySet()) {
			View view = viewWithIdName.get(idName);
				if (view instanceof FormViewAdpater) {//实现ViewValueAdpater接口的自定义View填充
					Object value = ((FormViewAdpater) view).getValue();
					if(value!=null){
						contentMap.put(idName, value);
					}
				} else if (view instanceof TextView) {//获取TextView或者TextView的子类文字
					TextView tv = (TextView) view;
					if (!TextUtils.isEmpty(tv.getText())) {
						contentMap.put(idName, tv.getText());
					}
				} else if (view instanceof RadioGroup) {
					RadioGroup rg = (RadioGroup) view;
					HashMap<String, Object> radioGroupMap = ViewUtils.getRadioGroupMap(rg, null);
					if (radioGroupMap != null) {
						contentMap.putAll(radioGroupMap);
					}
				}
		}
		return contentMap;
	}



}
