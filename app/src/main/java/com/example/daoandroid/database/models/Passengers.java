package com.example.daoandroid.database.models;

public class Passengers {

    //compose id - seatRow flight
    private int seatRow, flight;

    //weigh of row
    private double weigh;

    public Passengers() {}
    public Passengers(int seatRow, int flight, double weigh) {
        this.seatRow = seatRow;
        this.flight = flight;
        this.weigh = weigh;
    }

    public int getSeatRow() {
        return seatRow;
    }
    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }
    public int getFlight() {
        return flight;
    }
    public void setFlight(int flight) {
        this.flight = flight;
    }
    public double getWeigh() {
        return weigh;
    }
    public void setWeigh(double weigh) {
        this.weigh = weigh;
    }

}
