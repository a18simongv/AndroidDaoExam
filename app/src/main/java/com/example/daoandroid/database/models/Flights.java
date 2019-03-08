package com.example.daoandroid.database.models;

import java.sql.Date;

public class Flights {

    //identifier autoincrement
    private int id;

    //dates
    private Date dateInit, dateFin;
    //fuel and baggage charge
    private double fuel, baggage;
    //plane do it
    private Plane plane;

    public Flights(int id, Date dateInit, Date dateFin, double fuel, double baggage, String numberPlate) {
        this.id = id;
        this.dateInit = dateInit;
        this.dateFin = dateFin;
        this.fuel = fuel;
        this.baggage = baggage;
        //plane = dao.getById()
    }
    public Flights(Date dateInit, Date dateFin, double fuel, double baggage, Plane plane) {
        this.dateInit = dateInit;
        this.dateFin = dateFin;
        this.fuel = fuel;
        this.baggage = baggage;
        this.plane = plane;
    }
    public Flights(int id, Date dateInit, Date dateFin, double fuel, double baggage, Plane plane) {
        this.id = id;
        this.dateInit = dateInit;
        this.dateFin = dateFin;
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
