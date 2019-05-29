package com.playgilround.schedule.client.calendarschedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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

    private lateinit var fabOpen: Animation
    private lateinit var fabClose: Animation

    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabOpen = AnimationUtils.loadAnimation(applicationContext, R.anim.floating_open)
        fabClose = AnimationUtils.loadAnimation(applicationContext, R.anim.floating_close)

        floatingBtn.setOnClickListener {
            isFabOpen = if (isFabOpen) {
                floatingBtn.setImageResource(R.drawable.exit)
                floatingAdd.startAnimation(fabClose)
                floatingModify.startAnimation(fabClose)
                floatingDelete.startAnimation(fabClose)
                false
            } else {
                floatingBtn.setImageResource(R.drawable.add)
                floatingAdd.startAnimation(fabOpen)
                floatingModify.startAnimation(fabOpen)
                floatingDelete.startAnimation(fabOpen)
                true
            }
        }

       /* floatingBtn.setOnClickListener {
            floatingBtn.setImageResource(R.drawable.exit)
            if (calendarView.selectedDates.size == 0) {
                Toast.makeText(applicationContext, getString(R.string.toast_please_select_day), Toast.LENGTH_SHORT).show()
            } else {
                val iterator = (calendarView.selectedDates).iterator()
                iterator.forEach {
                    Log.d(TAG, "calendar time ->" + it.time.toString())
                }
            }
        }*/
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

    companion object {
        val STATE_EXPANDED = 0
        val STATE_COLLAPSED = 1

        val TAG = MainActivity.javaClass.simpleName;
    }

}