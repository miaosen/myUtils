package com.myutils.unit.file.atm.vdrcd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.myutils.base.AppFactory;
import com.myutils.core.logger.L;
import com.myutils.core.RowObject;
import com.myutils.unit.OnActivityResultState;
import com.myutils.unit.file.FileModel;
import com.myutils.utils.ImageUtils;
import com.myutils.utils.VideoUtils;

/**
 * @author zms
 * @Created by gzpykj.com
 * @Date 2016-3-18
 * @Descrition 录像模块，录完像后返回录像文件地址和缩略图地址
 */
public class VideoRcdUnit {

    private Context context;

    //private Activity activity;
    /**
     * 视频文件模型
     */
    private FileModel fmVideo;
    //视频截图
    private FileModel fmThumbnail;

    Fragment fragment;

    /**
     * 录像请求码
     */
    public static final int REQUEST_CODE = OnActivityResultState.VIDEO_RECORD;

    public VideoRcdUnit(Context context) {
        this.context = context;
//		activity = (Activity) context;
        fmVideo = new FileModel();
        fmVideo.setNextDir("/video");
        fmVideo.setPrefix("vd_");
        fmVideo.setSuffix(".mp4");

        fmThumbnail = new FileModel();
        fmThumbnail.setNextDir("/video/thumb");
        fmThumbnail.setPrefix("thumb_");
        fmThumbnail.setSuffix(".png");

    }


    /**
     * 跳转到视频录制界面
     *
     * @return
     */
    public void startRecord() {
        fmVideo.updateName();
        fmVideo.createFile();
        fmThumbnail.updateName();
        fmThumbnail.createFile();
        Intent intent = new Intent(context, VideoRcdAct.class);
        intent.putExtra("videoPath", fmVideo.getPath());
        intent.putExtra("videoName", fmVideo.getName());
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
    }


    /**
     * 跳转到视频录制界面
     *
     * @return
     */
    public void startRecord(Fragment fragment) {
        fmVideo.updateName();
        fmVideo.createFile();
        fmThumbnail.updateName();
        fmThumbnail.createFile();
        Intent intent = new Intent(context, VideoRcdAct.class);
        intent.putExtra("videoPath", fmVideo.getPath());
        intent.putExtra("videoName", fmVideo.getName());
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }



    /**
     * 返回图片路径
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    public RowObject onActivityResult(int requestCode,
                                      int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //videoPath = data.getStringExtra("videoPath");
                // File file = new File(videoPath);
                // String videoPath = copyFile(file);
                Bitmap videoThumbnail = VideoUtils.getVideoThumbnail(fmVideo.getPath(),
                        100, 100, MediaStore.Images.Thumbnails.MICRO_KIND);
                ImageUtils.saveBitmap(fmThumbnail.getFile(),
                        videoThumbnail);
                RowObject result = new RowObject();
                result.put("type", "video");
                result.put("videoPath", fmVideo.getPath());
                result.put("thumbnailPath", fmThumbnail.getPath());
                return result;
            }
        }

        return null;
    }

}
