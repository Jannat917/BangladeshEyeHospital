package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import java.time.LocalDateTime;

public class ShiftHandover {
    private String handoverId;
    private String currentOfficerId;
    private String nextOfficerId;
    private LocalDateTime handoverTime;
    private String notes;

    public ShiftHandover(String handoverId, String currentOfficerId, String nextOfficerId, String notes) {
        this.handoverId = handoverId;
        this.currentOfficerId = currentOfficerId;
        this.nextOfficerId = nextOfficerId;
        this.handoverTime = LocalDateTime.now();
        this.notes = notes;
    }

    public String getHandoverId() { return handoverId; }
    public void setHandoverId(String handoverId) { this.handoverId = handoverId; }
    public String getCurrentOfficerId() { return currentOfficerId; }
    public void setCurrentOfficerId(String currentOfficerId) { this.currentOfficerId = currentOfficerId; }
    public String getNextOfficerId() { return nextOfficerId; }
    public void setNextOfficerId(String nextOfficerId) { this.nextOfficerId = nextOfficerId; }
    public LocalDateTime getHandoverTime() { return handoverTime; }
    public void setHandoverTime(LocalDateTime handoverTime) { this.handoverTime = handoverTime; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
