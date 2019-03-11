package com.example.pawe.aplikacja;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;





public class AddEvent extends AppCompatActivity implements
        View.OnClickListener {

    Button btnDatePicker, btnTimePicker, endTime, buttonSaveEvent;
    EditText txtDate, txtTime, endText, editTitle;
    String date, timeStart, timeEnd, title;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String user;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        user = getIntent().getStringExtra("user");

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        endTime = (Button) findViewById(R.id.endTime);
        buttonSaveEvent = (Button) findViewById(R.id.buttonSaveEvent);

        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        endText=(EditText)findViewById(R.id.endText);
        editTitle=(EditText) findViewById(R.id.editTitle);

        endTime.setOnClickListener(this);
        buttonSaveEvent.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        mDatabaseHelper = new DatabaseHelper(this);


    }


    public int getIdUser(){
        int x = mDatabaseHelper.getUser(user);

        return x;

    }

    @Override
    public void onClick(View v) {




        if (v == btnDatePicker) {




            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {


                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            date = txtDate.getText().toString();

                        }
                    },2018, 12, 22 );
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {


            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                            timeStart = txtTime.getText().toString();





                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }

        if (v == endTime){
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            endText.setText(hourOfDay + ":" + minute);
                            timeEnd = endText.getText().toString();
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();

        }


        if (v==buttonSaveEvent){
            saveEvent();

        }
    }


    private void saveEvent(){
        if (!isEmpty()) {
            mDatabaseHelper.addCalendarList(title = editTitle.getText().toString(), date, getIdUser(), timeStart, timeEnd);
            Toast.makeText(AddEvent.this, timeStart, Toast.LENGTH_SHORT).show();
            Intent intentSave = new Intent(AddEvent.this, Calendar.class);
            intentSave.putExtra("user", user);
            startActivity(intentSave);
        } else
        {
            Toast.makeText(this, "Puste pola", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isEmpty() {
        if (TextUtils.isEmpty(editTitle.getText().toString()) || TextUtils.isEmpty(txtDate.getText().toString())
                || TextUtils.isEmpty(txtTime.getText().toString()) || TextUtils.isEmpty(endText.getText().toString())) {
            return true;
        } else {
            return false;

        }
    }


}