package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
        EditText edUsername, edPassword;
        Button btn;
        TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edPassword = findViewById(R.id.editTextLoginPassword);
        edUsername = findViewById(R.id.editTextLoginUsername);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textViewNewUSer);

         btn.setOnClickListener(view -> {
//                 startActivity(new Intent(LoginActivity.this,HomeActivity.class));
             String username = edUsername.getText().toString();
             String password = edPassword.getText().toString();
             Database db = new Database(getApplicationContext(),"healthcare",null,1);
             if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
             }
             else{
                 if (db.login(username,password)==1){
                     Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                     SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs",Context.MODE_PRIVATE);
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("username",username);
                     editor.apply();
                     startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                 }
                 else{
                     Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();

                 }
             }
         });

         tv.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,RegisterActivity.class)));

    }
}