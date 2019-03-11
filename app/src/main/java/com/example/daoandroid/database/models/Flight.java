package com.example.daoandroid.database.models;

import com.example.daoandroid.database.dao.daoimp.DaoImp;

import java.sql.Date;

public class Flight {

    //identifier autoincrement
    private int id;

    //dates
    private Date dateInit, dateFin;
    //fuel and baggage charge
    private double fuel, baggage;
    //plane do it
    private Plane plane;

    public Flight(long dateInit, long dateFin, double fuel, double baggage, Plane plane) {
        this.dateInit = new Date(dateInit);
        this.dateFin = new Date(dateFin);
        this.fuel = fuel;
        this.baggage = baggage;
        this.plane = plane;
    }
    public Flight(int id, long dateInit, long dateFin, double fuel, double baggage, Plane plane) {
        this.id = id;
        this.dateInit = new Date(dateInit);
        this.dateFin = new Date(dateFin);
        this.fuel = fuel;
        this.baggage = baggage;
        this.plane = plane;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDateInit() {
        return dateInit;
    }
    public void setDateInit(Date dateInit) {
        this.dateInit = dateInit;
    }
    public Date getDateFin() {
        return dateFin;
    }
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    public double getFuel() {
        return fuel;
    }
    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
    public double getBaggage() {
        return baggage;
    }
    public void setBaggage(double baggage) {
        this.baggage = baggage;
    }
    public Plane getPlane() {
        return plane;
    }
    public void setPlane(Plane plane) {
        this.plane = plane;
    }

}
