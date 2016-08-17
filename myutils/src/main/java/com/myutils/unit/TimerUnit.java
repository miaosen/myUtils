package com.myutils.unit;

import android.os.Handler;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016年3月28日
 * @Descrition 计时器
 */
public class TimerUnit {
	
	private int hour=0;
	
	private int minute=0;
	
	private int second=0;
	
	private OnTimeListener onTimeListener;
	
	
	public TimerUnit(){
		
	}
	
	public void start() {
		
	}
	
	public interface OnTimeListener{
		void onTime(int hour,int minute,int second);
	}
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				handler.sendEmptyMessageDelayed(1, 1000);
				onTimeListener.onTime(hour, minute, second);
				break;
			default:
				break;
			}
		}
	};
	

}
