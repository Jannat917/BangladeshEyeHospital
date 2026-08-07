package com.summer26.section1.group5.bangladesheyehospital.jannati;

import java.io.Serializable;
import java.time.LocalDate;

public class AppointmentModelClass implements Serializable {

    private static final long serialVersionUID = 1L;
       private String patientId;
       private String patientName;
       private String appointmentId;
       private int doctorId;
       private String doctorName;
       private  String appointmentTime;
       private LocalDate appointmentDate;
       private String appointmentType;
       private String appointmentStatus;
public AppointmentModelClass(){

}

    public AppointmentModelClass(String patientId, String patientName, String appointmentId, int doctorId, String doctorName, String appointmentTime, LocalDate appointmentDate, String appointmentType, String appointmentStatus) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.appointmentTime = appointmentTime;
        this.appointmentDate = appointmentDate;
        this.appointmentType = appointmentType;
        this.appointmentStatus = appointmentStatus;
    }


    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }


    @Override
    public String toString() {
        return "AppointmentModelClass{" +
                "patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", doctorId=" + doctorId +
                ", doctorName='" + doctorName + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentType='" + appointmentType + '\'' +
                ", appointmentStatus='" + appointmentStatus + '\'' +
                '}';
    }
}
