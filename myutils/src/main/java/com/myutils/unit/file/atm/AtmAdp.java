package com.myutils.unit.file.atm;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.myutils.R;
import com.myutils.base.AppFactory;
import com.myutils.base.BaseActivity;
import com.myutils.core.RowObject;
import com.myutils.core.logger.L;
import com.myutils.ui.view.listview.BaseFillAdapter;
import com.myutils.ui.dialog.MsgDialog;
import com.myutils.ui.dialog.bs.BaseFmDialog;
import com.myutils.utils.ApplicationUtils;
import com.myutils.utils.BitmapUtils;

public class AtmAdp extends BaseFillAdapter {

    private Context context;


    public AtmAdp(Context context, List<RowObject> rows, int layout) {
        super(context, rows, layout);
        this.context = context;
    }

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
        View rl_item = views.get("rl_item");
        rl_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("row=====" + row.toString());
                String type = row.getString("type") + "";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (type.equals("voice")) {
                    intent.setDataAndType(Uri.parse("file://" + row.getString("voicePath")), "audio/*");
                }else if (type.equals("video")) {
                    intent.setDataAndType(Uri.parse("file://" + row.getString("videoPath")), "video/*");
                    context.startActivity(intent);
                }else if(type.equals("pic")){
                    intent.setDataAndType(Uri.parse("file://" + row.getString("imagePath")), "image/*");
                }
                context.startActivity(intent);
            }
        });

        //图片样式
        SimpleDraweeView img_file = (SimpleDraweeView) views.get("img_file");
        String type = row.getString("type") + "";

        if (type.equals("voice")) {
            //录音
            Uri uri = Uri.parse("res://"+ AppFactory.getAppContext().getPackageName()+"/"+R.drawable.icon_music);
            img_file.setImageURI(uri);
        } else if (type.equals("pic")) {
            //图片
            String urlThumb = row.getString("imagePath");
            Uri uri = Uri.parse("file://"+ urlThumb);
            img_file.setImageURI(uri);
        } else if (type.equals("video")) {
            //视频
            String urlThumb = row.getString("thumbnailPath");
            Uri uri = Uri.parse("file://"+ urlThumb);
            img_file.setImageURI(uri);

            //播放图标
            SimpleDraweeView img_paly = (SimpleDraweeView) views.get("img_paly");
            Uri uriPaly = Uri.parse("res://"+ AppFactory.getAppContext().getPackageName()+"/"+R.drawable.icon_play);
            img_paly.setImageURI(uriPaly);
        } else {

            //文件
            Uri uri = Uri.parse("res://"+ AppFactory.getAppContext().getPackageName()+"/"+R.drawable.icon_file);
            img_file.setImageURI(uri);
            //img_file.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_file));
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
