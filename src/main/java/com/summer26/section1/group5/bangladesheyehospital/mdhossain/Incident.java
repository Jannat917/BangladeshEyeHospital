package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import java.time.LocalDateTime;

public class Incident {
    private String caseId;
    private String type;
    private String description;
    private String location;
    private String reportedBy;
    private LocalDateTime reportedTime;
    private String severity;
    private String status;

    public Incident(String caseId, String type, String description, String location, String reportedBy, String severity) {
        this.caseId = caseId;
        this.type = type;
        this.description = description;
        this.location = location;
        this.reportedBy = reportedBy;
        this.reportedTime = LocalDateTime.now();
        this.severity = severity;
        this.status = "Reported";
    }

    public String getCaseId() { return caseId; }
    public void setCaseId(String caseId) { this.caseId = caseId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getReportedBy() { return reportedBy; }
    public void setReportedBy(String reportedBy) { this.reportedBy = reportedBy; }
    public LocalDateTime getReportedTime() { return reportedTime; }
    public void setReportedTime(LocalDateTime reportedTime) { this.reportedTime = reportedTime; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
