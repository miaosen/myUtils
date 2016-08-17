package com.myutils.unit.file.atm;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.myutils.R;
import com.myutils.core.RowObject;
import com.myutils.ui.apdater.BaseFillAdapter;
import com.myutils.ui.dialog.MsgDialog;
import com.myutils.ui.dialog.MsgDialog.OnSureListener;
import com.myutils.utils.BitmapUtils;

public class AtmAdp extends BaseFillAdapter {

	private Context context;

	//删除弹窗
	private MsgDialog msgDialog;



	public AtmAdp(Context context, List<RowObject> rows, int layout) {
		super(context, rows, layout);
		this.context=context;
	}

//	@Override
//	public Map<String, Integer> setConvertView(Map<String, Integer> views) {
//		views.put("img_close", R.id.img_close);
//		views.put("img_file", R.id.img_file);
//		return views;
//	}

	@Override
	public void setItem(View convertView, final RowObject row, int position,
			Map<String, View> views, ViewHolder holder) {
		View img_close = views.get("img_close");
		img_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showMsgDialog(row);
			}

		});

		//图片样式
		ImageView img_file = (ImageView) views.get("img_file");
		String type = row.getString("type")+"";
		if(type.equals("voice")){
			//录音
			img_file.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_music));
		}else if(type.equals("pic")){
			//图片
			String urlThumb = row.getString("thumbnailPath");
			img_file.setImageBitmap(BitmapUtils.getLoacalBitmap(urlThumb));
		}else{
			//文件
			img_file.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_file));
		}

		if(type.equals("voice")){
		//	img_file.setImageDrawable(getContext().getResources().getDrawable(R.drawable.));
		}else{

		}
	}


	private void showMsgDialog(final RowObject row) {
		if(msgDialog==null){
			msgDialog=new MsgDialog(getContext(), "提示", "是否删除该项？");
			msgDialog.setContentGravity(Gravity.CENTER);
		}
		msgDialog.setOnSureListener(new OnSureListener() {
			@Override
			public void sure(MsgDialog dialog) {
				msgDialog.dismiss();
				getRows().remove(row);
				notifyDataSetChanged();
			}
		});
		msgDialog.show();
	}

}