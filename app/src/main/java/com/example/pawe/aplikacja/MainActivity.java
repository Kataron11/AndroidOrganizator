package com.example.pawe.aplikacja;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private Button buttonRegistration, buttonLogin, buttonSavePassword;
    private DatabaseHelper mDatabaseHelper;
    private EditText editTextPasswordAccount,editTextLoginAccount;


    public void exit(View v){

            finish();

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegistration= (Button) findViewById(R.id.buttonRegistration);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonSavePassword = (Button) findViewById(R.id.buttonSavePassword);
        editTextLoginAccount = (EditText) findViewById(R.id.editTextLoginAccount);
        editTextPasswordAccount = (EditText) findViewById(R.id.editTextPasswordAccount);


        buttonRegistration.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        buttonSavePassword.setOnClickListener(this);
        mDatabaseHelper = new DatabaseHelper(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegistration:
                Toast.makeText(this,"Nacisnięto tworzenie konta",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,OpenRegistration.class);
                startActivity(intent);
                break;
            case R.id.buttonLogin:
                verifyLogin();
                break;
            case R.id.buttonSavePassword:
                goToSavePassword();
                break;
        }
    }



    public void verifyLogin(){
        if (!emptyValidation()) {
            boolean user = mDatabaseHelper.queryUser(editTextLoginAccount.getText().toString(), editTextPasswordAccount.getText().toString());
            if (user != Boolean.parseBoolean(null)) {
                Bundle mBundle = new Bundle();
                mBundle.putString("user", editTextLoginAccount.getText().toString());
                Intent intent = new Intent(MainActivity.this, Calendar.class);
                intent.putExtras(mBundle);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Witaj " + editTextLoginAccount.getText().toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Nie znaleziono użytkownika", Toast.LENGTH_SHORT).show();
                editTextPasswordAccount.setText("");
            }
        }else{
            Toast.makeText(MainActivity.this, "Puste pola", Toast.LENGTH_SHORT).show();
        }


    }


    public void goToSavePassword(){
        Toast.makeText(this,"Nacisnięto odyzkiwwanie hasła",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, Recover.class);
        startActivity(intent);
    }




    private boolean emptyValidation() {
        if (TextUtils.isEmpty(editTextLoginAccount.getText().toString()) || TextUtils.isEmpty(editTextPasswordAccount.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }
}
