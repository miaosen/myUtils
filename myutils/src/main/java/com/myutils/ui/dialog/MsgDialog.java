package com.myutils.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.myutils.R;
import com.myutils.core.annotation.InjectReader;
import com.myutils.ui.dialog.bs.BaseFmDialog;
import com.myutils.core.annotation.ViewInject;
import com.myutils.utils.ViewUtils;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-5-8
 * @Descrition 信息提示弹窗
 */
public class MsgDialog extends BaseFmDialog {
	
	private Context context;
	/**
	 * 布局的中间部分
	 */
	private View contentView=null;
	/**
	 * 中间部分对其方式
	 * @param gravity
	 */
	private int containerGravity = Gravity.CENTER;
	
	@ViewInject
	private TextView tv_title;
	@ViewInject
	private LinearLayout ln_content;
	@ViewInject
	private ScrollView sv;
	@ViewInject
	private Button btn_left;
	@ViewInject
	private Button btn_right;
	@ViewInject
	private TextView tv_msg;
	
	private String title;
	private String msg;
	//弹窗宽度
	private int dialogWidth=-1;
	//弹窗高度
	private int dialogHeight=LayoutParams.WRAP_CONTENT;
	//弹窗内容区高度
	private int contentHeight=-1;



	@Override
	public void init(View dialogView){
		InjectReader.injectAllFields(this, dialogView);
		if(msg !=null){
			tv_msg.setText(msg);
		}
		btn_left.setOnClickListener(new mClick());
		btn_right.setOnClickListener(new mClick());
		setContainerGravity(containerGravity);
		tv_title.setText(title);
		if(contentView!=null){
			ln_content.removeAllViews();
			ln_content.addView(contentView);
		}else{
			tv_msg.setText(msg);
			ln_content.setGravity(containerGravity);
		}
	}

	@Override
	public int setLayout() {
		return R.layout.ui_dlg_msg;
	}


	class mClick implements android.view.View.OnClickListener{
		@Override
		public void onClick(View v) {
			if(v==btn_left){
				if(onSureListener!=null){
					onSureListener.sure(MsgDialog.this);
				}else{
					dismiss();
				}
			}else if(v==btn_right){
				if(onCancleListener!=null){
					onCancleListener.cancle(MsgDialog.this);
				}else{
					dismiss();
				}
			}
		}
		
	}

	public MsgDialog setTitle(String title) {
		this.title = title;
		return this;
	}



	public MsgDialog setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public MsgDialog setContainerGravity(int gravity) {
		this.containerGravity = gravity;
		return this;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getMsg() {
		return msg;
	}
	public View getDialogView() {
		return dialogView;
	}

	public void setDialogView(View dialogView) {
		this.dialogView = dialogView;
	}

	public View getContentView() {
		return contentView;
	}

	public void setContentView(View contentView) {
		this.contentView = contentView;
	}

	public TextView getTv_title() {
		return tv_title;
	}


	public LinearLayout getLn_content() {
		return ln_content;
	}

	public void setLn_content(LinearLayout ln_content) {
		this.ln_content = ln_content;
	}

	public ScrollView getSv() {
		return sv;
	}

	public void setSv(ScrollView sv) {
		this.sv = sv;
	}

	public Button getBtn_left() {
		return btn_left;
	}

	public void setBtn_left(Button btn_left) {
		this.btn_left = btn_left;
	}

	public Button getBtn_right() {
		return btn_right;
	}

	public void setBtn_right(Button btn_right) {
		this.btn_right = btn_right;
	}

	public TextView getTv_msg() {
		return tv_msg;
	}

	public void setTv_msg(TextView tv_msg) {
		this.tv_msg = tv_msg;
	}

	public String getTitle() {
		return title;
	}




	public int getContainerGravity() {
		return containerGravity;
	}



	public int getDialogWidth() {
		return dialogWidth;
	}

	public void setDialogWidth(int dialogWidth) {
		this.dialogWidth = dialogWidth;
	}

	public int getDialogHeight() {
		return dialogHeight;
	}

	public void setDialogHeight(int dialogHeight) {
		this.dialogHeight = dialogHeight;
	}

	public int getContentHeight() {
		return contentHeight;
	}

	public void setContentHeight(int contentHeight) {
		this.contentHeight = contentHeight;
	}

	public OnSureListener getOnSureListener() {
		return onSureListener;
	}

	public void setOnSureListener(OnSureListener onSureListener) {
		this.onSureListener = onSureListener;
	}
}
