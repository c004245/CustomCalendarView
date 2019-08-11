/*
package com.playgilround.schedule.client.calendarschedule.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager

class CalendarViewPager: ViewPager {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        var height = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

            val h = child.measuredHeight

            if (h > height) {
                height = h
            }
        }

        if (height != 0) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        }
        Log.d("TEST", "Pager -> $widthMeasureSpec -- $heightMeasureSpec")

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}*/
