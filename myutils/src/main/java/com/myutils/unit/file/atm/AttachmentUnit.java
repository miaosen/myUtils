package com.myutils.unit.file.atm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.myutils.core.RowObject;
import com.myutils.core.logger.L;
import com.myutils.unit.file.atm.vdrcd.VideoRcdUnit;
import com.myutils.unit.file.atm.vorcd.VocieRcdDlg;
import com.myutils.unit.file.atm.vorcd.VoiceRcdView;
import com.myutils.unit.file.atm.vorcd.VoiceRcdView.OnRecordComplateListener;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-3-22
 * @Descrition 附件(Attachment) 上传模块 ，集合录像，拍照
 */
public class AttachmentUnit {

	private Context context;

	private Fragment fragment;
	
	private TakePictureUnit takePictureUnit;
	private VideoRcdUnit videoRecordUnit;
	
	private VocieRcdDlg vocieRcdDlg;
	
	private AtmView atmView;
	
	private OnDataChangeListener onDataChangeListener;

	private View decorView;

	/**
	 * 附件在activity中用这个构造器
	 * @param context
	 */
	public AttachmentUnit(Context context, View decorView) {
		this.context = context;
		this.decorView=decorView;
		atmView=new AtmView(this,decorView);

	}


	/**
	 * 附件在fragment中用这个构造器
	 * @param fragment
     */
	public AttachmentUnit(Fragment fragment, View decorView) {
		this.fragment=fragment;
		this.context = fragment.getContext();
		this.decorView=decorView;
		//初始化View成功后调用initUnit()方法
		atmView=new AtmView(this,decorView);
	}
	

	
	public void initUnit() {
		takePictureUnit = new TakePictureUnit();
		// takePictureUnit.setFileName("aaa");
		// takePictureUnit.setFilePath("/"+TakePictureUnit.getAppDir()+"/aaa");
		videoRecordUnit = new VideoRcdUnit();
		// videoRecordUnit.setVideoPath(Environment
		// .getExternalStorageDirectory().getAbsolutePath()
		// + "/"
		// + VideoRecordUnit.AppDir);
		// videoRecordUnit.setVideoName("aaaa.mp4");
		vocieRcdDlg=new VocieRcdDlg(context);
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
		if(fragment!=null){
			takePictureUnit.takePicture(fragment);
		}else{
			takePictureUnit.takePicture((Activity) context);
		}

	}
	
	/**
	 * 开始录像
	 */
	public void videoRecord(){
		if(fragment!=null){
			videoRecordUnit.startRecord(fragment);
		}else{
			videoRecordUnit.startRecord((Activity) context);
		}
	}
	
	/**
	 * 开始录音
	 */
	public void voiceRecord(){
		vocieRcdDlg.show();
		VoiceRcdView voiceRcdView = vocieRcdDlg.getVoiceRcdView();
		voiceRcdView.setOnRecordComplateListener(new OnRecordComplateListener() {
			@Override
			public void onComplate(RowObject result) {
				if(onDataChangeListener!=null){
					onDataChangeListener.onChange(result);
					vocieRcdDlg.dismiss();
				}
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
