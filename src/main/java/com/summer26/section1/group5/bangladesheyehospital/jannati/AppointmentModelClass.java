package com.summer26.section1.group5.bangladesheyehospital.jannati;

import java.io.Serializable;

public class AppointmentModelClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private int appointmentId;

    private int patientId;
    private String patientName;

    private int doctorId;
    private String doctorName;

    private String department;

    private String appointmentDate;
    private String appointmentTime;

    private String appointmentType;      // Online / Offline

    private String doctorConfirmation;   // Pending / Confirmed
    private String receptionistConfirmation; // Pending / Confirmed

    public AppointmentModelClass() {
    }

    public AppointmentModelClass(
            int appointmentId,
            int patientId,
            String patientName,
            int doctorId,
            String doctorName,
            String department,
            String appointmentDate,
            String appointmentTime,
            String appointmentType,
            String doctorConfirmation,
            String receptionistConfirmation) {

        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.department = department;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentType = appointmentType;
        this.doctorConfirmation = doctorConfirmation;
        this.receptionistConfirmation = receptionistConfirmation;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getDoctorConfirmation() {
        return doctorConfirmation;
    }

    public void setDoctorConfirmation(String doctorConfirmation) {
        this.doctorConfirmation = doctorConfirmation;
    }

    public String getReceptionistConfirmation() {
        return receptionistConfirmation;
    }

    public void setReceptionistConfirmation(String receptionistConfirmation) {
        this.receptionistConfirmation = receptionistConfirmation;
    }

    @Override
    public String toString() {
        return "AppointmentModelClass{" +
                "appointmentId=" + appointmentId +
                ", patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", doctorId=" + doctorId +
                ", doctorName='" + doctorName + '\'' +
                ", department='" + department + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", appointmentType='" + appointmentType + '\'' +
                ", doctorConfirmation='" + doctorConfirmation + '\'' +
                ", receptionistConfirmation='" + receptionistConfirmation + '\'' +
                '}';
    }
}