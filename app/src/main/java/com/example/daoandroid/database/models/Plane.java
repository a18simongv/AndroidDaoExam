package com.example.daoandroid.database.models;

public class Plane {

    //plane identifier
    private String numberPlate;

    //model of plane
    private String model;

    public Plane(String numberPlate, String model) {
        this.numberPlate = numberPlate;
        this.model = model;
    }


    public String getNumberPlate() {
        return numberPlate;
    }
    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
}
