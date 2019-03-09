package com.example.daoandroid.database.dao.daoimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daoandroid.database.Dbs;
import com.example.daoandroid.database.dao.ints.DaoI;
import com.example.daoandroid.database.models.CgMass;
import com.example.daoandroid.database.models.Flight;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FlightImp implements DaoI<Flight,Integer> {

    private SQLiteDatabase database = null;
    private Context context;

    public FlightImp(Context context) {
        this.context = context;
        database = Dbs.getInstance(context);
    }

    @Override
    public Flight getById(Integer id) {
        Flight flight = null;

        Cursor cursor = database.rawQuery("select * from Flights where id=?",new String[] { String.valueOf(id)});
        if (cursor.moveToFirst()) {
            flight = new Flight(id, Date.valueOf(cursor.getString(1)),
                    Date.valueOf(cursor.getString(2)), cursor.getDouble(3), cursor.getDouble(4),
                    DaoImp.getDaoPlane(context).getById(cursor.getString(5)) );
        }

        return flight;
    }

    @Override
    public List<Flight> listAll() {
        List<Flight> flights = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from models", new String[]{});
        while (cursor.moveToNext()) {
            Flight flight = new Flight(cursor.getInt(0), Date.valueOf(cursor.getString(1)),
                    Date.valueOf(cursor.getString(2)), cursor.getDouble(3), cursor.getDouble(4),
                    DaoImp.getDaoPlane(context).getById(cursor.getString(5)) );
            flights.add(flight);
        }

        return flights;
    }

    @Override
    public boolean insert(Flight flight) {

        //id autoincrement
        ContentValues values = new ContentValues();
        values.put("plane",flight.getPlane().getNumberPlate());
        values.put("date_init",flight.getDateInit().toString());
        values.put("date_fin",flight.getDateFin().toString());
        values.put("fuel",flight.getFuel());
        values.put("baggage",flight.getBaggage());

        database.insert("flights",null,values);

        return true;
    }
}
