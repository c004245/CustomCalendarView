/*
package com.playgilround.schedule.client.calendarschedule.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.GridView
import java.util.jar.Attributes

class CalendarGridView: GridView {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
                View.MeasureSpec.AT_MOST)

        Log.d("TEST", "GridView -> $widthMeasureSpec -- $expandSpec")
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}
*/
