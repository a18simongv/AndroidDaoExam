package com.example.daoandroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbs extends SQLiteOpenHelper {

    //singleton database
    private static SQLiteDatabase db = null;

    public static synchronized void createInstace(Context context) {
        Dbs dbs = new Dbs(context);
        db = dbs.getWritableDatabase();
    }

    public static SQLiteDatabase getInstance(Context context) {
        if(db==null) createInstace(context);
        return db;
    }

    public Dbs(Context context) {
        super(context, "plane", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tableModel);
        db.execSQL(tableSmallPlane);
        db.execSQL(tableCGMass);
        db.execSQL(tableSeatRows);
        db.execSQL(tableFlights);
        db.execSQL(tablePassengers);
        db.execSQL(tableMultimedia);
        db.execSQL(tableType);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //script creación base datos
    private final String tableModel = "CREATE TABLE models (" +
            "name_model VARCHAR (50) PRIMARY KEY," +
            "fuel_max DOUBLE," +
            "fuel_arm DOUBLE," +
            "baggage_max DOUBLE," +
            "baggage_arm DOUBLE," +
            "basic_empty_weight DOUBLE," +
            "basic_empty_weight_arm DOUBLE" +
            ");";
    private final String tableSmallPlane = "CREATE TABLE planes (" +
            "number_plate VARCHAR (50) PRIMARY KEY," +
            "name_model VARCHAR (50) REFERENCES models (id)" +
            ");";
    private final String tableCGMass = "CREATE TABLE cg_mass (" +
            "cg INTEGER not null," +
            "name_model VARCHAR (50) REFERENCES models (id) not null," +
            "mass DOUBLE," +
            "primary key (cg,name_model)" +
            ");";
    private final String tableSeatRows = "CREATE TABLE seat_rows (" +
            "num_row INTEGER," +
            "name_model VARCHAR (50) REFERENCES models (id) ," +
            "num_seats INTEGER," +
            "row_arm DOUBLE," +
            "PRIMARY KEY (num_row,name_model)" +
            ");";
    private final String tableFlights = "CREATE TABLE flights (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "plane VARCHAR (50) REFERENCES planes (number_plate)," +
            "date_init DATETIME," +
            "date_fin DATETIME," +
            "fuel DOUBLE," +
            "baggage DOUBLE" +
            ");";
    private final String tablePassengers = "CREATE TABLE passengers (" +
            "name_model varchar (50) REFERENCES models (name_model)," +
            "seat_row INTEGER REFERENCES seat_rows (num_row)," +
            "id_flight INTEGER REFERENCES flights (id)," +
            "weight DOUBLE," +
            "PRIMARY KEY (id_flight,seat_row,name_model)" +
            ");";
    private final String tableMultimedia = "CREATE TABLE multimedia (" +
            "id_multimedia INTEGER," +
            "id_flight INTEGER REFERENCES flights (id)," +
            "type INTEGER REFERENCES type_multimedia (id)," +
            "name VARCHAR (50)," +
            "date DATETIME," +
            "PRIMARY KEY (id_flight,id_multimedia)" +
            ");";
    private final String tableType = "CREATE TABLE type_multimedia (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "type VARCHAR (20)," +
            "path TEXT" +
            ");";
}
