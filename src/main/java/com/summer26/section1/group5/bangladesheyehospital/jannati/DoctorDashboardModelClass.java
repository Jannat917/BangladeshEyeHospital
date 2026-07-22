package com.summer26.section1.group5.bangladesheyehospital.jannati;

import java.io.Serializable;

public class DoctorDashboardModelClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private int patientId;
    private String patientName;
    private int age;
    private String gender;
    private String disease;
    private String diagnosis;
    private String prescription;
    private String appointmentDate;
    private String doctorRemarks;

    public DoctorDashboardModelClass() {
    }

    public DoctorDashboardModelClass(int patientId, String patientName, int age, String gender, String disease, String diagnosis, String prescription, String appointmentDate, String doctorRemarks) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.appointmentDate = appointmentDate;
        this.doctorRemarks = doctorRemarks;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDoctorRemarks() {
        return doctorRemarks;
    }

    public void setDoctorRemarks(String doctorRemarks) {
        this.doctorRemarks = doctorRemarks;
    }

    @Override
    public String toString() {
        return "DoctorDashboardModelClass{" +
                "patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", disease='" + disease + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", prescription='" + prescription + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", doctorRemarks='" + doctorRemarks + '\'' +
                '}';
    }
}