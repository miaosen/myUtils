package com.myutils.ui.view;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.myutils.base.L;
import com.myutils.core.form.ViewUtils;


/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-9-6
 * @Descrition 仿照RadioGroup,解决RadioButton只能横向或者竖向布局
 */
public class CustomRadioGroup extends LinearLayout{
	
	private Context context;
	
	private List<RadioButton > listRb=new ArrayList<RadioButton>();



	public CustomRadioGroup(Context context) {
		super(context);
		 this.context= context;
	}
	

	public CustomRadioGroup(Context context,AttributeSet attr) {
		super(context, attr);
		this.context= context;
		
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(!isInEditMode()){
			init();
		}
	}
	
	

	private void init() {
		List<View> allChildViews = ViewUtils.getAllChildViews(this);
		//L.i("CustomRadioGroup==="+allChildViews.size());
		for (int i = 0; i < allChildViews.size(); i++) {
			View view = allChildViews.get(i);
			if(view instanceof RadioButton){
				RadioButton rb=(RadioButton) view;
				rb.setOnCheckedChangeListener(new mCheckedChange());
				//rb.setClickable(false);
				rb.setFocusable(false);
				//rb.set(false);
				listRb.add(rb);
			}
		}
	}
	
	
	
	
	class mCheckedChange implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			RadioButton rb=(RadioButton) buttonView;
			unCheckAll();
			if(isChecked){
				rb.setChecked(true);
			}else{
				//rb.setChecked(false);
			}
		}
	}
	
	
	/**
	 * 清除所有被选择按钮
	 */
	private void unCheckAll(){
		for (int i = 0; i < listRb.size(); i++) {
			RadioButton radioButton = listRb.get(i);
			if(radioButton.isChecked()){
				radioButton.setChecked(false);
			}
		}
	}
	
	
	
	
	public void setCheck(String value){
		L.i("CustomRadioGroup===value======"+value+"==text==");
		for (int i = 0; i < listRb.size(); i++) {
			RadioButton radioButton = listRb.get(i);
			String text = radioButton.getText()+"";
			if(text.equals(value)){
				radioButton.setChecked(true);
			}
		}
	}
	
	
	/**
	 * 获取选中的值
	 * @return
	 */
	public String getValue(){
		String text="";
		for (int i = 0; i < listRb.size(); i++) {
			RadioButton radioButton = listRb.get(i);
			if(radioButton.isChecked()){
				radioButton.setChecked(false);
				text=radioButton.getText()+"";
			}
		}
		return text;
	}

}
