package com.example.daoandroid.database.dao.daoimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daoandroid.database.Dbs;
import com.example.daoandroid.database.dao.ints.DaoI;
import com.example.daoandroid.database.models.TypeMultimedia;

import java.util.ArrayList;
import java.util.List;

public class TypeMultimediaImp implements DaoI<TypeMultimedia, Integer> {

    private SQLiteDatabase database = null;
    private Context context;

    public TypeMultimediaImp(Context context) {
        this.context = context;
        database = Dbs.getInstance(context);
    }

    @Override
    public TypeMultimedia getById(Integer id) {
        TypeMultimedia typeMultimedia = null;

        Cursor cursor = database.rawQuery("select * from type_multimedia where id=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            typeMultimedia = new TypeMultimedia(id, cursor.getString(1), cursor.getString(2));
        }

        return typeMultimedia;
    }

    @Override
    public List<TypeMultimedia> listAll() {
        List<TypeMultimedia> typeMultimediaList = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from type_multimedia", null);
        if (cursor.moveToNext()) {
            TypeMultimedia typeMultimedia = new TypeMultimedia(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            typeMultimediaList.add(typeMultimedia);
        }

        return typeMultimediaList;
    }

    @Override
    public boolean insert(TypeMultimedia typeMultimedia) {

        //id autoincrement
        ContentValues values = new ContentValues();
        values.put("type", typeMultimedia.getType());
        values.put("path", typeMultimedia.getPath());

        long num = database.insert("type_multimedia", null, values);

        if (num < 0) {
            return false;
        }

        return true;
    }
}
