package com.example.daoandroid.database.models;

public class Plane {

    //plane identifier
    private String numberPlate;

    //model of plane
    private Model model;

    public Plane(String numberPlate, Model model) {
        this.numberPlate = numberPlate;
        this.model = model;
    }


    public String getNumberPlate() {
        return numberPlate;
    }
    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
    public Model getModel() {
        return model;
    }
    public void setModel(Model model) {
        this.model = model;
    }
}
