package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class UpdateProfile {
    private int patientId;
    private String patientName;
    private String phone;
    private String email;
    private String address;

    public UpdateProfile(String address, String email, int patientId, String patientName, String phone) {
        this.address = address;
        this.email = email;
        this.patientId = patientId;
        this.patientName = patientName;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UpdateProfile{" +
                "address='" + address + '\'' +
                ", patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
