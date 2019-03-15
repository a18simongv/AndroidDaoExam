package com.example.daoandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.daoandroid.R;
import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.dao.daoimp.FlightImp;
import com.example.daoandroid.database.dao.daoimp.ModelImp;
import com.example.daoandroid.database.models.Flight;
import com.example.daoandroid.database.models.Passenger;
import com.example.daoandroid.database.models.Plane;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

public class RegisterFlight extends AppCompatActivity {

    private Button btnNewPlane, btnStartFlight;
    private Spinner spPlanes;
    private LinearLayout lyEtSeats;
    private EditText etFuel, etBaggage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_flight);

        btnNewPlane = findViewById(R.id.btnNewPlane);
        btnStartFlight = findViewById(R.id.btnStart);
        spPlanes = findViewById(R.id.spPlanes);
        lyEtSeats = findViewById(R.id.lyTvFlight);
        etFuel = findViewById(R.id.etFuel);
        etBaggage = findViewById(R.id.etBaggage);

        instanceSp();

        btnNewPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreatePlane.class);
                startActivity(intent);
            }
        });
        btnStartFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //comprobaciones
                List<Double> weights = weightPass(DaoImp.getDaoPlane(getApplicationContext()).getById((String) spPlanes.getSelectedItem()).getModel());
                /*
                * inserta algoritmo aqui
                * */
                //insercion vuelo
                Flight flight = new Flight((String) spPlanes.getSelectedItem());
                flight.setFuel( Double.parseDouble( etFuel.getText().toString() ) );
                flight.setBaggage( Double.parseDouble( etBaggage.getText().toString() ) );
                flight.setDateInit( new Date(Calendar.getInstance().getTimeInMillis()) );
                flight.setDateFin( null );

                FlightImp flightImp = DaoImp.getDaoFlight(getApplicationContext());
                flightImp.insert(flight);

                int lastFlight = flightImp.lastFlight();
                for(int i=0; i<weights.size(); i++) {
                    String nameModel = DaoImp.getDaoPlane(getApplicationContext()).getById((String) spPlanes.getSelectedItem()).getModel();
                    Passenger passenger = new Passenger( DaoImp.getDaoModel(getApplicationContext()).getRow(nameModel,i),
                            lastFlight, weights.get(i) );
                    DaoImp.getDaoPassenger(getApplicationContext()).insert(passenger);
                }

                Intent intent = new Intent(getApplicationContext(),OnFlight.class);
                startActivity(intent);
            }
        });

        spPlanes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chargeET( DaoImp.getDaoPlane(getApplicationContext()).getById((String) spPlanes.getSelectedItem()).getNumberPlate() );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    public List<Double> weightPass(String nameModel) {
        List<Double> passengers = new ArrayList<>();
        try {

            ModelImp modelImp = DaoImp.getDaoModel(getApplicationContext());
            int num = modelImp.getRows(nameModel).size();
            for(int i=0; i<num; i++) {
                EditText editText = (EditText) lyEtSeats.getChildAt(i);
                passengers.add( Double.parseDouble( editText.getText().toString() ) );
                Log.i("passengers",editText.getText().toString());
            }

        } catch (Exception e) {
            Log.e("Insert seats",e.getMessage()+" ");
        }
        return passengers;
    }

    public void chargeET(String nameModel) {

        try {

            lyEtSeats.removeAllViews();

            int num = DaoImp.getDaoModel(getApplicationContext()).getRows(nameModel).size();
            for(int i=0; i<num; i++) {
                EditText editText = new EditText(getApplicationContext());
                editText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                lyEtSeats.addView( editText );
            }

        } catch (Exception e) {
            Log.e("Seats",e.getMessage()+" ");
        }

    }

    public void instanceSp() {
        List<Plane> planes = DaoImp.getDaoPlane(getApplicationContext()).listAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1);
        for(Plane plane : planes) {
            adapter.add(plane.getNumberPlate());
        }
        spPlanes.setAdapter(adapter);
        spPlanes.refreshDrawableState();
    }

    @Override
    protected void onResume() {
        instanceSp();
        super.onResume();
    }
}
