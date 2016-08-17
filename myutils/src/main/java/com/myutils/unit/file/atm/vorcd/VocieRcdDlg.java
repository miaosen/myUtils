package com.myutils.unit.file.atm.vorcd;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.myutils.R;

public class VocieRcdDlg extends Dialog{

	private Context context;
	private View view;
	
	
	public VocieRcdDlg(Context context,View view) {
		super(context,R.style.custom_dialog);
		this.context=context;
		this.view=view;
		view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_white));
		setContentView(view);
	}
	
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(view);
//	}
//	
	

}
