package com.myutils.core.annotation;

import android.app.Activity;
import android.view.View;

import com.myutils.core.form.ResourceHold;

import java.lang.reflect.Field;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/7 11:08
 * @Descrition View相关的注解读取
 */
public class InjectReader {

    /**
     * 读取activity内注解
     *
     * @param activity
     */
    public static void injectAllFields(Activity activity) {
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
     * 解析所有注解,并给View赋值
     *
     * @param injectView
     *            需要赋值的view
     */
    public static void injectAllFields(Object object, View injectView) {
        // 读取对象父类注解
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
     * 读取View内注解
     *
     * @param view
     */
    public static void injectAllFields(View view) {
        injectAllFields(view, null, null);
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
                if (field.isAnnotationPresent(ViewInject.class)) {
                    ViewInject inject = field.getAnnotation(ViewInject.class);
                    int id = inject.value();
                    field.setAccessible(true);
                    if (id == 0) {
                        id = ResourceHold.getIdByName(field.getName());
                    }
                    if (object instanceof Activity) {
                        Activity activity = (Activity) object;
                        field.set(activity, activity.findViewById(id));// 给我们要找的字段设置值
                        // Logger.i("injectAllFields==============" + id +
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

}
