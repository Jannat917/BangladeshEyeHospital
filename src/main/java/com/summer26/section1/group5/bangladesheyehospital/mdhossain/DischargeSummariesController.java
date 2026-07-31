package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.print.PrinterJob;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DischargeSummariesController {
    @FXML private TextField patientIdField;
    @FXML private TextArea dischargeArea;
    @FXML private Label statusLabel;
    @FXML private Button printBtn;

    // REUSE patientDB from InitialEyeScreeningController
    private static final Map<Integer, PatientRecordModelClass> patientDB = InitialEyeScreeningController.patientDB;
    private static final Map<Integer, String> dischargeData = new HashMap<>();
    private String currentDischargeSummary = "";

    @FXML
    public void initialize() {
        printBtn.setDisable(true);
    }

    @FXML
    public void generateDischarge(ActionEvent event) {
        String patientIdText = patientIdField.getText().trim();
        if (patientIdText.isEmpty()) {
            statusLabel.setText("ERROR: Enter Patient ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            return;
        }

        int patientId;
        try {
            patientId = Integer.parseInt(patientIdText);
        } catch (NumberFormatException e) {
            statusLabel.setText("ERROR: Patient ID must be numeric!");
            statusLabel.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            return;
        }

        PatientRecordModelClass patient = patientDB.get(patientId);
        if (patient == null) {
            statusLabel.setText("ERROR: Patient not found! Use: 101, 102, 103");
            statusLabel.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            return;
        }

        // Handle null values with default messages
        String address = patient.getAddress() != null ? patient.getAddress() : "Not provided";
        String doctor = patient.getAssignedDoctor() != null ? patient.getAssignedDoctor() : "Not assigned";
        String disease = patient.getDisease() != null ? patient.getDisease() : "Not specified";
        String diagnosis = patient.getDiagnosis() != null ? patient.getDiagnosis() : "Not specified";
        String remarks = patient.getDoctorRemarks() != null ? patient.getDoctorRemarks() : "No remarks";

        String info = "========================================\n";
        info += "        DISCHARGE SUMMARY\n";
        info += "========================================\n";
        info += "  Patient ID : " + patient.getPatientId() + "\n";
        info += "  Name       : " + patient.getPatientName() + "\n";
        info += "  Age        : " + patient.getAge() + "\n";
        info += "  Gender     : " + patient.getGender() + "\n";
        info += "  Phone      : " + patient.getPhoneNumber() + "\n";
        info += "  Address    : " + address + "\n";
        info += "  Doctor     : " + doctor + "\n";
        info += "  Disease    : " + disease + "\n";
        info += "  Diagnosis  : " + diagnosis + "\n";
        info += "  Remarks    : " + remarks + "\n";
        info += "  Discharge Date: " + LocalDate.now() + "\n";
        info += "  Status     : Discharged\n";
        info += "========================================";

        dischargeArea.setText(info);
        currentDischargeSummary = info;
        dischargeData.put(patientId, info);
        statusLabel.setText("Discharge summary generated for " + patient.getPatientName());
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        printBtn.setDisable(false);
    }

    @FXML
    public void printDischarge() {
        if (currentDischargeSummary.isEmpty()) {
            statusLabel.setText("ERROR: No discharge summary to print!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        VBox printContent = new VBox(10);
        printContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New';");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Text title1 = new Text("========================================");
        Text title2 = new Text("     BANGLADESH EYE HOSPITAL");
        Text title3 = new Text("     DISCHARGE SUMMARY");
        Text title4 = new Text("========================================");

        String[] lines = currentDischargeSummary.split("\n");
        String patientId = "", patientName = "", age = "", gender = "", phone = "", address = "";
        String doctor = "", disease = "", diagnosis = "", remarks = "", dischargeDate = "";

        for (String line : lines) {
            if (line.contains("Patient ID :")) patientId = line.replace("  Patient ID : ", "").trim();
            if (line.contains("Name       :")) patientName = line.replace("  Name       : ", "").trim();
            if (line.contains("Age        :")) age = line.replace("  Age        : ", "").trim();
            if (line.contains("Gender     :")) gender = line.replace("  Gender     : ", "").trim();
            if (line.contains("Phone      :")) phone = line.replace("  Phone      : ", "").trim();
            if (line.contains("Address    :")) address = line.replace("  Address    : ", "").trim();
            if (line.contains("Doctor     :")) doctor = line.replace("  Doctor     : ", "").trim();
            if (line.contains("Disease    :")) disease = line.replace("  Disease    : ", "").trim();
            if (line.contains("Diagnosis  :")) diagnosis = line.replace("  Diagnosis  : ", "").trim();
            if (line.contains("Remarks    :")) remarks = line.replace("  Remarks    : ", "").trim();
            if (line.contains("Discharge Date:")) dischargeDate = line.replace("  Discharge Date: ", "").trim();
        }

        Text patientIdText = new Text("  Patient ID   : " + patientId);
        Text patientNameText = new Text("  Patient Name : " + patientName);
        Text ageText = new Text("  Age          : " + age);
        Text genderText = new Text("  Gender       : " + gender);
        Text phoneText = new Text("  Phone        : " + phone);
        Text addressText = new Text("  Address      : " + address);
        Text doctorText = new Text("  Doctor       : " + doctor);
        Text diseaseText = new Text("  Disease      : " + disease);
        Text diagnosisText = new Text("  Diagnosis    : " + diagnosis);
        Text remarksText = new Text("  Remarks      : " + remarks);
        Text dateText = new Text("  Discharge    : " + dischargeDate);
        Text printedText = new Text("  Printed At   : " + time);
        Text statusText = new Text("  Status       : Discharged");

        Text line1 = new Text("----------------------------------------");
        Text authText = new Text("  Authorized By: Nurse");
        Text footer = new Text("========================================");

        for (Text t : new Text[]{title1, title2, title3, title4, patientIdText, patientNameText,
                ageText, genderText, phoneText, addressText, doctorText, diseaseText,
                diagnosisText, remarksText, dateText, printedText, statusText, line1, authText, footer}) {
            t.setStyle("-fx-font-size: 12px;");
        }
        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                patientIdText, patientNameText, ageText, genderText, phoneText, addressText,
                doctorText, diseaseText, diagnosisText, remarksText, dateText,
                printedText, statusText,
                line1, authText, footer
        );

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(printBtn.getScene().getWindow())) {
            boolean success = job.printPage(printContent);
            if (success) {
                job.endJob();
                statusLabel.setText("Discharge summary printed successfully!");
                statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            } else {
                statusLabel.setText("ERROR: Print failed!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/nurseDashboard.fxml");
    }
}