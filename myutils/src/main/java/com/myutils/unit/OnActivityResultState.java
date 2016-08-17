package com.myutils.unit;
/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-1-27
 * @Descrition Activity的回调方法onActivityResult中requestCode 规范
 */
public interface OnActivityResultState {
	
	/**
	 * 照相
	 */
	public static final int TAKE_PICTURE = 1;
	
	/**
	 * 录像
	 */
	public static final int VIDEO_RECORD = 2;

	/**
	 * 录音
	 */
	public static final int VOICE_RECORD = 3;

}
