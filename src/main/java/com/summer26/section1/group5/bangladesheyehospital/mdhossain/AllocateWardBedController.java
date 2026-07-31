package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AllocateWardBedController {
    @FXML private TextField patientIdField;
    @FXML private TextField bedCodeField;
    @FXML private Label statusLabel;
    @FXML private Label availableBedsLabel;

    private static final Map<Integer, PatientRecordModelClass> patientDB = new HashMap<>();
    private static final Map<String, String> bedDB = new HashMap<>();

    static {
        PatientRecordModelClass p1 = new PatientRecordModelClass();
        p1.setPatientId(101);
        p1.setPatientName("Jahirul Islam");
        patientDB.put(101, p1);

        PatientRecordModelClass p2 = new PatientRecordModelClass();
        p2.setPatientId(102);
        p2.setPatientName("Fatema Begum");
        patientDB.put(102, p2);

        bedDB.put("W-101", "Available");
        bedDB.put("W-102", "Available");
        bedDB.put("W-103", "Occupied");
        bedDB.put("W-104", "Available");
        bedDB.put("W-105", "Available");
    }

    private void updateAvailableBeds() {
        int available = 0;
        for (String status : bedDB.values()) {
            if (status.equals("Available")) available++;
        }
        availableBedsLabel.setText("Available Beds: " + available + " / " + bedDB.size());
        availableBedsLabel.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold;");
    }

    @FXML
    public void initialize() {
        updateAvailableBeds();
    }

    @FXML
    public void checkAvailability(ActionEvent event) {
        updateAvailableBeds();
    }

    @FXML
    public void allocateBed(ActionEvent event) {
        String patientIdText = patientIdField.getText().trim();
        String bedCode = bedCodeField.getText().trim();

        if (patientIdText.isEmpty() || bedCode.isEmpty()) {
            statusLabel.setText("ERROR: Enter both Patient ID and Bed Code!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        int patientId;
        try {
            patientId = Integer.parseInt(patientIdText);
        } catch (NumberFormatException e) {
            statusLabel.setText("ERROR: Patient ID must be numeric!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        PatientRecordModelClass patient = patientDB.get(patientId);
        if (patient == null) {
            statusLabel.setText("ERROR: Patient not found! Use: 101, 102");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!bedDB.containsKey(bedCode)) {
            statusLabel.setText("ERROR: Invalid bed code! Use: W-101, W-102, W-104, W-105");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (bedDB.get(bedCode).equals("Occupied")) {
            statusLabel.setText("ERROR: Bed " + bedCode + " is already occupied!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        bedDB.put(bedCode, "Occupied");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String info = "========================================\n";
        info += "        BED ALLOCATED\n";
        info += "========================================\n";
        info += "  Patient ID : " + patientId + "\n";
        info += "  Name       : " + patient.getPatientName() + "\n";
        info += "  Bed Code   : " + bedCode + "\n";
        info += "  Allocated  : " + time + "\n";
        info += "  Status     : Occupied\n";
        info += "========================================";

        statusLabel.setText(info);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");

        patientIdField.clear();
        bedCodeField.clear();
        updateAvailableBeds();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/nurseDashboard.fxml");
    }
}