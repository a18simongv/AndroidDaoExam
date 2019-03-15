package com.example.daoandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.daoandroid.MainActivity;
import com.example.daoandroid.R;
import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.dao.daoimp.FlightImp;

public class OnFlight extends AppCompatActivity {

    private Button btnFinalize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_flight);

        btnFinalize = findViewById(R.id.btnFinalize);

        btnFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 FlightImp flightImp = DaoImp.getDaoFlight(getApplicationContext());
                 flightImp.finalizeFlight( flightImp.lastFlight() );
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
