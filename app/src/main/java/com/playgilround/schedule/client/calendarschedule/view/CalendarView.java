package com.playgilround.schedule.client.calendarschedule.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.playgilround.schedule.client.calendarschedule.R;
import com.playgilround.schedule.client.calendarschedule.adapter.CalendarPageAdapter;
import com.playgilround.schedule.client.calendarschedule.util.CalendarProperties;
import com.playgilround.schedule.client.calendarschedule.util.DateUtils;

import java.util.Calendar;

public class CalendarView extends LinearLayout {

    private static final String TAG = CalendarView.class.getSimpleName();

    private Context mContext;

    private CalendarProperties mCalendarProperties;

    private TextView tvDate;
    private CalendarViewPager mViewPager;

    private int mCurrentPage;

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
        //setAttributes(attrs);
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

    //set xml values for calendar elements

    //ViewPager Listener
    private final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            Calendar calendar =  mCalendarProperties.getFirstPageDate();
            //calendar.plusMonths(position);
            calendar.add(Calendar.MONTH, position);

            Log.d(TAG, "PlusDate ->" +position);
            if (!isScrollingLimited(calendar, position)) {
                setHeaderName(calendar, position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private void setHeaderName(Calendar dateTime, int position) {
        tvDate.setText(DateUtils.getMonthAndYearDate(mContext, dateTime));
        Log.d(TAG, "Text Date --->" + DateUtils.getMonthAndYearDate(mContext, dateTime));
        callOnPageChangeListeners(position);
    }

    private void callOnPageChangeListeners(int position) {
        if (position > mCurrentPage && mCalendarProperties.getOnForwardPageChangeListener() != null) {
            mCalendarProperties.getOnForwardPageChangeListener().onChange();
        }

        if (position < mCurrentPage && mCalendarProperties.getOnPreviousPageChangeListener() != null) {
            mCalendarProperties.getOnPreviousPageChangeListener().onChange();
        }

        mCurrentPage = position;
    }


    //스크롤 최대 판단
    private boolean isScrollingLimited(Calendar calendar, int position) {
        if (DateUtils.isMonthBefore(mCalendarProperties.getMinimumDate(), calendar)) {
            mViewPager.setCurrentItem(position + 1);
            return true;
        }

        if (DateUtils.isMonthAfter(mCalendarProperties.getMaximumDate(), calendar)) {
            mViewPager.setCurrentItem(position -1);
            return true;
        }

        return false;
    }

}
