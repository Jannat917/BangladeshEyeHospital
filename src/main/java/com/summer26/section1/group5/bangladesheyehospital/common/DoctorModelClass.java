package com.summer26.section1.group5.bangladesheyehospital.common;

import java.io.Serializable;

public class DoctorModelClass implements Serializable {

    private int doctorId;
    private String doctorName;
    private String password;
    private String specialization;
    private String gender;
    private String phoneNumber;
    private String email;
    private String availability;

    public DoctorModelClass() {

    }


    public DoctorModelClass(int doctorId, String doctorName, String password, String specialization, String gender, String phoneNumber, String email, String availability) {

        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.password = password;
        this.specialization = specialization;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.availability = availability;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "DoctorModelClass{" +
                "doctorId=" + doctorId +
                ", doctorName='" + doctorName + '\'' +
                ", password='" + password + '\'' +
                ", specialization='" + specialization + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }
}