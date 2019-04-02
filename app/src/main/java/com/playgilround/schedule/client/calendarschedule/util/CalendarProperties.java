package com.playgilround.schedule.client.calendarschedule.util;

import android.content.Context;

import com.playgilround.schedule.client.calendarschedule.listener.OnCalendarPageChangeListener;

import org.joda.time.DateTime;

/**
 * 19-03-31
 * all properties of the calendar.
 */
public class CalendarProperties {

    /**
     * 이 캘린더는 현재 기준으로 과거, 미래 +-100년으로 표시됨
     */

    public static final int CALENDAR_SIZE = 2401;

    private Context mContext;


    private OnCalendarPageChangeListener mOnForwardPageChangeListener;
    private OnCalendarPageChangeListener mOnPreviousPageChangeListener;

    private DateTime mMinimumDate, mMaximumDate;
    private DateTime mFirstPageCalendarDate = DateUtils.getDateTime();


    public CalendarProperties(Context context) {
        mContext = context;
    }

    public DateTime getFirstPageDate() {
        return mFirstPageCalendarDate;
    }

    public DateTime getMinimumDate() {
        return mMinimumDate;
    }

    public void setMinimumDate(DateTime minimumDate) {
        mMinimumDate = minimumDate;
    }

    public DateTime getMaximumDate() {
        return mMaximumDate;
    }

    public void setMaximumDate(DateTime maximumDate) {
        mMaximumDate = maximumDate;
    }

    //다음 달
    public OnCalendarPageChangeListener getOnForwardPageChangeListener() {
        return mOnForwardPageChangeListener;
    }

    public void setOnForwardPageChangeListener(OnCalendarPageChangeListener onForwardPageChangeListener) {
        mOnForwardPageChangeListener = onForwardPageChangeListener;
    }

    //저번 달
    public OnCalendarPageChangeListener getOnPreviousPageChangeListener() {
        return mOnPreviousPageChangeListener;
    }

    public void setOnPreviousPageChangeListener(OnCalendarPageChangeListener onPreviousPageChangeListener) {
        mOnPreviousPageChangeListener = onPreviousPageChangeListener;
    }



}
