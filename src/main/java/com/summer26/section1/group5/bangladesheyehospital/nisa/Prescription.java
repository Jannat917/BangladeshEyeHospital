package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class Prescription {
    private int patientId;
    private String doctorName;
    private String disease;
    private String medicine;
    private String advice;

    public Prescription(String advice, String disease, String doctorName, String medicine, int patientId) {
        this.advice = advice;
        this.disease = disease;
        this.doctorName = doctorName;
        this.medicine = medicine;
        this.patientId = patientId;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "advice='" + advice + '\'' +
                ", patientId=" + patientId +
                ", doctorName='" + doctorName + '\'' +
                ", disease='" + disease + '\'' +
                ", medicine='" + medicine + '\'' +
                '}';
    }
}
