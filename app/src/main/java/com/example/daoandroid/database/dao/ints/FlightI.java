package com.example.daoandroid.database.dao.ints;

import com.example.daoandroid.database.models.Flight;
import com.example.daoandroid.database.models.Multimedia;

import java.util.List;

public interface FlightI extends DaoI<Flight,Integer> {

    int getLastMultimedia(int idFlight);
    Multimedia getMultimedia(int idFlight, int idMult);
    List<Multimedia> listMultimedia(int idFlight);
    boolean insertMultimedia(Multimedia multimedia);

}
