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
        setMedicineCode(medicineCode);
        setMedicineName(medicineName);
        setCategory(category);
        setBatchNumber(batchNumber);
        setStockQuantity(stockQuantity);
        setReorderLevel(reorderLevel);
        setUnitPrice(unitPrice);
        setExpiryDate(expiryDate);
        setSupplierName(supplierName);
    }

    public boolean isLowStock() {
        return stockQuantity <= reorderLevel;
    }

    public boolean isExpired() {
        return expiryDate != null
                && expiryDate.isBefore(LocalDate.now());
    }

    public boolean hasEnoughStock(int quantity) {
        return quantity > 0 && stockQuantity >= quantity;
    }

    public void addStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Stock quantity must be greater than zero."
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
                    "Not enough medicine available in stock."
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
        if (medicineCode == null || medicineCode.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Medicine code cannot be empty."
            );
        }

        this.medicineCode = medicineCode.trim();
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        if (medicineName == null || medicineName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Medicine name cannot be empty."
            );
        }

        this.medicineName = medicineName.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Medicine category cannot be empty."
            );
        }

        this.category = category.trim();
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        if (batchNumber == null || batchNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Batch number cannot be empty."
            );
        }

        this.batchNumber = batchNumber.trim();
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
        if (expiryDate == null) {
            throw new IllegalArgumentException(
                    "Expiry date cannot be empty."
            );
        }

        this.expiryDate = expiryDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        if (supplierName == null || supplierName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Supplier name cannot be empty."
            );
        }

        this.supplierName = supplierName.trim();
    }

    @Override
    public String toString() {
        return medicineCode + " - " + medicineName;
    }
}