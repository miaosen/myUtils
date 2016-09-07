package com.myutils.unit.file.atm;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;

import com.myutils.R;
import com.myutils.core.logger.L;
import com.myutils.core.RowObject;
import com.myutils.ui.view.listview.BaseFillAdapter;
import com.myutils.ui.T;
import com.myutils.core.annotation.ViewInject;
import com.myutils.unit.file.atm.AttachmentUnit.OnDataChangeListener;
import com.myutils.utils.ViewUtils;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-6-21
 * @Descrition 附件模块布局
 */
public class AtmView {

	private Context context;

	private AttachmentUnit atmUnit;

	private View decorView;
	@ViewInject
	private ImageButton btn_photo, btn_voice, btn_video;
	private GridView upload_gridview;
	private AtmAdp atmAdp;
	private List<RowObject> rows = new LinkedList<RowObject>();

	public AtmView(AttachmentUnit atmUnit, View decorView) {
		this.decorView = decorView;
		this.atmUnit = atmUnit;
		context=atmUnit.getContext();
		ViewUtils.injectAllFields(this, decorView);
		if (initView()) {
			atmUnit.initUnit();
		} else {
			T.show("加载附件布局出错!");
		}
		onDataChange();

	}

	/**
	 * 数据改变，刷新适配器
	 */
	private void onDataChange() {
		atmUnit.setOnDataChangeListener(new OnDataChangeListener() {
			@Override
			public void onChange(RowObject result) {
				rows.add(result);
				atmAdp.notifyDataSetChanged();
				ViewUtils.setListViewHeightBasedOnChildren(upload_gridview);
			}
		});
	}

	class mClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == btn_photo) {
				atmUnit.takePic();
			} else if (v == btn_voice) {
				atmUnit.voiceRecord();
			} else if (v == btn_video) {
				atmUnit.videoRecord();
			}
		}

	}

	private boolean initView() {
		boolean success = true;
		try {
			btn_photo.setOnClickListener(new mClick());
			btn_video.setOnClickListener(new mClick());
			btn_voice.setOnClickListener(new mClick());
			upload_gridview = (GridView) decorView
					.findViewById(R.id.upload_gridview);
			atmAdp = new AtmAdp(context, rows,
					R.layout.upload_data_gridview_item);
			upload_gridview.setAdapter(atmAdp);
			atmAdp.setOnItemClickListener(new BaseFillAdapter.OnItemClickListener() {
				@Override
				public void onItemClick(View convertView, RowObject row,
						int position) {
					// TODO Auto-generated method stub
					L.i("row=====" + row.toString());
					String type = row.getString("type")+"";
					if(type.equals("voice")){
						 Intent it = new Intent(Intent.ACTION_VIEW);
			                it.setDataAndType(Uri.parse("file://" + row.getString("voicePath")), "audio/amr");
			                context.startActivity(it);        
					}
				}
			});
		} catch (NullPointerException e) {
			//L.e(e);
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	

	public void setRows(List<RowObject> rows) {
		this.rows = rows;
	}

	public List<RowObject> getRows() {
		return rows;
	}
	
	

}
