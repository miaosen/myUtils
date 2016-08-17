package com.myutils.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.myutils.R;
import com.myutils.ui.view.annotation.ViewInject;
import com.myutils.utils.ViewUtils;
import com.myutils.utils.WindowUtils;
/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-5-8
 * @Descrition 信息提示弹窗
 */
public class MsgDialog extends Dialog {
	
	private Context context;
	/**
	 * 布局文件
	 */
	private int layout=R.layout.ui_dlg_msg;
	/**
	 * 布局
	 */
	private View dialogView=null;
	/**
	 * 布局的中间部分
	 */
	private View contentView=null;
	/**
	 * 中间部分对其方式
	 * @param gravity
	 */
	private int contentGravity=-100;
	
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
	
	private String strTitle;
	private String strMsg;
	//弹窗宽度
	private int dialogWidth=-1;
	//弹窗高度
	private int dialogHeight=LayoutParams.WRAP_CONTENT;
	//弹窗内容区高度
	private int contentHeight=-1;
	
	private OnSureListener onSureListener=null;
	
	private OnCancleListener onCancleListener=null;
	
	
	public MsgDialog(Context context,String title,String msg) {
		super(context,R.style.dialogNoHeader);
		this.context=context;
		strTitle=title;
		strMsg=msg;
	}

	public MsgDialog(Context context,String title,String msg,OnSureListener onSureListener,OnCancleListener onCancelListener) {
		super(context,R.style.dialogNoHeader);
		this.context=context;
		this.onSureListener=onSureListener;
		this.onCancleListener=onCancelListener;
		strTitle=title;
		strMsg=msg;
	}
	
	public MsgDialog(Context context,String title,View contentView) {
		super(context,R.style.dialogNoHeader);
		this.context=context;
		this.contentView=contentView;
		strTitle=title;
	}
	
	public MsgDialog(Context context,String title,View contentView,OnSureListener onSureListener,OnCancleListener onCancelListener) {
		super(context,R.style.dialogNoHeader);
		this.context=context;
		this.contentView=contentView;
		this.onSureListener=onSureListener;
		this.onCancleListener=onCancelListener;
		strTitle=title;
	}
	
	/**
	 * 布局创建
	 * @return
	 */
	public View onCreatView(){
		 dialogView = LayoutInflater.from(context).inflate(layout, null);
		ViewUtils.injectAllFields(this, dialogView);
		tv_title.setText(strTitle);
		if(strMsg!=null){
			tv_msg.setText(strMsg);
		}
		btn_left.setOnClickListener(new mClick());
		btn_right.setOnClickListener(new mClick());
		if(contentView!=null){
			ln_content.removeAllViews();
			ln_content.addView(contentView);
		}
		if(contentGravity!=-100){
			ln_content.setGravity(contentGravity);
		}
		
		if(dialogWidth==-1){
			int screenWidth = WindowUtils.getScreenWidth();
			dialogWidth=screenWidth* 4 / 6;
		}
		if(contentHeight!=-1){
			sv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, contentHeight));
		}
		LayoutParams layoutParams = new LayoutParams(dialogWidth,dialogHeight );
		addContentView(dialogView, layoutParams);
		return dialogView;
		
	}
	
	@Override
	public void show() {
		if(dialogView==null){
			onCreatView();
		}
		super.show();
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
	
	public interface OnSureListener{
		void sure(MsgDialog dialog);
	}
	
	public interface OnCancleListener{
		void cancle(MsgDialog dialog);
	}

	public int getContentGravity() {
		return contentGravity;
	}

	public void setContentGravity(int gravity) {
		this.contentGravity = gravity;
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

	public OnCancleListener getOnCancleListener() {
		return onCancleListener;
	}

	public void setOnCancleListener(OnCancleListener onCancleListener) {
		this.onCancleListener = onCancleListener;
	}
	
	
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.eg_custom_dialog);
//	}

}
