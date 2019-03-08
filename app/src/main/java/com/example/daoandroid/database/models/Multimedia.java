package com.example.daoandroid.database.models;

import java.sql.Date;

public class Multimedia {

    //compose id - idMult - idFlight
    private int idMult, idFlight;

    //date done
    private Date date;
    //type of multimedia
    private TypeMultimedia type;

    public Multimedia() {}
    public Multimedia(int idMult, int idFlight, Date date, TypeMultimedia type) {
        this.idMult = idMult;
        this.idFlight = idFlight;
        this.date = date;
        this.type = type;
    }

    public int getIdMult() {
        return idMult;
    }
    public void setIdMult(int idMult) {
        this.idMult = idMult;
    }
    public int getIdFlight() {
        return idFlight;
    }
    public void setIdFlight(int idFlight) {
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

}
