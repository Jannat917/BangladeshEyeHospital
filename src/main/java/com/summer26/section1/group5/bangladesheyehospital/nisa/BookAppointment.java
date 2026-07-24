package com.summer26.section1.group5.bangladesheyehospital.nisa;

import java.time.LocalDate;

public class BookAppointment {
    private String department;
    private String doctor;
    private LocalDate date;
    private String time;
    private int serial;

    public BookAppointment(LocalDate date, String department, String doctor, int serial, String time) {
        this.date = date;
        this.department = department;
        this.doctor = doctor;
        this.serial = serial;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BookAppointment{" +
                "date=" + date +
                ", department='" + department + '\'' +
                ", doctor='" + doctor + '\'' +
                ", time='" + time + '\'' +
                ", serial=" + serial +
                '}';
    }
}
