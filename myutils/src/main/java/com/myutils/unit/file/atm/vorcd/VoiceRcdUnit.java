package com.myutils.unit.file.atm.vorcd;

import android.content.Context;

import com.myutils.unit.OnActivityResultState;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-3-18
 * @Descrition 录音模块，录完像后返回录像文件地址和缩略图地址
 */
public class VoiceRcdUnit {

	private Context context;

	/**
	 * 录像请求码
	 */
	private static final int REQUEST_CODE = OnActivityResultState.VOICE_RECORD;

	/**
	 * 录音器
	 */
	private VoiceRecord voiceRecord;
	/**
	 * 界面
	 */
	public VoiceRcdView voiceRcdView;
	/**
	 * 弹窗
	 */
	public VocieRcdDlg dlg;

	public VoiceRcdUnit(Context context) {
		this.context = context;
	}

	/**
	 * 录音弹窗
	 * 
	 * @return
	 */
	public void startRecord() {
		voiceRcdView = new VoiceRcdView(context);
		voiceRecord = voiceRcdView.getVoiceRecord();
		if (dlg == null) {
			dlg = new VocieRcdDlg(context, voiceRcdView);
		}
		dlg.show();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public VoiceRecord getVoiceRecord() {
		return voiceRecord;
	}

	public void setVoiceRecord(VoiceRecord voiceRecord) {
		this.voiceRecord = voiceRecord;
	}

	public VoiceRcdView getVoiceRcdView() {
		return voiceRcdView;
	}

	public void setVoiceRcdView(VoiceRcdView voiceRcdView) {
		this.voiceRcdView = voiceRcdView;
	}

	public VocieRcdDlg getDlg() {
		return dlg;
	}

	public void setDlg(VocieRcdDlg dlg) {
		this.dlg = dlg;
	}

	public static int getRequestCode() {
		return REQUEST_CODE;
	}

}
