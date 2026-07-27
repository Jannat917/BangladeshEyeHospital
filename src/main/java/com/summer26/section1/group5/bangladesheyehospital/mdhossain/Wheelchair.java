package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import java.time.LocalDateTime;

public class Wheelchair {
    private String code;
    private String type;
    private String condition;
    private boolean available;
    private String assignedToPatientId;
    private LocalDateTime assignedTime;

    public Wheelchair(String code, String type, String condition) {
        this.code = code;
        this.type = type;
        this.condition = condition;
        this.available = true;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public String getAssignedToPatientId() { return assignedToPatientId; }
    public void setAssignedToPatientId(String assignedToPatientId) { this.assignedToPatientId = assignedToPatientId; }
    public LocalDateTime getAssignedTime() { return assignedTime; }
    public void setAssignedTime(LocalDateTime assignedTime) { this.assignedTime = assignedTime; }

    public void assignTo(String patientId) {
        this.assignedToPatientId = patientId;
        this.available = false;
        this.assignedTime = LocalDateTime.now();
    }
}



