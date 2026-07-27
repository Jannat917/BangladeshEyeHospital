package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import java.time.LocalDateTime;

public class Visitor {
    private String visitorId;
    private String name;
    private String phone;
    private String nid;
    private String passNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String status; // "Inside" or "Left"

    // Corrected Constructor
    public Visitor(String visitorId, String name, String phone, String nid) {
        this.visitorId = visitorId;
        this.name = name;
        this.phone = phone;
        this.nid = nid;
        this.entryTime = LocalDateTime.now();
        this.status = "Inside";
    }

    // Getters and Setters
    public String getVisitorId() { return visitorId; }
    public void setVisitorId(String visitorId) { this.visitorId = visitorId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getNid() { return nid; }
    public void setNid(String nid) { this.nid = nid; }
    public String getPassNumber() { return passNumber; }
    public void setPassNumber(String passNumber) { this.passNumber = passNumber; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
