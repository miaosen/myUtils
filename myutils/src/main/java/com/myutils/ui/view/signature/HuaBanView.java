package com.myutils.ui.view.signature;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.myutils.R;
import com.myutils.base.L;

/**
 * @Created by gzpykj.com
 * @Date 2016年4月18日
 * @Descrition 绘画板
 */
public class HuaBanView extends View {

	/**
	 * 图片bitmap
	 */
	//private Bitmap cacheBitmap;
	/**
	 * 画布
	 */
	private Canvas canvas;
	//private Canvas cacheCanvas;
	/**
	 * 画笔
	 */
	private Paint paint;
	private Paint BitmapPaint;
	/**
	 * 绘制的线条
	 */
	private Path path;
	
	/**
	 * 绘制的线条
	 */
	private List<Path> paths=new ArrayList<Path>();
	/**
	 * 画布高度
	 */
	private int height;
	/**
	 * 画布宽度
	 */
	private int width;
	/**
	 * 触摸屏幕时的x轴坐标
	 */
	private float pX;
	/**
	 * 触摸屏幕时的y轴坐标
	 */
	private float pY;

	/**
	 * 默认画笔颜色
	 */
	private int paintColor = Color.GREEN;
	/**
	 * 默认画笔样式
	 */
	private static Paint.Style paintStyle = Paint.Style.STROKE;
	/**
	 * 默认画笔大小
	 */
	private static int paintWidth = 8;

	Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.icon_bendi);

	public HuaBanView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HuaBanView(Context context) {
		super(context);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		height = h;
		width = w;
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		this.canvas = canvas;
//		BitmapPaint = new Paint();
//		canvas.drawBitmap(bmp, 0, 0, BitmapPaint);
		canvas.drawPath(path, paint);

	}

	private void init() {
//		cacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
//		cacheCanvas = new Canvas(cacheBitmap);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		path = new Path();
		BitmapPaint = new Paint();
		updatePaint();
	}

	/**
	 * 更新画笔
	 */
	private void updatePaint() {
		paint.setColor(paintColor);
		paint.setStyle(paintStyle);
		paint.setStrokeWidth(paintWidth);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			path.moveTo(event.getX(), event.getY());
			pX = event.getX();
			pY = event.getY();
		} else if (action == MotionEvent.ACTION_MOVE) {
			path.quadTo(pX, pY, event.getX(), event.getY());
			pX = event.getX();
			pY = event.getY();
		} else if (action == MotionEvent.ACTION_UP) {
			//cacheCanvas.drawPath(path, paint);
			//path.reset();
			paths.add(path);
		}
		invalidate();
		super.onTouchEvent(event);
		return true;
	}
	

	/**
	 * 设置画笔颜色
	 * 
	 * @param color
	 */
	public void setColor(int color) {
		paintColor = color;
		updatePaint();
	}

	/**
	 * 设置画笔粗细
	 * 
	 * @param width
	 */
	public void setPaintWidth(int width) {
		paintWidth = width;
		updatePaint();
	}
	
	/**
	 *返回上一步
	 * 
	 * @param width
	 */
	public void setMoveToLast() {
		path.reset();
		path=paths.get(paths.size()-1);
		L.i("path====="+paths.size());
		invalidate();
	}


	public static final int PEN = 1;
	public static final int PAIL = 2;
	/**
	 * 画笔样式
	 * 
	 * @param style
	 */
	public void setStyle(int style) {
		switch (style) {
		case PEN:// 钢笔
			paintStyle = Paint.Style.STROKE;
			break;
		case PAIL:// 水桶
			paintStyle = Paint.Style.FILL;
			break;
		}
		updatePaint();
	}

	/**
	 * 清空签名
	 */
	public void clearScreen() {
//		if (canvas != null) {
//			Paint backPaint = new Paint();
//			backPaint.setColor(Color.WHITE);
//			canvas.drawRect(new Rect(0, 0, width, height), backPaint);
//			//cacheCanvas.drawRect(new Rect(0, 0, width, height), backPaint);
//		}
		path.reset();
		invalidate();
	}

}
