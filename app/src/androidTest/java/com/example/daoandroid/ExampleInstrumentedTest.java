package com.example.daoandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.daoandroid.database.Database;

import org.junit.Before;
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
    public void testSingleton() {

//        boolean flag;

        Context context = InstrumentationRegistry.getTargetContext();
        SQLiteDatabase database = Database.getInstance( context );
        SQLiteDatabase database2 = Database.getInstance( context );

//        if(database!=null) {
//            flag = true;
//        } else {
//            flag = false;
//        }

        assertEquals(database,database2);
    }
}
