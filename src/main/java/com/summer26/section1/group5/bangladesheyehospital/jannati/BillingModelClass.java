package com.summer26.section1.group5.bangladesheyehospital.jannati;

import java.io.Serializable;

public class BillingModelClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private int billId;
    private int appointmentId;

    private int patientId;
    private String patientName;

    private int doctorId;
    private String doctorName;

    private String department;

    private double doctorFee;
    private double testFee;
    private double otherCharge;
    private double totalBill;

    private String paymentStatus;   // Paid / Unpaid
    private String paymentDate;

    public BillingModelClass() {
    }

    public BillingModelClass(
            int billId,
            int appointmentId,
            int patientId,
            String patientName,
            int doctorId,
            String doctorName,
            String department,
            double doctorFee,
            double testFee,
            double otherCharge,
            double totalBill,
            String paymentStatus,
            String paymentDate) {

        this.billId = billId;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.department = department;
        this.doctorFee = doctorFee;
        this.testFee = testFee;
        this.otherCharge = otherCharge;
        this.totalBill = totalBill;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
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

    public double getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(double doctorFee) {
        this.doctorFee = doctorFee;
    }

    public double getTestFee() {
        return testFee;
    }

    public void setTestFee(double testFee) {
        this.testFee = testFee;
    }

    public double getOtherCharge() {
        return otherCharge;
    }

    public void setOtherCharge(double otherCharge) {
        this.otherCharge = otherCharge;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "BillingModelClass{" +
                "billId=" + billId +
                ", appointmentId=" + appointmentId +
                ", patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", doctorId=" + doctorId +
                ", doctorName='" + doctorName + '\'' +
                ", department='" + department + '\'' +
                ", doctorFee=" + doctorFee +
                ", testFee=" + testFee +
                ", otherCharge=" + otherCharge +
                ", totalBill=" + totalBill +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                '}';
    }
}