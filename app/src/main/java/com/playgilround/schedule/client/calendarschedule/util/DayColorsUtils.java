package com.playgilround.schedule.client.calendarschedule.util;

import android.graphics.PorterDuff;
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

    public static void setSelectedDayColors(TextView dayLabel, CalendarProperties calendarProperties) {
        dayLabel.setTypeface(null, Typeface.NORMAL);
        dayLabel.setTextColor(calendarProperties.getSelectionLabelColor());
        dayLabel.setBackgroundResource(R.drawable.background_color_circle_selector);
//        setDayColors(dayLabel, calendarProperties.getSelectionLabelColor(), Typeface.NORMAL,
//                R.drawable.background_color_circle_selector);

        dayLabel.getBackground().setColorFilter(calendarProperties.getSelectionColor(),
                PorterDuff.Mode.MULTIPLY);
    }
    public static void setCurrentMonthDayColor(Calendar day, Calendar today, TextView tvLabel,
                                               CalendarProperties calendarProperties) {
        if (today.equals(day)) {
            DayColorsUtils.setSelectedDayColors(tvLabel, calendarProperties);
        } else {
            setDayColors(tvLabel, calendarProperties.getDaysLabelsColor(), Typeface.NORMAL,
                    R.drawable.background_transparent);
        }
    }
}
