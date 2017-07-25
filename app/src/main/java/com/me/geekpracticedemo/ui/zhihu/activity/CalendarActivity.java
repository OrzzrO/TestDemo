package com.me.geekpracticedemo.ui.zhihu.activity;

import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.base.SimpleActivity;
import com.me.geekpracticedemo.util.DateUtil;
import com.me.geekpracticedemo.util.RxBus;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2017/7/25.
 */

public class CalendarActivity
    extends SimpleActivity {
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.view_calender)
    MaterialCalendarView mViewCalender;
    @BindView(R.id.tv_calender_enter)
    TextView mTvCalenderEnter;
    private CalendarDay mDate;

    @Override
    protected void initEventAndData() {
        setToolBar(mToolBar, "选择日期");
        mViewCalender
            .state()
            .edit()
            .setFirstDayOfWeek(Calendar.WEDNESDAY)
            .setMinimumDate(CalendarDay.from(2013, 5, 20))
            .setMaximumDate(CalendarDay.from(DateUtil.getCurrentYear(),
                                             DateUtil.getCurrentMonth(),
                                             DateUtil.getCurrentDay()))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit();
        mViewCalender.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget,
                                       @NonNull CalendarDay date,
                                       boolean selected) {
                mDate = date;

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calender;
    }


    @OnClick(R.id.tv_calender_enter)
    public void onViewClicked() {
        if (mDate != null){
            RxBus.getDefault().post(mDate);
        }
        finish();
    }
}
