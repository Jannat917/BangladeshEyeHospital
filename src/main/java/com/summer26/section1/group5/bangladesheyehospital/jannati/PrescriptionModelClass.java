package com.summer26.section1.group5.bangladesheyehospital.jannati;

import java.io.Serializable;

public class PrescriptionModelClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private int prescriptionId;

    private int patientId;
    private String patientName;

    private int doctorId;
    private String doctorName;

    private String appointmentDate;

    private String diagnosis;
    private String prescription;
    private String doctorRemarks;

    public PrescriptionModelClass() {
    }

    public PrescriptionModelClass(int prescriptionId,
                                  int patientId,
                                  String patientName,
                                  int doctorId,
                                  String doctorName,
                                  String appointmentDate,
                                  String diagnosis,
                                  String prescription,
                                  String doctorRemarks) {

        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.doctorRemarks = doctorRemarks;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
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

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getDoctorRemarks() {
        return doctorRemarks;
    }

    public void setDoctorRemarks(String doctorRemarks) {
        this.doctorRemarks = doctorRemarks;
    }

    @Override
    public String toString() {
        return "PrescriptionModelClass{" +
                "prescriptionId=" + prescriptionId +
                ", patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", doctorId=" + doctorId +
                ", doctorName='" + doctorName + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", prescription='" + prescription + '\'' +
                ", doctorRemarks='" + doctorRemarks + '\'' +
                '}';
    }
}