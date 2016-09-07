/*
 * Copyright 2015 Vikram Kakkar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appeaser.sublimepickerlibrary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;


/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @CreateDate 2016/7/25 22:54
 * @Descrition 日期弹窗
 */
public class SublimePickerDialog extends DialogFragment {

    // 日期选择View
    SublimePicker mSublimePicker;

    //确定取消回调
    Callback selectCallback=null;


    SublimeOptions options;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSublimePicker = (SublimePicker) getActivity()
                .getLayoutInflater().inflate(R.layout.sublime_picker, container);

        if(options==null){
            options=new SublimeOptions();
        }
        mSublimePicker.initializePicker(options, new SublimeListenerAdapter() {
            @Override
            public void onDateTimeRecurrenceSet(SublimePicker sublimeMaterialPicker, SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {
                if(selectCallback!=null){
                    selectCallback.onDateTimeRecurrenceSet(selectedDate,hourOfDay,minute,recurrenceOption,recurrenceRule);
                }
            }
            @Override
            public void onCancelled() {
                if(selectCallback!=null){
                    selectCallback.onCancelled();
                }
            }
        });
        return mSublimePicker;
    }




    /**
     * @Descrition 点击确定和取消的回调
     */
    public interface Callback {
        void onCancelled();

        void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                     int hourOfDay, int minute,
                                     SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                     String recurrenceRule);
    }

    public SublimePicker getmSublimePicker() {
        return mSublimePicker;
    }

    public void setmSublimePicker(SublimePicker mSublimePicker) {
        this.mSublimePicker = mSublimePicker;
    }

    public Callback getSelectCallback() {
        return selectCallback;
    }

    public void setSelectCallback(Callback selectCallback) {
        this.selectCallback = selectCallback;
    }


    public SublimeOptions getOptions() {
        return options;
    }

    public void setOptions(SublimeOptions options) {
        this.options = options;
    }


}
