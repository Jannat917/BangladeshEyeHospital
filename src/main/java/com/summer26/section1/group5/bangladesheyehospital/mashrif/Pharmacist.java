package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import com.summer26.section1.group5.bangladesheyehospital.common.UserModelClass;

public class Pharmacist extends UserModelClass {

    private static final long serialVersionUID = 1L;

    private String pharmacistId;
    private String licenseNumber;

    public Pharmacist() {
        super();
    }

    public Pharmacist(
            int userId,
            String name,
            String email,
            String password,
            String pharmacistId,
            String licenseNumber
    ) {
        super(userId, name, email, password, "Pharmacist");
        this.pharmacistId = pharmacistId;
        this.licenseNumber = licenseNumber;
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(String pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public String toString() {
        return pharmacistId + " - " + getName();
    }
}