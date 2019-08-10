package com.playgilround.schedule.client.calendarschedule.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.annimon.stream.Stream
import com.playgilround.schedule.client.calendarschedule.R
import com.playgilround.schedule.client.calendarschedule.util.CalendarProperties
import com.playgilround.schedule.client.calendarschedule.util.DateUtils
import com.playgilround.schedule.client.calendarschedule.util.DayColorsUtils
import com.playgilround.schedule.client.calendarschedule.util.SelectedDay
import com.playgilround.schedule.client.calendarschedule.view.CalendarView.CLASSIC
import java.util.*

class CalendarDayAdapter constructor(private val mCalendarPageAdapter: CalendarPageAdapter, private val mContext: Context, private val mCalendarProperties: CalendarProperties,
                                     private val dates: ArrayList<Date>, private val mPageMonth: Int, position: Int) : ArrayAdapter<Date>(mContext, mCalendarProperties.itemLayoutResource, dates) {

    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val mToday = DateUtils.getCalendar()

    override fun getView(position: Int, view: View, parent: ViewGroup): View {
        if (view == null) {
            val view = mLayoutInflater.inflate(mCalendarProperties.itemLayoutResource, parent, false)
        }

        val tvDay = view.findViewById(R.id.dayLabel) as TextView
        val ivDay = view.findViewById(R.id.dayIcon) as ImageView

        val day = GregorianCalendar()
        day.time = getItem(position)

        if (ivDay != null) loadIcon(ivDay, day)
        setLabelColors(tvDay, day)
        return view
    }

    fun loadIcon(ivDay: ImageView, day: Calendar) {

    }

    fun setLabelColors(tvLabel: TextView, day: Calendar) {
        if (!isCurrentMonthDay(day)) {
            DayColorsUtils.setDayColors(tvLabel, mCalendarProperties.anotherMonthsDaysLabelsColor,
                    Typeface.NORMAL, R.drawable.background_transparent)
        }

        if (isSelectedDay(day)) {
            Stream.of(mCalendarPageAdapter.getSelectedDays())
                    .filter { selectedDay -> selectedDay.calendar == day }
                    .findFirst().ifPresent { selectedDay -> selectedDay.view = tvLabel }

            if (day == mCalendarPageAdapter.getFirstSelectedDay()) DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 1)
            else if (day == mCalendarPageAdapter.getLastSelectedDay()) DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 2)
            else if (mCalendarPageAdapter.getFirstSelectedDay() == null || mCalendarPageAdapter.getLastSelectedDay() == null) DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 3)
            else DayColorsUtils.setSelectedDayColors(tvLabel, mCalendarProperties, 4)
        }
        DayColorsUtils.setCurrentMonthDayColor(day, mToday, tvLabel, mCalendarProperties)
    }

    fun isCurrentMonthDay(day: Calendar): Boolean {
        return day.get(Calendar.MONTH) == mPageMonth
    }

    fun isSelectedDay(day: Calendar): Boolean {
        return mCalendarProperties.calendarType != CLASSIC && day.get(Calendar.MONTH) == mPageMonth
                && mCalendarProperties.selectedDays.contains(SelectedDay(day))
    }


}