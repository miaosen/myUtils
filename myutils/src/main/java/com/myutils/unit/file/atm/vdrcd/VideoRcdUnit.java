package com.myutils.unit.file.atm.vdrcd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;

import com.myutils.base.AppFactory;
import com.myutils.core.logger.L;
import com.myutils.core.RowObject;
import com.myutils.unit.OnActivityResultState;
import com.myutils.utils.ImageUtils;
import com.myutils.utils.VideoUtils;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-3-18
 * @Descrition 录像模块，录完像后返回录像文件地址和缩略图地址
 */
public class VideoRcdUnit {

	private Context context;

	private Activity activity;

	/**
	 * 录像请求码
	 */
	private static final int REQUEST_CODE = OnActivityResultState.VIDEO_RECORD;

	// app文件目录
	public static String AppDir = AppFactory.getAppDir();

	// 自定义视频名称
	private String videoName = null;
	// 自定义视频地址
		private String videoPath = null;
	// 默认视频名称
	public String DF_VIDEO_NAME ="df.mp4";
	// 视频默认生成地址
	public static final String DF_FILE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/"
			+ AppDir
			+ "/" + "video";

	// 视频缩略图地址
	private String videoThumbnailPath = DF_FILE_PATH+"/thumb";
	// 视频缩略图地址
	private String videoThumbnailName = "";

	public VideoRcdUnit(Context context) {
		this.context = context;
		activity = (Activity) context;
	}

	/**
	 * 跳转到视频录制界面
	 * 
	 * @return
	 */
	public void startRecord() {
		initDfVideoName();
		initVideoThumbnailName();
		Intent intent = new Intent(context, VideoRcdAct.class);
		intent.putExtra("videoPath", getVideoPath());
		intent.putExtra("videoName", getVideoName());
		activity.startActivityForResult(intent, REQUEST_CODE);
		
	}

	
	/**
	 * 初始化默认视频名称
	 */
	private void initDfVideoName() {
		DF_VIDEO_NAME = String.valueOf(System.currentTimeMillis())
				+ ".mp4";
	}

	private void initVideoThumbnailName() {
		videoThumbnailName = "";
		videoThumbnailName = "/thumb_"
				+ String.valueOf(System.currentTimeMillis() + ".png");
		L.i("videoThumbnailPath==========" + videoThumbnailPath);
	}
	
	/**
	 * 获取名称
	 * @return
	 */
	private String getVideoName() {
		if(videoName==null){
			return DF_VIDEO_NAME;
		}else{
			return videoName;
		}
	}
	

	/**
	 * 获取路径
	 * @return
	 */
	private String getVideoPath() {
		if(videoPath==null){
			return DF_FILE_PATH;
		}else{
			return videoPath;
		}
	}
	
	/**
	 * 获取路径
	 * @return
	 */
	public String getVideoAbsolutelyPath() {
		return getVideoPath()+"/"+getVideoName();
		
	}

	/**
	 * 返回图片路径
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public RowObject onActivityResult(int requestCode,
			int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				//videoPath = data.getStringExtra("videoPath");
				L.i("videoPath============" + videoPath);
				// File file = new File(videoPath);
				// String videoPath = copyFile(file);
				Bitmap videoThumbnail = VideoUtils.getVideoThumbnail(getVideoAbsolutelyPath(),
						100, 100, MediaStore.Images.Thumbnails.MICRO_KIND);
				ImageUtils.saveBitmap(videoThumbnailPath, videoThumbnailName,
						videoThumbnail);
				RowObject result = new RowObject();
				result.put("type", "video");
				result.put("videoPath", videoPath);
				result.put("thumbnailPath" ,videoThumbnailPath + videoThumbnailName);
				return result;
			}
		}

		return null;
	}

	
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	
	

}
