package com.playgilround.schedule.client.calendarschedule.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.playgilround.schedule.client.calendarschedule.util.CalendarProperties;

import static com.playgilround.schedule.client.calendarschedule.util.CalendarProperties.CALENDAR_SIZE;

/**
 * 달력 페이지 로드 Adapter
 */
public class CalendarPageAdapter extends PagerAdapter {

    private Context mContext;

    private CalendarProperties mCalendarProperties;

    public CalendarPageAdapter(Context context, CalendarProperties calendarProperties) {
        mContext = context;
        mCalendarProperties = calendarProperties;
    }

    @Override
    public int getCount() {
        return CALENDAR_SIZE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
