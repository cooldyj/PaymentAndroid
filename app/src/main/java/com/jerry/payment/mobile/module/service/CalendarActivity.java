package com.jerry.payment.mobile.module.service;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.payment.mobile.R;
import com.jerry.payment.mobile.module.base.BaseActivity;
import com.jerry.payment.mobile.utils.Logs;
import com.jerry.payment.mobile.widget.calendar.DatePickerController;
import com.jerry.payment.mobile.widget.calendar.DayPickerView;
import com.jerry.payment.mobile.widget.calendar.SimpleMonthAdapter;

import java.util.Calendar;

/**
 * 日历选择Activity
 * Created by jerry on 2016/8/16.
 */
public class CalendarActivity extends BaseActivity implements DatePickerController {

    private DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_calendar_activity);
        initView();
    }

    private void initView(){
        ImageView backIV = (ImageView) findViewById(R.id.app_title_back);
        TextView titleTV = (TextView) findViewById(R.id.app_title_text);
        dayPickerView = (DayPickerView) findViewById(R.id.calendar_picker);
        dayPickerView.setController(this);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        titleTV.setText(getString(R.string.select_data));

        Calendar calendar = Calendar.getInstance();
        Logs.i(calendar.get(Calendar.YEAR) + "");
    }

    @Override
    public int getMaxYear() {
        return 2017;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        Logs.e("Day Selected", day + " / " + month + " / " + year);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.pop_bottom_out);
    }
}
