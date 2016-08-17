package com.myutils.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
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

import com.myutils.core.logger.L;
import com.myutils.ui.view.annotation.ViewInject;
import com.myutils.ui.view.date.DateButton;
import com.myutils.unit.ResourceUnit;

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
	 * 获取activity下的所有view，包括依赖activity的dialog的view
	 * 
	 * @param activity
	 * @return
	 */
	public List<View> getDecorViews(Activity activity) {
		View view = activity.getWindow().getDecorView();
		return getAllChildViews(view);
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
		}
		return allchildren;
	}
	
	/**
	 * 获取一个View下所有的id不为空的view
	 * @param view
	 * @return 以id名称为key的，value为对应的key
	 */
	public static Map<String,View> getAllChildViewWithId(View view) {
		Map<String,View> allchildren = new LinkedHashMap<String, View>();
		if (view instanceof ViewGroup) {
			ViewGroup vp = (ViewGroup) view;
			for (int i = 0; i < vp.getChildCount(); i++) {
				View viewchild = vp.getChildAt(i);
				if(viewchild.getId()>0){
					allchildren.put(ResourceUnit.getNameById(viewchild.getId()),viewchild);
				}
				allchildren.putAll(getAllChildViewWithId(viewchild));
			}
		}
		return allchildren;
	}

	public static Map<Integer, View> getFormView(Activity context,
			Class<?>[] formViewType, Integer... viewIds) {
		View view = ViewUtils.getDecorView(context);
		return getFormView(view, formViewType, viewIds);

	}

	/**
	 * 获取一个View中所有的子View返回一个Map
	 * 
	 * @param view
	 * @param formViewType
	 * @return
	 */

	// public static Map<Integer, View> getViewMap(View view,
	// Class<?>... formViewType) {
	// Map<Integer, View> allchildren = new HashMap<Integer, View>();
	// if (view instanceof ViewGroup) {
	// ViewGroup vp = (ViewGroup) view;
	// for (int i = 0; i < vp.getChildCount(); i++) {
	// View viewchild = vp.getChildAt(i);
	// if (ClassUtils.isClassTypeOf(viewchild, formViewType)) {
	// if (viewchild.getId() >0) {
	// allchildren.put(viewchild.getId(), viewchild);
	// }
	// }
	// allchildren.putAll(getViewMap(viewchild, formViewType));
	// }
	// }else {
	// if (view.getId()>0) {
	// allchildren.put(view.getId(), view);
	// }
	// }
	// return allchildren;
	// }

	/**
	 * 获取所有的表单View
	 * 
	 * @param view
	 * @param viewIds
	 *            需要过滤的View或者Viewgroup的ViewId
	 * @return 返回表单类型的View，在FormViewConfig中定义的类型
	 */
	@SuppressLint("UseSparseArrays")
	public static Map<Integer, View> getFormView(View view,
			Class<?>[] formViewType, Integer... viewIds) {
		Map<Integer, View> allchildren = new HashMap<Integer, View>();
		if (view instanceof ViewGroup) {
			if (StringUtils.isInArray(viewIds, -1)
					|| CompareUtils.containsKey(view.getId(), viewIds)) {
			} else {
				ViewGroup vp = (ViewGroup) view;
				for (int i = 0; i < vp.getChildCount(); i++) {
					View viewchild = vp.getChildAt(i);
					if (CompareUtils.containsKey(view.getId(), viewIds)) {
					} else {
						if (ClassUtils.isClassTypeOf(viewchild, formViewType)) {
							if (viewchild.getId() != -1) {
								allchildren.put(viewchild.getId(), viewchild);
							}
						}
					}
					allchildren.putAll(getFormView(viewchild, formViewType,
							viewIds));
				}
			}
		}
		return allchildren;
	}

	/**
	 * 读取activity内注解
	 * 
	 * @param activity
	 */
	public static void injectAllField(Activity activity) {
		// 读取activity父类注解
		for (Class<?> clazz = activity.getClass(); clazz != Activity.class; clazz = clazz
				.getSuperclass()) {
			try {
				injectAllFields(activity, null, clazz);
			} catch (Exception e) {
				// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				// 如果这里的异常打印或者往外抛，则就不会执行clazz =
				// clazz.getSuperclass(),最后就不会进入到父类中了
			}
		}
	}

	/**
	 * 读取View内注解
	 * 
	 * @param view
	 */
	public static void injectAllField(View view) {
		injectAllFields(view, null, null);
	}

	/**
	 * 解析所有注解,并给View赋值
	 * 
	 * @param injectView
	 *            需要赋值的view
	 */
	public static void injectAllFields(Object object, View injectView) {
		// 读取对象父类注解，子类必须实现ViewInjectInterface接口
		if(object.getClass().getSuperclass()!=null){
			for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz
					.getSuperclass()) {
				try {
					injectAllFields(object, injectView, clazz);
				} catch (Exception e) {
					// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
					// 如果这里的异常打印或者往外抛，则就不会执行clazz =
					// clazz.getSuperclass(),最后就不会进入到父类中了
				}
			}
		}
		injectAllFields(object, injectView, null);
	}

	/**
	 * 解析所有注解
	 * 
	 *            需要赋值的view
	 */
	public static void injectAllFields(Object object, View injectView,
			Class<?> clazz) {
		try {
			if (clazz == null) {
				clazz = object.getClass();
			}
			Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
			for (Field field : fields) {
				// 查看这个字段是否有我们自定义的注解类标志的
				// Logger.i("field===="+field.getName());
				if (field.isAnnotationPresent(ViewInject.class)) {
					ViewInject inject = field.getAnnotation(ViewInject.class);
					int id = inject.value();
					field.setAccessible(true);
					if (id == 0) {
						id = ResourceUnit.getIdByName(field.getName());
					}
					if (object instanceof Activity) {
						Activity activity = (Activity) object;
						field.set(activity, activity.findViewById(id));// 给我们要找的字段设置值
						// Logger.i("injectAllField==============" + id +
						// "=====" + inject.annotationType());
					} else if (object instanceof View) {
						View view = (View) object;
						field.set(view, view.findViewById(id));
					} else {
						if (injectView != null) {
							field.set(object, injectView.findViewById(id));
						}
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
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
	 * 获取View中填写的数据，返回一个key为id名称，value为它填写的值
	 * 
	 * @return
	 */
	public static Map<String, Object> getFormMap(View view,
			Class<?>... formViewType) {
		Map<String, Object> formMap = new HashMap<String, Object>();
		if (view instanceof ViewGroup) {
			ViewGroup vp = (ViewGroup) view;
			for (int i = 0; i < vp.getChildCount(); i++) {
				View viewchild = vp.getChildAt(i);
				if (viewchild instanceof ViewGroup) {
					formMap.putAll(getFormMap(viewchild, formViewType));
				} else {
					if (ClassUtils.isClassTypeOf(viewchild, formViewType)) {
						if (viewchild.getId() > 0) {
							String idName = ResourceUnit.getNameById(viewchild
									.getId());
							L.i("getFormMap=======" + idName
									+ "  value==="
									+ ((TextView) viewchild).getText() + "");
							if (idName != null) {
								// TODO 统一一个方法取值
								if (viewchild instanceof TextView) {
									formMap.put(idName,
											((TextView) viewchild).getText()
													+ "");

								} else if (viewchild instanceof RadioGroup) {
									RadioGroup rg = (RadioGroup) viewchild;
									RadioButton rb = (RadioButton) rg
											.findViewById(rg
													.getCheckedRadioButtonId());
									if (rb != null) {
										formMap.put(idName, rb.getText() + "");
									}
								} else if (viewchild instanceof CheckBox) {
									// TODO 获取CheckBox
								}
							}
						}
					}
				}
			}
		} else {
		}
		return formMap;
	}

	/**
	 * 获取View中填写的数据，返回一个key为id名称，value为它填写的值,默认取View中的EditText，DateButton，
	 * RadioGroup
	 * 
	 * @return
	 */
	public static Map<String, Object> getFormMap(View view) {
		return getFormMap(view, EditText.class, DateButton.class,
				RadioGroup.class);
	}

	/**
	 * 获取activity整个布局中填写的数据，返回一个key为id名称，value为它填写的值,默认取View中的EditText，
	 * DateButton，RadioGroup
	 * 
	 * @return
	 */
	public static Map<String, Object> getFormMap(Context context) {
		View decorView = getDecorView(context);
		return getFormMap(decorView, EditText.class, DateButton.class,
				RadioGroup.class);
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
