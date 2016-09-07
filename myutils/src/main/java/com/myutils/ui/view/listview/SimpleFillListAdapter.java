package com.myutils.ui.view.listview;

import android.content.Context;
import android.view.View;

import com.myutils.core.RowObject;

import java.util.List;

public class SimpleFillListAdapter extends BaseFillAdapter{


	public SimpleFillListAdapter(Context context, List<RowObject> rows, int layout) {
		super(context, rows, layout);
	}

	@Override
	public void setItem(View convertView, RowObject row, int position, ViewHolder holder) {

	}
}
