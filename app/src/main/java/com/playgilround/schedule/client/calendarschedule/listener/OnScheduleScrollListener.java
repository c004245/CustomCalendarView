package com.playgilround.schedule.client.calendarschedule.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.playgilround.schedule.client.calendarschedule.view.CalendarView;

/**
 * 달력, 스케줄 부분 스크롤 리스너.
 */
public class OnScheduleScrollListener extends GestureDetector.SimpleOnGestureListener {

    private CalendarView mCalendarView;

    public OnScheduleScrollListener(CalendarView calendarView) {
        mCalendarView = calendarView;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        mCalendarView.onCalendarScroll(distanceY);
        return true;
    }
}
