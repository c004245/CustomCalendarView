package com.playgilround.schedule.client.calendarschedule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.playgilround.schedule.client.calendarschedule.R;
import com.playgilround.schedule.client.calendarschedule.adapter.CalendarPageAdapter;
import com.playgilround.schedule.client.calendarschedule.util.AppearanceUtils;
import com.playgilround.schedule.client.calendarschedule.util.CalendarProperties;
import com.playgilround.schedule.client.calendarschedule.util.DateUtils;

import java.util.Calendar;

public class CalendarView extends LinearLayout {

    public static final int CLASSIC = 0;
    public static final int ONE_DAY_PICKER = 1;
    public static final int MANY_DAYS_PICKER = 2;
    public static final int RANGE_PICKER = 3;

    private static final String TAG = CalendarView.class.getSimpleName();

    private Context mContext;

    private CalendarProperties mCalendarProperties;

    private TextView tvDate;
    private CalendarViewPager mViewPager;

    private int mCurrentPage;

    private CalendarPageAdapter mCalendarPageAdapter;

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
            Calendar calendar = mCalendarProperties.getFirstPageDate();
            //calendar.plusMonths(position);
            calendar.add(Calendar.MONTH, position);

            Log.d(TAG, "PlusDate ->" + position);
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
            mViewPager.setCurrentItem(position - 1);
            return true;
        }

        return false;
    }

}
