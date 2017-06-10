package com.wenny.breadmakerdemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by ${wenny} on 2017/4/1.
 */

public class DateTimePicker extends Dialog implements View.OnClickListener, TimePicker.OnTimeChangedListener, DatePicker.OnDateChangedListener {
    private Context context;
    private DatePicker day_picker;
    private TimePicker timepicker;
    private TextView tv_confirm;
    private Calendar calendar;
    private int year,month,day,hour,minute;
    private OnConfirmListener onConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public DateTimePicker(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_datetime_picker);
        day_picker = (DatePicker) findViewById(R.id.day_picker);
        timepicker = (TimePicker) findViewById(R.id.timepicker);

        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);
        timepicker.setOnTimeChangedListener(this);
        timepicker.setIs24HourView(true);
        // 获取日历对象
        calendar = Calendar.getInstance();
        // 获取当前对应的年、月、日的信息
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        day_picker.init(year,month,day,this);
    }

    public void setDate(Long date){
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        // 获取当前对应的年、月、日的信息
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        day_picker.init(year,month,day,this);
        timepicker.setCurrentHour(hour);
        timepicker.setCurrentMinute(minute);

    }

    @Override
    public void onClick(View view) {
        if(onConfirmListener != null){
            onConfirmListener.onConfirm(year,month,day,hour,minute);
        }
    }

    @Override
    public void onTimeChanged(TimePicker timePicker,int hourOfDay, int minute) {
        //时间改变
        hour = hourOfDay;
        this.minute = minute;
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        //日期改变
        this.year = year;
        month = monthOfYear;
        day = dayOfMonth;
    }

    public interface OnConfirmListener{
        void onConfirm(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute);
    }
}
