package com.example.daoandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.daoandroid.R;
import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.models.Flight;

import java.util.List;

public class SeeFlights extends AppCompatActivity {

    private ListView lvFlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_flights);

        lvFlights = findViewById(R.id.lvFilghts);

        List<Flight> flights = DaoImp.getDaoFlight(getApplicationContext()).listAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1);
        for(Flight flight : flights) {
            adapter.add(flight.getPlane() + " " + flight.getDateInit().toString());
        }
        lvFlights.setAdapter(adapter);
        lvFlights.refreshDrawableState();

    }
}
