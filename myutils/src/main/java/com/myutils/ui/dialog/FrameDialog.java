package com.myutils.ui.dialog;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.myutils.R;
import com.myutils.core.logger.L;
import com.myutils.utils.DPUtils;

@SuppressLint("NewApi")
public class FrameDialog extends LinearLayout {

	private LinearLayout alertView;
	private LinearLayout lnAlertViewTop;
	private LinearLayout lnAlertViewButtom;
	private LinearLayout lnAlertViewLeft;
	private LinearLayout lnAlertViewRight;
	private LinearLayout lnAlertViewCenter;
	private LinearLayout lnContent;

	private Context context;
	private View contenView;
	// 获取activity根布局
	private FrameLayout rootLayout;

	private int rootWidth;
	private int rootHeight;
	
	private boolean isClickShadowDisMiss=true;

	public int shadowColor = Color.parseColor("#b0000000");
	
	private onDismissListener onDismissListener;

	public FrameDialog(Context context, View view) {
		super(context);
		this.context = context;
		this.contenView = view;
		initWindow();
		initView();
		setView();
		setShadowListener();
	}

	private void initWindow() {
		// view加载完成时回调
		contenView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						if (rootWidth == 0 || rootHeight == 0) {
							rootWidth = rootLayout.getWidth();
							rootHeight = rootLayout.getHeight();
							L.i("rootWidth=======" + rootWidth
									+ "    rootHeight=========" + rootHeight);
						}
					}
				});
//		WindowManager wm = ((Activity) context).getWindowManager();
//		rootWidth = wm.getDefaultDisplay().getWidth();
//		rootHeight =  wm.getDefaultDisplay().getHeight();
//		Logger.info("rootWidth=======" + rootWidth
//				+ "    rootHeight=========" + rootHeight);
	
	}

	/**
	 * 布局加载
	 */
	private void setView() {
		// 获取activity根布局并在其中添加传进来的View
		this.addView(alertView);
		ViewGroup viewGroup = (ViewGroup) ((ViewGroup) ((Activity) context)
				.getWindow().getDecorView().findViewById(android.R.id.content))
				.getChildAt(0);
		if (viewGroup instanceof FrameLayout) {
			rootLayout = (FrameLayout) viewGroup;
			rootLayout.addView(this);
			FrameDialog.this.setVisibility(View.GONE);
		} else {
			L.i("弹窗加载不成功！activity根布局必须为FrameLayout");
		}

	}

	/**
	 * 初始化布局
	 */
	private void initView() {
		alertView = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.alert_view, null);
		alertView.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		lnAlertViewTop = (LinearLayout) alertView.findViewById(R.id.top);
		lnAlertViewButtom = (LinearLayout) alertView.findViewById(R.id.bottom);
		lnAlertViewLeft = (LinearLayout) alertView.findViewById(R.id.left);
		lnAlertViewRight = (LinearLayout) alertView.findViewById(R.id.right);
		lnAlertViewCenter = (LinearLayout) alertView.findViewById(R.id.center);
		lnContent = (LinearLayout) alertView.findViewById(R.id.content);
		lnContent.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		lnContent.setBackgroundColor(Color.parseColor("#ffffff"));
		lnContent.addView(contenView);
	}

	/**
	 * 默认居中显示
	 */
	public void show() {
		setVisible();
		
		lnAlertViewTop.setLayoutParams(new LinearLayout.LayoutParams(rootWidth,
				rootHeight / 5));

		lnContent.setLayoutParams(new LinearLayout.LayoutParams(
				rootWidth * 2 / 3, rootHeight / 2));
		
		lnAlertViewLeft.setLayoutParams(new LinearLayout.LayoutParams(
				rootWidth / 6, rootHeight / 2));

		lnAlertViewRight.setLayoutParams(new LinearLayout.LayoutParams(
				rootWidth / 6, rootHeight / 2));

		lnAlertViewButtom.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, rootHeight *3/ 10));
		L.i("show()---------");
	}

	/**
	 * 显示在控件下面
	 * 
	 * @param view
	 */
	public void showAsDown(final View view) {
		setVisible();
		// 设置顶部高度
		int height = (int) (view.getY() + view.getHeight());
		lnAlertViewTop.setLayoutParams(new LinearLayout.LayoutParams(rootWidth,
				height));
		lnAlertViewCenter.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT,1));
	}

	/**
	 * 显示在控件左边
	 * 
	 * @param view
	 */
	public void showAsLeft(final View view) {
		setVisible();
		// 设置左边高度
		int width = (int) view.getX();
		L.i("showAsLeft---------width====" + view.getX());
		lnAlertViewLeft.setLayoutParams(new LinearLayout.LayoutParams(width,
				LayoutParams.MATCH_PARENT));
	}
	
	/**
	 * 右边阴影宽度
	 * 
	 * @param view
	 */
	public void setRightShadowWidth(int dp) {
		lnAlertViewRight.setLayoutParams(new LinearLayout.LayoutParams(DPUtils.dip2px( dp),
				LayoutParams.MATCH_PARENT));
	}


	/**
	 * 显示在控件上面
	 * 
	 * @param view
	 */
	public void showAsUp(final View view, int topShadowHeight) {
		setVisible();
		
		// 设置底部高度
		final int height = (int) (rootHeight - view.getY()-view.getMeasuredHeight());
		
		L.i("showAsUp---->height==" +rootHeight+"111s"+ height+"======"+view.getY()+"   view.getY()=="+"view.getMeasuredHeight()=="+view.getMeasuredHeight());
		lnAlertViewButtom.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, height));
		
		lnAlertViewCenter.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,1));
		
		// 设置顶部高度
		if(topShadowHeight==LayoutParams.WRAP_CONTENT){
			lnAlertViewTop.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1));
		}else{
			lnAlertViewTop.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, topShadowHeight));
		}

	}
	

	/**
	 * 设置弹窗可见
	 */
	private void setVisible() {
		if (FrameDialog.this.getVisibility() == View.GONE) {
			FrameDialog.this.setVisibility(View.VISIBLE);
			contenView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1));
		}
	}

	/**
	 * 设置阴影
	 * 
	 * @param top
	 * @param right
	 * @param buttom
	 * @param left
	 */
	public void setShadow(boolean top, boolean right, boolean buttom,
			boolean left) {
		if (top) {
			lnAlertViewTop.setBackgroundColor(shadowColor);
		}
		if (right) {
			lnAlertViewRight.setBackgroundColor(shadowColor);
		}
		if (buttom) {
			lnAlertViewButtom.setBackgroundColor(shadowColor);
		}
		if (left) {
			lnAlertViewLeft.setBackgroundColor(shadowColor);
		}
	}

	/**
	 * 隐藏弹窗
	 */
	public void dismiss() {
		FrameDialog.this.setVisibility(View.GONE);
		if(onDismissListener!=null){
			onDismissListener.onDisMiss();
			L.i("onDismissListener");
		}
	}
	
	
	/**
	 * 是否显示
	 * @return
	 */
	public boolean isShowing(){
		if(FrameDialog.this.getVisibility()==View.GONE){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 点击阴影部分监听
	 * 
	 * @param isDisMiss
	 */
	public void setShadowListener() {
		lnAlertViewTop.setOnClickListener(new mClick());
		lnAlertViewRight.setOnClickListener(new mClick());
		lnAlertViewButtom.setOnClickListener(new mClick());
		lnAlertViewLeft.setOnClickListener(new mClick());
	}

	/**
	 * 点击阴影部分隐藏弹窗
	 * 
	 * @param isDisMiss
	 */
	class mClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (isClickShadowDisMiss) {
				dismiss();
			}
		}
	}
	
	public interface onDismissListener{
		void onDisMiss();
	}
	
	
	
	

	public LinearLayout getLnAlertViewTop() {
		return lnAlertViewTop;
	}

	public void setLnAlertViewTop(LinearLayout lnAlertViewTop) {
		this.lnAlertViewTop = lnAlertViewTop;
	}

	public LinearLayout getLnAlertViewButtom() {
		return lnAlertViewButtom;
	}

	public void setLnAlertViewButtom(LinearLayout lnAlertViewButtom) {
		this.lnAlertViewButtom = lnAlertViewButtom;
	}

	public LinearLayout getLnAlertViewLeft() {
		return lnAlertViewLeft;
	}

	public void setLnAlertViewLeft(LinearLayout lnAlertViewLeft) {
		this.lnAlertViewLeft = lnAlertViewLeft;
	}

	public LinearLayout getLnAlertViewRight() {
		return lnAlertViewRight;
	}

	public void setLnAlertViewRight(LinearLayout lnAlertViewRight) {
		this.lnAlertViewRight = lnAlertViewRight;
	}

	public LinearLayout getLnAlertViewCenter() {
		return lnAlertViewCenter;
	}

	public void setLnAlertViewCenter(LinearLayout lnAlertViewCenter) {
		this.lnAlertViewCenter = lnAlertViewCenter;
	}

	public int getShadowColor() {
		return shadowColor;
	}

	public void setShadowColor(int shadowColor) {
		this.shadowColor = shadowColor;
	}

	public void setClickShadowDisMiss(boolean isClickShadowDisMiss) {
		this.isClickShadowDisMiss = isClickShadowDisMiss;
	}

	public onDismissListener getOnDismissListener() {
		return onDismissListener;
	}

	public void setOnDismissListener(onDismissListener onDismissListener) {
		this.onDismissListener = onDismissListener;
	}
	
	

}
