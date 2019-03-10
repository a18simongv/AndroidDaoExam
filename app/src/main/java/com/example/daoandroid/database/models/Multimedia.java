package com.example.daoandroid.database.models;

import java.sql.Date;

public class Multimedia {

    //compose id - idMult - idFlightt
    private int idMult, idFlight;

    private String name;
    //date done
    private Date date;
    //type of multimedia
    private TypeMultimedia type;

    public Multimedia() {}
    public Multimedia(int idMult, int idFlight, String name, Date date, TypeMultimedia type) {
        this.idMult = idMult;
        this.idFlight = idFlight;
        this.name = name;
        this.date = date;
        this.type = type;
    }

    public int getIdMult() {
        return idMult;
    }
    public void setIdMult(int idMult) {
        this.idMult = idMult;
    }
    public int getidFlight() {
        return idFlight;
    }
    public void setidFlightt(int idFlightt) {
        this.idFlight = idFlight;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public TypeMultimedia getType() {
        return type;
    }
    public void setType(TypeMultimedia type) {
        this.type = type;
    }
    public String getName() { return name;}
    public void setName(String name) { this.name = name;}
}
