package com.playgilround.schedule.client.calendarschedule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.playgilround.schedule.client.calendarschedule.listener.OnScheduleScrollListener;

public class ScheduleLayout extends LinearLayout {

    private CalendarView calendarView;
    private RelativeLayout rlScheduleList;
    private GestureDetector mGestureDetector;

    public ScheduleLayout(Context context) {
        this(context, null);
    }

    public ScheduleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGestureDetector();
    }

    private void initGestureDetector() {
        mGestureDetector = new GestureDetector(getContext(), new OnScheduleScrollListener(this));
    }


    static final String TAG = ScheduleLayout.class.getSimpleName();

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
//                transferEvent(e);
                Log.d(TAG, "onMove..");
                return true;
        }
        return super.onTouchEvent(e);
    }


    public void onCalendarScroll(float distanceY) {
        Log.d(TAG, "onCalendarScroll...");
    }
}
