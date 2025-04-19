package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class LabTestDetailsActivity extends AppCompatActivity {

    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button btnAddToCart,btnBack;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        tvPackageName = findViewById(R.id.textViewLTDTitle);
        tvTotalCost = findViewById(R.id.textViewLTDTotalCost);
        edDetails = findViewById(R.id.editTextLTDTextMultiLine);
        btnBack = findViewById(R.id.buttonLTDGoBack);
        btnAddToCart = findViewById(R.id.buttonLTDAddToCart);

        edDetails.setKeyListener(null);

        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost : " + intent.getStringExtra("text3") + "/-");

        btnBack.setOnClickListener(view -> startActivity(new Intent(LabTestDetailsActivity.this,LabTestActivity.class)));
        btnAddToCart.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username","");
            String product = tvPackageName.getText().toString();
            float price = Float.parseFloat(Objects.requireNonNull(intent.getStringExtra("text3")));

            Database db = new Database(getApplicationContext(),"healthcare",null,1);
            if (db.checkCart(username,product)==1){
                Toast.makeText(getApplicationContext(), "Product Already Added...", Toast.LENGTH_SHORT).show();
            }else {
                db.addCart(username, product, price, "lab");
                Toast.makeText(getApplicationContext(), "Record Inserted to Cart", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LabTestDetailsActivity.this,LabTestActivity.class));
            }

        });
    }
}