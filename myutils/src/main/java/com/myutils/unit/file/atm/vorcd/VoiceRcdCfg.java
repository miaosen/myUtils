//package com.myutils.unit.file.atm.vorcd;
//
//import com.myutils.unit.file.FileModel;
///**
// * @Created by gzpykj.com
// * @author zms
// * @Date 2016-6-23
// * @Descrition 生成录音文件配置
// */
//public class VoiceRcdCfg {
//
//	public FileModel fileModel;
//
//
//	public VoiceRcdCfg(){
//		fileModel = new FileModel();
//		fileModel.setNextDir("/voice");
//		fileModel.setPrefix("voice_");
//		fileModel.setSuffix(".amr");
//		fileModel.createFile();
//	}
//
//	public void update() {
//		fileModel.updateName();
//		fileModel.createFile();
//	}
//
//	public FileModel getFileModel() {
//		return fileModel;
//	}
//
//	public void setFileModel(FileModel fileModel) {
//		this.fileModel = fileModel;
//	}
//
//	public String getVociePath() {
//		return fileModel.getPath();
//	}
//
//
//}
