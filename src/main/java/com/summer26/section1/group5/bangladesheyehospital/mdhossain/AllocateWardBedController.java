package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllocateWardBedController {
    @FXML private TextField patientIdField;
    @FXML private TextField bedCodeField;
    @FXML private Label statusLabel;
    @FXML private Label availableBedsLabel;

    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    private List<PatientRecordModelClass> patientList = new ArrayList<>();
    private static final Map<String, String> bedDB = new HashMap<>();

    static {
        bedDB.put("W-101", "Available");
        bedDB.put("W-102", "Available");
        bedDB.put("W-103", "Occupied");
        bedDB.put("W-104", "Available");
        bedDB.put("W-105", "Available");
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
        loadPatientsFromFile();
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

        String phone = patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "Not provided";

        String info = "========================================\n";
        info += "        BED ALLOCATED\n";
        info += "========================================\n";
        info += "  Patient ID : " + patientId + "\n";
        info += "  Name       : " + patient.getPatientName() + "\n";
        info += "  Phone      : " + phone + "\n";
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