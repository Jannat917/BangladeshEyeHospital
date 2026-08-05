package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import java.io.Serializable;
import java.time.LocalDate;

public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    private String medicineCode;
    private String medicineName;
    private String category;
    private String batchNumber;
    private int stockQuantity;
    private int reorderLevel;
    private double unitPrice;
    private LocalDate expiryDate;
    private String supplierName;

    public Medicine() {
    }

    public Medicine(
            String medicineCode,
            String medicineName,
            String category,
            String batchNumber,
            int stockQuantity,
            int reorderLevel,
            double unitPrice,
            LocalDate expiryDate,
            String supplierName
    ) {
        this.medicineCode = medicineCode;
        this.medicineName = medicineName;
        this.category = category;
        this.batchNumber = batchNumber;
        this.stockQuantity = stockQuantity;
        this.reorderLevel = reorderLevel;
        this.unitPrice = unitPrice;
        this.expiryDate = expiryDate;
        this.supplierName = supplierName;
    }

    public boolean isLowStock() {
        return stockQuantity <= reorderLevel;
    }

    public boolean isExpired() {
        return expiryDate != null && expiryDate.isBefore(LocalDate.now());
    }

    public boolean hasEnoughStock(int requestedQuantity) {
        return requestedQuantity > 0
                && stockQuantity >= requestedQuantity;
    }

    public void addStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }

        stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }

        if (quantity > stockQuantity) {
            throw new IllegalArgumentException(
                    "Not enough medicine in stock."
            );
        }

        stockQuantity -= quantity;
    }

    public String getStockStatus() {
        if (isExpired()) {
            return "Expired";
        }

        if (stockQuantity == 0) {
            return "Out of Stock";
        }

        if (isLowStock()) {
            return "Low Stock";
        }

        return "Available";
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException(
                    "Stock quantity cannot be negative."
            );
        }

        this.stockQuantity = stockQuantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        if (reorderLevel < 0) {
            throw new IllegalArgumentException(
                    "Reorder level cannot be negative."
            );
        }

        this.reorderLevel = reorderLevel;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        if (unitPrice < 0) {
            throw new IllegalArgumentException(
                    "Unit price cannot be negative."
            );
        }

        this.unitPrice = unitPrice;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        return medicineCode + " - " + medicineName;
    }
}