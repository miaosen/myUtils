package com.myutils.unit.file.atm.vorcd;

import java.io.IOException;

import android.media.MediaRecorder;

import com.myutils.ui.UIHelper;
import com.myutils.unit.file.FileModel;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-6-6
 * @Descrition 使用MediaRecorder的录音器,必须传入录音文件的输出路径
 * 
 */
public class VoiceRecord {

	private MediaRecorder mMediaRecorder;

	// 是否正在录音
	private boolean isRecording = false;

	// 是否正在录音
	private boolean isInitializ = false;


	private FileModel fileModel;

	public VoiceRecord(FileModel fileModel) {
		this.fileModel = fileModel;
	}

	/**
	 * 初始化录音组件
	 */
	public void initRecord() {
		try {
			/* ①Initial：实例化MediaRecorder对象 */
			mMediaRecorder = new MediaRecorder();
			mMediaRecorder.setOnErrorListener(null);// 防止start()与stop()间隔小于1秒(有时候大于1秒也崩)时崩溃
			/* ②setAudioSource/setVedioSource */
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
			/*
			 * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
			 * THREE_GPP(3gp格式，H263视频
			 * /ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
			 */
			mMediaRecorder
					.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			/* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			/* ②设置输出文件的路径 */
			mMediaRecorder.setOutputFile(fileModel.getPath());
			/* ③准备 */
			mMediaRecorder.prepare();
			isInitializ=true;
		} catch (IOException e) {
			e.printStackTrace();
			isInitializ=false;
			UIHelper.toast("初始化出错 !");
			//Logger.e("初始化出错 !");
		}
	}

	/**
	 * 开始录音
	 */
	public void start() {
		fileModel.updateName();
		fileModel.createFile();
		if (isRecording) {
			UIHelper.toast("正在录音");
		} else {
			initRecord();
			if(isInitializ){
				mMediaRecorder.start();
				isRecording = true;
			}
			
		}

	}

	/**
	 * 停止录音
	 */
	public void stop() {
		if (mMediaRecorder != null&&isRecording) {
			/* ⑤停止录音 */
			mMediaRecorder.stop();
			/* ⑥释放MediaRecorder */
			mMediaRecorder.release();
			isRecording = false;
		} else {
			UIHelper.toast("请先开始录音");
		}
	}

	public MediaRecorder getmMediaRecorder() {
		return mMediaRecorder;
	}

	public void setmMediaRecorder(MediaRecorder mMediaRecorder) {
		this.mMediaRecorder = mMediaRecorder;
	}

	public String getVociePath() {
		return fileModel.getPath();
	}

	public boolean isRecording() {
		return isRecording;
	}

	public void setRecording(boolean isRecording) {
		this.isRecording = isRecording;
	}

}
