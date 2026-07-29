package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ReportIncidentController {
    @FXML private ComboBox<String> incidentTypeCombo;
    @FXML private ComboBox<String> patientCombo;
    @FXML private TextArea incidentDetailsArea;
    @FXML private Label statusLabel;

    private static final Map<String, Incident> incidentDB = new HashMap<>();
    private static int incidentCount = 0;

    // Patient database using PatientRecordModelClass from common
    private static final Map<Integer, PatientRecordModelClass> patientDB = new HashMap<>();

    static {
        // Patient 1
        PatientRecordModelClass p1 = new PatientRecordModelClass();
        p1.setPatientId(12345);
        p1.setPatientName("Jahirul Islam");
        p1.setAge(45);
        p1.setGender("Male");
        p1.setPhoneNumber("01712345678");
        p1.setAddress("Dhaka, Bangladesh");
        p1.setAssignedDoctor("Dr. Rahman");
        patientDB.put(12345, p1);

        // Patient 2
        PatientRecordModelClass p2 = new PatientRecordModelClass();
        p2.setPatientId(23456);
        p2.setPatientName("Fatema Begum");
        p2.setAge(52);
        p2.setGender("Female");
        p2.setPhoneNumber("01723456789");
        p2.setAddress("Chittagong, Bangladesh");
        p2.setAssignedDoctor("Dr. Sultana");
        patientDB.put(23456, p2);

        // Patient 3
        PatientRecordModelClass p3 = new PatientRecordModelClass();
        p3.setPatientId(34567);
        p3.setPatientName("Rahim Khan");
        p3.setAge(38);
        p3.setGender("Male");
        p3.setPhoneNumber("01734567890");
        p3.setAddress("Sylhet, Bangladesh");
        p3.setAssignedDoctor("Dr. Islam");
        patientDB.put(34567, p3);
    }

    @FXML
    public void initialize() {
        incidentTypeCombo.getItems().addAll(
                "Security Breach",
                "Medical Emergency",
                "Fire Hazard",
                "Theft",
                "Accident",
                "Suspicious Activity",
                "Patient Complaint",
                "Other"
        );

        // Populate patient combo with patient names and IDs
        for (PatientRecordModelClass p : patientDB.values()) {
            patientCombo.getItems().add(p.getPatientId() + " - " + p.getPatientName());
        }
    }

    @FXML
    public void submitIncident(ActionEvent event) {
        String type = incidentTypeCombo.getValue();
        String patientSelection = patientCombo.getValue();
        String details = incidentDetailsArea.getText().trim();

        if (type == null || type.isEmpty()) {
            statusLabel.setText("ERROR: Please select an incident type!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (patientSelection == null || patientSelection.isEmpty()) {
            statusLabel.setText("ERROR: Please select a patient!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (details.isEmpty()) {
            statusLabel.setText("ERROR: Please enter incident details!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Extract patient ID from selection
        int patientId = Integer.parseInt(patientSelection.split(" - ")[0]);
        PatientRecordModelClass patient = patientDB.get(patientId);

        incidentCount++;
        String caseId = "C" + String.format("%06d", System.currentTimeMillis() % 1000000);
        Incident incident = new Incident(
                caseId,
                type,
                details,
                "Hospital Premises",
                "Security Staff",
                "Medium"
        );
        incidentDB.put(caseId, incident);

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String info = "========================================\n";
        info += "        INCIDENT REPORTED\n";
        info += "========================================\n";
        info += "  Case ID     : " + caseId + "\n";
        info += "  Type        : " + type + "\n";
        info += "  Patient     : " + patient.getPatientName() + " (ID: " + patientId + ")\n";
        info += "  Doctor      : " + patient.getAssignedDoctor() + "\n";
        info += "  Phone       : " + patient.getPhoneNumber() + "\n";
        info += "  Age/Gender  : " + patient.getAge() + " / " + patient.getGender() + "\n";
        info += "  Address     : " + patient.getAddress() + "\n";
        info += "  Description : " + details + "\n";
        info += "  Location    : Hospital Premises\n";
        info += "  Reported By : Security Staff\n";
        info += "  Time        : " + time + "\n";
        info += "  Severity    : Medium\n";
        info += "  Status      : Reported\n";
        info += "  Total Incidents : " + incidentCount + "\n";
        info += "========================================";

        statusLabel.setText(info);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");

        incidentTypeCombo.setValue(null);
        patientCombo.setValue(null);
        incidentDetailsArea.clear();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
    }
}