package com.playgilround.schedule.client.calendarschedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.playgilround.schedule.client.calendarschedule.R.id.*
import com.playgilround.schedule.client.calendarschedule.adapter.ScheduleAdapter
import com.playgilround.schedule.client.calendarschedule.view.ExpandIconView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.calendar_view.*
import kotlinx.android.synthetic.main.schedule_view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var expanded : Boolean = true

    var mInitHeight : Int = 0
    var mState = STATE_EXPANDED

    private lateinit var mScheduleAdapter: ScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivAddSchedule.setOnClickListener {
            val iterator = (calendarView.selectedDates).iterator()
            iterator.forEach {
                Log.d(TAG, "calendar time ->" +it.time.toString())
            }
        }
        initScheduleList()
    }

    @SuppressLint("WrongConstant")
    private fun initScheduleList() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL

        rvScheduleList!!.layoutManager = manager

        val itemAnimation = DefaultItemAnimator()
        itemAnimation.supportsChangeAnimations = false
        rvScheduleList!!.itemAnimator = itemAnimation

        mScheduleAdapter = ScheduleAdapter(this)
        rvScheduleList!!.adapter = mScheduleAdapter
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        mInitHeight = calendarView.height
    }

   /* //캘린더 축소
    fun reduction(duration: Int) {
        if (mState == STATE_EXPANDED) {
            val currentHeight = mInitHeight
            val targetHeight = 166
            val topHeight = 166

            val anim = object:  Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    scrollView.layoutParams.height = if (interpolatedTime == 1f)
                        targetHeight
                    else
                        currentHeight - ((currentHeight - targetHeight) * interpolatedTime).toInt()
                    scrollView.requestLayout()

                    if (scrollView.measuredHeight < topHeight + targetHeight) {
                        val position = topHeight + targetHeight - scrollView.measuredHeight
                        scrollView.smoothScrollBy(0, position)
                    }

                    if (interpolatedTime == 1f)
                       mState = STATE_COLLAPSED
                }
            }
            anim.duration = duration.toLong()
            scrollView.startAnimation(anim)
        }

        expandIcon.setState(ExpandIconView.MORE, true)
    }

    //캘린더 확장
    fun expansion(duration: Int) {
        if (mState == STATE_COLLAPSED) {
            val currentHeight = 166
            val targetHeight = mInitHeight

            val anim = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    scrollView.layoutParams.height = if (interpolatedTime == 1f)
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    else
                        currentHeight - ((currentHeight - targetHeight) * interpolatedTime).toInt()
                    scrollView.requestLayout()

                    if (interpolatedTime == 1f)
                        mState = STATE_EXPANDED
                }
            }

            anim.duration = duration.toLong()
            scrollView.startAnimation(anim)
        }

        expandIcon.setState(ExpandIconView.LESS, true)
    }*/
    companion object {
        val STATE_EXPANDED = 0
        val STATE_COLLAPSED = 1

        val TAG = MainActivity.javaClass.simpleName;
    }

}