package com.myutils.unit.file.atm.vdrcd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Timer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myutils.R;
import com.myutils.core.logger.L;
import com.myutils.utils.FileUtils;

/**
 * 视频播放控件
 * 
 * @author liuyinjun
 * 
 * @date 2015-2-5
 */
public class VideoRcdView extends LinearLayout implements OnErrorListener {
	private Context context;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private ProgressBar mProgressBar;

	private MediaRecorder mMediaRecorder;
	private Camera mCamera;
	private Timer mTimer;// 计时器
	private OnRecordFinishListener mOnRecordFinishListener;// 录制完成回调接口

	private int mWidth;// 视频分辨率宽度
	private int mHeight;// 视频分辨率高度
	private boolean isOpenCamera;// 是否一开始就打开摄像头
	private int mRecordMaxTime;// 一次拍摄最长时间
	private int mTimeCount;// 时间计数
	private File videoFile = null;// 文件
	// 视频名称
	private String videoName = null;
	// 视频地址
	private String videoPath = null;
	
	private FileChannel fc;// 输入流
	private Handler timeHandler;
	private Runnable mRunnable;
	private TextView tvTime;
	
	
	
	

	public VideoRcdView(Context context) {
		this(context, null);
	}

	public VideoRcdView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public VideoRcdView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.VideoRcdView, defStyle, 0);
//		mWidth = a.getInteger(R.styleable.VideoRcdView_width, 320);// 默认320
//		mHeight = a.getInteger(R.styleable.VideoRcdView_height, 240);// 默认240
		mWidth = 320;// 默认320
		mHeight = 240;// 默认240

		isOpenCamera = a.getBoolean(
				R.styleable.VideoRcdView_is_open_camera, true);// 默认打开
		mRecordMaxTime = a.getInteger(
				R.styleable.VideoRcdView_record_max_time, 10000);// 默认为10

		LayoutInflater.from(context)
				.inflate(R.layout.recorder_video_view, this);
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mProgressBar.setMax(mRecordMaxTime);// 设置进度条最大量
		tvTime = (TextView) findViewById(R.id.tv_time);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(new CustomCallBack());
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		a.recycle();
	}
	
	public void setFile(String videoPath,String videoName){
		videoFile=FileUtils.getFile(videoPath, videoName);
	}

	/**
	 * 
	 * @author liuyinjun
	 * 
	 * @date 2015-2-5
	 */
	private class CustomCallBack implements Callback {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			if (!isOpenCamera)
				return;
			try {
				initCamera();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (!isOpenCamera)
				return;
			freeCameraResource();
		}

	}

	/**
	 * 初始化摄像头
	 * 
	 * @author liuyinjun
	 * @date 2015-2-5
	 * @throws IOException
	 */
	private void initCamera() throws IOException {
		if (mCamera != null) {
			freeCameraResource();
		}
		try {
			mCamera = Camera.open();
		} catch (Exception e) {
			e.printStackTrace();
			freeCameraResource();
		}
		if (mCamera == null)
			return;

		setCameraParams();
		mCamera.setDisplayOrientation(90);
		mCamera.setPreviewDisplay(mSurfaceHolder);
		mCamera.startPreview();
		mCamera.unlock();
	}

	/**
	 * 设置摄像头为竖屏
	 * 
	 * @author liuyinjun
	 * @date 2015-2-5
	 */
	private void setCameraParams() {
		if (mCamera != null) {
			Parameters params = mCamera.getParameters();
			params.set("orientation", "portrait");
			mCamera.setParameters(params);
		}
	}

	/**
	 * 释放摄像头资源
	 * 
	 * @author liuyinjun
	 * @date 2015-2-5
	 */
	private void freeCameraResource() {
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.lock();
			mCamera.release();
			mCamera = null;
		}
	}

	

	


	/**
	 * 初始化
	 * 
	 * @author liuyinjun
	 * @date 2015-2-5
	 * @throws IOException
	 */
	@SuppressLint("NewApi")
	private void initRecord() throws IOException {
		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.reset();
		if (mCamera != null)
			mMediaRecorder.setCamera(mCamera);
		mMediaRecorder.setOnErrorListener(this);
		mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
		mMediaRecorder.setVideoSource(VideoSource.CAMERA);// 视频源
		mMediaRecorder.setAudioSource(AudioSource.MIC);// 音频源
		mMediaRecorder.setOutputFormat(OutputFormat.MPEG_4);// 视频输出格式
		mMediaRecorder.setAudioEncoder(AudioEncoder.AMR_NB);// 音频格式
		mMediaRecorder.setVideoSize(mWidth, mHeight);// 设置分辨率：
		// mMediaRecorder.setVideoFrameRate(16);// 这个我把它去掉了，感觉没什么用
		mMediaRecorder.setVideoEncodingBitRate(2 * 1024 * 512);// 设置帧频率，然后就清晰了
		mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制
		mMediaRecorder.setVideoEncoder(VideoEncoder.MPEG_4_SP);// 视频录制格式
		// mediaRecorder.setMaxDuration(Constant.MAXVEDIOTIME * 1000);
		mMediaRecorder.setOutputFile(videoFile.getAbsolutePath());
		mMediaRecorder.prepare();
		try {
			mMediaRecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开始录制视频
	 * 
	 * @author liuyinjun
	 * @date 2015-2-5
	 * @param fileName
	 *            视频储存位置
	 * @param onRecordFinishListener
	 *            达到指定时间之后回调接口
	 */
	public void record(final OnRecordFinishListener onRecordFinishListener) {
		this.mOnRecordFinishListener = onRecordFinishListener;
		if(videoFile!=null){
			try {
				if (videoFile.exists() && videoFile.isFile()) {
					FileInputStream fis = new FileInputStream(videoFile);
					fc = fis.getChannel();
				}else{
					L.i("record====mVecordFile不存在"+videoFile.exists()+videoFile.isFile());
				}
				if (!isOpenCamera){// 如果未打开摄像头，则打开
					initCamera();
				}
				initRecord();
				mTimeCount = 0;// 时间计数器重新赋值
				timeHandler = new Handler();
				mRunnable = new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							L.i("---fc--->" + fc.size());
							if (fc.size() > 5000000) {
								stop();
								if (mOnRecordFinishListener != null) {
									mOnRecordFinishListener.onRecordFinish();
								}
								Toast.makeText(context, "拍摄视频大小已达5M文件,已停止拍摄",
										Toast.LENGTH_SHORT).show();
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mTimeCount++;
						tvTime.setText("拍摄时间: " + mTimeCount + " s");
						timeHandler.postDelayed(this, 1000);
						/*
						 * 定时器 mTimeCount++; mProgressBar.setProgress(mTimeCount);//
						 * 设置进度条 if (mTimeCount == mRecordMaxTime) {// 达到指定时间，停止拍摄
						 * stop(); if (mOnRecordFinishListener != null)
						 * mOnRecordFinishListener.onRecordFinish(); }
						 */

					}
				};
				timeHandler.post(mRunnable);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			L.i("获取文件为空!");
		}
		
	}

	/**
	 * 停止拍摄
	 * 
	 * @author liuyinjun
	 * @date 2015-2-5
	 */
	public void stop() {
		stopRecord();
		releaseRecord();
		freeCameraResource();
	}

	/**
	 * 停止录制
	 * 
	 * @author liuyinjun
	 * @date 2015-2-5
	 */
	public void stopRecord() {
		mProgressBar.setProgress(0);
		if (mTimer != null)
			mTimer.cancel();
		if (mMediaRecorder != null) {
			// 设置后不会崩
			mMediaRecorder.setOnErrorListener(null);
			mMediaRecorder.setPreviewDisplay(null);
			try {
				mMediaRecorder.stop();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 释放资源
	 * 
	 * @author liuyinjun
	 * @date 2015-2-5
	 */
	private void releaseRecord() {
		if (mMediaRecorder != null) {
			mMediaRecorder.setOnErrorListener(null);
			try {
				mMediaRecorder.release();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (null != mRunnable) {
			timeHandler.removeCallbacks(mRunnable);
		}
		mMediaRecorder = null;
	}

	public int getTimeCount() {
		return mTimeCount;
	}


	/**
	 * 录制完成回调接口
	 * 
	 * @author liuyinjun
	 * 
	 * @date 2015-2-5
	 */
	public interface OnRecordFinishListener {
		public void onRecordFinish();
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		try {
			if (mr != null)
				mr.reset();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public interface VideoRcdViewParam{
		public File setFile();
	}

	public File getVideoFile() {
		return videoFile;
	}

	public String getVideoName() {
		return videoName;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	
	
	
}