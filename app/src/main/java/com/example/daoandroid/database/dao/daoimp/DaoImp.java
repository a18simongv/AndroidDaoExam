package com.example.daoandroid.database.dao.daoimp;

import android.content.Context;

import com.example.daoandroid.database.models.Plane;

public class DaoImp {
    //class to return daos of tables

    public static PlaneImp getDaoPlane(Context context) { return new PlaneImp(context);}
    public static ModelImp getDaoModel(Context context) { return new ModelImp(context);}
    public static FlightImp getDaoFlight(Context context) { return new FlightImp(context);}
    public static TypeMultimediaImp getDaoTypeMult(Context context) { return new TypeMultimediaImp(context);}
    public static PassengerImp getDaoPassenger(Context context) { return new PassengerImp(context);}

}
