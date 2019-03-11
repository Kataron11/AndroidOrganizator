package com.example.pawe.aplikacja;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Cataloge extends AppCompatActivity {


    ArrayAdapter<String> catalogs;
    ListView idCataloge;
    DatabaseHelper mDatabaseHelper;
    String user;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Cataloge.this,Calendar.class);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cataloge);

        mDatabaseHelper = new DatabaseHelper(this);
        idCataloge = (ListView) findViewById(R.id.idCataloge);
        user = getIntent().getStringExtra("user");

        loadCatalogeList();

    }


    private void loadCatalogeList() {
        ArrayList<String> allCataloge = mDatabaseHelper.getCatalogeList();
        if (catalogs == null) {
            catalogs= new ArrayAdapter<String>(this, R.layout.rowcataloge, R.id.catalogeName, allCataloge);

            idCataloge.setAdapter(catalogs);


        } else {
            catalogs.clear();
            catalogs.addAll(allCataloge);
            catalogs.notifyDataSetChanged();
        }
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

                final EditText taskEditText = new EditText(this);
                                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Co chcesz zapisąć");


                taskEditText.setHint("Tytuł");


                LinearLayout layout = new LinearLayout(getApplicationContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(taskEditText);


                alert.setView(layout);

                alert.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String task = String.valueOf(taskEditText.getText());

                        mDatabaseHelper.addCatalog(task);
                        loadCatalogeList();
                    }
                });

                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();


        }
        return super.onOptionsItemSelected(item);
    }


    public void showCatalog(View v){
        View parent = (View) v.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.catalogeName);
        String cat = String.valueOf(taskTextView.getText());

        Intent intent = new Intent(Cataloge.this, Contact.class);
        intent.putExtra("cat", cat);
        intent.putExtra("user", user);

        startActivity(intent);


    }
}
