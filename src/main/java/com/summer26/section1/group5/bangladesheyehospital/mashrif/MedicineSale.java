package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import java.io.Serializable;
import java.time.LocalDate;

public class MedicineSale implements Serializable {

    private static final long serialVersionUID = 1L;

    private String saleId;
    private LocalDate saleDate;
    private String prescriptionId;
    private String patientName;
    private String medicineName;
    private int quantity;
    private double totalAmount;

    public MedicineSale() {
    }

    public MedicineSale(
            String saleId,
            LocalDate saleDate,
            String prescriptionId,
            String patientName,
            String medicineName,
            int quantity,
            double totalAmount
    ) {
        this.saleId = saleId;
        this.saleDate = saleDate;
        this.prescriptionId = prescriptionId;
        this.patientName = patientName;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}