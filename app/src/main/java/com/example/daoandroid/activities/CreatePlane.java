package com.example.daoandroid.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.daoandroid.R;
import com.example.daoandroid.Utils;
import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.dao.ints.DaoI;
import com.example.daoandroid.database.models.Plane;

import java.io.File;
import java.util.List;

public class CreatePlane extends AppCompatActivity {

    private Spinner spModels;
    private EditText etNumberPlate;
    private Button btnDownload, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plane);

        spModels = findViewById(R.id.spModels);
        etNumberPlate = findViewById(R.id.etNumberPlate);
        btnDownload = findViewById(R.id.btnDownInf);
        btnSave = findViewById(R.id.btnSavePlane);

        new loadSpinner().execute();

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String nameModel = (String) spModels.getSelectedItem();

                        Utils.download( "https://informatica.iessanclemente.net/pmdm/WandB-"+nameModel+".xml",
                                new File(getApplicationContext().getFilesDir(),"WandB-"+nameModel+".xml" ) );
                        Utils.download("https://informatica.iessanclemente.net/pmdm/CGvsW-"+nameModel+".xml",
                                new File(getApplicationContext().getFilesDir(),"CGvsW-"+nameModel+".xml" ));
                    }
                }).start();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNumberPlate.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"You have to write a number plate",Toast.LENGTH_SHORT).show();
                    return;
                } else if(DaoImp.getDaoPlane(getApplicationContext()).getById(etNumberPlate.getText().toString()) != null ) {
                    Toast.makeText(getApplicationContext(),"Number plate already exists",Toast.LENGTH_SHORT).show();
                    return;
                }

                String nameModel = (String) spModels.getSelectedItem();
                Utils.parseInsModel(nameModel, getApplicationContext());
                Utils.parseCgs(nameModel, getApplicationContext());

                Plane plane = new Plane(etNumberPlate.getText().toString(),(String) spModels.getSelectedItem());
                DaoImp.getDaoPlane(getApplicationContext()).insert(plane);

            }
        });
    }

    private void instanceSp(List<String> values){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,values);
        spModels.setAdapter(adapter);
        spModels.refreshDrawableState();
    }

    class loadSpinner extends AsyncTask<Void,Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            Utils.download( "https://informatica.iessanclemente.net/pmdm/models.xml", new File(getApplicationContext().getFilesDir(),"models.xml"));
            return Utils.parseModel(getApplicationContext());
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            instanceSp(strings);
            super.onPostExecute(strings);
        }
    }
}
