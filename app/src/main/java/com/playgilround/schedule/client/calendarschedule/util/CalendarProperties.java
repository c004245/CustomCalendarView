package com.playgilround.schedule.client.calendarschedule.util;

import android.content.Context;

import com.playgilround.schedule.client.calendarschedule.listener.OnCalendarPageChangeListener;

import java.util.Calendar;

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

    //private DateTime mMinimumDate, mMaximumDate;
    private Calendar mMinimumDate, mMaximumDate;
//    private DateTime mFirstPageCalendarDate = DateUtils.getDateTime();
    private Calendar mFirstPageCalendarDate = DateUtils.getCalendar();

    private int mItemLayoutResource;


    public CalendarProperties(Context context) {
        mContext = context;
    }

//    public DateTime getFirstPageDate() {
    public Calendar getFirstPageDate() {
        return mFirstPageCalendarDate;
    }

    public Calendar getMinimumDate() {
        return mMinimumDate;
    }

    public void setMinimumDate(Calendar minimumDate) {
        mMinimumDate = minimumDate;
    }

    public Calendar getMaximumDate() {
        return mMaximumDate;
    }

    public void setMaximumDate(Calendar maximumDate) {
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

    public int getItemLayoutResource() {
        return mItemLayoutResource;
    }
    public void setItemLayoutResource(int itemLayoutResource) {
        mItemLayoutResource = itemLayoutResource;
    }


}
