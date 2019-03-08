package com.example.daoandroid.database.models;

public class SeatRow {

    //compose id -> row - nameModel
    private int row;
    private String nameModel;

    //number of seats
    private int numberSeats;
    //row arm
    private double rowArm;

    public SeatRow() {}
    public SeatRow(int row, String nameModel, int numberSeats, double rowArm) {
        this.row = row;
        this.nameModel = nameModel;
        this.numberSeats = numberSeats;
        this.rowArm = rowArm;
    }

    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public String getNameModel() {
        return nameModel;
    }
    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
    }
    public int getNumberSeats() {
        return numberSeats;
    }
    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }
    public double getRowArm() {
        return rowArm;
    }
    public void setRowArm(double rowArm) {
        this.rowArm = rowArm;
    }

}
