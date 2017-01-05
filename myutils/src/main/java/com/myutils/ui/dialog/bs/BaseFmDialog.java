package com.myutils.ui.dialog.bs;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.myutils.base.L;

/**
 * @author zms
 * @Created by gzpykj.com
 * @Date 2016-6-20
 * @Descrition 基于DialogFragment创建的弹窗
 */
// TODO 转屏时此类的变量被清空
@SuppressLint("NewApi")
public abstract class BaseFmDialog extends DialogFragment {

    private String TAG = "BaseFmDialog";

    public View dialogView;


    public OnSureListener onSureListener = null;

    public OnCancleListener onCancleListener = null;

    public LayoutInflater mInflater;


    @Deprecated
    public BaseFmDialog() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        L.i("onCreateView=================");
        //不要使用方法里的inflater，否则不能在dialog里弹出另一个dialog
        mInflater=LayoutInflater.from(getContext());
        dialogView = mInflater.inflate(setLayout(), null, false);
        init(dialogView);
        return dialogView;
    }

    public abstract int setLayout();


    public abstract void init(View dialogView);


    @Override
    public void dismiss() {
        super.dismiss();
    }


    public interface OnSureListener {
        void sure(BaseFmDialog baseFmDialog);
    }

    public interface OnCancleListener {
        void cancle(BaseFmDialog baseFmDialog);
    }


    public OnSureListener getOnSureListener() {
        return onSureListener;
    }

    public void setOnSureListener(OnSureListener onSureListener) {
        this.onSureListener = onSureListener;
    }

    public OnCancleListener getOnCancleListener() {
        return onCancleListener;
    }

    public void setOnCancleListener(OnCancleListener onCancleListener) {
        this.onCancleListener = onCancleListener;
    }


}
