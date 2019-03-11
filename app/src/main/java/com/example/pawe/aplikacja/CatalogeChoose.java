package com.example.pawe.aplikacja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CatalogeChoose extends AppCompatActivity {

    ArrayAdapter<String> catalogs;
    ListView idCatalogeChoose;
    DatabaseHelper mDatabaseHelper;
    String idContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cataloge_choose);

        mDatabaseHelper = new DatabaseHelper(this);
        idCatalogeChoose = (ListView) findViewById(R.id.idCatalogeChoose);
        idContact = getIntent().getStringExtra("task");

        loadCatalogeList();

    }



    private void loadCatalogeList() {
        ArrayList<String> allCataloge = mDatabaseHelper.getCatalogeList();
        if (catalogs == null) {
            catalogs= new ArrayAdapter<String>(this, R.layout.rowcatalogechoose, R.id.catalogeNameChoose, allCataloge);

            idCatalogeChoose.setAdapter(catalogs);


        } else {
            catalogs.clear();
            catalogs.addAll(allCataloge);
            catalogs.notifyDataSetChanged();
        }
    }

    public int getIdContact() {
        int x = mDatabaseHelper.getIdContact(idContact);

        return x;

    }



    public void changeContact(View v){
        View parent = (View) v.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.catalogeNameChoose);
        String cat = String.valueOf(taskTextView.getText());
        int x = mDatabaseHelper.getIdCatalog(cat);
        mDatabaseHelper.changeContanct(x,getIdContact());
        Intent intent = new Intent(CatalogeChoose.this, Cataloge.class);
//        intent.putExtra("cat", cat);
//        intent.putExtra("user", user);

        startActivity(intent);


    }
}
