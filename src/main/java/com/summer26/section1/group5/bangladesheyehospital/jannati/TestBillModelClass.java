package com.summer26.section1.group5.bangladesheyehospital.jannati;

import java.io.Serializable;

public class TestBillModelClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private int patientId;
    private String patientName;

    private String selectedTests;
    private double totalAmount;

    public TestBillModelClass() {
    }

    public TestBillModelClass(int patientId,
                              String patientName,
                              String selectedTests,
                              double totalAmount) {

        this.patientId = patientId;
        this.patientName = patientName;
        this.selectedTests = selectedTests;
        this.totalAmount = totalAmount;
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

    public String getSelectedTests() {
        return selectedTests;
    }

    public void setSelectedTests(String selectedTests) {
        this.selectedTests = selectedTests;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "TestBillModelClass{" +
                "patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", selectedTests='" + selectedTests + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}