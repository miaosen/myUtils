package com.myutilsdemo.ui.dlg;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.myutils.ui.dialog.bs.BaseFmDialog;
import com.myutilsdemo.R;

/**
 * Created by OAIM on 2016/8/26.
 */
public class TestDialog extends BaseFmDialog {




    public TestDialog() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init(View dialogView) {

    }
}
