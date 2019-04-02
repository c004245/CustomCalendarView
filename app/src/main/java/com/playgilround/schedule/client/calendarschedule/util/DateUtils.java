package com.playgilround.schedule.client.calendarschedule.util;

import android.content.Context;

import com.playgilround.schedule.client.calendarschedule.R;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Locale;

public class DateUtils {

    public static DateTime getDateTime() {
        return new DateTime();
    }

    public static boolean isMonthBefore(DateTime firstDT, DateTime secondDT) {
        if (firstDT == null) {
            return false;
        }

        Calendar firstDay = firstDT.toCalendar(Locale.KOREA);
        firstDay.set(Calendar.DAY_OF_MONTH, 1);

        Calendar secondDay = secondDT.toCalendar(Locale.KOREA);
        secondDay.set(Calendar.DAY_OF_MONTH, 1);

        return secondDay.before(firstDay);
    }

    public static boolean isMonthAfter(DateTime firstDT, DateTime secondDT) {
        if (firstDT == null) {
            return false;
        }

        Calendar firstDay = firstDT.toCalendar(Locale.KOREA);
        firstDay.set(Calendar.DAY_OF_MONTH, 1);

        Calendar secondDay = secondDT.toCalendar(Locale.KOREA);
        secondDay.set(Calendar.DAY_OF_MONTH, 1);

        return secondDay.after(firstDay);
    }

    //월 이름, 연도 포함 문자 반환.
    public static String getMonthAndYearDate(Context context, DateTime dateTime) {
        return String.format("%s  %s", context.getResources().getStringArray(R.array.material_calendar_months_array)
                [dateTime.getMonthOfYear()], dateTime.getYear());
    }

}
