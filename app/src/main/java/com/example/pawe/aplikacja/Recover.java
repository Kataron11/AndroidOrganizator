package com.example.pawe.aplikacja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Recover extends AppCompatActivity  implements View.OnClickListener {


    EditText editTextSaveEmail, editTextSavePin;
    Button buttonEndSavePassword;
    DatabaseHelper mDatabaseHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        buttonEndSavePassword = (Button) findViewById(R.id.buttonEndSavePassword);
        editTextSaveEmail = (EditText) findViewById(R.id.editTextSaveEmail);
        editTextSavePin = (EditText) findViewById(R.id.editTextSavePin);

        buttonEndSavePassword.setOnClickListener(this);
        mDatabaseHelper = new DatabaseHelper(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEndSavePassword:
                recoverPassword();

                break;

        }
    }



    public void recoverPassword(){
        if (!emptyValidation()) {
            boolean user = mDatabaseHelper.recoverUser(editTextSaveEmail.getText().toString(), editTextSavePin.getText().toString());
            if (user != Boolean.parseBoolean(null)) {

                Intent intent = new Intent(Recover.this,ChangePassword.class);
                intent.putExtra("EMAIL",editTextSaveEmail.getText().toString().trim());

                startActivity(intent);

                Toast.makeText(Recover.this, "Poprawne dane", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Recover.this, "Nie znaleziono", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(Recover.this, "Puste pola", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(editTextSaveEmail.getText().toString()) || TextUtils.isEmpty(editTextSavePin.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }
}
