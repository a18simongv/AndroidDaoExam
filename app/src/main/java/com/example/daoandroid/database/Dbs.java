package com.example.daoandroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbs extends SQLiteOpenHelper {

    //script creación base datos
    private String tableModel = "CREATE TABLE models (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name_model VARCHAR (50)," +
            "fuel_max DOUBLE," +
            "fuel_arm DOUBLE," +
            "baggage_max DOUBLE," +
            "baggage_arm DOUBLE," +
            "basic_empty_weight DOUBLE," +
            "basic_empty_weight_arm DOUBLE" +
            ");";
    private String tableSmallPlane = "CREATE TABLE small_planes (" +
            "number_plate VARCHAR (50) PRIMARY KEY," +
            "id_model INTEGER REFERENCES models (id)" +
            ");";
    private String tableCGMass = "CREATE TABLE cg_mass (" +
            "cg INTEGER not null," +
            "id_model INTEGER REFERENCES models (id) not null," +
            "mass DOUBLE," +
            "primary key (cg,id_model)" +
            ");";
    private String tableSeatRows = "CREATE TABLE seat_rows (" +
            "num_row INTEGER," +
            "id_model INTEGER REFERENCES models (id) ," +
            "num_seats INTEGER," +
            "row_arm DOUBLE," +
            "PRIMARY KEY (num_row,id_model)" +
            ");";
    private String tableFlights = "CREATE TABLE flights (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "small_plane VARCHAR (50) REFERENCES small_planes (number_plate)," +
            "date_init DATETIME," +
            "date_fin DATETIME," +
            "fuel DOUBLE," +
            "baggage DOUBLE" +
            ");";
    private String tablePassengers = "CREATE TABLE passengers (" +
            "seat_row INTEGER REFERENCES seat_rows (num_row)," +
            "id_flight INTEGER REFERENCES flights (id)," +
            "weight DOUBLE," +
            "PRIMARY KEY (id_flight,seat_row)" +
            ");";
    private String tableMultimedia = "CREATE TABLE multimedia (" +
            "id_multimedia INTEGER," +
            "id_flight INTEGER REFERENCES flights (id)," +
            "type INTEGER REFERENCES type_multimedia (id)," +
            "date DATETIME," +
            "PRIMARY KEY (id_flight,id_multimedia)" +
            ");";
    private String tableType = "CREATE TABLE type_multimedia (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "type VARCHAR (20)," +
            "path TEXT" +
            ");";

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
}
