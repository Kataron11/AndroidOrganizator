package com.example.pawe.aplikacja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OpenRegistration extends AppCompatActivity implements View.OnClickListener {


    private DatabaseHelper mDatabaseHelper;


    private Button buttonEndRegister;
    private EditText editTextLoginRegistration, editTextPasswordRegistration, editTextMailRegistration, editTextConfirmPassword;
    private EditText editTextPin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_registration);

        initViews();
        initListeners();
        initObject();


    }

    private void initViews() {


        editTextLoginRegistration = (EditText) findViewById(R.id.editTextLoginRegistration);
        editTextPasswordRegistration = (EditText) findViewById(R.id.editTextPasswordRegistration);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        editTextMailRegistration = (EditText) findViewById(R.id.editTextMailRegistration);
        editTextPin = (EditText) findViewById(R.id.editTextPin);


        buttonEndRegister = (Button) findViewById(R.id.buttonEndRegister);


    }

    private void initListeners() {
        buttonEndRegister.setOnClickListener(this);


    }

    private void initObject() {

        mDatabaseHelper = new DatabaseHelper(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEndRegister:
                registerAccount();

                break;

        }

    }

    private void registerAccount() {


        if (!isEmpty()) {
            if(!toSame()) {
                if(!checkName()) {
                    if(!checkEmail()) {
                            mDatabaseHelper.addUser(editTextLoginRegistration.getText().toString(), editTextPasswordRegistration.getText().toString(), editTextPin.getText().toString(), editTextMailRegistration.getText().toString());
                            Toast.makeText(this, "Stworzono konto", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(OpenRegistration.this, MainActivity.class);
                            startActivity(intent);

                    }else{
                            Toast.makeText(this, "Mail już użyty", Toast.LENGTH_SHORT).show();}

                } else {
                        Toast.makeText(this, "Login już użyty", Toast.LENGTH_SHORT).show();}

            } else {
                Toast.makeText(this, "Różne hasła", Toast.LENGTH_SHORT).show();}

        } else {
            Toast.makeText(this, "Puste pola", Toast.LENGTH_SHORT).show();}

    }



    private boolean isEmpty() {
        if (TextUtils.isEmpty(editTextLoginRegistration.getText().toString()) || TextUtils.isEmpty(editTextMailRegistration.getText().toString())
                || TextUtils.isEmpty(editTextPasswordRegistration.getText().toString()) || TextUtils.isEmpty(editTextConfirmPassword.getText().toString())) {
            return true;
        } else {
            return false;

        }
    }

    private boolean toSame(){
        if(editTextPasswordRegistration.getText().toString().equals(editTextConfirmPassword.getText().toString())){
            return false;
        }else {
            return true;
        }

    }

    private boolean checkName(){
        if(mDatabaseHelper.userLogin(editTextLoginRegistration.getText().toString()).equals(editTextLoginRegistration.getText().toString())){
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEmail(){
        if(mDatabaseHelper.userEmail(editTextMailRegistration.getText().toString()).equals(editTextMailRegistration.getText().toString())){
            return true;
        } else {
            return false;
        }
    }

}
