package com.example.daoandroid.database.dao.daoimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daoandroid.database.Dbs;
import com.example.daoandroid.database.dao.ints.FlightI;
import com.example.daoandroid.database.models.Flight;
import com.example.daoandroid.database.models.Multimedia;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FlightImp implements FlightI {

    private SQLiteDatabase database = null;
    private Context context;

    public FlightImp(Context context) {
        this.context = context;
        database = Dbs.getInstance(context);
    }

    @Override
    public int getLastMultimedia(int idFlight) {
        int numMultimedia = 0;

        Cursor cursor = database.rawQuery("select count() from multimedia where id_flight=?",
                new String[] {String.valueOf(idFlight)});
        if (cursor.moveToFirst()) {
            numMultimedia = cursor.getInt(0);
        }

        return numMultimedia;
    }
    @Override
    public Multimedia getMultimedia(int idFlight, int idMult) {
        Multimedia multimedia = null;

        Cursor cursor = database.rawQuery("select * from multimedia where id_multimedia=? and id_flight=?",
                new String[] { String.valueOf(idMult),String.valueOf(idFlight)});
        if (cursor.moveToFirst()) {
            multimedia = new Multimedia(idMult, idFlight, cursor.getString(3),
                    cursor.getInt(4), DaoImp.getDaoTypeMult(context).getById( cursor.getInt(2) ) );
        }

        return multimedia;
    }
    @Override
    public List<Multimedia> listMultimedia(int idFlight) {
        List<Multimedia> multimediaList = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from multimedia", null);
        if (cursor.moveToNext()) {
            Multimedia multimedia = new Multimedia(cursor.getInt(0), idFlight, cursor.getString(2),
                    cursor.getInt(3), DaoImp.getDaoTypeMult(context).getById( cursor.getInt(4) ));
            multimediaList.add(multimedia);
        }

        return multimediaList;
    }
    @Override
    public boolean insertMultimedia(Multimedia multimedia) {

        ContentValues values = new ContentValues();
        values.put("id_flight",multimedia.getidFlight());
        int idMult = getLastMultimedia( multimedia.getidFlight() ) + 1;
        values.put("id_multimedia", idMult );
        values.put("type", multimedia.getType().getId());
        values.put("date", multimedia.getDate().toString());
        values.put("name", multimedia.getName());

        long num = database.insert("multimedia",null,values);

        if (num < 0) {
            return false;
        }

        return true;
    }

    @Override
    public Flight getById(Integer id) {
        Flight flight = null;

        Cursor cursor = database.rawQuery("select * from flights where id=?",new String[] { String.valueOf(id)});
        if (cursor.moveToFirst()) {
            flight = new Flight(id, new Date(cursor.getLong(2)),
                    new Date(cursor.getLong(2)), cursor.getDouble(4), cursor.getDouble(5),
                    cursor.getString(1) );
        }

        return flight;
    }

    @Override
    public List<Flight> listAll() {
        List<Flight> flights = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from flights", new String[]{});
        while (cursor.moveToNext()) {
            Flight flight = new Flight(cursor.getInt(0),new Date(cursor.getLong(2)),
                    new Date(cursor.getLong(2)), cursor.getDouble(4), cursor.getDouble(5),
                    cursor.getString(1) );
            flights.add(flight);
        }

        return flights;
    }

    @Override
    public int lastFlight() {
        int last = 0;

        Cursor cursor = database.rawQuery("select count() from flights", new String[]{});
        if (cursor.moveToFirst()) {
            last = cursor.getInt(0);
        }

        return last;
    }

    @Override
    public void finalizeFlight(int idFlight) {
        ContentValues values = new ContentValues();
        values.put("date_fin", (new Date(Calendar.getInstance().getTimeInMillis())).toString() );
        database.update("flights",values,"id=?",new String[] {String.valueOf(idFlight)});
    }

    @Override
    public boolean insert(Flight flight) {

        //id autoincrement
        ContentValues values = new ContentValues();
        values.put("plane",flight.getPlane());
        values.put("date_init",flight.getDateInit().toString());
        values.put("date_fin",flight.getDateFin()==null? null : flight.getDateFin().toString());
        values.put("fuel",flight.getFuel());
        values.put("baggage",flight.getBaggage());

        long num = database.insert("flights",null,values);

        if (num < 0) {
            return false;
        }

        return true;
    }
}
