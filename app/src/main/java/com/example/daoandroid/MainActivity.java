package com.example.daoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.daoandroid.activities.RegisterFlight;
import com.example.daoandroid.activities.SeeFlights;

import java.sql.Date;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button btnRegister, btnSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = findViewById(R.id.btnRegisterFlight);
        btnSee = findViewById(R.id.btnSeeFlights);

        Log.e("date", (new Date(Calendar.getInstance().getTimeInMillis())).toString());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterFlight.class);
                startActivity(intent);
            }
        });

        btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeeFlights.class);
                startActivity(intent);
            }
        });
    }
}
