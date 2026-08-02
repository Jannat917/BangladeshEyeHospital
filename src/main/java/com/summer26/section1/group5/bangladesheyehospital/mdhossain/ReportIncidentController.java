package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.print.PrinterJob;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportIncidentController {
    @FXML private ComboBox<String> incidentTypeCombo;
    @FXML private ComboBox<String> patientCombo;
    @FXML private TextArea incidentDetailsArea;
    @FXML private Label statusLabel;
    @FXML private Button printBtn;

    private static final Map<String, Incident> incidentDB = new HashMap<>();
    private static int incidentCount = 0;
    private Incident currentIncident = null;

    // Data folder
    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    // List to hold all patients from file
    private List<PatientRecordModelClass> patientList = new ArrayList<>();

    @FXML
    public void initialize() {
        incidentTypeCombo.getItems().addAll(
                "Security Breach", "Medical Emergency", "Fire Hazard",
                "Theft", "Accident", "Suspicious Activity", "Patient Complaint", "Other"
        );

        loadPatientsFromFile();
        populatePatientCombo();
        printBtn.setDisable(true);
    }

    private void loadPatientsFromFile() {
        patientList.clear();

        if (!patientFile.exists()) {
            System.out.println("patients.bin not found. Please register patients first.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {
            while (true) {
                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();
                patientList.add(patient);
            }
        } catch (EOFException e) {
            // End of file reached - normal
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Loaded " + patientList.size() + " patients from file.");
    }

    private void populatePatientCombo() {
        patientCombo.getItems().clear();
        for (PatientRecordModelClass p : patientList) {
            patientCombo.getItems().add(p.getPatientId() + " - " + p.getPatientName());
        }
    }

    private PatientRecordModelClass findPatientById(int id) {
        for (PatientRecordModelClass patient : patientList) {
            if (patient.getPatientId() == id) {
                return patient;
            }
        }
        return null;
    }

    @FXML
    public void submitIncident(ActionEvent event) {
        String type = incidentTypeCombo.getValue();
        String patientSelection = patientCombo.getValue();
        String details = incidentDetailsArea.getText().trim();

        if (type == null || type.isEmpty()) {
            statusLabel.setText("ERROR: Select an incident type!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        if (patientSelection == null || patientSelection.isEmpty()) {
            statusLabel.setText("ERROR: Select a patient!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        if (details.isEmpty()) {
            statusLabel.setText("ERROR: Enter incident details!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        int patientId = Integer.parseInt(patientSelection.split(" - ")[0]);
        PatientRecordModelClass patient = findPatientById(patientId);

        if (patient == null) {
            statusLabel.setText("ERROR: Patient not found!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        incidentCount++;
        String caseId = "C" + String.format("%06d", System.currentTimeMillis() % 1000000);
        currentIncident = new Incident(caseId, type, details, "Hospital Premises", "Security Staff", "Medium");
        incidentDB.put(caseId, currentIncident);

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String phone = patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "Not provided";
        String address = patient.getAddress() != null ? patient.getAddress() : "Not provided";
        String doctor = patient.getAssignedDoctor() != null ? patient.getAssignedDoctor() : "Not assigned";

        String info = "========================================\n";
        info += "        INCIDENT REPORTED\n";
        info += "========================================\n";
        info += "  Case ID     : " + caseId + "\n";
        info += "  Type        : " + type + "\n";
        info += "  Patient     : " + patient.getPatientName() + " (ID: " + patientId + ")\n";
        info += "  Doctor      : " + doctor + "\n";
        info += "  Phone       : " + phone + "\n";
        info += "  Age/Gender  : " + patient.getAge() + " / " + patient.getGender() + "\n";
        info += "  Address     : " + address + "\n";
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
        printBtn.setDisable(false);
        incidentTypeCombo.setValue(null);
        patientCombo.setValue(null);
        incidentDetailsArea.clear();
    }

    @FXML
    public void printIncident(ActionEvent event) {
        if (currentIncident == null) {
            statusLabel.setText("ERROR: No incident to print!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        VBox printContent = new VBox(10);
        printContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New';");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Text title1 = new Text("========================================");
        Text title2 = new Text("     BANGLADESH EYE HOSPITAL");
        Text title3 = new Text("     INCIDENT REPORT");
        Text title4 = new Text("========================================");
        Text caseIdText = new Text("  Case ID      : " + currentIncident.getCaseId());
        Text typeText = new Text("  Type         : " + currentIncident.getType());
        Text descriptionText = new Text("  Description  : " + currentIncident.getDescription());
        Text locationText = new Text("  Location     : " + currentIncident.getLocation());
        Text reportedByText = new Text("  Reported By  : " + currentIncident.getReportedBy());
        Text reportedTimeText = new Text("  Time         : " + time);
        Text severityText = new Text("  Severity     : " + currentIncident.getSeverity());
        Text statusText = new Text("  Status       : " + currentIncident.getStatus());
        Text line1 = new Text("----------------------------------------");
        Text authText = new Text("  Authorized By: Security Staff");
        Text footer = new Text("========================================");

        for (Text t : new Text[]{title1, title2, title3, title4, caseIdText, typeText,
                descriptionText, locationText, reportedByText, reportedTimeText,
                severityText, statusText, line1, authText, footer}) {
            t.setStyle("-fx-font-size: 12px;");
        }
        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                caseIdText, typeText, descriptionText, locationText,
                reportedByText, reportedTimeText, severityText, statusText,
                line1, authText, footer
        );

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(printBtn.getScene().getWindow())) {
            boolean success = job.printPage(printContent);
            if (success) {
                job.endJob();
                statusLabel.setText("Incident report printed successfully!");
                statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            } else {
                statusLabel.setText("ERROR: Print failed!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
    }
}