package com.myutils.ui.view.signature;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.myutils.R;
import com.myutils.core.annotation.ViewInject;
import com.myutils.utils.ViewUtils;
/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016年4月18日
 * @Descrition 签名页面
 */
public class SignatureView extends LinearLayout{
	
	private Context context;
	@ViewInject
	private LinearLayout ln_setting;
	@ViewInject
	private LinearLayout ln_clear;
	@ViewInject
	private LinearLayout ln_save;
	@ViewInject
	private LinearLayout ln_cancl;
	@ViewInject
	private HuaBanView huaBan;

	public SignatureView(Context context) {
		super(context);
		this.context=context;
		initView();
		
	}
	
	public SignatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		initView();
	}
	

	private void initView() {
		View view = LayoutInflater.from(context).inflate(R.layout.ui_view_signature, null);
		//view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		this.addView(view);
		//注解查找View
		ViewUtils.injectAllFields(this);
		ln_setting.setOnClickListener(new mClick());
		ln_clear.setOnClickListener(new mClick());
		ln_save.setOnClickListener(new mClick());
		ln_cancl.setOnClickListener(new mClick());
	}
	
	class mClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			if(v==ln_setting){
				
			}else if(v==ln_clear){
				huaBan.clearScreen();
			}else if(v==ln_save){
				//TODO
//				String filePath=AppFactory.getAppConfig().getAPP_DIR_PATH()+"/aa.png";
//				if(ImageUtils.saveView(huaBan, filePath)){
//					T.show("保存成功！");
//				}else{
//					T.show("保存失败！");
//				}
				
			}else if(v==ln_cancl){
				huaBan.setMoveToLast();
			}
		}
	}
	

}
