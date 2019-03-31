package com.playgilround.schedule.client.calendarschedule.util;

import android.content.Context;

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

    public CalendarProperties(Context context) {
        mContext = context;
    }
}
