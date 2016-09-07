package com.appeaser.sublimepickerlibrary;




import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/7/26 0:25
 * @Descrition 日期按钮
 */
public class DateButton extends TextView{

	private Context context;

	private SublimePickerDialog dialog;


	private SublimeOptions options=new SublimeOptions();

	/**
	 * 日期显示格式
	 */
	private String strDateFormat=null;

	/**
	 * 显示配置 time 只显示时间，date只显示日期
	 */
	private String strOptions;

	SimpleDateFormat format;

	private boolean isInitDate=false;


	//在xml定义时必须引用AttributeSet参数
	public DateButton(Context context, AttributeSet attrs) {
		super(context,attrs);
		this.context=context;
		logicAttr(attrs);
		init();
		getContext();
	}


	public DateButton(Context context) {
		super(context);
		this.context=context;

		init();

	}

	private void init() {
		initDate();
		this.setOnClickListener(new mClick());
	}


	/**
	 * 解析自定义属性
	 * @param attrs
     */
	protected void logicAttr(AttributeSet attrs) {
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DateButton);
		strDateFormat = ta.getString(R.styleable.DateButton_format);
		strOptions = ta.getString(R.styleable.DateButton_options)+"";
		isInitDate=ta.getBoolean(R.styleable.DateButton_isInitDate,false);
		ta.recycle();
		if(!strOptions.equals("")&&!strOptions.equals("null")){
			if(strOptions.equals("time")){
				options.setmDisplayOptions(0);
				showOnlyTimePicker(options);
			}else if(strOptions.equals("date")){
				options.setmDisplayOptions(0);
				showOnlyDatePicker(options);
			}
		}else{
			options.setmDisplayOptions(0);
			showOnlyTimePicker(options);
			showOnlyDatePicker(options);
		}
	}

	/**
	 * 初始化时间
	 */
	private void initDate() {
		if(strDateFormat==null){
			strDateFormat="yyyy-MM-dd";
		}
		 format = new SimpleDateFormat(strDateFormat);
		if(isInitDate){
			//String format = this.format.format(new Date());
			setText(format.format(new Date()));
		}

	}


	/**
	 * 显示时间选择框
	 * @return
	 */
	public SublimeOptions showOnlyTimePicker(SublimeOptions options){
		int displayOptions = options.getmDisplayOptions();
		displayOptions|= SublimeOptions.ACTIVATE_TIME_PICKER;
		options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
		options.setDisplayOptions(displayOptions);
		return options;
	}

	/**
	 * 显示日期选择框
	 * @return
	 */
	public SublimeOptions showOnlyDatePicker(SublimeOptions options){
		int displayOptions = options.getmDisplayOptions();
		displayOptions|= SublimeOptions.ACTIVATE_DATE_PICKER;
		options.setDisplayOptions(displayOptions);
		options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
		return options;
	}


	class mClick implements OnClickListener{
		@Override
		public void onClick(View v) {
            if(dialog==null){
                dialog=new SublimePickerDialog();
            }
			dialog.setOptions(options);
			dialog.setSelectCallback(new SublimePickerDialog.Callback() {
				@Override
				public void onCancelled() {
					dialog.dismiss();
				}
				@Override
				public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {
					String strHour=hourOfDay>9?hourOfDay+"":"0"+hourOfDay;
					String strMinute=minute>9?minute+"":"0"+minute;
					//选择日期
					if(strOptions.equals("time")){
						setText(strHour+":"+strMinute+":00");
					}else if(strOptions.equals("date")){
						Calendar firstDate = selectedDate.getFirstDate();
						String strDate = format.format(firstDate.getTime());
						setText(strDate+"");
					}else{
						if(selectedDate!=null){
							Calendar firstDate = selectedDate.getFirstDate();
							String strDate = format.format(firstDate.getTime());
							setText(strDate+" "+strHour+":"+strMinute+":00");
						}
					}
					dialog.dismiss();
				}
			});
            FragmentActivity act= (FragmentActivity) context;
            dialog.show(act.getSupportFragmentManager(),"SUBLIME_PICKER");
		}
	}





}
