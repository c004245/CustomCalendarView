package com.playgilround.schedule.client.calendarschedule;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.playgilround.schedule.client.calendarschedule.view.CalendarView;
import com.playgilround.schedule.client.calendarschedule.view.ExpandIconView;

public class MainActivity extends AppCompatActivity {


    private boolean expanded = false;

    static final String TAG = MainActivity.class.getSimpleName();

    private int mInitHeight = 0;

    //State
    public static final int STATE_EXPANDED = 0;
    public static final int STATE_COLLAPSED = 1;
    public static final int STATE_PROCESSING = 2;
    CalendarView calendarView;
    ViewPager mPageView;

    ScrollView scrollView;

    protected ExpandIconView expandIconView;

    private int mState = STATE_COLLAPSED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        scrollView = findViewById(R.id.scrollView);
        mPageView = findViewById(R.id.calendarViewPager);
        expandIconView = findViewById(R.id.expandIcon);

        mInitHeight = scrollView.getMeasuredHeight();

        ExpandIconView expandIconView = findViewById(R.id.expandIcon);

        expandIconView.setState(ExpandIconView.LESS, true);

        expandIconView.setOnClickListener(l -> {
            Log.d(TAG, "mState ---> " + mState);
            if (expanded) {
                Log.d(TAG, "Collapse Try this.");
                collapse(400);
                expanded = false;

            } else {
                Log.d(TAG, "Expand Try this.");
                expand(400);
                expanded = true;
            }
        });
    }

    public void collapse(int duration) {
        if (getState() == STATE_EXPANDED) {
            setState(STATE_PROCESSING);

            final int currentHeight = 588;
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
//            }
            };
            anim.setDuration(duration);
            scrollView.startAnimation(anim);
        }
        expandIconView.setState(ExpandIconView.MORE, true);
    }

    public void expand(int duration) {
        if (getState() == STATE_COLLAPSED) {
            setState(STATE_PROCESSING);

            final int currentHeight = 166;
            final int targetHeight = 588;

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

        if (mState == STATE_EXPANDED) {
            expanded = false;
        }
        if (mState == STATE_COLLAPSED) {
            expanded = true;
        }
    }

}



