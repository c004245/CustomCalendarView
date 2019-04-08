package com.playgilround.schedule.client.calendarschedule.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playgilround.schedule.client.calendarschedule.R;
import com.playgilround.schedule.client.calendarschedule.util.CalendarProperties;
import com.playgilround.schedule.client.calendarschedule.view.CalendarGridView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.playgilround.schedule.client.calendarschedule.util.CalendarProperties.CALENDAR_SIZE;

/**
 * 달력 페이지 로드 Adapter
 */
public class CalendarPageAdapter extends PagerAdapter {

    private static final String TAG = CalendarPageAdapter.class.getSimpleName();

    private Context mContext;

    private CalendarProperties mCalendarProperties;
    private CalendarGridView mCalendarGridView;

    private int mPageMonth;

    public CalendarPageAdapter(Context context, CalendarProperties calendarProperties) {
        mContext = context;
        mCalendarProperties = calendarProperties;
    }

    @Override
    public int getCount() {
        return CALENDAR_SIZE;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container,int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCalendarGridView = (CalendarGridView) inflater.inflate(R.layout.calendar_view_grid, null);

        loadMonth(position);

        container.addView(mCalendarGridView);
        return mCalendarGridView;
    }

    //GridView에 일 추가.
    private void loadMonth(int position) {
      /*  ArrayList<Date> days = new ArrayList<>();

        DateTime dateTime = (DateTime) mCalendarProperties.getFirstPageDate();

        dateTime.plusMonths(position);
        dateTime.withDayOfMonth(1);

        int dayOfWeek = dateTime.getDayOfWeek();*/

        ArrayList<Date> days = new ArrayList<>();
        Calendar calendar = (Calendar) mCalendarProperties.getFirstPageDate().clone();

        calendar.add(Calendar.MONTH, position);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int monthBeginningCell = (dayOfWeek < firstDayOfWeek ? 7 : 0) + dayOfWeek - firstDayOfWeek;

        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while (days.size() < 42) {
            days.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        mPageMonth = calendar.get(Calendar.MONTH) - 1;
        CalendarDayAdapter calendarDayAdapter = new CalendarDayAdapter(this, mContext,
                mCalendarProperties, days, mPageMonth);

        mCalendarGridView.setAdapter(calendarDayAdapter);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
