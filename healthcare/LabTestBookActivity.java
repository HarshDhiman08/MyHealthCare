package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LabTestBookActivity extends AppCompatActivity {
    EditText edname, edaddress, edcontact, edpincode;
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);
        edname = findViewById(R.id.editTextLTDFullname);
        edaddress = findViewById(R.id.editTextLTDAddress);
        edcontact = findViewById(R.id.editTextLTDContact);
        edpincode = findViewById(R.id.editTextLTDPincode);
        btnBooking = findViewById(R.id.buttonLTDBooking);

        Intent intent = getIntent();
        String[] price = Objects.requireNonNull(intent.getStringExtra("price")).split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        btnBooking.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username","");

            Database db = new Database(getApplicationContext(),"healthcare",null,1);
            db.addOrder(username,edname.getText().toString(),edaddress.getText().toString(),edcontact.getText().toString(),
                    Integer.parseInt(edpincode.getText().toString()), Objects.requireNonNull(date), Objects.requireNonNull(time),Float.parseFloat(price[1]),"lab");
            db.removeCart(username,"lab");
            Toast.makeText(getApplicationContext(), "Your Booking is Done Successfully.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LabTestBookActivity.this,HomeActivity.class));
        });

    }
}