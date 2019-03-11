package com.example.pawe.aplikacja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    EditText editTextChangePassword, editTextChangeConfirmPassword;
    Button buttonEndChangePassword;
    DatabaseHelper  mDatabaseHelper;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextChangePassword = (EditText) findViewById(R.id.editTextChangePassword);
        editTextChangeConfirmPassword = (EditText) findViewById(R.id.editTextChangeConfrimPassword);

        buttonEndChangePassword = (Button) findViewById(R.id.buttonEndChangePassword);

        buttonEndChangePassword.setOnClickListener(this);

        mDatabaseHelper = new DatabaseHelper(this);

        email = getIntent().getStringExtra("EMAIL");

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonEndChangePassword:
            updateData();

                break;

        }
    }


    private void updateData(){
         if(!emptyValidation()){
                if(!toSame()){
                    if(!earlyPassword()) {
                        mDatabaseHelper.updateUser(email, editTextChangePassword.getText().toString());
                        Toast.makeText(ChangePassword.this, "Zmienione hasło", Toast.LENGTH_SHORT).show();
                        Intent intentTwo = new Intent(ChangePassword.this, MainActivity.class);
                        startActivity(intentTwo);
                    } else {
                        Toast.makeText(ChangePassword.this, "Hasło identyczne jak wcześniejsze", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ChangePassword.this, "Nie pasują do siebie", Toast.LENGTH_SHORT).show();
                }
         }else{
             Toast.makeText(ChangePassword.this, "Puste pola", Toast.LENGTH_SHORT).show();
         }
    }


    private boolean emptyValidation() {
        if (TextUtils.isEmpty(editTextChangePassword.getText().toString()) || TextUtils.isEmpty(editTextChangeConfirmPassword.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }

    private boolean toSame(){
        if(editTextChangePassword.getText().toString().equals(editTextChangeConfirmPassword.getText().toString())){
            return false;
        }else {
            return true;
        }

    }

    private boolean earlyPassword(){
        if(editTextChangePassword.getText().toString().equals(mDatabaseHelper.userPassword(email))){
            return true;
        } else {
            return false;
        }
    }
}



