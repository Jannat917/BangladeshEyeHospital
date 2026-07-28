package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class Payment {
    private int patientId;
    private double totalBill;
    private String paymentStatus;

    public Payment(int patientId, String paymentStatus, double totalBill) {
        this.patientId = patientId;
        this.paymentStatus = paymentStatus;
        this.totalBill = totalBill;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "patientId=" + patientId +
                ", totalBill=" + totalBill +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
