package com.myutils.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myutils.R;
import com.myutils.core.annotation.InjectReader;
import com.myutils.core.annotation.ViewInject;
import com.myutils.core.form.ViewUtils;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2017-01-12  10:52
 * @Descrition 通用头部
 */
public class HeaderView extends LinearLayout {

    View contentview;

    int headerlaout;

    @ViewInject
    Button left,right;

    @ViewInject
    TextView title;


    String titleText,leftText,rightText;

    boolean isLeftFinish,isRightFinish,isHideLeft,isHideRight;

    int leftDrawable,rightDrawable;

    public HeaderView(Context context) {
        super(context);
        init();
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        analysisAttr(attrs);
        init();
    }

    public int setHeaderLayout(){
        return R.layout.ui_view_header;
    }

    private void analysisAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HeaderView);
        titleText=typedArray.getString(R.styleable.HeaderView_title);
        leftText=typedArray.getString(R.styleable.HeaderView_leftText);
        rightText=typedArray.getString(R.styleable.HeaderView_rightText);
        isLeftFinish=typedArray.getBoolean(R.styleable.HeaderView_isLeftFinish,false);
        isRightFinish=typedArray.getBoolean(R.styleable.HeaderView_isRightFinish,false);
        isHideLeft=typedArray.getBoolean(R.styleable.HeaderView_isHideLeft,false);
        isHideRight=typedArray.getBoolean(R.styleable.HeaderView_isHideRight,false);
        leftDrawable=typedArray.getResourceId(R.styleable.HeaderView_leftDrawable,-1);
        rightDrawable=typedArray.getResourceId(R.styleable.HeaderView_rightDrawable,-1);
        typedArray.recycle();

    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        headerlaout= setHeaderLayout();
        if(contentview==null){
            contentview= ViewUtils.inflatView(getContext(), headerlaout);
        }
        addView(contentview);
        contentview.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        InjectReader.injectAllFields(this);
        if(!TextUtils.isEmpty(titleText)){
            title.setText(titleText);
        }
        if(!TextUtils.isEmpty(leftText)){
            right.setText(leftText);
        }
        if(!TextUtils.isEmpty(rightText)){
            title.setText(rightText);
        }
        if(isLeftFinish){
            ViewUtils.finishByClick(left);
        }
        if(isRightFinish){
            ViewUtils.finishByClick(right);
        }
        if(isHideLeft){
            left.setVisibility(View.INVISIBLE);
        }
        if(isHideRight){
            right.setVisibility(View.INVISIBLE);
        }
        if (leftDrawable > 0) {
            Drawable drawable= getResources().getDrawable(leftDrawable);// 获取图片资源icon_date
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//设置图片的x轴，y轴，宽度，高度
            left.setCompoundDrawables(drawable, null, null, null);
        }
        if (rightDrawable > 0) {
            Drawable drawable= getResources().getDrawable(leftDrawable);// 获取图片资源icon_date
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//设置图片的x轴，y轴，宽度，高度
            right.setCompoundDrawables(null, null, drawable, null);
        }
    }


    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
        title.setText(titleText);
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
        left.setText(leftText);
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        right.setText(rightText);
    }

    public boolean isLeftFinish() {
        return isLeftFinish;
    }

    public void setLeftFinish(boolean leftFinish) {
        isLeftFinish = leftFinish;
    }

    public boolean isRightFinish() {
        return isRightFinish;
    }

    public void setRightFinish(boolean rightFinish) {
        isRightFinish = rightFinish;
    }
}
