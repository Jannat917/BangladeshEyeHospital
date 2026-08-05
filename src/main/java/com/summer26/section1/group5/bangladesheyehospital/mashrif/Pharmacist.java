package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import java.io.Serializable;

public class Pharmacist implements Serializable {

    private static final long serialVersionUID = 1L;

    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;
    private String pharmacistId;
    private String licenseNumber;

    public Pharmacist() {
        this.role = "Pharmacist";
    }

    public Pharmacist(
            int userId,
            String name,
            String email,
            String password,
            String pharmacistId,
            String licenseNumber
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "Pharmacist";
        this.pharmacistId = pharmacistId;
        this.licenseNumber = licenseNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        return "Pharmacist{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", pharmacistId='" + pharmacistId + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                '}';
    }
}