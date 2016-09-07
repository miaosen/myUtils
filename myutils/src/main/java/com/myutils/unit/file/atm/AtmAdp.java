package com.myutils.unit.file.atm;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.myutils.R;
import com.myutils.base.BaseActivity;
import com.myutils.core.RowObject;
import com.myutils.ui.view.listview.BaseFillAdapter;
import com.myutils.ui.dialog.MsgDialog;
import com.myutils.ui.dialog.bs.BaseFmDialog;
import com.myutils.utils.BitmapUtils;

public class AtmAdp extends BaseFillAdapter {

    private Context context;


    public AtmAdp(Context context, List<RowObject> rows, int layout) {
        super(context, rows, layout);
        this.context = context;
    }

//	@Override
//	public Map<String, Integer> setConvertView(Map<String, Integer> views) {
//		views.put("img_close", R.id.img_close);
//		views.put("img_file", R.id.img_file);
//		return views;
//	}

    @Override
    public void setItem(View convertView, final RowObject row, int position,
                       ViewHolder holder) {
        Map<String, View> views=holder.views;
        View img_close = views.get("img_close");
        img_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsgDialog(row);
            }
        });

        //图片样式
        ImageView img_file = (ImageView) views.get("img_file");
        String type = row.getString("type") + "";
        if (type.equals("voice")) {
            //录音
            img_file.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_music));
        } else if (type.equals("pic")) {
            //图片
            String urlThumb = row.getString("thumbnailPath");
            img_file.setImageBitmap(BitmapUtils.getLoacalBitmap(urlThumb));
        } else {
            //文件
            img_file.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_file));
        }

        if (type.equals("voice")) {
            //	img_file.setImageDrawable(getContext().getResources().getDrawable(R.drawable.));
        } else {

        }
    }


    private void showMsgDialog(final RowObject row) {
        //删除弹窗
        MsgDialog msgDialog = new MsgDialog();
        msgDialog.setTitle("提示");
        msgDialog.setMsg("是否删除该项？");
        msgDialog.setContainerGravity(Gravity.CENTER);
        msgDialog.setOnSureListener(new BaseFmDialog.OnSureListener() {
            @Override
            public void sure(BaseFmDialog dialog) {
                dialog.dismiss();
                getRows().remove(row);
                notifyDataSetChanged();
            }
        });
        BaseActivity act= (BaseActivity) context;
        msgDialog.show(act.getSupportFragmentManager(),"atm");
    }

}
