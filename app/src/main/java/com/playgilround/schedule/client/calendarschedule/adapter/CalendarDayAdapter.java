package com.playgilround.schedule.client.calendarschedule.adapter;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.playgilround.schedule.client.calendarschedule.R;
import com.playgilround.schedule.client.calendarschedule.util.CalendarProperties;
import com.playgilround.schedule.client.calendarschedule.util.DateUtils;
import com.playgilround.schedule.client.calendarschedule.util.DayColorsUtils;
import com.playgilround.schedule.client.calendarschedule.util.SelectedDay;
import com.playgilround.schedule.client.calendarschedule.view.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 캘린더 하루 칸 로딩 Adapter
 */
public class CalendarDayAdapter extends ArrayAdapter<Date> {

    private CalendarPageAdapter mCalendarPageAdapter;
    private LayoutInflater mLayoutInflater;
    private int mPageMonth;
    private int mPosition;
    private Calendar mToday = DateUtils.getCalendar();

    private CalendarProperties mCalendarProperties;

    private static final String TAG = CalendarDayAdapter.class.getSimpleName();


    CalendarDayAdapter(CalendarPageAdapter calendarPageAdapter, Context context, CalendarProperties calendarProperties,
                       ArrayList<Date> dates, int pageMonth, int position) {
        super(context, calendarProperties.getItemLayoutResource(), dates);
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
        mPosition = position;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = mLayoutInflater.inflate(mCalendarProperties.getItemLayoutResource(), parent, false);
        }

        TextView tvDay = view.findViewById(R.id.dayLabel);
        ImageView ivDay = view.findViewById(R.id.dayIcon);

        Calendar day = new GregorianCalendar();
        day.setTime(getItem(position));

        //Loading an Image of the event.
        if (ivDay != null) {
            loadIcon(ivDay, day);
        }

        setLabelColors(tvDay, day);

        tvDay.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));
        return view;
    }

    private void setLabelColors(TextView tvLabel, Calendar day) {
        //Setting not current month day color
        if (!isCurrentMonthDay(day)) {
            DayColorsUtils.setDayColors(tvLabel, mCalendarProperties.getAnotherMonthsDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
            return;
        }

        // Set view for all SelectedDays
        if (isSelectedDay(day)) {
            Stream.of(mCalendarPageAdapter.getSelectedDays())
                    .filter(selectedDay -> selectedDay.getCalendar().equals(day))
                    .findFirst().ifPresent(selectedDay -> selectedDay.setView(tvLabel));

            if (day.equals(mCalendarPageAdapter.getFirstSelectedDay()) || day.equals(mCalendarPageAdapter.getLastSelectedDay())){
                DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, true);
            } else {
                DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, false);
            }
            return;
        }
            DayColorsUtils.setCurrentMonthDayColor(day, mToday, tvLabel, mCalendarProperties);


    }

    private boolean isSelectedDay(Calendar day) {
        return mCalendarProperties.getCalendarType() != CalendarView.CLASSIC && day.get(Calendar.MONTH) == mPageMonth
                && mCalendarPageAdapter.getSelectedDays().contains(new SelectedDay(day));
    }

    private boolean isCurrentMonthDay(Calendar day) {
        return day.get(Calendar.MONTH) == mPageMonth;
    }

    private void loadIcon(ImageView ivDay, Calendar day) {

    }
}
