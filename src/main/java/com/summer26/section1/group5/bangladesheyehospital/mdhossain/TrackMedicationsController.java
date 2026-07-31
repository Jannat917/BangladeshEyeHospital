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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TrackMedicationsController {
    @FXML private TextField patientIdField;
    @FXML private TextField medicationNameField;
    @FXML private TextField quantityField;
    @FXML private TextArea logArea;
    @FXML private Label statusLabel;
    @FXML private Button printBtn;

    // REUSE patientDB from InitialEyeScreeningController
    private static final Map<Integer, PatientRecordModelClass> patientDB = InitialEyeScreeningController.patientDB;
    private static final Map<Integer, String> medicationLogs = new HashMap<>();
    private String lastMedicationLog = "";

    @FXML
    public void initialize() {
        printBtn.setDisable(true);
    }

    @FXML
    public void administerMedication(ActionEvent event) {
        String patientIdText = patientIdField.getText().trim();
        String medName = medicationNameField.getText().trim();
        String qtyStr = quantityField.getText().trim();

        if (patientIdText.isEmpty() || medName.isEmpty() || qtyStr.isEmpty()) {
            statusLabel.setText("ERROR: All fields required!");
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

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String log = "[" + time + "] " + medName + " (" + qtyStr + ") given to " + patient.getPatientName() + "\n";
        logArea.appendText(log);
        lastMedicationLog = log;
        medicationLogs.put(patientId, log);

        String info = "========================================\n";
        info += "        MEDICATION ADMINISTERED\n";
        info += "========================================\n";
        info += "  Patient ID : " + patientId + "\n";
        info += "  Name       : " + patient.getPatientName() + "\n";
        info += "  Medication : " + medName + "\n";
        info += "  Quantity   : " + qtyStr + "\n";
        info += "  Time       : " + time + "\n";
        info += "  Status     : Administered\n";
        info += "========================================";

        statusLabel.setText(info);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        printBtn.setDisable(false);

        patientIdField.clear();
        medicationNameField.clear();
        quantityField.clear();
    }

    @FXML
    public void printMedication() {
        if (lastMedicationLog.isEmpty()) {
            statusLabel.setText("ERROR: No medication to print!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        VBox printContent = new VBox(10);
        printContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New';");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Text title1 = new Text("========================================");
        Text title2 = new Text("     BANGLADESH EYE HOSPITAL");
        Text title3 = new Text("     MEDICATION LOG");
        Text title4 = new Text("========================================");

        // Extract info from log
        String log = lastMedicationLog;
        String logTime = "";
        String medName = "";
        String qty = "";
        String patientName = "";

        if (log.contains("[") && log.contains("]")) {
            logTime = log.substring(log.indexOf("[") + 1, log.indexOf("]"));
            String rest = log.substring(log.indexOf("]") + 1).trim();
            if (rest.contains(" given to ")) {
                String beforeGiven = rest.substring(0, rest.indexOf(" given to "));
                patientName = rest.substring(rest.indexOf(" given to ") + 9).trim();
                if (beforeGiven.contains("(")) {
                    medName = beforeGiven.substring(0, beforeGiven.indexOf("(")).trim();
                    qty = beforeGiven.substring(beforeGiven.indexOf("(") + 1, beforeGiven.indexOf(")"));
                }
            }
        }

        Text timeText = new Text("  Administered At : " + logTime);
        Text patientText = new Text("  Patient         : " + patientName);
        Text medText = new Text("  Medication      : " + medName);
        Text qtyText = new Text("  Quantity        : " + qty);
        Text printedText = new Text("  Printed At      : " + time);
        Text statusText = new Text("  Status          : Administered");

        Text line1 = new Text("----------------------------------------");
        Text authText = new Text("  Authorized By: Nurse");
        Text footer = new Text("========================================");

        for (Text t : new Text[]{title1, title2, title3, title4, timeText, patientText,
                medText, qtyText, printedText, statusText, line1, authText, footer}) {
            t.setStyle("-fx-font-size: 12px;");
        }
        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                timeText, patientText, medText, qtyText, printedText, statusText,
                line1, authText, footer
        );

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(printBtn.getScene().getWindow())) {
            boolean success = job.printPage(printContent);
            if (success) {
                job.endJob();
                statusLabel.setText("Medication log printed successfully!");
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