package com.example.daoandroid.database.models;

public class CgMass {

    //compose cg id_model/nameModel
    private int cg;
    private String nameModel;

    //max mass permit
    private double mass;

    public CgMass(){}
    public CgMass(String nameModel) {
        this.nameModel = nameModel;
    }
    public CgMass(int cg, String nameModel, double mass) {
        this.cg = cg;
        this.nameModel = nameModel;
        this.mass = mass;
    }

    public int getCg() {
        return cg;
    }
    public void setCg(int cg) {
        this.cg = cg;
    }
    public String getNameModel() {
        return nameModel;
    }
    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
    }
    public double getMass() {
        return mass;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }

}
