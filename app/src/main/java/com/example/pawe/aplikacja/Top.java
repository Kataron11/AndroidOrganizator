package com.example.pawe.aplikacja;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Top extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper ;
    ArrayAdapter<String> mAdapter;
    ListView firstCalendar;
    String user;
    String date;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Top.this,Calendar.class);
        intent.putExtra("user",user);

        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        mDatabaseHelper = new DatabaseHelper(this);
        firstCalendar  = (ListView) findViewById(R.id.firstCalendar);
        user = getIntent().getStringExtra("user");

        date= getIntent().getStringExtra("date");


        loadCalednerList();

    }



    public void loadCalednerList(){
        ArrayList<String> calendarList = mDatabaseHelper.getCalendarList(date);



        if(mAdapter==null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.calendarTask, calendarList);
            firstCalendar.setAdapter(mAdapter);

        }
        else {
            mAdapter.clear();

            mAdapter.addAll(calendarList);

            mAdapter.notifyDataSetChanged();

            }
    }


    public void deleteCaldendar(View v){
        View parent = (View) v.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.calendarTask);

        String task = String.valueOf(taskTextView.getText());
        mDatabaseHelper.deleteCalendar(task);
        loadCalednerList();

    }

    public void showDate(View v){
        View parent = (View) v.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.calendarTask);
        String name = String.valueOf(taskTextView.getText());



        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(name);


        final TextView startTime = new TextView (this);
        final TextView endTime = new TextView(this);
        startTime.setText("               Czas roz: " + mDatabaseHelper.getCalendarTime(name));
        endTime.setText("               Czas zak: " + mDatabaseHelper.getCalendarTimeEnd(name));

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(startTime);
        layout.addView(endTime);

        alert.setView(layout);


        alert.show();


    }

}
