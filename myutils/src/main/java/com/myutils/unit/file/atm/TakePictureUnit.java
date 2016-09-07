package com.myutils.unit.file.atm;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.myutils.base.AppFactory;
import com.myutils.core.logger.L;
import com.myutils.core.RowObject;
import com.myutils.unit.OnActivityResultState;
import com.myutils.unit.file.FileModel;
import com.myutils.utils.BitmapUtils;
import com.myutils.utils.DPUtils;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-3-14
 * @Descrition 调用系统照相机拍照，然后压缩图片，返回压缩图片地址和缩略图地址
 */
public class TakePictureUnit {



	private Intent openCameraIntent;

	public static String AppDir = AppFactory.getAppDir();

	private OnBitmapDeal onBitmapDeal;

	/**
	 * 生成照片的临时文件
	 */
	private static final String DF_TEMP_FILE_NAME = "image.png";

	/**
	 * 拍照请求码
	 */
	private static final int REQUEST_CODE = OnActivityResultState.TAKE_PICTURE;

	private Uri imageUri;
	
	/**
	 * 图片模型
	 */
	private FileModel fmPic;
	/**
	 * 缩略图模型
	 */
	private FileModel fmThumbPic;
	/**
	 * 临时图片模型
	 */
	private FileModel fmTempPic;


	public TakePictureUnit() {
		initFileCfg();
		init();
	}
	

	private void initFileCfg() {
		fmPic = new FileModel();
		fmPic.setNextDir("/image");
		fmPic.setPrefix("img_");
		fmPic.setSuffix(".png");
		
		fmThumbPic = new FileModel();
		fmThumbPic.setNextDir("/image/thumb");
		fmThumbPic.setPrefix("thumb_");
		fmThumbPic.setSuffix(".png");
		
		fmTempPic = new FileModel();
		fmTempPic.setNextDir("/image");
		fmTempPic.setPrefix("temp_");
		fmTempPic.setSuffix(".png");
		fmTempPic.setName("img");
		fmTempPic.createFile();
	}


	/**
	 * 参数初始化
	 */
	private void init() {
		openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		imageUri = Uri.fromFile(fmTempPic.getFile());
		// 指定照片保存路径（SD卡），DF_TEMP_FILE_NAME为一个临时文件，每次拍照后这个图片都会被替换
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		
	}

	/**
	 * 开始照相
	 */
	public void takePicture(Fragment fragment) {
		updateName();

		fragment.startActivityForResult(openCameraIntent, REQUEST_CODE);
	}

	/**
	 * 开始照相
	 */
	public void takePicture(Activity activity) {
		updateName();
		activity.startActivityForResult(openCameraIntent, REQUEST_CODE);
	}

	private void updateName() {
		fmPic.updateName();
		fmThumbPic.updateName();
		fmPic.createFile();
		fmThumbPic.createFile();
		fmTempPic.createFile();
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
				try {
					//获取临时图片的Bitmap并压缩
					BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inSampleSize = 1;
					Bitmap bitmapImg=BitmapUtils.getLoacalBitmap(fmTempPic.getPath(),opts);
					Bitmap newBitmapImg = BitmapUtils.createImageThumbnail(bitmapImg, 800, 80);
					//拍照后的压缩图片处理
					L.i("newBitmapImg==============="+fmTempPic.getPath());
					if(onBitmapDeal!=null){
						newBitmapImg =onBitmapDeal.deal(newBitmapImg);
					}
					//压缩后图片保存到本地
					BitmapUtils.saveBitmapToSD(fmPic.getFile(), newBitmapImg, 100);
					//创建缩略图
					Bitmap createImageThumbnail = BitmapUtils
							.createImageThumbnail(bitmapImg, 100, 64);
					Bitmap roundCorners = BitmapUtils.getRoundedCornerBitmap(createImageThumbnail, DPUtils.dip2px(5));
					BitmapUtils.saveBitmapToSD(fmThumbPic.getFile(), roundCorners, 100);
					if(createImageThumbnail!=null){
						createImageThumbnail.recycle();
						createImageThumbnail=null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				RowObject result  = new RowObject();
				result.put("type", "pic");
				result.put("imagePath", fmPic.getPath());
				result.put("thumbnailPath" ,fmThumbPic.getPath());
				return result;

			}
		}
		return null;
	}

	public static String getAppDir() {
		return AppDir;
	}

	public static void setAppDir(String appDir) {
		AppDir = appDir;
	}
	
	public interface OnBitmapDeal{
		Bitmap deal(Bitmap bitmap);
	}

	public OnBitmapDeal getOnBitmapDeal() {
		return onBitmapDeal;
	}

	public void setOnBitmapDeal(OnBitmapDeal onBitmapDeal) {
		this.onBitmapDeal = onBitmapDeal;
	}

	
	

}
