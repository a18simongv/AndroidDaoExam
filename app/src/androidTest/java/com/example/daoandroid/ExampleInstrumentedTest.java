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
    public void testCGS() {

        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        String nameModel = "PA-28-161";
        CgMass cg = null;
        List<CgMass> cgs = new ArrayList<>();

        Utils.download("https://informatica.iessanclemente.net/pmdm/CGvsW-" + nameModel + ".xml", new File(context.getFilesDir(), "CGvsW-" + nameModel + ".xml"));

        try {
            InputStream is = new FileInputStream(new File(context.getFilesDir(), "CGvsW-" + nameModel + ".xml"));

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "UTF-8");
            int evento = -6;
            while (evento != XmlPullParser.END_DOCUMENT) {

                evento = parser.nextTag();
                if (evento == XmlPullParser.START_TAG) {

                    switch (parser.getName()) {
                        case "cg":
                            cg = new CgMass(nameModel);
                            parser.nextTag();
                            cg.setCg(Integer.parseInt(parser.nextText()));
                            parser.nextTag();
                            cg.setMass(Double.parseDouble(parser.nextText()));

                            cgs.add(cg);
                    }
                }

                evento = parser.next();
                if (evento == XmlPullParser.END_DOCUMENT) {
                    if(cgs.get( cgs.size()-1 ).getMass() == 2325)
                        flag=true;
                }
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        assertEquals(true,flag);
    }

    @Test
    public void noCgs() {

        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        if( DaoImp.getDaoModel(context).getCg("PA-28-161").size() == 0 ) {
            flag = true;
        }

        assertEquals(true,flag);
    }

    @Test
    public void noModel() {

        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        if( DaoImp.getDaoModel(context).getById("PA-28-161") == null ) {
            flag = true;
        }

        assertEquals(true,flag);
    }

}