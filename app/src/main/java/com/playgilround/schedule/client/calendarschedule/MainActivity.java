package com.playgilround.schedule.client.calendarschedule;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.playgilround.schedule.client.calendarschedule.adapter.ScheduleAdapter;
import com.playgilround.schedule.client.calendarschedule.view.CalendarView;
import com.playgilround.schedule.client.calendarschedule.view.ExpandIconView;
import com.playgilround.schedule.client.calendarschedule.view.ScheduleRecyclerView;

public class MainActivity extends AppCompatActivity {


    private boolean expanded = true;

    static final String TAG = MainActivity.class.getSimpleName();

    private int mInitHeight = 0;

    //State
    public static final int STATE_EXPANDED = 0;
    public static final int STATE_COLLAPSED = 1;
    CalendarView calendarView;
    ViewPager mPageView;

    ScrollView scrollView;

    protected ExpandIconView expandIconView;
    private ScheduleRecyclerView rvScheduleList;


    private int mState = STATE_EXPANDED;

    private ScheduleAdapter mScheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        scrollView = findViewById(R.id.scrollView);
        mPageView = findViewById(R.id.calendarViewPager);
        expandIconView = findViewById(R.id.expandIcon);
        rvScheduleList = findViewById(R.id.rvScheduleList);

        ExpandIconView expandIconView = findViewById(R.id.expandIcon);

        expandIconView.setState(ExpandIconView.LESS, true);

        expandIconView.setOnClickListener(l -> {
            if (expanded) {
                reduction(1000);
                expanded = false;

            } else {
                expansion(1000);
                expanded = true;
            }
        });

        initScheduleList();
    }

    @SuppressLint("WrongConstant")
    private void initScheduleList() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rvScheduleList.setLayoutManager(manager);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);
        rvScheduleList.setItemAnimator(itemAnimator);

        mScheduleAdapter = new ScheduleAdapter(this);
        rvScheduleList.setAdapter(mScheduleAdapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        mInitHeight = calendarView.getHeight();
    }

    //캘린더 축소
    public void reduction(int duration) {
        if (getState() == STATE_EXPANDED) {
            final int currentHeight = mInitHeight;
            final int targetHeight = 166;
            final int topHeight = 166;

            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    scrollView.getLayoutParams().height = (interpolatedTime == 1)
                            ? targetHeight
                            : currentHeight - (int) ((currentHeight - targetHeight) * interpolatedTime);
                    scrollView.requestLayout();

                    if (scrollView.getMeasuredHeight() < topHeight + targetHeight) {
                        int position = topHeight + targetHeight - scrollView.getMeasuredHeight();
                        scrollView.smoothScrollTo(0, position);
                    }

                    if (interpolatedTime == 1) {
                        setState(STATE_COLLAPSED);
                    }
                }
            };
            anim.setDuration(duration);
            scrollView.startAnimation(anim);
        }
        expandIconView.setState(ExpandIconView.MORE, true);
    }

    //캘린더 확장
    public void expansion(int duration) {
        if (getState() == STATE_COLLAPSED) {

            final int currentHeight = 166;
            final int targetHeight = mInitHeight;

            Animation anim = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    scrollView.getLayoutParams().height = (interpolatedTime == 1)
                            ? LinearLayout.LayoutParams.WRAP_CONTENT
                            : currentHeight - (int) ((currentHeight - targetHeight) * interpolatedTime);
                    scrollView.requestLayout();

                    if (interpolatedTime == 1) {
                        setState(STATE_EXPANDED);
                    }
                }
            };
            anim.setDuration(duration);
            scrollView.startAnimation(anim);

        }
        expandIconView.setState(ExpandIconView.LESS, true);
    }


    public int getState() {
        return mState;
    }


    public void setState(int state) {
        this.mState = state;
    }

}



