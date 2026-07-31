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
import java.io.IOException;
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

    public static final Map<Integer, PatientRecordModelClass> patientDB = new HashMap<>();
    private static final Map<Integer, String> screeningData = new HashMap<>();
    private String lastScreeningResult = "";

    static {
        // Patient 1 - Complete Data
        PatientRecordModelClass p1 = new PatientRecordModelClass();
        p1.setPatientId(101);
        p1.setPatientName("Jahirul Islam");
        p1.setAge(45);
        p1.setGender("Male");
        p1.setPhoneNumber("01712345678");
        p1.setAddress("Dhaka, Bangladesh");
        p1.setAssignedDoctor("Dr. Rahman");
        p1.setDisease("Cataract");
        p1.setDiagnosis("Left eye cataract");
        p1.setDoctorRemarks("Surgery recommended");
        patientDB.put(101, p1);

        // Patient 2 - Complete Data
        PatientRecordModelClass p2 = new PatientRecordModelClass();
        p2.setPatientId(102);
        p2.setPatientName("Fatema Begum");
        p2.setAge(52);
        p2.setGender("Female");
        p2.setPhoneNumber("01723456789");
        p2.setAddress("Chittagong, Bangladesh");
        p2.setAssignedDoctor("Dr. Sultana");
        p2.setDisease("Glaucoma");
        p2.setDiagnosis("Increased eye pressure");
        p2.setDoctorRemarks("Follow up in 2 weeks");
        patientDB.put(102, p2);

        // Patient 3 - Complete Data
        PatientRecordModelClass p3 = new PatientRecordModelClass();
        p3.setPatientId(103);
        p3.setPatientName("Rahim Khan");
        p3.setAge(38);
        p3.setGender("Male");
        p3.setPhoneNumber("01734567890");
        p3.setAddress("Sylhet, Bangladesh");
        p3.setAssignedDoctor("Dr. Islam");
        p3.setDisease("Myopia");
        p3.setDiagnosis("High myopia");
        p3.setDoctorRemarks("Regular check-up needed");
        patientDB.put(103, p3);
    }

    @FXML
    public void initialize() {
        for (PatientRecordModelClass p : patientDB.values()) {
            patientCombo.getItems().add(p.getPatientId() + " - " + p.getPatientName());
        }
        printBtn.setDisable(true);
    }

    @FXML
    public void saveScreening(ActionEvent event) {
        String selected = patientCombo.getValue();
        String eyeScore = eyeScoreField.getText().trim();
        String bp = bloodPressureField.getText().trim();

        if (selected == null || selected.isEmpty()) {
            statusLabel.setText("ERROR: Select a patient!");
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

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String record = "Eye Score: " + eyeScore + " | BP: " + bp + " | Time: " + time;
        screeningData.put(patientId, record);
        lastScreeningResult = record;

        String info = "========================================\n";
        info += "        SCREENING COMPLETED\n";
        info += "========================================\n";
        info += "  Patient ID : " + patientId + "\n";
        info += "  Name       : " + patient.getPatientName() + "\n";
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