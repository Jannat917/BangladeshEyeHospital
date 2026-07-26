package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

public class Patient {
    private String id;
    private String name;
    private String status;

    public Patient(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getStatus() { return status; }
}
