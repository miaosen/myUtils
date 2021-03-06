package com.myutilsdemo.unit.file;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myutils.core.RowObject;
import com.myutils.core.json.JSONSerializer;
import com.myutils.base.L;
import com.myutils.core.annotation.ViewInject;
import com.myutils.unit.file.atm.AtmView;
import com.myutils.unit.file.atm.AttachmentUnit;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseAct;

import java.util.List;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-6-20
 * @Descrition 附件生成
 */
public class AtmAct extends BaseAct {
	
	private Context context;
	
	private AttachmentUnit atmUnit;
	
	@ViewInject
	private Button button1;
	@ViewInject
	AtmView atmView;


	@Override
	public int initConfig(Bundle savedInstanceState) {
		context=this;
		return R.layout.unit_atm_act;
	}

	@Override
	public void initView(View decorView) {
		button1.setOnClickListener(this);
		atmUnit=atmView.getAtmUnit();
		//如果View的布局是在Fragment,必须把Fragment对象传过去
		//atmUnit.setFragment(this);
	}

	@Override
	public void initData() {

	}

	@Override
	public void click(View v) {
		if(v==button1){
			submit();
			List<RowObject> filePaths = atmView.getFilePaths();
			//List<RowObject> rows=new LinkedList<>();
			//rows.addAll(filePaths);
			filePaths.addAll(filePaths);
			atmView.notifyDataSetChanged();
		}
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		atmUnit.onActivityResult(requestCode, resultCode, data);
	}
	
	private void submit() {
		List<RowObject> list = atmView.getFilePaths();
		L.json("list===" + list.size() + "   " , JSONSerializer.toJson(list));
	}
	
}
