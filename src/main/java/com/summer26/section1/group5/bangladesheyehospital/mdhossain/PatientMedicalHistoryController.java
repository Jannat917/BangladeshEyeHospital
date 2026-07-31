package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PatientMedicalHistoryController {
    @FXML private TextField patientIdField;
    @FXML private TextArea historyArea;
    @FXML private Label statusLabel;

    // REUSE patientDB from InitialEyeScreeningController
    private static final Map<Integer, PatientRecordModelClass> patientDB = InitialEyeScreeningController.patientDB;

    @FXML
    public void searchHistory(ActionEvent event) {
        String id = patientIdField.getText().trim();
        if (id.isEmpty()) {
            statusLabel.setText("ERROR: Enter Patient ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        int patientId;
        try {
            patientId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            statusLabel.setText("ERROR: ID must be numeric!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        PatientRecordModelClass patient = patientDB.get(patientId);
        if (patient == null) {
            historyArea.setText("No patient found with ID: " + id + "\n\nUse IDs: 101, 102, 103");
            statusLabel.setText("Patient not found!");
            statusLabel.setStyle("-fx-text-fill: #f39c12;");
            return;
        }

        // Handle null values with default messages
        String phone = patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "Not provided";
        String disease = patient.getDisease() != null ? patient.getDisease() : "Not specified";
        String diagnosis = patient.getDiagnosis() != null ? patient.getDiagnosis() : "Not specified";
        String prescription = patient.getPrescription() != null ? patient.getPrescription() : "No prescription";
        String remarks = patient.getDoctorRemarks() != null ? patient.getDoctorRemarks() : "No remarks";

        String info = "========================================\n";
        info += "        MEDICAL HISTORY\n";
        info += "========================================\n";
        info += "  Patient ID : " + patient.getPatientId() + "\n";
        info += "  Name       : " + patient.getPatientName() + "\n";
        info += "  Age        : " + patient.getAge() + "\n";
        info += "  Gender     : " + patient.getGender() + "\n";
        info += "  Phone      : " + phone + "\n";
        info += "----------------------------------------\n";
        info += "  Disease    : " + disease + "\n";
        info += "  Diagnosis  : " + diagnosis + "\n";
        info += "  Prescription: " + prescription + "\n";
        info += "  Remarks    : " + remarks + "\n";
        info += "========================================";

        historyArea.setText(info);
        statusLabel.setText("History loaded successfully!");
        statusLabel.setStyle("-fx-text-fill: #27ae60;");
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/nurseDashboard.fxml");
    }
}