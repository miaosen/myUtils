package com.myutils.core.okhttp.callback;


import com.myutils.base.BaseActivity;
import com.myutils.base.L;
import com.myutils.core.JSONResult;
import com.myutils.core.ResultCallBack;
import com.myutils.core.okhttp.HandlerQueue;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @CreateDate 2016/8/7 20:01
 * @Descrition 文本回调
 */
public abstract class CustomeCallBack<T extends JSONResult> extends ResultCallBack {

    public T t;

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (response.isSuccessful()) {
            final String result = response.body().string();
            HandlerQueue.onResultCallBack(new Runnable() {
                @Override
                public void run() {
                    try {
                        Class genericType = getGenericType();
                        Constructor cons = genericType.getDeclaredConstructor(String.class);
                        t= (T)cons.newInstance(result);
                        onSuccess(t);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }


    @SuppressWarnings("rawtypes")
    private Class getGenericType() {
        Class cs = getTargetType(getClass());
        if (cs != null) {
            Type genType = cs.getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType)
                    .getActualTypeArguments();
            for (Type type : params) {
                L.i("type===========" + type.toString());
            }
            return (Class) params[0];
        }
        return null;
    }

    private Class getTargetType(Class clazz) {
        // Logger.info(clazz.getName());
        Class pcls = clazz.getSuperclass();
        L.i("=========getTargetType==============" + pcls);
        if (pcls != null) {
            if (pcls.getName().equals(CustomeCallBack.class.getName())) {
                return clazz;
            } else {
                Class sp = clazz.getSuperclass();
                if (sp != null) {
                    return getTargetType(sp);
                }
                return null;
            }
        }
        return null;
    }
}
