package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.print.PrinterJob;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class InitialEyeScreeningController {
    @FXML private ComboBox<String> patientCombo;
    @FXML private TextField eyeScoreField;
    @FXML private TextField bloodPressureField;
    @FXML private Label statusLabel;
    @FXML private Button printBtn;

    // Shared patient database loaded from file
    public static final Map<Integer, PatientRecordModelClass> patientDB = new HashMap<>();
    private static final Map<Integer, String> screeningData = new HashMap<>();
    private String lastScreeningResult = "";

    // Data folder
    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    @FXML
    public void initialize() {
        printBtn.setDisable(true);
        loadPatientsFromFile();
        populatePatientCombo();
    }

    private void loadPatientsFromFile() {
        patientDB.clear();

        if (!patientFile.exists()) {
            statusLabel.setText("ERROR: patients.bin not found! Please register patients first.");
            statusLabel.setStyle("-fx-text-fill: red;");
            System.out.println("patients.bin not found. Please register patients first.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {
            while (true) {
                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();
                patientDB.put(patient.getPatientId(), patient);
            }
        } catch (EOFException e) {
            // End of file reached - normal
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("ERROR: Unable to load patient data!");
            statusLabel.setStyle("-fx-text-fill: red;");
        }

        System.out.println("Loaded " + patientDB.size() + " patients from file.");
    }

    private void populatePatientCombo() {
        patientCombo.getItems().clear();
        if (patientDB.isEmpty()) {
            patientCombo.getItems().add("No patients found. Please register first.");
            return;
        }
        for (PatientRecordModelClass p : patientDB.values()) {
            patientCombo.getItems().add(p.getPatientId() + " - " + p.getPatientName());
        }
    }

    @FXML
    public void saveScreening(ActionEvent event) {
        String selected = patientCombo.getValue();
        String eyeScore = eyeScoreField.getText().trim();
        String bp = bloodPressureField.getText().trim();

        if (selected == null || selected.isEmpty() || patientCombo.getItems().get(0).equals("No patients found. Please register first.")) {
            statusLabel.setText("ERROR: No patients available! Please register patients first.");
            statusLabel.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            return;
        }

        if (eyeScore.isEmpty() || bp.isEmpty()) {
            statusLabel.setText("ERROR: Enter both eye score and BP!");
            statusLabel.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            return;
        }

        int patientId = Integer.parseInt(selected.split(" - ")[0]);
        PatientRecordModelClass patient = patientDB.get(patientId);

        if (patient == null) {
            statusLabel.setText("ERROR: Patient not found!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String record = "Eye Score: " + eyeScore + " | BP: " + bp + " | Time: " + time;
        screeningData.put(patientId, record);
        lastScreeningResult = record;

        String phone = patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "Not provided";
        String address = patient.getAddress() != null ? patient.getAddress() : "Not provided";

        String info = "========================================\n";
        info += "        SCREENING COMPLETED\n";
        info += "========================================\n";
        info += "  Patient ID : " + patientId + "\n";
        info += "  Name       : " + patient.getPatientName() + "\n";
        info += "  Phone      : " + phone + "\n";
        info += "  Age        : " + patient.getAge() + "\n";
        info += "  Gender     : " + patient.getGender() + "\n";
        info += "  Address    : " + address + "\n";
        info += "  Eye Score  : " + eyeScore + "\n";
        info += "  BP         : " + bp + "\n";
        info += "  Time       : " + time + "\n";
        info += "  Status     : Completed\n";
        info += "========================================";

        statusLabel.setText(info);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        printBtn.setDisable(false);

        eyeScoreField.clear();
        bloodPressureField.clear();
    }

    @FXML
    public void printScreening() {
        if (lastScreeningResult.isEmpty()) {
            statusLabel.setText("ERROR: No screening to print!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        VBox printContent = new VBox(10);
        printContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New';");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Text title1 = new Text("========================================");
        Text title2 = new Text("     BANGLADESH EYE HOSPITAL");
        Text title3 = new Text("     EYE SCREENING REPORT");
        Text title4 = new Text("========================================");

        String[] parts = lastScreeningResult.split(" \\| ");
        String eyeScore = parts[0].replace("Eye Score: ", "");
        String bp = parts[1].replace("BP: ", "");
        String recordedTime = parts[2].replace("Time: ", "");

        Text screeningText = new Text("  Eye Score   : " + eyeScore);
        Text bpText = new Text("  BP          : " + bp);
        Text timeText = new Text("  Recorded At : " + recordedTime);
        Text printedText = new Text("  Printed At  : " + time);

        Text line1 = new Text("----------------------------------------");
        Text authText = new Text("  Authorized By: Nurse");
        Text footer = new Text("========================================");

        for (Text t : new Text[]{title1, title2, title3, title4, screeningText, bpText,
                timeText, printedText, line1, authText, footer}) {
            t.setStyle("-fx-font-size: 12px;");
        }
        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                screeningText, bpText, timeText, printedText,
                line1, authText, footer
        );

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(printBtn.getScene().getWindow())) {
            boolean success = job.printPage(printContent);
            if (success) {
                job.endJob();
                statusLabel.setText("Screening report printed successfully!");
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