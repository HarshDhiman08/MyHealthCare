package com.example.healthcare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {

    HashMap<String,String> item;
    ArrayList list;
    ListView lst;
    SimpleAdapter sa;
    TextView tvTotal;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    Button dateButton, timeButton, btnCheckout, btnBack;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_lab);

        dateButton = findViewById(R.id.buttonCartDates);
        timeButton = findViewById(R.id.buttonCartTime);
        btnCheckout = findViewById(R.id.buttonCartCheckout);
        btnBack = findViewById(R.id.buttonCartBack);
        tvTotal = findViewById(R.id.textViewCartotalPrice);
        lst = findViewById(R.id.listViewCart);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        Database db = new Database(getApplicationContext(),"healthcare",null,1);
        float totalAmount = 0;
        ArrayList dbData = db.getCartData(username,"lab");
        //Toast.makeText(this, ""+dbData, Toast.LENGTH_LONG).show();

        String[][] packages = new String[dbData.size()][];
        for (int i = 0; i< packages.length; i++){
            packages[i]= new String[5];
        }

        for (int i=0;i<dbData.size();i++){
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0]= strData[0];
            packages[i][4]= "Cost : "+strData[1]+"/-";
            totalAmount = totalAmount + Float.parseFloat(strData[1]);
        }
        tvTotal.setText("Total Cost : "+totalAmount);

        list = new ArrayList();
        for (String[] aPackage : packages) {
            item = new HashMap<>();
            item.put("line1", aPackage[0]);
            item.put("line2", aPackage[1]);
            item.put("line3", aPackage[2]);
            item.put("line4", aPackage[3]);
            item.put("line5", aPackage[4]);
            list.add(item);
        }
        sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e,});
        lst.setAdapter(sa);

        btnBack.setOnClickListener(view -> startActivity(new Intent(CartLabActivity.this,LabTestActivity.class)));
        btnCheckout.setOnClickListener(view -> {
            Intent it = new Intent(new Intent(CartLabActivity.this,LabTestBookActivity.class));
            it.putExtra("price",tvTotal.getText());
            it.putExtra("date",dateButton.getText());
            it.putExtra("time",timeButton.getText());
            startActivity(it);
        });

        //datepicker
        initDatePicker();
        dateButton.setOnClickListener(view -> datePickerDialog.show());
        //timepicker
        initTimePicker();
        timeButton.setOnClickListener(view -> timePickerDialog.show());
    }
    private void initDatePicker(){
        @SuppressLint("SetTextI18n") DatePickerDialog.OnDateSetListener dateSetListener= (datePicker, i, i1, i2) -> {
            i1 = i1+1;
            dateButton.setText(i2+"/"+i1+"/"+i);
        };
        Calendar cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        int day= cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+86400000);
    }
    private void initTimePicker(){
        @SuppressLint("SetTextI18n") TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, i, i1) -> timeButton.setText(i+":"+i1);
        Calendar cal = Calendar.getInstance();
        int hrs= cal.get(Calendar.HOUR);
        int mins= cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this,style,timeSetListener,hrs,mins,true);

    }
}