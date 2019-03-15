package com.example.daoandroid;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.models.Model;
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
            }
        } catch (Exception e) {
            Log.e("Download:", e.getMessage()+"");
        }
    }

    public static List<String> parseModel(String path) {
        List<String> models = new ArrayList<>();

        try {
            InputStream is = new FileInputStream(path);

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
            Log.e("Parsing:", e.getMessage());
        }
    }

}
