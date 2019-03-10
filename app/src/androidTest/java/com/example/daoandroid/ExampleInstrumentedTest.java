package com.example.daoandroid;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.daoandroid.database.dao.daoimp.DaoImp;
import com.example.daoandroid.database.dao.ints.DaoI;
import com.example.daoandroid.database.dao.ints.ModelI;
import com.example.daoandroid.database.models.Model;
import com.example.daoandroid.database.models.Plane;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void testLastRow() {

//        boolean flag;

        Context context = InstrumentationRegistry.getTargetContext();

//        assertEquals(0, DaoImp.getDaoModel(context).getLastRow("algo"));
        assertEquals(0, DaoImp.getDaoFlight(context).getLastMultimedia(2));
    }

    @Test
    public void testDao(){

        //data to insert
        Model model = new Model("model-test-1",1000,35,200,40,2000,120);
        Plane plane = new Plane("12345",model);

        Context context = InstrumentationRegistry.getTargetContext();

        DaoImp.getDaoModel(context).insert(model);
        DaoImp.getDaoPlane(context).insert(plane);

        boolean flag = false;
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
