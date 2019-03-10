package com.example.daoandroid.database.dao.daoimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daoandroid.database.Dbs;
import com.example.daoandroid.database.dao.ints.DaoI;
import com.example.daoandroid.database.dao.ints.ModelI;
import com.example.daoandroid.database.models.CgMass;
import com.example.daoandroid.database.models.Model;
import com.example.daoandroid.database.models.SeatRow;

import java.util.ArrayList;
import java.util.List;

public class ModelImp implements ModelI {

    private SQLiteDatabase database;

    public ModelImp(Context context) {
        database = Dbs.getInstance(context);
    }

    @Override
    public SeatRow getRow(String nameModel, int row) {
        SeatRow seatRow = null;

        Cursor cursor = database.rawQuery("select * from seat_rows where name_model=? and num_row=?",
                new String[]{nameModel, String.valueOf(row)});
        if (cursor.moveToFirst()) {
            seatRow = new SeatRow(row,nameModel,cursor.getInt(2),cursor.getDouble(3));
        }
        return seatRow;
    }
    @Override
    public int getLastRow(String nameModel) {
        int rows = 0;
        Cursor cursor = database.rawQuery("select count() from seat_rows where name_model=?", new String[]{nameModel});
        if (cursor.moveToFirst()) {
            rows = cursor.getInt(0);
        }
        return rows;
    }
    @Override
    public boolean insertRow(SeatRow seatRow) {
        ContentValues values = new ContentValues();
        int row = getLastRow(seatRow.getNameModel()) + 1;
        values.put("num_row",row);
        values.put("name_model",seatRow.getNameModel());
        values.put("num_seats",seatRow.getNumberSeats());
        values.put("row_arm",seatRow.getRowArm());

        database.insert("seat_rows",null,values);
        return true;
    }
    @Override
    public List<SeatRow> getRows(String nameModel) {
        List<SeatRow> seatRows = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from seat_rows", new String[]{});
        while (cursor.moveToNext()) {
            SeatRow seatRow = new SeatRow(cursor.getInt(0),nameModel,cursor.getInt(2),cursor.getDouble(3));
            seatRows.add(seatRow);
        }
        return seatRows;
    }

    @Override
    public boolean insertCg(CgMass cgMass) {

        ContentValues values = new ContentValues();
        values.put("cg",cgMass.getCg());
        values.put("name_model",cgMass.getNameModel());
        values.put("mass",cgMass.getMass());

        database.insert("cg_mass",null,values);

        return true;
    }
    @Override
    public List<CgMass> getCg(String nameModel) {
        List<CgMass> cgMasses = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from models", new String[]{});
        while (cursor.moveToNext()) {
            CgMass cgMass = new CgMass(cursor.getInt(0),nameModel,cursor.getDouble(2));
            cgMasses.add(cgMass);
        }
        return cgMasses;
    }

    @Override
    public Model getById(String id) {
        Model model = null;
        Cursor cursor = database.rawQuery("select * from models where name_model=?", new String[]{id});
        if (cursor.moveToFirst()) {
            model = new Model(id, cursor.getDouble(1),cursor.getDouble(2),
                    cursor.getDouble(3), cursor.getDouble(4),
                    cursor.getDouble(5), cursor.getDouble(6));
        }
        return model;
    }

    @Override
    public List<Model> listAll() {
        List<Model> models = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from models", new String[]{});
        while (cursor.moveToNext()) {
            Model model = new Model(cursor.getString(0), cursor.getDouble(1),cursor.getDouble(2),
                    cursor.getDouble(3), cursor.getDouble(4),
                    cursor.getDouble(5), cursor.getDouble(6));
            models.add(model);
        }
        return models;
    }

    @Override
    public boolean insert(Model model) {

        ContentValues values = new ContentValues();
        values.put("name_model",model.getNameModel());
        values.put("fuel_max",model.getFuelMax());
        values.put("fuel_arm",model.getFuelArm());
        values.put("baggage_max",model.getBaggageMax());
        values.put("baggage_arm",model.getBaggageArm());
        values.put("basic_empty_weight",model.getBew());
        values.put("basic_empty_weight_arm",model.getBewArm());

        long num = database.insert("models",null,values);

        return true;
    }
}
