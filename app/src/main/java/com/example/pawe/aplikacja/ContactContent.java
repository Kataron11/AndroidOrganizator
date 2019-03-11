package com.example.pawe.aplikacja;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.widget.Toast;

public class ContactContent extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper mDatabaseHelper;
    Button buttonModifyContact, buttonDeleteContact,buttonMoveContact, buttonAddBirth;
    ImageButton imageButtonCall;
    String contact, number,user,cat;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ContactContent.this,Contact.class);
        intent.putExtra("user",user);
        intent.putExtra("cat", cat);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_content);
        TextView txtName = (TextView) findViewById(R.id.textName);

        buttonModifyContact = (Button) findViewById(R.id.buttonModifyContact);
        buttonDeleteContact = (Button) findViewById(R.id.buttonDeleteContact);
        imageButtonCall = (ImageButton) findViewById(R.id.imageButtonCall);
        buttonMoveContact = (Button) findViewById(R.id.buttonMoveContact);
        buttonAddBirth = (Button) findViewById(R.id.buttonAddBirth);

        buttonAddBirth.setOnClickListener(this);
        buttonDeleteContact.setOnClickListener(this);
        buttonModifyContact.setOnClickListener(this);
        imageButtonCall.setOnClickListener(this);
        buttonMoveContact.setOnClickListener(this);

        mDatabaseHelper = new DatabaseHelper(this);
        cat = getIntent().getStringExtra("cat");
        contact = getIntent().getStringExtra("task");
        user = getIntent().getStringExtra("user");
        txtName.setText(contact);
        showContent();
    }


    public String setNumber() {

        number = mDatabaseHelper.contentContact(contact);
        return number;
    }

    public void showContent() {

        number = setNumber();
        TextView txtContent = (TextView) findViewById(R.id.textNumber);
        txtContent.setText(number);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDeleteContact:
                deleteContact();
                break;

            case R.id.buttonModifyContact:
                modifyContact();

                break;
            case R.id.imageButtonCall:
                call();
                break;

            case R.id.buttonMoveContact:
                moveCataloge();
                break;


            case R.id.buttonAddBirth:
                addBirth();
//                saveBirth();

                break;
        }
    }

    public void deleteContact() {
        mDatabaseHelper.deleteContact(contact);
        Intent intent = new Intent(ContactContent.this, Cataloge.class);
        intent.putExtra("user", contact);
        startActivity(intent);
        this.finish();

    }


    public int getIdContact(){
        int x = mDatabaseHelper.getIdContact(contact);
        return x;
    }

    public void modifyContact() {
        final EditText name = new EditText(this);
        final EditText number = new EditText(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Modyfikuj tekst");


        name.setText(contact);
        number.setText(mDatabaseHelper.contentContact(contact));

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(name);
        layout.addView(number);

        alert.setView(layout);

        alert.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String goodName = String.valueOf(name.getText());
                String goodNumber = String.valueOf(number.getText());

                mDatabaseHelper.updateContact(goodName, goodNumber,getIdContact());
                Intent intent = new Intent(ContactContent.this, Cataloge.class );
                startActivity(intent);
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();

    }

    public void call(){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",mDatabaseHelper.contentContact(contact), null)));
    }



    public void moveCataloge(){
        Intent intent = new Intent(ContactContent.this, CatalogeChoose.class);
        intent.putExtra("task",contact);
        startActivity(intent);
    }


    public int getIdUser() {
        int x = mDatabaseHelper.getUser(user);

        return x;

    }

    public void addBirth(){
        DatePickerDialog dialog = new DatePickerDialog(ContactContent.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        String x = dayOfMonth + "-" + (monthOfYear + 1);
                        mDatabaseHelper.updateContact(x,getIdContact());


                    }
                },2018, 12, 22 );

               dialog.show();

           saveBirth();



    }

    public void saveBirth(){
        String date = mDatabaseHelper.getDateContact(getIdContact());

        for (int i=2018;i<2030;i++ ){

            String x = String.valueOf(i);
            String y = date + "-" + x;
            mDatabaseHelper.addCalendarList(contact,y,getIdUser(),"00-00","00-01");

        }


        Toast.makeText(ContactContent.this, date, Toast.LENGTH_SHORT).show();

    }
}
