package com.example.pawe.aplikacja;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Contact extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper mDatabaseHelper;
    ArrayAdapter<String> contacts;
    ListView contactList;
    String user,cat;
    Button buttonSaveContact;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Contact.this,Cataloge.class);
        intent.putExtra("user",user);

        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mDatabaseHelper = new DatabaseHelper(this);
        contactList = (ListView) findViewById(R.id.contactList);
        user = getIntent().getStringExtra("user");
        cat = getIntent().getStringExtra("cat");
        buttonSaveContact = (Button) findViewById(R.id.buttonSaveContact);

        buttonSaveContact.setOnClickListener(this);

        loadContactList();
    }

    private void loadContactList() {
        ArrayList<String> allContact = mDatabaseHelper.getContactList(getIdCataloge());
        if (contacts == null) {
            contacts = new ArrayAdapter<String>(this, R.layout.rowcontact, R.id.contactName, allContact);

            contactList.setAdapter(contacts);


        } else {
            contacts.clear();
            contacts.addAll(allContact);
            contacts.notifyDataSetChanged();
        }
    }

    public int getIdUser() {
        int x = mDatabaseHelper.getUser(user);

        return x;

    }

    public int getIdCataloge(){
        int x = mDatabaseHelper.getIdCatalog(cat);
        return x;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonSaveContact:

                final EditText name = new EditText(this);
                final EditText number = new EditText(this);


                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Dane osobowe");


                name.setHint("Imię i Nazwisko");
                number.setHint("Numer Telefonu");

                LinearLayout layout = new LinearLayout(getApplicationContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(name);
                layout.addView(number);

                alert.setView(layout);

                alert.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String task = String.valueOf(name.getText());
                        String task2 = String.valueOf(number.getText());
                        mDatabaseHelper.addContact(task, task2, getIdUser(),getIdCataloge());
                        loadContactList();
                    }
                });

                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
        }


    }

    public void showContact(View v){
        View parent = (View) v.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.contactName);
        String task = String.valueOf(taskTextView.getText());
        int id = mDatabaseHelper.getIdContact(task);

        Intent intent = new Intent(Contact.this, ContactContent.class);
        intent.putExtra("user", user);
        intent.putExtra("task", task);
        intent.putExtra("idC", id);
        intent.putExtra("cat", cat);
        startActivity(intent);


    }

}
