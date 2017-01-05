package com.myutils.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;
import android.os.StatFs;

import com.myutils.base.AppFactory;
import com.myutils.base.L;

/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-3-21
 * @Descrition 文件操作工具类
 */
public class FileUtils {
	
	
	/**
	 * 根据传入文件的绝对路径，如果文件不存在则创建文件夹和一个文件
	 * 
	 * @param path
	 *            内存卡上的文件路径，如："/MyUtils/dom4j"
	 * @param name
	 *            文件名称
	 * @return
	 */
	public static File getFile(String AbsolutePath) {
		String path=AbsolutePath.substring(0,AbsolutePath.lastIndexOf("/"));
		String name=AbsolutePath.substring(AbsolutePath.lastIndexOf("/")+1,AbsolutePath.length());
		L.i("path====" + path +"   name====" + name);
		return  getFile(path,name);
	}

	/**
	 * 根据传入文件的路径和名称获取文件对象，如果文件不存在则创建文件夹和一个文件
	 * 
	 * @param dir
	 *            内存卡上的文件路径，如："/MyUtils/dom4j"
	 * @param name
	 *            文件名称
	 * @return
	 */
	public static File getFile(String dir, String name) {
		L.i("FilePath====" + dir +"/"+ name);
		// 判断是否存在sd卡
		if (!isSDCardEnable()) {// 如果不存在,
			L.i("SD卡管理：SD卡不存在，请加载SD卡");
			return null;
		} else {// 如果存在
			// 获取sd卡路径
//			String dbDir = android.os.Environment.getExternalStorageDirectory()
//					.getAbsolutePath();
//			// 判断目录是否存在，不存在则创建该目录
			File dirFile = new File(dir);
			if (!dirFile.exists()) {
				boolean suceess=dirFile.mkdirs();
				L.i("指定目录" + dirFile.getName() + "不存在，已为你创建一个！suceess=="+suceess);
			}
			// 文件是否创建成功
			boolean isFileCreateSuccess = false;
			// 判断文件是否存在，不存在则创建该文件
			File file = new File(dir, name);
			if (!file.exists()) {
				try {
					isFileCreateSuccess = file.createNewFile();// 创建文件
					L.i("文件不存在，已为你创建一个！ isFileCreateSuccess="+isFileCreateSuccess);
				} catch (IOException e) {
					L.i("创建文件失败！");
					e.printStackTrace();
				}
			} else
				isFileCreateSuccess = true;
			// 返回文件对象
			if (isFileCreateSuccess) {
				return file;
			} else {
				return null;
			}
		}
	}
	
	
	/**
	 * 校验路径是否有问题
	 * @param path
	 * @return
	 */
	public static boolean validate(String path) {
		boolean success=false;
		try{
			new File(path);
			success=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * 检查内存卡是否挂载
	 * 
	 * @return
	 */
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SD卡路径
	 * 
	 * @return
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * 获取SD卡的剩余容量 单位byte
	 * 
	 * @return
	 */
	public static long getSDCardAllSize() {
		if (isSDCardEnable()) {
			StatFs stat = new StatFs(getSDCardPath());
			// 获取空闲的数据块的数量
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * 获取指定路径所在空间的剩余可用容量字节数，单位byte
	 * 
	 * @param filePath
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 */
	public static long getFreeBytes(String filePath) {
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getSDCardPath())) {
			filePath = getSDCardPath();
		} else {// 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * 获取系统存储路径
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath() {
		return Environment.getRootDirectory().getAbsolutePath();
	}

	/**
	 * =========================Assets文件夹操作======================
	 */

	/**
	 * 工具Assets的文件名称获取InputStream
	 * 
	 * @param filepath
	 * @return
	 */
	public static InputStream readFromAssets(String filepath) {
		try {
			InputStream is = AppFactory.getAppContext().getAssets()
					.open(filepath);
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断是否存在改路径
	 * 
	 * @return
	 */
	public static boolean isEixtPath(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 复制单个文件
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				L.i("oldPath======"+oldPath+"  newPath==="+newPath);
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			L.i("复制单个文件操作出错");
			e.printStackTrace();

		}

	}
	
	

}
