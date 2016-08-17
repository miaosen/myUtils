package com.myutils.unit.zxing.view;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/**
 * @Created by eaglesoft.org
 * @author yangjincheng
 * @Date 2013-11-12
 */
public final class ViewfinderResultPointCallback implements ResultPointCallback {
	private final ViewfinderView viewfinderView;

	public ViewfinderResultPointCallback(ViewfinderView viewfinderView) {
		this.viewfinderView = viewfinderView;
	}

	public void foundPossibleResultPoint(ResultPoint point) {
		viewfinderView.addPossibleResultPoint(point);
	}

}
