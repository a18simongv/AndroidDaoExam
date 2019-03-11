package com.example.daoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.dao.daoimp.FlightImp;
import com.example.daoandroid.database.dao.daoimp.ModelImp;
import com.example.daoandroid.database.dao.ints.DaoI;
import com.example.daoandroid.database.dao.ints.ModelI;
import com.example.daoandroid.database.models.CgMass;
import com.example.daoandroid.database.models.Flight;
import com.example.daoandroid.database.models.Model;
import com.example.daoandroid.database.models.Plane;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

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
    public void testCGmass() {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        ModelImp daoMod = DaoImp.getDaoModel(context);

//        CgMass cgMass = new CgMass(85,"model-test-1",1234.3);
//        CgMass cgMass2 = new CgMass(86,"model-test-1",1234.3);

//        daoMod.insertCg(cgMass);
//        daoMod.insertCg(cgMass2);

        List<CgMass> masses = daoMod.getCg("model-test-1");

        if(masses.size()==2){
            flag=true;
        }

        assertEquals(true,flag);
    }

    @Test
    public void testFlight() {

        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        FlightImp daoFlg = DaoImp.getDaoFlight(context);

        Model model = new Model("model-test-1",1000,35,200,40,2000,120);
        Plane plane = new Plane("12345",model);

        Flight flight = new Flight( (new Date()).getTime(), (new Date()).getTime(),123,200,plane);

//        DaoImp.getDaoModel(context).insert(model);
//        DaoImp.getDaoPlane(context).insert(plane);
//        daoFlg.insert(flight);

        Flight aux = daoFlg.getById(1);

        if(aux.getPlane().getNumberPlate().equals( flight.getPlane().getNumberPlate() )){
            flag = true;
        }

        assertEquals(true,flag);
    }

    @Test
    public void testLastRow() {
        Context context = InstrumentationRegistry.getTargetContext();
        assertEquals(0, DaoImp.getDaoFlight(context).getLastMultimedia(2));
    }

    @Test
    public void testDao(){

        Context context = InstrumentationRegistry.getTargetContext();
        boolean flag = false;

        //data to insert
        Model model = new Model("model-test-1",1000,35,200,40,2000,120);
        Plane plane = new Plane("12345",model);

        DaoImp.getDaoModel(context).insert(model);
        DaoImp.getDaoPlane(context).insert(plane);

//        Model aux = DaoImp.getDaoModel(context).getById("model-test-1");
//
//        if(model.getBew() == aux.getBew()) {
//            flag = true;
//        } --> correct

        Plane aux = DaoImp.getDaoPlane(context).getById("12345");
        if(plane.getNumberPlate().equals( aux.getNumberPlate() )) {
            flag = true;
        }

        assertEquals(true,flag);
    }
}