package com.example.pawe.aplikacja;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Note extends AppCompatActivity implements View.OnClickListener {

    String user;
    DatabaseHelper mDatabaseHelper;
    ArrayAdapter<String> notes;
    ListView allNotes;
    Button buttonStartNote;
    ImageButton buttonSearch;
    EditText editSearchNote;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Note.this,Calendar.class);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mDatabaseHelper = new DatabaseHelper(this);

        allNotes = (ListView)findViewById(R.id.allNotes);

        buttonStartNote = (Button) findViewById(R.id.buttonStartNote);
        buttonSearch = (ImageButton) findViewById(R.id.buttonSearch);
        editSearchNote = (EditText) findViewById(R.id.editSearchNote);
        user = getIntent().getStringExtra("user");

        loadNoteList();
        buttonStartNote.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);



    }



    private void loadNoteList() {
        ArrayList<String> notesList = mDatabaseHelper.getNoteList();
        if(notes==null){
            notes = new ArrayAdapter<String>(this,R.layout.rownote,R.id.noteTitle,notesList);

            allNotes.setAdapter(notes);



        }
        else{
            notes.clear();
            notes.addAll(notesList);
            notes.notifyDataSetChanged();
        }
    }

    public int getIdUser(){
        int x = mDatabaseHelper.getUser(user);

        return x;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonStartNote:

                final EditText taskEditText = new EditText(this);
                final EditText taskEditText2 = new EditText(this);

                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Co chcesz zapisąć");


                taskEditText.setHint("Tytuł");
                taskEditText2.setHint("Treść");

                LinearLayout layout = new LinearLayout(getApplicationContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(taskEditText);
                layout.addView(taskEditText2);

                alert.setView(layout);

                alert.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String task = String.valueOf(taskEditText.getText());
                        String task2 = String.valueOf(taskEditText2.getText());
                        mDatabaseHelper.addNoteList(task, task2, getIdUser());
                        loadNoteList();
                    }
                });

                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
                break;

            case R.id.buttonSearch:

             showSearchNote();
             break;
            }
          }

    public void showNote(View v){
        View parent = (View) v.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.noteTitle);
        String task = String.valueOf(taskTextView.getText());

        Intent intent = new Intent(Note.this, NoteContent.class);
        intent.putExtra("task", task);
        intent.putExtra("user", user);
        startActivity(intent);


    }

    public void showSearchNote(){
        if(!emptySearch()){
                if(!emptyNote()) {
                    Intent intent = new Intent(Note.this, NoteContent.class);
                    String task = editSearchNote.getText().toString();
                    intent.putExtra("task", task);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else
                {
                    Toast.makeText(this,"Notatka nie istnieje",Toast.LENGTH_SHORT).show();
                }
        }   else {
            Toast.makeText(this,"Pole szukania jest puste",Toast.LENGTH_SHORT).show();
        }
    }


    private boolean emptySearch() {
        if (TextUtils.isEmpty(editSearchNote.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }

    private boolean emptyNote(){
        if(editSearchNote.getText().toString().equals(mDatabaseHelper.noteSearch(editSearchNote.getText().toString()))){
            return false;
        }else{
            return true;
        }

    }

}
