package com.example.daoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.InputType;
import android.util.Log;
import android.util.Xml;
import android.widget.EditText;

import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.dao.daoimp.FlightImp;
import com.example.daoandroid.database.dao.daoimp.ModelImp;
import com.example.daoandroid.database.dao.daoimp.PassengerImp;
import com.example.daoandroid.database.dao.daoimp.TypeMultimediaImp;
import com.example.daoandroid.database.dao.ints.DaoI;
import com.example.daoandroid.database.dao.ints.ModelI;
import com.example.daoandroid.database.models.CgMass;
import com.example.daoandroid.database.models.Flight;
import com.example.daoandroid.database.models.Model;
import com.example.daoandroid.database.models.Multimedia;
import com.example.daoandroid.database.models.Passenger;
import com.example.daoandroid.database.models.Plane;
import com.example.daoandroid.database.models.SeatRow;
import com.example.daoandroid.database.models.TypeMultimedia;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.daoandroid", appContext.getPackageName());
    }

    @Test
    public void testCGIns() {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        String nameModel = "PA-28-161";//es lo que recibiría el metodo
        CgMass cg = null;
        List<CgMass> cgs = new ArrayList<>();

        try(InputStream is = new FileInputStream(new File(context.getFilesDir(),"CGvsW-"+nameModel+".xml"))){
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
                            cg.setCg( Integer.parseInt(parser.nextText()) );
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
        } catch (Exception e) {}
        if( DaoImp.getDaoModel(context).getCg(nameModel).size() ==  11)
            flag = true;

        assertEquals(true,flag);
    }

    @Test
    public void testParseModelIns() {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        String nameModel = "PA-28-161";//es lo que recibiría el metodo
        Model model = new Model(nameModel);
        List<SeatRow> seats = null;

        try(InputStream is = new FileInputStream(new File(context.getFilesDir(),"WandB-"+nameModel+".xml"))){
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is,"UTF-8");
            int evento=-6;
            while( evento != XmlPullParser.END_DOCUMENT ) {

                evento = parser.nextTag();
                if(evento == XmlPullParser.START_TAG) {

                    switch (parser.getName()) {
                        case "bew":
                            parser.nextTag();
                            model.setBew( Double.parseDouble( parser.nextText() ) );
                            parser.nextTag();
                            model.setBewArm( Double.parseDouble( parser.nextText() ) );
                            break;

                        case "seats":
                            seats = new ArrayList<>();
                            break;

                        case "seat":
                            SeatRow seatRow = new SeatRow(nameModel);
                            parser.nextTag();
                            seatRow.setRow( Integer.parseInt(parser.nextText()) );
                            parser.nextTag();
                            seatRow.setNumberSeats( Integer.parseInt(parser.nextText()) );
                            parser.nextTag();
                            seatRow.setRowArm( Double.parseDouble( parser.nextText() ) );

                            seats.add(seatRow);
                            break;

                        case "fuel":
                            parser.nextTag();
                            model.setFuelArm( Double.parseDouble( parser.nextText() ) );
                            parser.nextTag();
                            model.setFuelMax( Double.parseDouble( parser.nextText() )*6 );
                            break;

                        case "baggage":
                            parser.nextTag();
                            model.setBaggageArm( Double.parseDouble( parser.nextText() ) );
                            parser.nextTag();
                            model.setBaggageMax( Double.parseDouble( parser.nextText() ) );
                            break;
                    }
                }

                evento= parser.next();
                if(evento == XmlPullParser.END_DOCUMENT) {
//                    DaoImp.getDaoModel(context).insert(model);
                    for (SeatRow row: seats) {
                        DaoImp.getDaoModel(context).insertRow(row);
                    }
                }
            }
        } catch (Exception e) {}

        Model modelDb = DaoImp.getDaoModel(context).getById(nameModel);
        List<SeatRow> seats2 = DaoImp.getDaoModel(context).getRows(nameModel);

        if( modelDb.getBew() == 1466 && modelDb.getBewArm() == 87.74
                && seats2.size() ==2
                && modelDb.getFuelArm()==95 && modelDb.getFuelMax()==288
                && modelDb.getBaggageArm() == 142.8 && modelDb.getBaggageMax() == 200)
            flag = true;

        assertEquals(true,flag);
    }

    @Test
    public void testDownInfModel() {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        //download modelInf
        boolean inf = false;
        try {
            URL url = new URL("https://informatica.iessanclemente.net/pmdm/WandB-PA-28-161.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            InputStream is = connection.getInputStream();
            File file = new File(context.getFilesDir(), "WandB-PA-28-161.xml");
            OutputStream os = new FileOutputStream(file);

            byte[] buffer = new byte[256];
            int num;
            while ((num = is.read(buffer)) != -1) {
                os.write(buffer, 0, num);
            }

            os.flush();
            is.close();
            os.close();

            inf = true;
        } catch (Exception e) {
        }

        //download modelInf
        boolean cgs = false;
        try {
            URL url = new URL("https://informatica.iessanclemente.net/pmdm/CGvsW-PA-28-161.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            InputStream is = connection.getInputStream();
            File file = new File(context.getFilesDir(), "CGvsW-PA-28-161.xml");
            OutputStream os = new FileOutputStream(file);

            byte[] buffer = new byte[256];
            int num;
            while ((num = is.read(buffer)) != -1) {
                os.write(buffer, 0, num);
            }

            os.flush();
            is.close();
            os.close();

            cgs = true;
        } catch (Exception e) {
        }

        if ( inf == true && cgs==true)
            flag = true;

        assertEquals(true, flag);
    }

    @Test
    public void testParseModels() {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        List<String> models = new ArrayList<>();

        try(InputStream is = new FileInputStream(new File(context.getFilesDir(),"models.xml"))){
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is,"UTF-8");
            int evento;
            while( (evento=parser.next()) != XmlPullParser.END_DOCUMENT ) {

                if(evento == XmlPullParser.START_TAG && parser.getName().equals("name")) {
                    parser.next();
                    models.add(parser.getText());
                }

            }
        } catch (Exception e) {}

        if(models.size() == 2)
            flag = true;

        assertEquals(true,flag);
    }

    @Test
    public void testDownloadModel() {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;
        try {
            URL url = new URL("https://informatica.iessanclemente.net/pmdm/models.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            InputStream is = connection.getInputStream();
            File file = new File(context.getFilesDir(), "models.xml");
            OutputStream os = new FileOutputStream(file);

            byte[] buffer = new byte[256];
            int num;
            while ((num = is.read(buffer)) != -1) {
                os.write(buffer, 0, num);
            }

            os.flush();
            is.close();
            os.close();
        } catch (Exception e) {
        }

        if ((new File(context.getFilesDir(), "models.xml")).exists())
            flag = true;

        assertEquals(true, flag);
    }

    @Test
    public void testPassengers() {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        PassengerImp daoPass = DaoImp.getDaoPassenger(context);
        int numRows = daoPass.numRows("model-test-1");
        for (int i = 1; i <= numRows; i++) {
            daoPass.insert(new Passenger(DaoImp.getDaoModel(context).getRow("model-test-1", i), 1, i * 10));
        }

        int numPass = daoPass.listAll(1).size();
        if (numRows == numPass)
            flag = true;

        assertEquals(true, flag);
    }

    @Test
    public void testSeatRow() {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        ModelImp daoSRw = DaoImp.getDaoModel(context);

        for (int i = 0; i < 3; i++) {
            daoSRw.insertRow(new SeatRow(i + 1, "model-test-1", 2, i + 10));
        }

        List<SeatRow> rows = daoSRw.getRows("model-test-1");
        if (rows.size() == 3)
            flag = true;

        assertEquals(true, flag);
    }

    @Test
    public void testMultimedias() {

        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        FlightImp daoFlg = DaoImp.getDaoFlight(context);
        TypeMultimediaImp daoType = DaoImp.getDaoTypeMult(context);

//        TypeMultimedia type = new TypeMultimedia("audio", "pathprueba");
//        daoType.insert(type);

        Multimedia mult = new Multimedia(1, "something", (new Date()).getTime(), daoType.getById(1));
//        daoFlg.insertMultimedia(mult);

        Multimedia aux = daoFlg.getMultimedia(1, 1);

        if (aux.getName().equals(mult.getName()))
            flag = true;

        assertEquals(true, flag);
    }

    @Test
    public void testCGmass() {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        ModelImp daoMod = DaoImp.getDaoModel(context);

        CgMass cgMass = new CgMass(85, "model-test-1", 1234.3);
        CgMass cgMass2 = new CgMass(86, "model-test-1", 1234.3);

//        daoMod.insertCg(cgMass);
//        daoMod.insertCg(cgMass2);

        List<CgMass> masses = daoMod.getCg("model-test-1");

        if (masses.size() == 2) {
            flag = true;
        }

        assertEquals(true, flag);
    }

//    @Test
//    public void testFlight() {
//
//        Context context = InstrumentationRegistry.getTargetContext();
//        boolean flag = false;
//
//        FlightImp daoFlg = DaoImp.getDaoFlight(context);
//
//        Model model = new Model("model-test-1", 1000, 35, 200, 40, 2000, 120);
//        Plane plane = new Plane("12345", "model-test-1");
//
////        Flight flight = new Flight((new Date()).getTime(), (new Date()).getTime(), 123, 200, plane);
//
////        DaoImp.getDaoModel(context).insert(model);
////        DaoImp.getDaoPlane(context).insert(plane);
////        daoFlg.insert(flight);
//
//        Flight aux = daoFlg.getById(1);
//
//        if (aux.getPlane().equals(flight.getPlane())) {
//            flag = true;
//        }
//
//        assertEquals(true, flag);
//    }

//    public List<Double> weightPass(String nameModel) {
//        List<Double> passengers = new ArrayList<>();
//        try {
//
//            ModelImp modelImp = DaoImp.getDaoModel(getApplicationContext());
//            int num = modelImp.getRows(nameModel).size();
//            for(int i=0; i<num; i++) {
//                EditText editText = (EditText) lyEtSeats.getChildAt(i);
//                passengers.add( Double.parseDouble( editText.getText().toString() ) );
//                Log.i("passengers",editText.getText().toString());
//            }
//
//        } catch (Exception e) {
//            Log.e("Insert seats",e.getMessage()+" ");
//        }
//        return passengers;
//    }
//
//    public void chargeET(String nameModel) {
//
//        try {
//
//            lyEtSeats.removeAllViews();
//
//            int num = DaoImp.getDaoModel(getApplicationContext()).getRows(nameModel).size();
//            for(int i=0; i<num; i++) {
//                EditText editText = new EditText(getApplicationContext());
//                editText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//                lyEtSeats.addView( editText );
//            }
//
//        } catch (Exception e) {
//            Log.e("Seats",e.getMessage()+" ");
//        }
//
//    }

    @Test
    public void testLastRow() {
        Context context = InstrumentationRegistry.getTargetContext();
        assertEquals(0, DaoImp.getDaoFlight(context).getLastMultimedia(2));
    }

    @Test
    public void testDao() {

        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        //data to insert
        Model model = new Model("model-test-1", 1000, 35, 200, 40, 2000, 120);
        Plane plane = new Plane("12345", "model-test-1");

        DaoImp.getDaoModel(context).insert(model);
        DaoImp.getDaoPlane(context).insert(plane);

//        Model aux = DaoImp.getDaoModel(context).getById("model-test-1");
//
//        if(model.getBew() == aux.getBew()) {
//            flag = true;
//        } --> correct

        Plane aux = DaoImp.getDaoPlane(context).getById("12345");
        if (plane.getNumberPlate().equals(aux.getNumberPlate())) {
            flag = true;
        }

        assertEquals(true, flag);
    }
}