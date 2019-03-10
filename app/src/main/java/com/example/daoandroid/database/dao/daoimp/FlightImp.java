package com.example.daoandroid.database.dao.daoimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daoandroid.database.Dbs;
import com.example.daoandroid.database.dao.ints.FlightI;
import com.example.daoandroid.database.models.Flight;
import com.example.daoandroid.database.models.Multimedia;

import java.sql.Date;
import java.util.ArrayList;
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
            multimedia = new Multimedia(idMult, idFlight, cursor.getString(2),
                    Date.valueOf(cursor.getString(3)), DaoImp.getDaoTypeMult(context).getById( cursor.getInt(4) ) );
        }

        return multimedia;
    }
    @Override
    public List<Multimedia> listMultimedia(int idFlight) {
        List<Multimedia> multimediaList = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from multimedia", null);
        if (cursor.moveToNext()) {
            Multimedia multimedia = new Multimedia(cursor.getInt(0), idFlight, cursor.getString(2),
                    Date.valueOf(cursor.getString(3)), DaoImp.getDaoTypeMult(context).getById( cursor.getInt(4) ));
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

        database.insert("multimedia",null,values);
        return true;
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
