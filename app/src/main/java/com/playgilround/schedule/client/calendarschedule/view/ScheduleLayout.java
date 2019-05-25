package com.playgilround.schedule.client.calendarschedule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.playgilround.schedule.client.calendarschedule.R;
import com.playgilround.schedule.client.calendarschedule.listener.OnScheduleScrollListener;
import com.playgilround.schedule.client.calendarschedule.util.ScheduleState;

public class ScheduleLayout extends LinearLayout {

    private CalendarView calendarView;
    private RelativeLayout rlScheduleList;
    private ScheduleRecyclerView rvScheduleList;

    private GestureDetector mGestureDetector;
    private float mDownPosition[] = new float[2];
    private boolean mIsScrolling = false;

    private int mMinDistance;
    private int mAutoScrollDistance;
    private int mRowSize;
    private ScheduleState mState;

    public ScheduleLayout(Context context) {
        this(context, null);
    }

    public ScheduleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.ScheduleLayout));
        initGestureDetector();
    }

    private void initAttrs(TypedArray array) {
        mState = ScheduleState.OPEN;
        mMinDistance = getResources().getDimensionPixelSize(R.dimen.calendar_min_distance);
        mAutoScrollDistance = getResources().getDimensionPixelSize(R.dimen.auto_scroll_distance);
        mRowSize = getResources().getDimensionPixelSize(R.dimen.week_calendar_height);

    }

    private void initGestureDetector() {
        Log.d(TAG, "onScheduleListener");
        mGestureDetector = new GestureDetector(getContext(), new OnScheduleScrollListener(this));
    }


    static final String TAG = ScheduleLayout.class.getSimpleName();

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        calendarView = findViewById(R.id.calendarView);
        rlScheduleList = findViewById(R.id.rlScheduleList);
        rvScheduleList = findViewById(R.id.rvScheduleList);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onDown...");
                return true;

            case MotionEvent.ACTION_MOVE:
                transferEvent(e);
                Log.d(TAG, "onMove..");
                mIsScrolling = true;
                return true;

            case MotionEvent.ACTION_CANCEL:
                transferEvent(e);
                resetScrollingState();
                return true;
        }
        return super.onTouchEvent(e);
    }

    private void transferEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
    }

    private void resetScrollingState() {
        mDownPosition[0] = 0;
        mDownPosition[1] = 0;
        mIsScrolling = false;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownPosition[0] = ev.getRawX();
                mDownPosition[1] = ev.getRawY();

                Log.d(TAG, "dispatch -->" + ev.getRawX() + "--" + ev.getRawY());
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIsScrolling) {
            return true;
        }
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent");
                float x = ev.getRawX();
                float y = ev.getRawY();

                float distanceX = Math.abs(x - mDownPosition[0]);
                float distanceY = Math.abs(y - mDownPosition[1]);
                if (distanceY > mMinDistance && distanceY > distanceX * 2.0f) {
                    return (y > mDownPosition[1] && isRecyclerViewTouch()) || (y < mDownPosition[1] && mState == ScheduleState.OPEN);
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private boolean isRecyclerViewTouch() {
        return mState == ScheduleState.CLOSE && (rvScheduleList.getChildCount() == 0 ||
            rvScheduleList.isScrollTop());
    }


    public void onCalendarScroll(float distanceY) {
        Log.d(TAG, "onCalendarScroll...");
        distanceY = Math.min(distanceY, mAutoScrollDistance);

        float calendarDistanceY = distanceY / (5.0f);
        Log.d(TAG, "calendarDistanceY -->" + calendarDistanceY);

        int row = 5;
        int calendarTop = -row * mRowSize;

        int scheduleTop = mRowSize;

        float calendarY = calendarView.getY() - calendarDistanceY * row;
        calendarY = Math.min(calendarY, 0);
        calendarY = Math.max(calendarY, calendarTop);

        calendarView.setY(calendarY);

        float scheduleY = rlScheduleList.getY() - distanceY;

        scheduleY = Math.min(scheduleY, calendarView.getHeight() - mRowSize);

        scheduleY = Math.max(scheduleY, scheduleTop);
        rlScheduleList.setY(scheduleY);
    }
}
