package com.summer26.section1.group5.bangladesheyehospital.common;

import java.io.Serializable;

public class PatientRecordModelClass implements Serializable {

    private static final long serialVersionUID = 1L;

    // Basic Information
    private int patientId;
    private String patientName;
    private int age;
    private String gender;
    private String phoneNumber;
    private String address;

    // Receptionist
    private String appointmentDate;
    private String assignedDoctor;

    // Doctor
    private String disease;
    private String diagnosis;
    private String prescription;
    private String testReports;
    private String doctorRemarks;

    // Optician
    private String eyePowerPrescription;
    private String lensType;
    private String glassesRecommendation;

    // Accountant
    private double billAmount;
    private String paymentStatus;

    public PatientRecordModelClass() {
    }

    public PatientRecordModelClass(int patientId, String patientName, int age, String gender, String phoneNumber, String address,
                                   String appointmentDate, String assignedDoctor, String disease, String diagnosis, String prescription, String testReports, String doctorRemarks, String eyePowerPrescription, String lensType, String glassesRecommendation, double billAmount, String paymentStatus) {

        this.patientId = patientId;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.appointmentDate = appointmentDate;
        this.assignedDoctor = assignedDoctor;
        this.disease = disease;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.testReports = testReports;
        this.doctorRemarks = doctorRemarks;
        this.eyePowerPrescription = eyePowerPrescription;
        this.lensType = lensType;
        this.glassesRecommendation = glassesRecommendation;
        this.billAmount = billAmount;
        this.paymentStatus = paymentStatus;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
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

    public String getTestReports() {
        return testReports;
    }

    public void setTestReports(String testReports) {
        this.testReports = testReports;
    }

    public String getDoctorRemarks() {
        return doctorRemarks;
    }

    public void setDoctorRemarks(String doctorRemarks) {
        this.doctorRemarks = doctorRemarks;
    }

    public String getEyePowerPrescription() {
        return eyePowerPrescription;
    }

    public void setEyePowerPrescription(String eyePowerPrescription) {
        this.eyePowerPrescription = eyePowerPrescription;
    }

    public String getLensType() {
        return lensType;
    }

    public void setLensType(String lensType) {
        this.lensType = lensType;
    }

    public String getGlassesRecommendation() {
        return glassesRecommendation;
    }

    public void setGlassesRecommendation(String glassesRecommendation) {
        this.glassesRecommendation = glassesRecommendation;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "PatientRecordModelClass{" +
                "patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", assignedDoctor='" + assignedDoctor + '\'' +
                ", disease='" + disease + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", prescription='" + prescription + '\'' +
                ", testReports='" + testReports + '\'' +
                ", doctorRemarks='" + doctorRemarks + '\'' +
                ", eyePowerPrescription='" + eyePowerPrescription + '\'' +
                ", lensType='" + lensType + '\'' +
                ", glassesRecommendation='" + glassesRecommendation + '\'' +
                ", billAmount=" + billAmount +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}