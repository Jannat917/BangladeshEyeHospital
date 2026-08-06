package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import java.io.Serializable;
import java.time.LocalDate;

public class OpticalPrescription implements Serializable {
    private static final long serialVersionUID = 1L;

    private String prescriptionId;
    private int patientId;
    private String patientName;
    private String doctorName;
    private LocalDate prescriptionDate;
    private String rightEyePower;
    private String leftEyePower;
    private String doctorNotes;
    private String status;

    public OpticalPrescription() {
        status = "Pending";
    }

    public OpticalPrescription(String prescriptionId, int patientId, String patientName,
                               String doctorName, LocalDate prescriptionDate,
                               String rightEyePower, String leftEyePower,
                               String doctorNotes, String status) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.prescriptionDate = prescriptionDate;
        this.rightEyePower = rightEyePower;
        this.leftEyePower = leftEyePower;
        this.doctorNotes = doctorNotes;
        this.status = status;
    }

    public String getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(String prescriptionId) { this.prescriptionId = prescriptionId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public LocalDate getPrescriptionDate() { return prescriptionDate; }
    public void setPrescriptionDate(LocalDate prescriptionDate) { this.prescriptionDate = prescriptionDate; }
    public String getRightEyePower() { return rightEyePower; }
    public void setRightEyePower(String rightEyePower) { this.rightEyePower = rightEyePower; }
    public String getLeftEyePower() { return leftEyePower; }
    public void setLeftEyePower(String leftEyePower) { this.leftEyePower = leftEyePower; }
    public String getDoctorNotes() { return doctorNotes; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return prescriptionId + " - " + patientName;
    }
}
