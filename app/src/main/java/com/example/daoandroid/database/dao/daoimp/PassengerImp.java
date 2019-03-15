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

import java.util.ArrayList;
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
    public int numRows(String nameModel) {
        return DaoImp.getDaoModel(context).getRows(nameModel).size();
    }

    @Override
    public List<Passenger> listAll(int flight) {
        List<Passenger> passengers = new ArrayList<>();

        FlightImp flightImp = DaoImp.getDaoFlight(context);
        PlaneImp planeImp = DaoImp.getDaoPlane(context);

        String model =  planeImp.getById( flightImp.getById(flight).getPlane() ).getModel();
        Cursor cursor = database.rawQuery("select * from passengers where id_flight=? and name_model=?",
                new String[] { String.valueOf( flight ), model });
        while (cursor.moveToNext()) {
            Passenger passenger = new Passenger(DaoImp.getDaoModel(context).getRow(model, cursor.getInt(1)),
                    flight, cursor.getDouble(3));
            passengers.add(passenger);
        }

        return passengers;
    }

    @Override
    public boolean insert(Passenger passenger) {

        try {
            ContentValues values = new ContentValues();
            values.put("name_model", passenger.getSeatRow().getNameModel());
            values.put("seat_row", passenger.getSeatRow().getRow());
            values.put("id_flight", passenger.getFlight());
            values.put("weight", passenger.getWeigh());

            long num = database.insert("passengers", null, values);

            if (num < 0) {
                return false;
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
