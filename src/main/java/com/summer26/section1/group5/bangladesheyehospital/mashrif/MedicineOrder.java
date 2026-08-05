package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import java.io.Serializable;
import java.time.LocalDate;

public class MedicineOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;
    private String medicineCode;
    private String medicineName;
    private String supplierName;
    private int orderQuantity;
    private LocalDate orderDate;
    private String status;
    private String notes;

    public MedicineOrder() {
    }

    public MedicineOrder(
            String orderId,
            String medicineCode,
            String medicineName,
            String supplierName,
            int orderQuantity,
            LocalDate orderDate,
            String status,
            String notes
    ) {
        this.orderId = orderId;
        this.medicineCode = medicineCode;
        this.medicineName = medicineName;
        this.supplierName = supplierName;
        this.orderQuantity = orderQuantity;
        this.orderDate = orderDate;
        this.status = status;
        this.notes = notes;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMedicineCode() {
        return medicineCode;
    }

    public void setMedicineCode(String medicineCode) {
        this.medicineCode = medicineCode;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}