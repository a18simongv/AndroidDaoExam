package com.example.daoandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.daoandroid.R;
import com.example.daoandroid.Utils;
import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.models.CgMass;
import com.example.daoandroid.database.models.Plane;

import java.util.List;

public class Information extends AppCompatActivity {

    private TextView tvData;
    private ImageView ivPhoto;
    private Spinner spSeePlanes,spSeeFlights,spSeeAudios,spSeePhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        tvData = findViewById(R.id.tvData);
        ivPhoto = findViewById(R.id.ivPhoto);
        spSeePlanes = findViewById(R.id.spSeePlanes);
        spSeeFlights = findViewById(R.id.spSeeFlights);

        Utils.instanceSpinner(Utils.TYPE_SPINNER.PLANE, spSeePlanes, getApplicationContext());
        Utils.instanceSpinner(Utils.TYPE_SPINNER.FLIGHT,spSeeFlights,getApplicationContext());

        spSeePlanes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Plane plane = DaoImp.getDaoPlane(getApplicationContext()).getById( (String) spSeePlanes.getSelectedItem() );
                if(plane == null)
                    return;

                List<CgMass> masses = DaoImp.getDaoModel(getApplicationContext()).getCg(plane.getModel());

                String data = "Plane: " + plane.getNumberPlate() + "\n" +
                        "Model: " + plane.getModel() + "\n" +
                        "Higher CG: " + masses.get( masses.size()-1 ).getCg();
                tvData.setText(data);

                tvData.setVisibility(View.VISIBLE);
                ivPhoto.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

}
