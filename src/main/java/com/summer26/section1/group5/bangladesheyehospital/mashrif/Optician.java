package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import com.summer26.section1.group5.bangladesheyehospital.common.UserModelClass;

public class Optician extends UserModelClass {

    private static final long serialVersionUID = 1L;

    private String opticianId;
    private String licenseNumber;

    public Optician() {
        super();
    }

    public Optician(
            int userId,
            String name,
            String email,
            String password,
            String opticianId,
            String licenseNumber
    ) {
        super(userId, name, email, password, "Optician");
        this.opticianId = opticianId;
        this.licenseNumber = licenseNumber;
    }

    public String getOpticianId() {
        return opticianId;
    }

    public void setOpticianId(String opticianId) {
        this.opticianId = opticianId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public String toString() {
        return opticianId + " - " + getName();
    }
}