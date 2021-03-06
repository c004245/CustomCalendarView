package com.playgilround.schedule.client.calendarschedule.listener;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.playgilround.schedule.client.calendarschedule.R;
import com.playgilround.schedule.client.calendarschedule.adapter.CalendarPageAdapter;
import com.playgilround.schedule.client.calendarschedule.util.CalendarProperties;
import com.playgilround.schedule.client.calendarschedule.util.CalendarUtils;
import com.playgilround.schedule.client.calendarschedule.util.DateUtils;
import com.playgilround.schedule.client.calendarschedule.util.DayColorsUtils;
import com.playgilround.schedule.client.calendarschedule.util.EventDay;
import com.playgilround.schedule.client.calendarschedule.util.SelectedDay;
import com.playgilround.schedule.client.calendarschedule.view.CalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Stream;

import static com.annimon.stream.Stream.*;

/**
 * 19-04-27
 * 날짜 클릭 리스너
 */
public class DayRowClickListener implements AdapterView.OnItemClickListener {

    private CalendarPageAdapter mCalendarPageAdapter;

    private CalendarProperties mCalendarProperties;
    private int mPageMonth;

    private static final String TAG =  DayRowClickListener.class.getSimpleName();

    public DayRowClickListener(CalendarPageAdapter calendarPageAdapter, CalendarProperties calendarProperties, int pageMonth) {
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
        Calendar day = new GregorianCalendar();
        day.setTime((Date) adapterView.getItemAtPosition(position));

        if (mCalendarProperties.getOnDayClickListener() != null) {
            Log.d(TAG, "onClick DayRowClickListener.");
            onClick(day);
        }
        Log.d(TAG, "mCalednarProperties type --->" + mCalendarProperties.getCalendarType());

        switch (mCalendarProperties.getCalendarType()) {
            case CalendarView.RANGE_PICKER:
                //기본을 RANGE_PICKER.
                selectRange(v, day);
                break;
        }
    }

    private void selectRange(View v, Calendar day) {
        TextView tvDay = v.findViewById(R.id.dayLabel);

        if (!isCurrentMonthDay(day)) {
            return;
        }

        List<SelectedDay> selectedDays = mCalendarPageAdapter.getSelectedDays();

        if (selectedDays.size() > 1) {
            clearAndSelectOne(tvDay, day);
        }

        if (selectedDays.size() == 1) {
            selectOneAndRange(tvDay, day);
        }

        if (selectedDays.isEmpty()) {
            selectDay(tvDay, day);
        }
    }

    private void clearAndSelectOne(TextView tvDay, Calendar day) {
        of(mCalendarPageAdapter.getSelectedDays()).forEach(this::reverseUnselectedColor);
        selectDay(tvDay, day);
    }

    private void selectOneAndRange(TextView tvDay, Calendar day) {
        SelectedDay previousSelectedDay = mCalendarPageAdapter.getSelectedDay();

        of(CalendarUtils.getDatesRange(previousSelectedDay.getCalendar(), day))
                .forEach(calendar -> mCalendarPageAdapter.addSelectedDay(new SelectedDay(calendar)));

        DayColorsUtils.setSelectedDayColors(tvDay, mCalendarProperties, 4);

        mCalendarPageAdapter.addSelectedDay(new SelectedDay(tvDay, day));
        mCalendarPageAdapter.notifyDataSetChanged();;
    }

    private void reverseUnselectedColor(SelectedDay selectedDay) {
        DayColorsUtils.setCurrentMonthDayColor(selectedDay.getCalendar(),
                DateUtils.getCalendar(), (TextView) selectedDay.getView(), mCalendarProperties);
    }

    private void selectDay(TextView tvDay, Calendar day) {
        DayColorsUtils.setSelectedDayColors(tvDay, mCalendarProperties, 3);
        mCalendarPageAdapter.setSelectedDay(new SelectedDay(tvDay, day));

    }

    private void onClick(Calendar day) {
        if (mCalendarProperties.getEventDays() == null) {
            createEmptyEventDay(day);
            return;
        }

        of(mCalendarProperties.getEventDays())
                .filter(eventDate -> eventDate.getCalendar().equals(day))
                .findFirst()
                .ifPresentOrElse(this::callOnClickListener, () ->createEmptyEventDay(day));
    }

    private void createEmptyEventDay(Calendar day) {
        EventDay eventDay = new EventDay(day);
        callOnClickListener(eventDay);
    }

    private void callOnClickListener(EventDay eventDay) {
        mCalendarProperties.getOnDayClickListener().onDayClick(eventDay);
    }

    private boolean isCurrentMonthDay(Calendar day) {
        return day.get(Calendar.MONTH) == mPageMonth && isBetweenMinAndMax(day);
    }

    private boolean isBetweenMinAndMax(Calendar day) {
        return !((mCalendarProperties.getMinimumDate() != null &&
                    day.before(mCalendarProperties.getMinimumDate()))
                || (mCalendarProperties.getMaximumDate() != null && day.after(mCalendarProperties.getMaximumDate())));
    }
}
