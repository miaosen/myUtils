package com.myutils.unit.file.atm;

import android.content.Context;
import android.content.Intent;

import com.myutils.core.logger.L;
import com.myutils.core.RowObject;
import com.myutils.unit.file.atm.vdrcd.VideoRcdUnit;
import com.myutils.unit.file.atm.vorcd.VocieRcdDlg;
import com.myutils.unit.file.atm.vorcd.VoiceRcdUnit;
import com.myutils.unit.file.atm.vorcd.VoiceRcdView;
import com.myutils.unit.file.atm.vorcd.VoiceRcdView.OnRecordComplateListener;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-3-22
 * @Descrition 附件(Attachment) 上传模块 ，集合录像，拍照
 */
public class AtmUnit {

	private Context context;

	
	private TakePictureUnit takePictureUnit;
	private VideoRcdUnit videoRecordUnit;
	
	private VoiceRcdUnit voiceRecordUnit;
	
	private AtmView atmView;
	
	private OnDataChangeListener onDataChangeListener;
	
	

	public AtmUnit(Context context) {
		this.context = context;
		//初始化View成功后调用initUnit()方法
		atmView=new AtmView(this,context);
	}
	
	

	
	public void initUnit() {
		takePictureUnit = new TakePictureUnit(context);
		// takePictureUnit.setFileName("aaa");
		// takePictureUnit.setFilePath("/"+TakePictureUnit.getAppDir()+"/aaa");
		videoRecordUnit = new VideoRcdUnit(context);
		// videoRecordUnit.setVideoPath(Environment
		// .getExternalStorageDirectory().getAbsolutePath()
		// + "/"
		// + VideoRecordUnit.AppDir);
		// videoRecordUnit.setVideoName("aaaa.mp4");
		voiceRecordUnit=new VoiceRcdUnit(context);
	}
	
	
	
	

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 照相
		RowObject takePicture = takePictureUnit
				.onActivityResult(requestCode, resultCode, data);
		if (takePicture != null) {
			if(onDataChangeListener!=null){
				onDataChangeListener.onChange(takePicture);
			}
			L.i("TakePicture================");
		}
		// 录像
		RowObject videoRecord = videoRecordUnit
				.onActivityResult(requestCode, resultCode, data);
		if (videoRecord != null) {
			if(onDataChangeListener!=null){
				onDataChangeListener.onChange(videoRecord);
			}
			L.i("MovieRecord================");
		}
		
	}
	
	
//	/**
//	 * 添加数据和刷新适配器
//	 * @param row
//	 */
//	public void update(RowObject row){
//		rows.add(row);
//		attachmentAdapter.notifyDataSetChanged();
//		setListViewHeightBasedOnChildren(upload_gridview);
//	}
	
	/**
	 * 开始照相
	 */
	public void takePic(){
		takePictureUnit.takePicture();
	}
	
	/**
	 * 开始录像
	 */
	public void videoRecord(){
		videoRecordUnit.startRecord();
	}
	
	/**
	 * 开始录音
	 */
	public void voiceRecord(){
		voiceRecordUnit.startRecord();
		final VocieRcdDlg dlg = voiceRecordUnit.getDlg();
		VoiceRcdView voiceRcdView = voiceRecordUnit.getVoiceRcdView();
		voiceRcdView.setOnRecordComplateListener(new OnRecordComplateListener() {
			@Override
			public void onComplate(RowObject result) {
				//TODO 
				if(onDataChangeListener!=null){
					onDataChangeListener.onChange(result);
					dlg.dismiss();
				}
				L.i("voiceRecord================");
			}
		});
	}
	
	/**
	 * 数据变化时调用
	 * @author OAIM
	 */
	public interface OnDataChangeListener{
		void onChange(RowObject result);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public TakePictureUnit getTakePictureUnit() {
		return takePictureUnit;
	}

	public void setTakePictureUnit(TakePictureUnit takePictureUnit) {
		this.takePictureUnit = takePictureUnit;
	}

	public VideoRcdUnit getVideoRecordUnit() {
		return videoRecordUnit;
	}

	public void setVideoRecordUnit(VideoRcdUnit videoRecordUnit) {
		this.videoRecordUnit = videoRecordUnit;
	}

	public VoiceRcdUnit getVoiceRecordUnit() {
		return voiceRecordUnit;
	}

	public void setVoiceRecordUnit(VoiceRcdUnit voiceRecordUnit) {
		this.voiceRecordUnit = voiceRecordUnit;
	}

	public AtmView getAtmView() {
		return atmView;
	}


	public void setAtmView(AtmView atmView) {
		this.atmView = atmView;
	}


	public OnDataChangeListener getOnDataChangeListener() {
		return onDataChangeListener;
	}


	public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
		this.onDataChangeListener = onDataChangeListener;
	}



	

}
