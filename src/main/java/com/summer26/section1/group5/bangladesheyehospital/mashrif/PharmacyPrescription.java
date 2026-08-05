package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class PharmacyPrescription implements Serializable {

    private static final long serialVersionUID = 1L;

    private String prescriptionId;
    private int patientId;
    private String patientName;
    private int doctorId;
    private String doctorName;
    private LocalDate prescriptionDate;
    private String doctorNotes;
    private String status;
    private ArrayList<PrescriptionMedicine> prescribedMedicines;

    public PharmacyPrescription() {
        this.prescribedMedicines = new ArrayList<>();
        this.status = "Pending";
    }

    public PharmacyPrescription(
            String prescriptionId,
            int patientId,
            String patientName,
            int doctorId,
            String doctorName,
            LocalDate prescriptionDate,
            String doctorNotes,
            String status,
            ArrayList<PrescriptionMedicine> prescribedMedicines
    ) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.prescriptionDate = prescriptionDate;
        this.doctorNotes = doctorNotes;
        this.status = status;

        if (prescribedMedicines == null) {
            this.prescribedMedicines = new ArrayList<>();
        } else {
            this.prescribedMedicines = prescribedMedicines;
        }
    }

    public void addMedicine(PrescriptionMedicine medicine) {
        if (medicine != null) {
            prescribedMedicines.add(medicine);
        }
    }

    public boolean isFullyDispensed() {
        if (prescribedMedicines.isEmpty()) {
            return false;
        }

        for (PrescriptionMedicine medicine : prescribedMedicines) {
            if (!medicine.isFullyDispensed()) {
                return false;
            }
        }

        return true;
    }

    public void updateStatus() {
        if (isFullyDispensed()) {
            status = "Dispensed";
        } else {
            status = "Pending";
        }
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
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

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<PrescriptionMedicine> getPrescribedMedicines() {
        return prescribedMedicines;
    }

    public void setPrescribedMedicines(
            ArrayList<PrescriptionMedicine> prescribedMedicines
    ) {
        if (prescribedMedicines == null) {
            this.prescribedMedicines = new ArrayList<>();
        } else {
            this.prescribedMedicines = prescribedMedicines;
        }
    }

    @Override
    public String toString() {
        return prescriptionId + " - " + patientName;
    }
}