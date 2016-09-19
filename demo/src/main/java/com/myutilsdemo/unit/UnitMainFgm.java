package com.myutilsdemo.unit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myutils.core.RowObject;
import com.myutils.core.annotation.ViewInject;
import com.myutils.core.gson.JSONSerializer;
import com.myutils.core.logger.L;
import com.myutils.unit.file.atm.TakePictureUnit;
import com.myutils.utils.IntentUtils;
import com.myutilsdemo.R;
import com.myutilsdemo.base.BaseFgm;
import com.myutilsdemo.unit.file.AtmAct;
import com.myutilsdemo.unit.file.VocieRecordAct;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/1 16:17
 * @Descrition 核心示例主页
 */
public class UnitMainFgm extends BaseFgm {

    @ViewInject
    Button attachment, recordVoice, take_pic, video_rcd;

    TakePictureUnit takePictureUnit;

    @Override
    public int initConfig(Bundle savedInstanceState) {
        return R.layout.unit_main_fgm;
    }

    @Override
    public void initView(View view) {
        attachment.setOnClickListener(this);
        recordVoice.setOnClickListener(this);
        take_pic.setOnClickListener(this);
        video_rcd.setOnClickListener(this);

    }

    @Override
    public void click(View v) {
        if (v == attachment) {
            IntentUtils.jump(getContext(), AtmAct.class);
        } else if (v == recordVoice) {
            IntentUtils.jump(getContext(), VocieRecordAct.class);
        } else if (v == take_pic) {
            takePictureUnit = new TakePictureUnit();
            takePictureUnit.takePicture(this);
        } else if (v == video_rcd) {
            IntentUtils.jump(getContext(), VocieRecordAct.class);

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RowObject rowObject = takePictureUnit.onActivityResult(requestCode, resultCode, data);
        L.i("rowObject====" + JSONSerializer.toJson(rowObject));
    }

    @Override
    public void initData() {

    }
}
