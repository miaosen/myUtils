package com.myutils.unit.zxing;

import java.io.IOException;
import java.util.Vector;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.myutils.R;
import com.myutils.base.BaseActivity;
import com.myutils.unit.zxing.camera.CameraManager;
import com.myutils.unit.zxing.decoding.CaptureActivityHandler;
import com.myutils.unit.zxing.decoding.InactivityTimer;
import com.myutils.unit.zxing.view.ViewfinderView;

/**
 * @Created by eaglesoft.org
 * @author yangjincheng
 * @Date 2013-11-13
 */
public abstract class CaptureActivity extends BaseActivity
		implements Callback {
	protected CaptureActivityHandler handler;
	protected ViewfinderView viewfinderView;
	protected boolean hasSurface;
	protected Vector<BarcodeFormat> decodeFormats;
	protected String characterSet;
	protected InactivityTimer inactivityTimer;
	protected MediaPlayer mediaPlayer;
	protected boolean playBeep;
	protected static final float BEEP_VOLUME = 0.10f;
	protected boolean vibrate;
	private static final long VIBRATE_DURATION = 200L;

	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * //闪关灯 Log.i("bundle---","capture--kk-"); mCamera = Camera.open();
		 * mParam = mCamera.getParameters();
		 * 
		 * mParam.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
		 * mCamera.setParameters(mParam);
		 */

		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = "ISO-8859-1";

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;

	}
	
	@Override
	public void initConfig() {
		setContentView(R.layout.zxing_capture_act);
		// config.setHeaderText("扫描企业二维码");
		Intent intent = getIntent();
		// viewId = intent.getIntExtra("viewId", 0);
	}

	
	@Override
	public void initView() {
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		/*
		 * //闪关灯 mParam.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
		 * mCamera.setParameters(mParam);
		 * 
		 * //必须执行以下操作，否则打开Camera应用出错。 if (mCamera != null) { mCamera.release();
		 * mCamera = null; }
		 * 
		 * if (mParam != null) { mParam = null; }
		 */
		// Log.i("bundle---", "capture--jj-");
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	protected void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.zxing_beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	protected void playBeepSoundAndVibrate() {
		// 播放声音
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		// 振动
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
		}
		return false;

	};

	public abstract void handleDecode(Result result, Bitmap barcode);
}
