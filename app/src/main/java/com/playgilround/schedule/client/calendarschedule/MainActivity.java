package com.playgilround.schedule.client.calendarschedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.playgilround.schedule.client.calendarschedule.view.CalendarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);
    }
}
