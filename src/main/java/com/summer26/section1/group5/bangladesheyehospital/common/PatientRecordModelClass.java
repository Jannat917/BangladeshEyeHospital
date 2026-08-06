package com.summer26.section1.group5.bangladesheyehospital.common;

import java.io.Serializable;

public class PatientRecordModelClass implements Serializable {

    private static final long serialVersionUID = 1L;

    // ==========================
// Basic Information
// ==========================
    private int patientId;
    private String password;
    private String patientName;
    private int age;
    private String gender;
    private String phoneNumber;
    private String address;


    // ==========================
// Receptionist
// ==========================
    private String appointmentDate;
    private String appointmentTime;
    private String department;
    private String assignedDoctor;
    private  int  assignedDoctorId;
    private int serialNumber;

    // ==========================
// Doctor
// ==========================
    private String disease;
    private String diagnosis;
    private String prescription;

    private String doctorRemarks;

    // ==========================
// Optician
// ==========================
    private String eyePowerPrescription;

    private String glassesRecommendation;

    // ==========================
// Accountant
// ==========================
    private double doctorFee;

    private double billAmount;
    private String paymentStatus;
    private String appointmentType;
    private String appointmentStatus;

    public PatientRecordModelClass() {
    }

    public PatientRecordModelClass(
            int patientId,
            String password,
            String patientName,
            int age,
            String gender,
            String phoneNumber,
            String address,
            String appointmentDate,
            String appointmentTime,
            String department,
            String assignedDoctor,
            int assignedDoctorId,
            int serialNumber,
            String disease,
            String diagnosis,
            String prescription,

            String doctorRemarks,
            String eyePowerPrescription,

            String glassesRecommendation,

            double billAmount,
            String paymentStatus,
            String appointmentType,
            String appointmentStatus) {

        this.patientId = patientId;
        this.password = password;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.department = department;
        this.assignedDoctor = assignedDoctor;
        this.assignedDoctorId = assignedDoctorId;
        this.serialNumber = serialNumber;


        this.disease = disease;
        this.diagnosis = diagnosis;
        this.prescription = prescription;

        this.doctorRemarks = doctorRemarks;
        this.eyePowerPrescription = eyePowerPrescription;

        this.glassesRecommendation = glassesRecommendation;

        this.billAmount = billAmount;
        this.paymentStatus = paymentStatus;
        this.appointmentType = appointmentType;
        this.appointmentStatus = appointmentStatus;
    }



    // ==========================
// Getters & Setters
// ==========================

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public int getAssignedDoctorId() {
        return assignedDoctorId;
    }

    public void setAssignedDoctorId(int assignedDoctorId) {
        this.assignedDoctorId = assignedDoctorId;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }
    public int getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(int serialNumber){
        this.serialNumber = serialNumber;
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

//    public String getTestReports() {
//        return testReports;
//    }
//
//    public void setTestReports(String testReports) {
//        this.testReports = testReports;
//    }

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
//
//    public String getLensType() {
//        return lensType;
//    }
//
//    public void setLensType(String lensType) {
//        this.lensType = lensType;
////    }
//
//    public String getGlassesRecommendation() {
//        return glassesRecommendation;
//    }
//
//    public void setGlassesRecommendation(String glassesRecommendation) {
//        this.glassesRecommendation = glassesRecommendation;
//    }

    public double getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(double doctorFee) {
        this.doctorFee = doctorFee;
    }
//
//    public double getTestFee() {
//        return testFee;
//    }
//
//    public void setTestFee(double testFee) {
//        this.testFee = testFee;
//    }

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
                ", password='" + password + '\'' +
                ", patientName='" + patientName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", department='" + department + '\'' +
                ", assignedDoctor='" + assignedDoctor + '\'' +
                ", assignedDoctorId='" + assignedDoctorId + '\'' +
                ", disease='" + disease + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", prescription='" + prescription + '\'' +

                ", doctorRemarks='" + doctorRemarks + '\'' +
                ", eyePowerPrescription='" + eyePowerPrescription + '\'' +

                ", glassesRecommendation='" + glassesRecommendation + '\'' +
                ", doctorFee=" + doctorFee +
                ", billAmount=" + billAmount +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", appointmentType='" + appointmentType + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", appointmentStatus='" + appointmentStatus + '\'' +

                '}';
    }
}