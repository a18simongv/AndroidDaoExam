package com.example.daoandroid.database.dao.ints;

import com.example.daoandroid.database.models.Flight;
import com.example.daoandroid.database.models.Passenger;

import java.util.List;

public interface PassengerI extends DaoI<Passenger, Flight> {


    List<Passenger> listAll(int flight);
    int numRows(String nameModel);

}
