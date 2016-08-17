//package com.myutils.ui.view.listview;
//
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.View;
//import android.widget.ListView;
//
//import com.myutils.R;
//import com.myutils.data.RowObject;
//import com.myutils.ui.apdater.BaseFillListViewAdapter;
//
//public class SimpleListView extends ListView{
//
//	private int mLayout=R.layout.spl_listview_item;
//
//	private MAdapter mAdapter;
//
//
//	public SimpleListView(Context context,List<RowObject> rows) {
//		super(context);
//		mAdapter=new MAdapter(context, rows, mLayout);
//		setBackgroundColor(Color.parseColor("#ffffff"));
////		setM
//		setAdapter(mAdapter);
//	}
//
//	public void refresh(){
//		mAdapter.notifyDataSetChanged();
//	}
//
//
//	class MAdapter extends BaseFillListViewAdapter{
//
//		public MAdapter(Context context, List<RowObject> rows, int layout) {
//			super(context, rows, layout);
//		}
//
//
//		@Override
//		public Map<String, Integer> setConvertView(Map<String, Integer> views) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public void setItem(View convertView, RowObject row, int position,
//				Map<String, View> views, ViewHolder holder) {
//			// TODO Auto-generated method stub
//
//		}
//
//	}
//
//}
