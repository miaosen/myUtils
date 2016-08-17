package com.myutils.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myutils.R;
import com.myutils.core.logger.L;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-6-20
 * @Descrition 基于DialogFragment创建的弹窗
 * 
 */
// TODO 转屏时此类的变量被清空
@SuppressLint("NewApi")
public class BaseFmDialog extends DialogFragment {

	private String TAG = "BaseFmDialog";

	private Activity activity;
	private View dialogView;

	int layout;

	public BaseFmDialog() {

	}

	public BaseFmDialog(Context context, View view) {
		this.activity = (Activity) context;
		this.dialogView = view;
	}

	public BaseFmDialog(Activity activity, View view) {
		this.activity = activity;
		this.dialogView = view;
	}

	public BaseFmDialog(Activity activity, int layout) {
		this.activity = activity;
		this.layout = layout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL,
				android.R.style.Theme_Black_NoTitleBar_Fullscreen);

	}

	//
	// @Override
	// public Dialog onCreateDialog(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	// builder.setView(dialogView);
	// return builder.create();
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		L.i("onCreateView=================");
		View view = inflater.inflate(R.layout.attachment_layout, null);
		final TextView tv_file = (TextView) view.findViewById(R.id.tv_file);
		tv_file.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tv_file.setText("DDDD");
			}
		});
		return view;
	}

	public void show(String tag) {
		android.app.FragmentManager fragmentManager = activity
				.getFragmentManager();
		super.show(fragmentManager, tag);
	}

	public void show() {
		android.app.FragmentManager fragmentManager = activity
				.getFragmentManager();
		super.show(fragmentManager, TAG);
	}

	// @Override
	// public void onStart() {
	// super.onStart();
	// Window window = getDialog().getWindow();
	// WindowManager.LayoutParams windowParams = window.getAttributes();
	// windowParams.dimAmount = 0.0f;
	// window.setAttributes(windowParams);
	// getDialog().getWindow().setBackgroundDrawable(
	// new ColorDrawable(Color.TRANSPARENT));
	// }
}
