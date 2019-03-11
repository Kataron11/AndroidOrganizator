package com.example.pawe.aplikacja;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoteContent extends AppCompatActivity implements View.OnClickListener{

    String textTitle, textContent, user;
    DatabaseHelper mDatabaseHelper;
    Button buttonModifyNote, buttonDeleteNote;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NoteContent.this,Note.class);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);



        TextView txtProduct = (TextView) findViewById(R.id.title);
        buttonModifyNote = (Button) findViewById(R.id.buttonModifyNote);
        buttonDeleteNote = (Button) findViewById(R.id.buttonDeleteNote);

        buttonDeleteNote.setOnClickListener(this);
        buttonModifyNote.setOnClickListener(this);

        mDatabaseHelper = new DatabaseHelper(this);
        user = getIntent().getStringExtra("user");
         textTitle =  getIntent().getStringExtra("task");
        txtProduct.setText(textTitle);
        showContent();
    }


    public String text(){


        textContent = mDatabaseHelper.contentNote(textTitle);
        return textContent;
    }
    public void showContent(){

        textContent = text();
        TextView txtContent = (TextView) findViewById(R.id.content);
        txtContent.setText(textContent);
    }

    public int getIdNote(){
        int x = mDatabaseHelper.getIdNote(textTitle);
        return x;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDeleteNote:
                deleteNote();
                break;

            case R.id.buttonModifyNote:
                modifyNote();

                break;
        }
    }

    public void deleteNote(){
        mDatabaseHelper.deleteNote(textTitle);
        Intent intent  = new Intent(NoteContent.this, Note.class);
        intent.putExtra("user", textTitle);
        startActivity(intent);
    }

    public void modifyNote(){
        final EditText title  = new EditText(this);
        final EditText content = new EditText(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Modyfikuj tekst");


        title.setText(textTitle);
        content.setText(mDatabaseHelper.contentNote(textTitle));


        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(title);
        layout.addView(content);

        alert.setView(layout);

        alert.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String goodTitle = String.valueOf(title.getText());
                String goodContent = String.valueOf(content.getText());

                mDatabaseHelper.updateNote(goodTitle,goodContent,getIdNote());
                Intent intent = new Intent(NoteContent.this, Note.class);
                startActivity(intent);
            }
        });

        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();

    }
}
