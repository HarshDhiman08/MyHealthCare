package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        CardView exit = findViewById(R.id.cardFDBack);
        exit.setOnClickListener(view -> startActivity(new Intent(FindDoctorActivity.this,HomeActivity.class)));

        CardView familyphysician = findViewById(R.id.cardFDFamilyPhysician);
        familyphysician.setOnClickListener(view -> {
            Intent it = new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
            it.putExtra("title","Family Physician");
            startActivity(it);
        });
        CardView dietician = findViewById(R.id.cardFDDietician);
        dietician.setOnClickListener(view -> {
            Intent it = new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
            it.putExtra("title","Dietecian");
            startActivity(it);
        });
        CardView dentist = findViewById(R.id.cardFDDentist);
        dentist.setOnClickListener(view -> {
            Intent it = new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
            it.putExtra("title","Dentist");
            startActivity(it);
        });
        CardView surgeon = findViewById(R.id.cardFDSurgeon);
        surgeon.setOnClickListener(view -> {
            Intent it = new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
            it.putExtra("title","Surgeon");
            startActivity(it);
        });
        CardView cardiologist = findViewById(R.id.cardFDCardiologist);
        cardiologist.setOnClickListener(view -> {
            Intent it = new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
            it.putExtra("title","Cardiologist");
            startActivity(it);
        });
    }
}