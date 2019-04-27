package com.playgilround.schedule.client.calendarschedule.listener;

import android.view.View;
import android.widget.AdapterView;

import com.playgilround.schedule.client.calendarschedule.adapter.CalendarPageAdapter;
import com.playgilround.schedule.client.calendarschedule.util.CalendarProperties;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 19-04-27
 * 날짜 클릭 리스너
 */
public class DayRowClickListener implements AdapterView.OnItemClickListener {

    private CalendarPageAdapter mCalendarPageAdapter;

    private CalendarProperties mCalendarProperties;
    private int mPageMonth;

    public DayRowClickListener(CalendarPageAdapter calendarPageAdapter, CalendarProperties calendarProperties, int pageMonth) {
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
        Calendar day = new GregorianCalendar();
        day.setTime((Date) adapterView.getItemAtPosition(position));

//        if (mCalendarProperties.geton)
    }
}
