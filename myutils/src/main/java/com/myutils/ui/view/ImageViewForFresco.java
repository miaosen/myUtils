package com.myutils.ui.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;
import com.myutils.core.form.FormViewAdpater;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2017-01-10  10:42
 * @Descrition
 */

public class ImageViewForFresco extends SimpleDraweeView implements FormViewAdpater{


    public ImageViewForFresco(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewForFresco(Context context) {
        super(context);
    }

    @Override
    public void setValue(Object object) {
        String url = object.toString();
        Uri uri = Uri.parse(url);
        setImageURI(uri);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean isScanAsOne() {
        return false;
    }
}
