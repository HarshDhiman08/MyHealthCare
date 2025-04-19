package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username","");
        Toast.makeText(getApplicationContext(), "Welcome "+username, Toast.LENGTH_SHORT).show();

        CardView exit = findViewById(R.id.cardExit);
        exit.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        });
        CardView findDoctor = findViewById(R.id.cardFindDoctor);
        findDoctor.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this,FindDoctorActivity.class)));
        CardView labTest = findViewById(R.id.cardLabTest);
        labTest.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this,LabTestActivity.class)));
        CardView orderDetails = findViewById(R.id.cardOrderDetails);
        orderDetails.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this,OrderDetailsActivity.class)));
        CardView buyMedicine = findViewById(R.id.cardBuyMedicine);
        buyMedicine.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this,BuyMedicineActivity.class)));
        CardView health = findViewById(R.id.cardHealthDoctor);
        health.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this,HealthArticlesActivity.class)));

    }
}