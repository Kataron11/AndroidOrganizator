package com.example.pawe.aplikacja;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class Calendar extends AppCompatActivity implements View.OnClickListener{

    private CalendarView mCalenderView;

    String user;
    private Button buttonNotes, buttonContanct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        buttonNotes = (Button) findViewById(R.id.buttonNotes);
        buttonContanct = (Button) findViewById(R.id.buttonContact);

        buttonContanct.setOnClickListener(this);
        buttonNotes.setOnClickListener(this);

        mCalenderView = (CalendarView) findViewById(R.id.calendarView);
        user = getIntent().getStringExtra("user");

      mCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

          @Override
          public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                String date = i2+ "-" + (i1+1) + "-" + i;

              Intent intent = new Intent(Calendar.this, Top.class);

              intent.putExtra("date", date);
              intent.putExtra("user", user);
              Toast.makeText(Calendar.this, date, Toast.LENGTH_SHORT).show();
              startActivity(intent);
          }
      });


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Intent intent = new Intent(Calendar.this, AddEvent.class);
                intent.putExtra("user",user);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
        }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonNotes:
                Intent intentNotes = new Intent(Calendar.this, Note.class);
                intentNotes.putExtra("user", user);
                startActivity(intentNotes);
                break;

            case R.id.buttonContact:
                Intent intentContact = new Intent(Calendar.this, Cataloge.class);
                intentContact.putExtra("user", user);
                startActivity(intentContact);
                break;

        }

    }
}
