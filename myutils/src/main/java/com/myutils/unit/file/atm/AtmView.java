package com.myutils.unit.file.atm;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.myutils.R;
import com.myutils.core.annotation.InjectReader;
import com.myutils.core.RowObject;
import com.myutils.ui.view.FlowLayout;
import com.myutils.core.annotation.ViewInject;
import com.myutils.unit.file.atm.AttachmentUnit.OnDataChangeListener;


/**
 * @author zms
 * @Created by gzpykj.com
 * @Date 2016-6-21
 * @Descrition 附件模块布局
 */
public class AtmView extends LinearLayout {

    private Context context;

    private AttachmentUnit atmUnit;

    @ViewInject
    private ImageButton btn_photo, btn_voice, btn_video;
    private FlowLayout flowLayout;
    private AtmAdp atmAdp;
    private List<RowObject> rows = new LinkedList<RowObject>();

    public AtmView(Context context) {
        super(context);
        initView();
    }

    public AtmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AtmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    class mClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == btn_photo) {
                atmUnit.takePic();
            } else if (v == btn_voice) {
                atmUnit.voiceRecord();
            } else if (v == btn_video) {
                atmUnit.videoRecord();
            }
        }

    }

    private void initView() {
        context = getContext();
        View attachment_layout = LayoutInflater.from(context).inflate(R.layout.attachment_layout, null);
        addView(attachment_layout);
        InjectReader.injectAllFields(this);
        btn_photo.setOnClickListener(new mClick());
        btn_video.setOnClickListener(new mClick());
        btn_voice.setOnClickListener(new mClick());
        flowLayout = (FlowLayout)
                findViewById(R.id.flowLayout);
        atmAdp = new AtmAdp(context, rows, R.layout.file_atmview_item);
        flowLayout.setAdapter(atmAdp);
        atmUnit = new AttachmentUnit(context);
        atmUnit.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onChange(RowObject result) {
                rows.add(result);
                atmAdp.notifyDataSetChanged();
            }
        });

    }

    public void notifyDataSetChanged(){
        atmAdp.notifyDataSetChanged();
    }


    public void setRows(List<RowObject> rows) {
        this.rows = rows;
    }

    public List<RowObject> getFilePaths() {
        return rows;
    }

    public AttachmentUnit getAtmUnit() {
        return atmUnit;
    }

    public void setAtmUnit(AttachmentUnit atmUnit) {
        this.atmUnit = atmUnit;
    }
}
