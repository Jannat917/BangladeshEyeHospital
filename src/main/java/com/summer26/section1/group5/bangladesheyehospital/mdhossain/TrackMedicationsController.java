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
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TrackMedicationsController {
    @FXML private TextField patientIdField;
    @FXML private TextField medicationNameField;
    @FXML private TextField quantityField;
    @FXML private TextArea logArea;
    @FXML private Label statusLabel;
    @FXML private Button printBtn;

    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    private List<PatientRecordModelClass> patientList = new ArrayList<>();

    @FXML
    public void initialize() {
        printBtn.setDisable(true);
        loadPatientsFromFile();
        logArea.setEditable(true);
    }

    private void loadPatientsFromFile() {
        patientList.clear();
        if (!patientFile.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {
            while (true) {
                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();
                patientList.add(patient);
            }
        } catch (EOFException e) {
        } catch (Exception e) {
            e.printStackTrace();
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

        PatientRecordModelClass patient = findPatientById(patientId);
        if (patient == null) {
            StringBuilder availableIds = new StringBuilder();
            for (PatientRecordModelClass p : patientList) {
                availableIds.append(p.getPatientId()).append(", ");
            }
            if (availableIds.length() > 0) {
                availableIds.setLength(availableIds.length() - 2);
            }
            statusLabel.setText("ERROR: Patient not found! Available IDs: " + availableIds.toString());
            statusLabel.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            return;
        }

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String log = "[" + time + "] " + medName + " (" + qtyStr + ") given to " + patient.getPatientName() + "\n";
        logArea.appendText(log);

        String info = "========================================\n";
        info += "        MEDICATION ADMINISTERED\n";
        info += "========================================\n";
        info += "  Patient ID : " + patientId + "\n";
        info += "  Name       : " + patient.getPatientName() + "\n";
        info += "  Phone      : " + (patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "Not provided") + "\n";
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
        String logText = logArea.getText().trim();

        if (logText.isEmpty()) {
            statusLabel.setText("ERROR: Nothing to print!");
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
        Text logTextContent = new Text(logText);
        logTextContent.setStyle("-fx-font-size: 12px;");
        Text line1 = new Text("----------------------------------------");
        Text printedText = new Text("  Printed At   : " + time);
        Text authText = new Text("  Authorized By: Nurse");
        Text footer = new Text("========================================");

        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                logTextContent,
                line1, printedText, authText, footer
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