package com.myutils.unit.file.atm.vdrcd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.myutils.R;
import com.myutils.core.logger.L;
import com.myutils.unit.OnActivityResultState;
import com.myutils.unit.file.atm.vdrcd.VideoRcdView.OnRecordFinishListener;
import com.myutils.utils.FileUtils;

public class VideoRcdAct extends Activity {
	private VideoRcdView mRecorderView;
	private Button mShootBtn;
	private Button starButton;
	private boolean isFinish = true;
	/** 视频请求码 **/
	public static final int REQUEST_CODE = OnActivityResultState.VIDEO_RECORD;
	
	private String videoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recorder_video_act);
		getIntentData();
		initRecorderView();
		initView();
		

	}
	private void getIntentData() {
		videoPath = getIntent().getStringExtra("videoPath");
		//videoName= getIntent().getStringExtra("videoName");
		L.i("videoPath==="+videoPath);
	}
	

	private void initRecorderView() {
		mRecorderView = (VideoRcdView) findViewById(R.id.movieRecorderView);
		mRecorderView.setFile(FileUtils.getFile(videoPath));
	}

	

	
	private void initView() {
		starButton = (Button) findViewById(R.id.start_button);
		starButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String strBtn = starButton.getText().toString();
				if ("开始拍摄".equals(strBtn)) {
					mRecorderView.record(new OnRecordFinishListener() {
						@Override
						public void onRecordFinish() {
							handler.sendEmptyMessage(1);
						}
					});
					starButton.setText("停止拍摄");
				} else {
					handler.sendEmptyMessage(1);
				}
			}
		});

		final Button cancleButton = (Button) findViewById(R.id.cancle_button);
		cancleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mRecorderView.stop();
				finish();
				
			}
		});
	}
	@Override
	public void onResume() {
		super.onResume();
		isFinish = true;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		isFinish = false;
		mRecorderView.stop();
	}


	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			finishActivity();
		}
	};

	private void finishActivity() {
		FileChannel fc = null;
		if (isFinish) {
			File file = mRecorderView.getVideoFile();
			if (file.exists() && file.isFile()) {
				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
					fc = fis.getChannel();
					if (fc.size() > 50000) {
						mRecorderView.stop();
						L.i("--MovieRecorderActivity-->"
								+ mRecorderView.getVideoFile().toString()+ "    "+mRecorderView
								.getVideoFile().getAbsolutePath());
						Intent intent = new Intent();
//						intent.putExtra("videoPath", mRecorderView
//								.getVideoFile().toString());
						setResult(Activity.RESULT_OK, intent);
						setRequestedOrientation(REQUEST_CODE);
						finish();
					} else {
						Toast.makeText(VideoRcdAct.this, "视频录制时间太短",
								Toast.LENGTH_SHORT).show();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// VideoPlayerActivity.startActivity(this,
			// mRecorderView.getmVecordFile().toString());
		}
	}

	/**
	 * 录制完成回调
	 * 
	 * @author liuyinjun
	 * 
	 * @date 2015-2-9
	 */
	public interface OnShootCompletionListener {
		public void OnShootSuccess(String path, int second);

		public void OnShootFailure();
	}
}
