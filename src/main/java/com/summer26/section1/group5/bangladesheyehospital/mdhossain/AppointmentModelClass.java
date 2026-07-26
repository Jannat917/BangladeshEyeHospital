package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import java.time.LocalDateTime;

public class AppointmentModelClass {
    private String appointmentId;
    private String patientId;
    private String patientName;
    private String doctorName;
    private String department;
    private LocalDateTime appointmentTime;
    private String timeSlot;
    private String status;
    private boolean isVerified;

    public AppointmentModelClass() {}

    public AppointmentModelClass(String appointmentId, String patientId, String patientName,
                       String doctorName, String department, LocalDateTime appointmentTime,
                       String timeSlot, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.department = department;
        this.appointmentTime = appointmentTime;
        this.timeSlot = timeSlot;
        this.status = status;
        this.isVerified = false;
    }

    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }
}
