package com.example.daoandroid.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.daoandroid.R;
import com.example.daoandroid.Utils;
import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.models.Plane;

import java.io.File;
import java.util.List;

public class CreatePlane extends AppCompatActivity {

    private EditText etNumberPlate;
    private Spinner spModels;
    private Button btnCreatePlane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plane);

        etNumberPlate = findViewById(R.id.etNumberPlate);
        spModels = findViewById(R.id.spModels);
        btnCreatePlane = findViewById(R.id.btnCreatePlane);

        (new loadModels()).execute("https://informatica.iessanclemente.net/pmdm/models.xml");

        btnCreatePlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String modelName = (String) spModels.getSelectedItem();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File infModel = new File(getApplicationContext().getFilesDir().getAbsolutePath(), "WandB-" + modelName + ".xml");
                        if( !infModel.exists() )
                            Utils.download("https://informatica.iessanclemente.net/pmdm/WandB-" + modelName + ".xml", infModel);

                        File cgModel = new File(getApplicationContext().getFilesDir().getAbsolutePath(), "CGvsW-" + modelName + ".xml");
                        if( !cgModel.exists() )
                            Utils.download("https://informatica.iessanclemente.net/pmdm/CGvsW-" + modelName + ".xml", cgModel);

                        if (DaoImp.getDaoModel(getApplicationContext()).getById(modelName) == null)
                            Utils.parseInsModel(modelName, getApplicationContext());

                        if (!etNumberPlate.getText().toString().equals("")) {
                            DaoImp.getDaoPlane(getApplicationContext()).insert(new Plane(etNumberPlate.getText().toString(), modelName));
                        }
                    }
                }).start();

                try {
                    this.wait(300);
                } catch (Exception e) {}

                finish();
            }
        });
    }

    class loadModels extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... uri) {
            File file = new File(getApplicationContext().getFilesDir().getAbsolutePath(), "models.xml");
            Utils.download(uri[0], file);
            return Utils.parseModel(getApplicationContext().getFilesDir().getAbsolutePath() + "/models.xml");
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            spModels.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, strings));
            spModels.refreshDrawableState();
            super.onPostExecute(strings);
        }
    }

}
