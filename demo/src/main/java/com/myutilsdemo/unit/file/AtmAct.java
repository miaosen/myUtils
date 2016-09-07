package com.myutilsdemo.unit.file;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myutils.core.RowObject;
import com.myutils.core.gson.JSONSerializer;
import com.myutils.core.logger.L;
import com.myutils.core.annotation.ViewInject;
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

	

	@Override
	public int initConfig(Bundle savedInstanceState) {
		context=this;
		return R.layout.core_atm_act;
	}

	@Override
	public void initView(View decorView) {
		atmUnit = new AttachmentUnit(context,decorView);
		button1.setOnClickListener(this);
	}

	@Override
	public void initData() {

	}

	@Override
	public void click(View v) {
		if(v==button1){
			submit();
		}
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		atmUnit.onActivityResult(requestCode, resultCode, data);
	}
	
	private void submit() {
		List<RowObject> list = atmUnit.getAtmView().getRows();
		L.i("list===" + list.size() + "   " + JSONSerializer.toJson(list));
	}
	
}
