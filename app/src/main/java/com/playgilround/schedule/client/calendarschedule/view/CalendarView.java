package com.playgilround.schedule.client.calendarschedule.view;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.playgilround.schedule.client.calendarschedule.R;
import com.playgilround.schedule.client.calendarschedule.adapter.CalendarPageAdapter;
import com.playgilround.schedule.client.calendarschedule.listener.OnScheduleScrollListener;
import com.playgilround.schedule.client.calendarschedule.util.AppearanceUtils;
import com.playgilround.schedule.client.calendarschedule.util.CalendarProperties;
import com.playgilround.schedule.client.calendarschedule.util.DateUtils;
import com.playgilround.schedule.client.calendarschedule.util.ScheduleState;
import com.playgilround.schedule.client.calendarschedule.util.SelectedDay;

import java.util.Calendar;
import java.util.List;

import static com.playgilround.schedule.client.calendarschedule.util.CalendarProperties.FIRST_VISIBLE_PAGE;

public class CalendarView extends FrameLayout {

    public static final int CLASSIC = 0;
    public static final int ONE_DAY_PICKER = 1;
    public static final int MANY_DAYS_PICKER = 2;
    public static final int RANGE_PICKER = 3;

    private static final String TAG = CalendarView.class.getSimpleName();

    private Context mContext;

    private CalendarProperties mCalendarProperties;

    private TextView tvDate;
    private CalendarViewPager mViewPager;
    private RelativeLayout rlScheduleList;
    private ScheduleRecyclerView rvScheduleList;
    private CalendarView calendarView;

    private GestureDetector mGestureDetector;

    private int mCurrentPage;

    private CalendarPageAdapter mCalendarPageAdapter;

    private float mDownPosition[] = new float[2];
    private boolean mIsScrolling = false;
    private boolean mCurrentRowsIsSix = true;
    private int mAutoScrollDistance;


    private int mMinDistance;
    private int mRowSize;


    private ScheduleState mState;


    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
        initCalendar();
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
        setAttributes(attrs);
    }

    private void setAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            initCalendarProperties(typedArray);
            initAttributes();
        } finally {
            typedArray.recycle();
        }
    }

    private void initUIElements() {
        tvDate = findViewById(R.id.tvDate);
        mViewPager = findViewById(R.id.calendarViewPager);
        rvScheduleList = findViewById(R.id.rvScheduleList);
        rlScheduleList = findViewById(R.id.rlScheduleList);
        calendarView = findViewById(R.id.calendarView);

        initGestureDetector();
    }

    private void initGestureDetector() {
        mGestureDetector = new GestureDetector(getContext(), new OnScheduleScrollListener(this));
    }

    public void onCalendarScroll(float distanceY) {
        Log.d(TAG, "onScroll...." + mAutoScrollDistance);
        distanceY = Math.min(distanceY, mAutoScrollDistance);

        float calendarDistanceY = distanceY / (mCurrentRowsIsSix ? 5.0f : 4.0f);
        int row = 1;

        int calendarTop = -row * mRowSize;
        int scheduleTop = mRowSize;

        float calendarY = calendarView.getY() - calendarDistanceY * row;

        calendarY = Math.min(calendarY, 0);
        calendarY = Math.max(calendarY, calendarTop);

        calendarView.setY(calendarY);

        float scheduleY = rlScheduleList.getY() - distanceY;

        if (mCurrentRowsIsSix) {
            scheduleY = Math.min(scheduleY, calendarView.getHeight());
        } else {
            scheduleY = Math.min(scheduleY, calendarView.getHeight() - mRowSize);
        }

        scheduleY = Math.max(scheduleY, scheduleTop);
        rlScheduleList.setY(scheduleY);
    }

    //Calendar Properties Setting.
    private void initCalendarProperties(TypedArray typedArray) {
        int headerColor = typedArray.getColor(R.styleable.CalendarView_headerColor, 0);
        mCalendarProperties.setHeaderColor(headerColor);

        int headerLabelColor = typedArray.getColor(R.styleable.CalendarView_headerLabelColor, 0);
        mCalendarProperties.setHeaderLabelColor(headerLabelColor);

        int abbreviationsBarColor = typedArray.getColor(R.styleable.CalendarView_abbreviationsBarColor, 0);
        mCalendarProperties.setAbbreviationsBarColor(abbreviationsBarColor);

        int abbreviationsLabelsColor = typedArray.getColor(R.styleable.CalendarView_abbreviationsLabelsColor, 0);
        mCalendarProperties.setAbbreviationsLabelsColor(abbreviationsLabelsColor);

        int pagesColor = typedArray.getColor(R.styleable.CalendarView_pagesColor, 0);
        mCalendarProperties.setPagesColor(pagesColor);

        int daysLabelsColor = typedArray.getColor(R.styleable.CalendarView_daysLabelsColor, 0);
        mCalendarProperties.setDaysLabelsColor(daysLabelsColor);

        int anotherMonthsDaysLabelsColor = typedArray.getColor(R.styleable.CalendarView_anotherMonthsDaysLabelsColor, 0);
        mCalendarProperties.setAnotherMonthsDaysLabelsColor(anotherMonthsDaysLabelsColor);

        int todayLabelColor = typedArray.getColor(R.styleable.CalendarView_todayLabelColor, 0);
        mCalendarProperties.setTodayLabelColor(todayLabelColor);

        int selectionColor = typedArray.getColor(R.styleable.CalendarView_selectionColor, 0);
        mCalendarProperties.setSelectionColor(selectionColor);

        int selectionLabelColor = typedArray.getColor(R.styleable.CalendarView_selectionLabelColor, 0);
        mCalendarProperties.setSelectionLabelColor(selectionLabelColor);

        int disabledDaysLabelsColor = typedArray.getColor(R.styleable.CalendarView_disabledDaysLabelsColor, 0);
        mCalendarProperties.setDisabledDaysLabelsColor(disabledDaysLabelsColor);

        int calendarType = typedArray.getInt(R.styleable.CalendarView_type, CLASSIC);
        mCalendarProperties.setCalendarType(calendarType);

        if (typedArray.getBoolean(R.styleable.CalendarView_datePicker, false)) {
            mCalendarProperties.setCalendarType(ONE_DAY_PICKER);
        }

        boolean eventsEnabled = typedArray.getBoolean(R.styleable.CalendarView_eventsEnabled,
                mCalendarProperties.getCalendarType() == CLASSIC);
        mCalendarProperties.setEventsEnabled(eventsEnabled);
    }

    private void initAttributes() {
        AppearanceUtils.setHeaderColor(getRootView(), mCalendarProperties.getHeaderColor());

        AppearanceUtils.setHeaderVisibility(getRootView(), mCalendarProperties.getHeaderVisibility());

        AppearanceUtils.setHeaderLabelColor(getRootView(), mCalendarProperties.getHeaderLabelColor());

        AppearanceUtils.setAbbreviationsBarColor(getRootView(), mCalendarProperties.getAbbreviationsBarColor());

        AppearanceUtils.setAbbreviationsLabels(getRootView(), mCalendarProperties.getAbbreviationsLabelsColor(),
                mCalendarProperties.getFirstPageDate().getFirstDayOfWeek());

        AppearanceUtils.setPagesColor(getRootView(), mCalendarProperties.getPagesColor());

        mMinDistance = getResources().getDimensionPixelSize(R.dimen.calendar_min_distance);
        mRowSize = getResources().getDimensionPixelSize(R.dimen.week_calendar_height);
        mAutoScrollDistance = getResources().getDimensionPixelSize(R.dimen.auto_scroll_distance);

        setCalendarRowLayout();
    }

    private void setCalendarRowLayout() {
        mCalendarProperties.setItemLayoutResource(R.layout.calendar_view_day);
    }

    private void initCalendar() {
        mCalendarPageAdapter = new CalendarPageAdapter(mContext, mCalendarProperties);

        mViewPager.setAdapter(mCalendarPageAdapter);
        mViewPager.addOnPageChangeListener(onPageChangeListener);

        setUpCalendarPosition(Calendar.getInstance());
    }

    private void setUpCalendarPosition(Calendar calendar) {
        DateUtils.setMidnight(calendar);

        mCalendarProperties.getFirstPageDate().setTime(calendar.getTime());
        mCalendarProperties.getFirstPageDate().add(Calendar.MONTH, -FIRST_VISIBLE_PAGE);

        mViewPager.setCurrentItem(FIRST_VISIBLE_PAGE);
    }

    //set xml values for calendar elements

    //ViewPager Listener
    private final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            mCalendarProperties.setCalendarPosition(position);

            Calendar calendar = (Calendar) mCalendarProperties.getFirstPageDate().clone();
            calendar.add(Calendar.MONTH, position);

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
        callOnPageChangeListeners(position);
    }

    private void callOnPageChangeListeners(int position) {
        mCurrentPage = position;
    }


    //스크롤 최대 판단
    private boolean isScrollingLimited(Calendar calendar, int position) {
        if (DateUtils.isMonthBefore(mCalendarProperties.getMinimumDate(), calendar)) {
            mViewPager.setCurrentItem(position + 1);
            return true;
        }

        if (DateUtils.isMonthAfter(mCalendarProperties.getMaximumDate(), calendar)) {
            mViewPager.setCurrentItem(position - 1);
            return true;
        }

        return false;
    }

    //List Of Calendar Object representing a selected dates
    public List<Calendar> getSelectedDates() {
        return Stream.of(mCalendarPageAdapter.getSelectedDays())
                .map(SelectedDay::getCalendar)
                .sortBy(calendar -> calendar).toList();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "Action Down...");

                mDownPosition[0] = ev.getRawX();
                mDownPosition[1] = ev.getRawY();
                mGestureDetector.onTouchEvent(ev);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "Action Down...");
                mDownPosition[0] = event.getRawX();
                mDownPosition[1] = event.getRawY();
                return true;

            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Action Move...");
                transferEvent(event);
                mIsScrolling = true;
                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Action Up...");
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "Action Cancel...");
                return true;
        }

        return super.onTouchEvent(event);
    }

    private void transferEvent(MotionEvent event) {
        if (mState == ScheduleState.CLOSE) {
            mGestureDetector.onTouchEvent(event);
        } else {
            mGestureDetector.onTouchEvent(event);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIsScrolling) {
            return true;
        }

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
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
        return mState == ScheduleState.CLOSE && (rvScheduleList.getChildCount() == 0
            || rvScheduleList.isScrollTop());
    }
}
