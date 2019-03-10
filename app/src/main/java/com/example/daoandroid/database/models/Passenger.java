package com.example.daoandroid.database.models;

public class Passenger {

    //compose id - seatRow flight
    private int flight;
    private SeatRow seatRow;

    //weigh of row
    private double weigh;

    public Passenger() {}
    public Passenger(SeatRow seatRow, int flight, double weigh) {
        this.seatRow = seatRow;
        this.flight = flight;
        this.weigh = weigh;
    }

    public SeatRow getSeatRow() {
        return seatRow;
    }
    public void setSeatRow(SeatRow seatRow) {
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
