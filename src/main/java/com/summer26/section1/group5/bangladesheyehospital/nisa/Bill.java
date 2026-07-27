package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class Bill {
    private int patientId;
    private double doctorFee;
    private double testFee;
    private double medicineFee;

    public Bill(double doctorFee, double medicineFee, int patientId, double testFee) {
        this.doctorFee = doctorFee;
        this.medicineFee = medicineFee;
        this.patientId = patientId;
        this.testFee = testFee;
    }

    public double getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(double doctorFee) {
        this.doctorFee = doctorFee;
    }

    public double getMedicineFee() {
        return medicineFee;
    }

    public void setMedicineFee(double medicineFee) {
        this.medicineFee = medicineFee;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public double getTestFee() {
        return testFee;
    }

    public void setTestFee(double testFee) {
        this.testFee = testFee;
    }

    public double getTotalBill(){
        return doctorFee + testFee + medicineFee;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "doctorFee=" + doctorFee +
                ", patientId=" + patientId +
                ", testFee=" + testFee +
                ", medicineFee=" + medicineFee +
                '}';
    }
}
