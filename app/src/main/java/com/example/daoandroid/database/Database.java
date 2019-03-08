package com.example.daoandroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {
    private static SQLiteDatabase db = null;

    public static synchronized void createInstace(Context context) {
        Dbs dbs = new Dbs(context);
        db = dbs.getWritableDatabase();
    }

    public static SQLiteDatabase getInstance(Context context) {
        if(db==null) createInstace(context);
        return db;
    }

    private Database() {}
}
