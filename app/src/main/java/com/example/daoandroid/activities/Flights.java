package com.example.daoandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daoandroid.R;
import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.models.Model;
import com.example.daoandroid.database.models.Plane;
import com.example.daoandroid.database.models.SeatRow;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class Flights extends AppCompatActivity {

    private LinearLayout ly;
    private EditText etFuel, etBaggage, etDate;
    private TextView tvFly;
    private Button btnFly, btnSaveFlight;

    private String numberPlate;
    private Plane plane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);

        ly = findViewById(R.id.lySeats);
        etDate = findViewById(R.id.etDate);
        etFuel = findViewById(R.id.etFuel);
        etBaggage = findViewById(R.id.etBaggage);
        tvFly = findViewById(R.id.tvFly);
        btnFly = findViewById(R.id.btnFly);

        Intent intent = getIntent();
        numberPlate = intent.getStringExtra("Plane");
        plane = DaoImp.getDaoPlane(getApplicationContext()).getById(numberPlate);

        instanceLy();

        btnFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] data = calculate();
                if (data == null)
                    return;
                String strData = "Weight: " + data[0] + "\n" +
                        "CG: " + data[1];
                tvFly.setText(strData);
            }
        });

    }

    private double[] calculate() {
        double[] wCg = new double[2];
        double weight = 0.0, moments = 0.0;
        double fuel, baggage;

        try {
            fuel = Double.parseDouble(etFuel.getText().toString());
        } catch (Exception e) {
            fuel = 0;
        }
        try {
            baggage = Double.parseDouble(etBaggage.getText().toString());
        } catch (Exception e) {
            baggage = 0;
        }

        List<Double> passengers = takeValues();
        List<SeatRow> seats = DaoImp.getDaoModel(getApplicationContext()).getRows(plane.getModel());
        for (int i = 0; i < passengers.size(); i++) {
            if (i == 0 && passengers.get(i) < 80)
                return null;
            weight += passengers.get(i);
            moments += passengers.get(i) * seats.get(i).getRowArm();
        }

        Model model = DaoImp.getDaoModel(getApplicationContext()).getById(plane.getModel());
        if (fuel > model.getFuelMax())
            return null;
        if (baggage > model.getBaggageMax())
            return null;

        fuel *= 6;
        weight += fuel + baggage + model.getBew();
        moments += fuel * model.getFuelArm() + baggage * model.getBaggageArm() + model.getBew() * model.getBewArm();

        wCg[0] = weight;
        wCg[1] = moments / weight;

        return wCg;
    }

    private void instanceLy() {
        int seats = DaoImp.getDaoModel(getApplicationContext()).getNumRow(plane.getModel());
        for (int i = 0; i < seats; i++) {
            EditText editText = new EditText(getApplicationContext());
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            ly.addView(editText);
        }
    }

    private List<Double> takeValues() {

        List<Double> weights = new ArrayList<>();
        for (int i = 0; i < ly.getChildCount(); i++) {
            EditText editText = (EditText) ly.getChildAt(i);
            try {
                weights.add(Double.parseDouble(editText.getText().toString()));
            } catch (Exception e) {
                weights.add(0.0);
            }
        }
        return weights;
    }

}
