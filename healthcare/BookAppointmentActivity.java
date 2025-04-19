package com.example.healthcare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3, ed4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton;
    private Button timeButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        // View bindings
        tv = findViewById(R.id.textViewAppTitle);
        ed1 = findViewById(R.id.editTextAppFullname);
        ed2 = findViewById(R.id.editTextAppAddress);
        ed3 = findViewById(R.id.editTextAppContact);
        ed4 = findViewById(R.id.editTextAppPincode);
        dateButton = findViewById(R.id.buttonAppDate);
        timeButton = findViewById(R.id.buttonAppTime);
        Button btnBook = findViewById(R.id.buttonBookAppointment);
        Button btnBack = findViewById(R.id.buttonAppBack);

        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        // Getting Intent extras with null checks
        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String fullname = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contact = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        // Avoid null assignment to views
        tv.setText(title != null ? title : "Title");
        ed1.setText(fullname != null ? fullname : "N/A");
        ed2.setText(address != null ? address : "N/A");
        ed3.setText(contact != null ? contact : "N/A");
        ed4.setText(fees != null ? "Cons Fees " + fees + "/-" : "Cons Fees 0/-");

        // Date picker
        initDatePicker();
        dateButton.setOnClickListener(view -> datePickerDialog.show());

        // Time picker
        initTimePicker();
        timeButton.setOnClickListener(view -> timePickerDialog.show());

        // Back button
        btnBack.setOnClickListener(view ->
                startActivity(new Intent(BookAppointmentActivity.this, FindDoctorActivity.class)));

        // Book appointment logic
        btnBook.setOnClickListener(view -> {
            String selectedDate = dateButton.getText().toString();
            String selectedTime = timeButton.getText().toString();

            if (selectedDate.equals("Select Date") || selectedTime.equals("Select Time")) {
                Toast.makeText(getApplicationContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                return;
            }

            Database db = new Database(getApplicationContext(), "healthcare", null, 1);
            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);

            if (username == null || title == null || fullname == null || address == null || contact == null || fees == null) {
                Toast.makeText(getApplicationContext(), "Error: Missing user or appointment data", Toast.LENGTH_LONG).show();
                return;
            }

            String doctorDetail = title + " => " + fullname;

            if (db.checkAppintmentExists(username, doctorDetail, address, contact, selectedDate, selectedTime) == 1) {
                Toast.makeText(getApplicationContext(), "Appointment already booked", Toast.LENGTH_LONG).show();
            } else {
                db.addOrder(username, doctorDetail, address, contact, 0, selectedDate, selectedTime, Float.parseFloat(fees), "appointment");
                Toast.makeText(getApplicationContext(), "Your Appointment is booked Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(BookAppointmentActivity.this, HomeActivity.class));
            }
        });
    }

    private void initDatePicker() {
        @SuppressLint("SetTextI18n") DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            dateButton.setText(day + "/" + month + "/" + year);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {
        @SuppressLint("DefaultLocale") TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> timeButton.setText(String.format("%02d:%02d", hour, minute));
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);
        int style = AlertDialog.THEME_HOLO_DARK;

        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hrs, mins, true);
    }
}
