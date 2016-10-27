//package com.myutilsdemo.unit.file;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.myutils.core.RowObject;
//import com.myutils.core.annotation.ViewInject;
//import com.myutils.core.logger.L;
//import com.myutils.unit.file.FileModel;
//import com.myutils.unit.file.atm.vorcd.VocieRcdDlg;
//import com.myutils.unit.file.atm.vorcd.VoiceRcdView;
//import com.myutilsdemo.R;
//import com.myutilsdemo.base.BaseAct;
//
///**
// * @author zengmiaosen
// * @email 1510809124@qq.com
// * @git http://git.oschina.net/miaosen/MyUtils
// * @CreateDate 2016/9/6 14:56
// * @Descrition 录像示例
// */
//public class VideoRecordAct extends BaseAct {
//
//
//    @ViewInject
//    VoiceRcdView voiceRcdView;
//
//
//    @ViewInject
//    Button record_dlg;
//    VocieRcdDlg vocieRcdDlg;
//
//
//
//
//    @Override
//    public int initConfig(Bundle savedInstanceState) {
//        return R.layout.unit_voice_record_act;
//    }
//
//    @Override
//    public void initView(View decorView) {
//        record_dlg.setOnClickListener(this);
//        onView();
//        onDialog();
//    }
//
//    private void onDialog() {
//        vocieRcdDlg = new VocieRcdDlg(this);
//        VoiceRcdView voiceView=vocieRcdDlg.getVoiceRcdView();
//        fileOutPutCfg(voiceView.getFileModel());
//        voiceView.setOnRecordComplateListener(new VoiceRcdView.OnRecordComplateListener() {
//            @Override
//            public void onComplate(RowObject result) {
//                L.i("result===========" + result);
//                vocieRcdDlg.dismiss();
//            }
//        });
//    }
//
//    /**
//     * 文件输出配置
//     * @param fileModel
//     */
//    private void fileOutPutCfg(FileModel fileModel) {
//        fileModel.setNextDir("/aa");
//        fileModel.setPrefix("aaa_");
//    }
//
//
//    public void onView() {
//        fileOutPutCfg(voiceRcdView.getFileModel());
//        voiceRcdView.setOnRecordComplateListener(new VoiceRcdView.OnRecordComplateListener() {
//            @Override
//            public void onComplate(RowObject result) {
//                L.i("result===========" + result);
//            }
//        });
//    }
//
//    @Override
//    public void initData() {
//
//    }
//
//    @Override
//    public void click(View v) {
//        if (v == record_dlg) {
//            vocieRcdDlg.show();
//        }
//    }
//}
