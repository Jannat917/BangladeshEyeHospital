package com.summer26.section1.group5.bangladesheyehospital.jannati;

public class ReceiptionistDashboardModelClass {

    private int receptionistId;
    private String receptionistName;
    private String shift;
    private String phoneNumber;

    public ReceiptionistDashboardModelClass() {
    }

    public ReceiptionistDashboardModelClass(int receptionistId, String receptionistName, String shift, String phoneNumber) {
        this.receptionistId = receptionistId;
        this.receptionistName = receptionistName;
        this.shift = shift;
        this.phoneNumber = phoneNumber;
    }

    public int getReceptionistId() {

        return receptionistId;
    }

    public void setReceptionistId(int receptionistId) {

        this.receptionistId = receptionistId;
    }

    public String getReceptionistName() {

        return receptionistName;
    }

    public void setReceptionistName(String receptionistName) {

        this.receptionistName = receptionistName;
    }

    public String getShift() {

        return shift;
    }

    public void setShift(String shift) {

        this.shift = shift;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ReceiptionistDashboardModelClass{" +
                "receptionistId=" + receptionistId +
                ", receptionistName='" + receptionistName + '\'' +
                ", shift='" + shift + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}