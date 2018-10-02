package com.example.jacek.appointmentreminder;

public class Appointment {
    public String name;
    public String type;
    public String monthDate;
    public int dayDate;
    public int yearDate;
    public int hourTime;
    public int minuteTime;
    public String AMorPMTime;

    public Appointment(String name, String type, String monthDate, int dayDate, int yearDate, int hourTime, int minuteTime, String AMorPMTime) {
        this.name = name;
        this.type = type;
        this.monthDate = monthDate;
        this.dayDate = dayDate;
        this.yearDate = yearDate;
        this.hourTime = hourTime;
        this.minuteTime = minuteTime;
        this.AMorPMTime = AMorPMTime;
    }
}
