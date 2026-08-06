package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import java.io.Serializable;
import java.time.LocalDate;

public class OpticalSale implements Serializable {
    private static final long serialVersionUID = 1L;

    private String saleId;
    private String orderId;
    private String patientName;
    private String frameName;
    private LocalDate saleDate;
    private double amount;

    public OpticalSale() {}

    public OpticalSale(String saleId, String orderId, String patientName,
                       String frameName, LocalDate saleDate, double amount) {
        this.saleId = saleId;
        this.orderId = orderId;
        this.patientName = patientName;
        this.frameName = frameName;
        this.saleDate = saleDate;
        this.amount = amount;
    }

    public String getSaleId() { return saleId; }
    public void setSaleId(String saleId) { this.saleId = saleId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getFrameName() { return frameName; }
    public void setFrameName(String frameName) { this.frameName = frameName; }
    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
