package com.example.yangtianrui.ticketmanagement.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangtianrui on 16-6-19.
 */
public class Flight implements Serializable {
    private int _id;
    private String company;
    private String starting;
    private String ending;
    private String prize;
    private String flightTime;

    public Flight() {
    }

    public Flight(int _id, String company, String starting, String ending, String prize, String flightTime) {
        this._id = _id;
        this.company = company;
        this.starting = starting;
        this.ending = ending;
        this.prize = prize;
        this.flightTime = flightTime;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStarting() {
        return starting;
    }

    public void setStarting(String starting) {
        this.starting = starting;
    }

    public String getEnding() {
        return ending;
    }

    public void setEnding(String ending) {
        this.ending = ending;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }
}
