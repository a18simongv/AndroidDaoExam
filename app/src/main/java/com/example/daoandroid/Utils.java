package com.example.daoandroid;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.models.CgMass;
import com.example.daoandroid.database.models.Flight;
import com.example.daoandroid.database.models.Model;
import com.example.daoandroid.database.models.Plane;
import com.example.daoandroid.database.models.SeatRow;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public enum TYPE_SPINNER { PLANE,FLIGHT,AUDIO,PHOTO}

    public static void instanceSpinner(TYPE_SPINNER type, Spinner spin, Context context) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);

        switch (type) {
            case PLANE:
                List<Plane> planes = DaoImp.getDaoPlane(context).listAll();

                for (Plane plane : planes) {
                    adapter.add(plane.getNumberPlate());
                }
                break;
            case FLIGHT:
                List<Flight> flights = DaoImp.getDaoFlight(context).listAll();

                for(Flight flight : flights) {
                    adapter.add(flight.getId()+"");
                }
                break;
        }

        spin.setAdapter(adapter);
        spin.refreshDrawableState();
    }
    public static void instanceSpinner(TYPE_SPINNER type, Spinner spin, Context context, int idFlight) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);

        switch (type) {
            case AUDIO:
                break;
            case PHOTO:
                break;
        }

        spin.setAdapter(adapter);
        spin.refreshDrawableState();
    }

    public static void download(String uri, File file) {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            if (!file.exists()) {

                InputStream is = connection.getInputStream();
                OutputStream os = new FileOutputStream(file);

                byte[] buffer = new byte[256];
                int num;
                while ((num = is.read(buffer)) != -1) {
                    os.write(buffer, 0, num);
                }

                os.flush();
                is.close();
                os.close();
            } else {
                Log.e("File:","File already exists");
            }
        } catch (Exception e) {
            Log.e("Download:", e.getMessage()+"");
        }
    }

    public static List<String> parseModel(Context context) {
        List<String> models = new ArrayList<>();

        try {
            InputStream is = new FileInputStream(new File(context.getFilesDir(),"models.xml"));

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "UTF-8");
            int evento;
            while ((evento = parser.next()) != XmlPullParser.END_DOCUMENT) {

                if (evento == XmlPullParser.START_TAG && parser.getName().equals("name")) {
                    parser.next();
                    models.add(parser.getText());
                }

            }

            is.close();
        } catch (Exception e) {
            Log.e("Parsing:", e.getMessage());
        }

        return models;
    }

    public static void parseInsModel(String nameModel, Context context) {
        Model model = new Model(nameModel);
        List<SeatRow> seats = null;

        if( DaoImp.getDaoModel(context).getById(nameModel) != null ) {
            Log.e("Model","model is already in Dbs");
            return;
        }

        try {

            InputStream is = new FileInputStream(new File(context.getFilesDir(), "WandB-" + nameModel + ".xml"));
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "UTF-8");
            int evento = -6;
            while (evento != XmlPullParser.END_DOCUMENT) {

                evento = parser.nextTag();
                if (evento == XmlPullParser.START_TAG) {

                    switch (parser.getName()) {
                        case "bew":
                            parser.nextTag();
                            model.setBew(Double.parseDouble(parser.nextText()));
                            parser.nextTag();
                            model.setBewArm(Double.parseDouble(parser.nextText()));
                            break;

                        case "seats":
                            seats = new ArrayList<>();
                            break;

                        case "seat":
                            SeatRow seatRow = new SeatRow(nameModel);
                            parser.nextTag();
                            seatRow.setRow(Integer.parseInt(parser.nextText()));
                            parser.nextTag();
                            seatRow.setNumberSeats(Integer.parseInt(parser.nextText()));
                            parser.nextTag();
                            seatRow.setRowArm(Double.parseDouble(parser.nextText()));

                            seats.add(seatRow);
                            break;

                        case "fuel":
                            parser.nextTag();
                            model.setFuelArm(Double.parseDouble(parser.nextText()));
                            parser.nextTag();
                            model.setFuelMax(Double.parseDouble(parser.nextText()) * 6);
                            break;

                        case "baggage":
                            parser.nextTag();
                            model.setBaggageArm(Double.parseDouble(parser.nextText()));
                            parser.nextTag();
                            model.setBaggageMax(Double.parseDouble(parser.nextText()));
                            break;
                    }
                }

                evento = parser.next();
                if (evento == XmlPullParser.END_DOCUMENT) {
                    DaoImp.getDaoModel(context).insert(model);
                    for (SeatRow row : seats) {
                        DaoImp.getDaoModel(context).insertRow(row);
                    }
                }
            }

            is.close();
        } catch (Exception e) {
            Toast.makeText(context,"Do you download information?",Toast.LENGTH_SHORT).show();
            Log.e("Parsing:", e.getMessage());
        }
    }

    public static void parseCgs(String nameModel, Context context) {

        CgMass cg = null;
        List<CgMass> cgs = new ArrayList<>();

        if( DaoImp.getDaoModel(context).getCg(nameModel).size() != 0 ) {
            Log.e("Cgs","Cgs is already in Dbs");
            return;
        }

        try{
            InputStream is = new FileInputStream(new File(context.getFilesDir(),"CGvsW-"+nameModel+".xml"));

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is,"UTF-8");
            int evento=-6;
            while( evento != XmlPullParser.END_DOCUMENT ) {

                evento = parser.nextTag();
                if(evento == XmlPullParser.START_TAG) {

                    switch (parser.getName()) {
                        case "cg":
                            cg = new CgMass(nameModel);
                            parser.nextTag();
                            cg.setCg( Double.parseDouble(parser.nextText()) );
                            parser.nextTag();
                            cg.setMass( Double.parseDouble(parser.nextText()) );
                            cgs.add(cg);
                    }
                }

                evento= parser.next();
                if(evento == XmlPullParser.END_DOCUMENT) {
                    for (CgMass row: cgs) {
                        DaoImp.getDaoModel(context).insertCg(row);
                    }
                }
            }
            is.close();
        } catch (Exception e) {
            Toast.makeText(context,"Do you download information?",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

//    public static void instanceSpinner(List<ModelInt> list, Spinner spin, Context context) {
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
//        for(ModelInt item : list) {
//            adapter.add( item.getId()+"" );
//        }
//
//        spin.setAdapter(adapter);
//        spin.refreshDrawableState();
//    }

}
