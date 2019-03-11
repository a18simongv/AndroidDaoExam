package com.example.daoandroid.database.dao.daoimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daoandroid.database.Dbs;
import com.example.daoandroid.database.dao.ints.DaoI;
import com.example.daoandroid.database.dao.ints.PassengerI;
import com.example.daoandroid.database.models.Flight;
import com.example.daoandroid.database.models.Model;
import com.example.daoandroid.database.models.Passenger;

import java.util.List;

public class PassengerImp implements PassengerI {

    private SQLiteDatabase database = null;
    private Context context;

    public PassengerImp(Context context) {
        this.context = context;
        database = Dbs.getInstance(context);
    }

    @Override
    public Passenger getById(Flight flight) {
        return null;
    }
    @Override
    public List<Passenger> listAll() {
        return null;
    }

    @Override
    public List<Passenger> listAll(Flight flight) {
        List<Passenger> passengers = null;

        Model model = flight.getPlane().getModel();
        Cursor cursor = database.rawQuery("select * from passengers where id_flight=? and name_model=?",
                new String[] { String.valueOf( flight.getId() ), model.getNameModel() });
        if (cursor.moveToNext()) {
            Passenger passenger = new Passenger(DaoImp.getDaoModel(context).getRow(model.getNameModel(), cursor.getInt(0)),
                    flight.getId(), cursor.getDouble(3));
            passengers.add(passenger);
        }

        return passengers;
    }

    @Override
    public boolean insert(Passenger passenger) {

        ContentValues values = new ContentValues();
        values.put("name_model",passenger.getSeatRow().getNameModel());
        values.put("seat_row",passenger.getSeatRow().getRow());
        values.put("id_flight",passenger.getFlight());
        values.put("weight",passenger.getWeigh());

        long num = database.insert("passengers",null,values);

        if (num < 0) {
            return false;
        }

        return false;
    }
}
