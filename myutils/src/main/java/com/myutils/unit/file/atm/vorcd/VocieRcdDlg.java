package com.myutils.unit.file.atm.vorcd;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.myutils.R;


/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/6 15:19
 * @Descrition 录音弹窗
 */
public class VocieRcdDlg extends Dialog{

	private Context context;
	/**
	 * 界面
	 */
	public VoiceRcdView voiceRcdView;
	/**
	 * 弹窗
	 */

	
	public VocieRcdDlg(Context context) {
		super(context,R.style.custom_dialog);
		voiceRcdView = new VoiceRcdView(context);
		this.context=context;
		voiceRcdView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_white));
		setContentView(voiceRcdView);
	}


	///**
	// * 录音弹窗
	// *
	// * @return
	// */
	//public void startRecord() {
	//	show();
	//}



	public void setContext(Context context) {
		this.context = context;
	}

	public VoiceRcdView getVoiceRcdView() {
		return voiceRcdView;
	}

	public void setVoiceRcdView(VoiceRcdView voiceRcdView) {
		this.voiceRcdView = voiceRcdView;
	}
}
