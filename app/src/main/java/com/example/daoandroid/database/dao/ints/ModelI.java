package com.example.daoandroid.database.dao.ints;

import com.example.daoandroid.database.models.CgMass;
import com.example.daoandroid.database.models.Model;
import com.example.daoandroid.database.models.SeatRow;

import java.util.List;

public interface ModelI extends DaoI<Model,String> {

    SeatRow getRow(String nameModel, int row);
    List<SeatRow> getRows(String nameModel);
    List<CgMass> getCg(String nameModel);

    int getNumRow(String nameModel);

    boolean insertRow(SeatRow seatRow);
    boolean insertCg(CgMass cgMass);

}
