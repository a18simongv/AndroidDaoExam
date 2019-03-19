package com.example.daoandroid;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.daoandroid.activities.CreatePlane;
import com.example.daoandroid.activities.Flights;
import com.example.daoandroid.activities.Information;
import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.models.Plane;

import java.util.ArrayList;
import java.util.List;

public class CC_a18simongv extends AppCompatActivity {

    private SharedPreferences preferences;

    private EditText etPhone;
    private Button btnCall, btnNwPlane, btnInf, btnFlight;
    private Spinner spCrPlanes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc_a18simongv);

        preferences = getPreferences(MODE_PRIVATE);

        etPhone = findViewById(R.id.etPhone);
        btnCall = findViewById(R.id.btnCall);
        spCrPlanes = findViewById(R.id.spCrPlanes);
        btnNwPlane = findViewById(R.id.btnNewPlane);
        btnInf = findViewById(R.id.btnInf);
        btnFlight = findViewById(R.id.btnFlights);

        askPermissions();

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( etPhone.getText().toString().equals("") ) {
                    Toast.makeText(getApplicationContext(),"You have to write a number",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + etPhone.getText().toString()));
                startActivity(intent);

            }
        });
        btnNwPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreatePlane.class);
                startActivity(intent);
            }
        });

        btnInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Information.class);
                startActivity(intent);
            }
        });

        btnFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spCrPlanes.getSelectedItem() == null) {
                    Toast.makeText(getApplicationContext(),"You have to create a plane",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), Flights.class);
                intent.putExtra("Plane",(String) spCrPlanes.getSelectedItem());
                startActivity(intent);
            }
        });
    }

    private void askPermissions() {
        if(Build.VERSION.SDK_INT>=23)
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE} ,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences.Editor editor = preferences.edit();

        switch (item.getItemId()) {
            case R.id.mClRed:
                editor.putInt("colour",  getResources().getColor(R.color.clRed) );
                break;
            case R.id.mClGreen:
                editor.putInt("colour",  getResources().getColor(R.color.clGreen) );
                break;
            case R.id.mClBlue:
                editor.putInt("colour",  getResources().getColor(R.color.clBlue) );
                break;
        }
        editor.commit();

        etPhone.setTextColor( preferences.getInt("colour", getResources().getColor(R.color.clGreen)) );

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        etPhone.setTextColor( preferences.getInt("colour", getResources().getColor(R.color.clGreen)) );
        Utils.instanceSpinner(Utils.TYPE_SPINNER.PLANE, spCrPlanes, getApplicationContext());

        super.onResume();
    }

}
