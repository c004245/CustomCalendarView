package com.playgilround.schedule.client.calendarschedule.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.playgilround.schedule.client.calendarschedule.R;
import com.playgilround.schedule.client.calendarschedule.adapter.CalendarPageAdapter;
import com.playgilround.schedule.client.calendarschedule.util.CalendarProperties;

import org.joda.time.DateTime;

import java.util.Calendar;

public class CalendarView extends LinearLayout {

    private Context mContext;

    private CalendarProperties mCalendarProperties;

    private TextView tvDate;
    private CalendarViewPager mViewPager;


    private CalendarPageAdapter mCalendarPageAdapter;

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
        initCalendar();
    }

    private void initControl(Context context, AttributeSet attrs) {
        mContext = context;
        mCalendarProperties = new CalendarProperties(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_view, this);

        initUIElements();
    }

    private void initUIElements() {
        tvDate = findViewById(R.id.tvDate);
        mViewPager = findViewById(R.id.calendarViewPager);
    }

    private void initCalendar() {
        mCalendarPageAdapter = new CalendarPageAdapter(mContext, mCalendarProperties);

        mViewPager.setAdapter(mCalendarPageAdapter);
        mViewPager.addOnPageChangeListener(onPageChangeListener);

    }

    //ViewPager Listener
    private final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            DateTime calendar =  mCalendarProperties.getFirstPageDate();
            calendar.plusMonths(i);


        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
