package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import java.io.Serializable;

public class PrescriptionMedicine implements Serializable {

    private static final long serialVersionUID = 1L;

    private String medicineCode;
    private String medicineName;
    private String dosage;
    private String frequency;
    private int durationDays;
    private int prescribedQuantity;
    private int dispensedQuantity;
    private double unitPrice;

    public PrescriptionMedicine() {
    }

    public PrescriptionMedicine(
            String medicineCode,
            String medicineName,
            String dosage,
            String frequency,
            int durationDays,
            int prescribedQuantity,
            int dispensedQuantity,
            double unitPrice
    ) {
        this.medicineCode = medicineCode;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.prescribedQuantity = prescribedQuantity;
        this.dispensedQuantity = dispensedQuantity;
        this.unitPrice = unitPrice;
    }

    public double calculateSubtotal() {
        return dispensedQuantity * unitPrice;
    }

    public int getRemainingQuantity() {
        return prescribedQuantity - dispensedQuantity;
    }

    public boolean isFullyDispensed() {
        return dispensedQuantity >= prescribedQuantity;
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

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public int getPrescribedQuantity() {
        return prescribedQuantity;
    }

    public void setPrescribedQuantity(int prescribedQuantity) {
        if (prescribedQuantity < 0) {
            throw new IllegalArgumentException(
                    "Prescribed quantity cannot be negative."
            );
        }

        this.prescribedQuantity = prescribedQuantity;
    }

    public int getDispensedQuantity() {
        return dispensedQuantity;
    }

    public void setDispensedQuantity(int dispensedQuantity) {
        if (dispensedQuantity < 0) {
            throw new IllegalArgumentException(
                    "Dispensed quantity cannot be negative."
            );
        }

        if (dispensedQuantity > prescribedQuantity) {
            throw new IllegalArgumentException(
                    "Dispensed quantity cannot exceed prescribed quantity."
            );
        }

        this.dispensedQuantity = dispensedQuantity;
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

    @Override
    public String toString() {
        return medicineName + " - " + dosage;
    }
}