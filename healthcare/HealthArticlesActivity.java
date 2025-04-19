package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticlesActivity extends AppCompatActivity {

    private final String [][] health_details = {
            {"Walking Daily","","","","Click More Details"},
            {"Home Care of Covid-19","","","","Click More Details"},
            {"Stop Smoking","","","","Click More Details"},
            {"Menstrual Cramps","","","","Click More Details"},
            {"Healthy Gut","","","","Click More Details"}
    };
    private final int[] images ={
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5,
    };
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    Button btnBack;
    ListView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles);

        lst = findViewById(R.id.listViewHA);
        btnBack = findViewById(R.id.buttonHABack);

        btnBack.setOnClickListener(view -> startActivity(new Intent(HealthArticlesActivity.this,HomeActivity.class)));

        list = new ArrayList();
        for (String[] healthDetail : health_details) {
            item = new HashMap<>();
            item.put("line1", healthDetail[0]);
            item.put("line2", healthDetail[1]);
            item.put("line3", healthDetail[2]);
            item.put("line4", healthDetail[3]);
            item.put("line5", healthDetail[4]);
            list.add(item);
        }
        sa = new SimpleAdapter(this,list,R.layout.multi_lines,new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});
        lst.setAdapter(sa);

        lst.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent it = new Intent(HealthArticlesActivity.this,HealthArticlesDetailsActivity.class);
            it.putExtra("Text1", health_details[i][0]);
            it.putExtra("Text2", images[i]);
            startActivity(it);
        });
    }
}