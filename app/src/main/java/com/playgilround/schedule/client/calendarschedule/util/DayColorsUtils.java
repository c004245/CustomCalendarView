package com.playgilround.schedule.client.calendarschedule.util;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.playgilround.schedule.client.calendarschedule.R;

import java.util.Calendar;

/**
 * 날짜 색깔 관련.
 */
public class DayColorsUtils {

    public static void setDayColors(TextView tv, int textColor, int typeface, int background) {
        if (tv == null) {
            return;
        }

        tv.setTypeface(null, typeface);
        tv.setTextColor(textColor);
        tv.setBackgroundResource(background);
    }

    public static void setHideDays(TextView tv, int textColor, int typeface, int background) {
        if (tv == null) {
            return;
        }

        tv.setTypeface(null, typeface);
        tv.setTextColor(textColor);
        tv.setBackgroundResource(background);
        tv.setVisibility(View.GONE);
    }


    public static void setCurrentMonthDayColor(Calendar day, Calendar today, TextView tvLabel,
                                               CalendarProperties calendarProperties) {
        if (today.equals(day)) {
            setDayColors(tvLabel, calendarProperties.getTodayLabelColor(), Typeface.BOLD,
                    R.drawable.background_transparent);
        } else {
            setDayColors(tvLabel, calendarProperties.getDaysLabelsColor(), Typeface.NORMAL,
                    R.drawable.background_transparent);
        }
    }
}
