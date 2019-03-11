package com.example.daoandroid.database.dao.daoimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daoandroid.database.Dbs;
import com.example.daoandroid.database.dao.ints.DaoI;
import com.example.daoandroid.database.models.Plane;

import java.util.ArrayList;
import java.util.List;

public class PlaneImp implements DaoI<Plane, String> {

    private SQLiteDatabase database = null;
    private Context context;

    public PlaneImp(Context context) {
        this.context = context;
        database = Dbs.getInstance(context);
    }

    @Override
    public Plane getById(String id) {
        Plane plane = null;
        Cursor cursor = database.rawQuery("select * from planes where number_plate=?", new String[]{id});
        if (cursor.moveToFirst()) {
            plane = new Plane(id, DaoImp.getDaoModel(context).getById(cursor.getString(1)));
        }
        return plane;
    }

    @Override
    public List<Plane> listAll() {
        List<Plane> planes = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from planes", new String[]{});
        while (cursor.moveToNext()) {
            Plane plane = new Plane(cursor.getString(0), DaoImp.getDaoModel(context).getById(cursor.getString(1)));
            planes.add(plane);
        }

        return planes;
    }

    @Override
    public boolean insert(Plane plane) {
        ContentValues values = new ContentValues();
        values.put("number_plate", plane.getNumberPlate());
        values.put("name_model", plane.getModel().getNameModel());

        long num = database.insert("planes", null, values);

        if (num < 0) {
            return false;
        }

        return true;
    }
}
