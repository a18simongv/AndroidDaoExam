package com.example.daoandroid.database.models;

public class Model {

    //model identifier
    private String nameModel;

    //fuel data
    private double fuelMax, fuelArm;
    //baggage data
    private double baggageMax, baggageArm;
    //basicEmptyWeigh
    private double bew, bewArm;

    public Model() {}
    public Model(String nameModel) {
        this.nameModel = nameModel;
    }
    public Model(String nameModel, double fuelMax, double fuelArm, double baggageMax, double baggageArm, double bew, double bewArm) {
        this.nameModel = nameModel;
        this.fuelMax = fuelMax;
        this.fuelArm = fuelArm;
        this.baggageMax = baggageMax;
        this.baggageArm = baggageArm;
        this.bew = bew;
        this.bewArm = bewArm;
    }

    public String getNameModel() {
        return nameModel;
    }
    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
    }
    public double getFuelMax() {
        return fuelMax;
    }
    public void setFuelMax(double fuelMax) {
        this.fuelMax = fuelMax;
    }
    public double getFuelArm() {
        return fuelArm;
    }
    public void setFuelArm(double fuelArm) {
        this.fuelArm = fuelArm;
    }
    public double getBaggageMax() {
        return baggageMax;
    }
    public void setBaggageMax(double baggageMax) {
        this.baggageMax = baggageMax;
    }
    public double getBaggageArm() {
        return baggageArm;
    }
    public void setBaggageArm(double baggageArm) {
        this.baggageArm = baggageArm;
    }
    public double getBew() {
        return bew;
    }
    public void setBew(double bew) {
        this.bew = bew;
    }
    public double getBewArm() {
        return bewArm;
    }
    public void setBewArm(double bewArm) {
        this.bewArm = bewArm;
    }

}
