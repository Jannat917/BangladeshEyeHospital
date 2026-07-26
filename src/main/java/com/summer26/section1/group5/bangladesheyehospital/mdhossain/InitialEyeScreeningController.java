package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InitialEyeScreeningController {

    @FXML private TextField patientIdField;
    @FXML private TextField patientNameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> eyeResultBox;
    @FXML private TextField bpField;
    @FXML private TextArea remarksArea;
    @FXML private Label statusLabel;

    @FXML private TableView<ScreeningRecord> screeningTable;
    @FXML private TableColumn<ScreeningRecord, String> idColumn;
    @FXML private TableColumn<ScreeningRecord, String> nameColumn;
    @FXML private TableColumn<ScreeningRecord, String> ageColumn;
    @FXML private TableColumn<ScreeningRecord, String> eyeColumn;
    @FXML private TableColumn<ScreeningRecord, String> bpColumn;
    @FXML private TableColumn<ScreeningRecord, String> remarkColumn;

    private final ObservableList<ScreeningRecord> records = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        eyeResultBox.setItems(FXCollections.observableArrayList(
                "Normal",
                "Mild Issue",
                "Needs Doctor Review",
                "Emergency"
        ));

        idColumn.setCellValueFactory(data -> data.getValue().patientIdProperty());
        nameColumn.setCellValueFactory(data -> data.getValue().patientNameProperty());
        ageColumn.setCellValueFactory(data -> data.getValue().ageProperty());
        eyeColumn.setCellValueFactory(data -> data.getValue().eyeResultProperty());
        bpColumn.setCellValueFactory(data -> data.getValue().bloodPressureProperty());
        remarkColumn.setCellValueFactory(data -> data.getValue().remarksProperty());

        screeningTable.setItems(records);

        records.add(new ScreeningRecord("P001", "Rahim", "34", "Normal", "120/80", "Routine screening"));
        records.add(new ScreeningRecord("P002", "Karim", "41", "Needs Doctor Review", "140/90", "Blurred vision complaint"));

        screeningTable.getSelectionModel().selectedItemProperty().addListener((obs, oldRecord, newRecord) -> {
            if (newRecord != null) {
                patientIdField.setText(newRecord.getPatientId());
                patientNameField.setText(newRecord.getPatientName());
                ageField.setText(newRecord.getAge());
                eyeResultBox.setValue(newRecord.getEyeResult());
                bpField.setText(newRecord.getBloodPressure());
                remarksArea.setText(newRecord.getRemarks());
                statusLabel.setText("Record loaded from table.");
            }
        });

        statusLabel.setText("Ready to screen the patient.");
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String patientId = patientIdField.getText().trim();
        String patientName = patientNameField.getText().trim();
        String age = ageField.getText().trim();
        String eyeResult = eyeResultBox.getValue();
        String bp = bpField.getText().trim();
        String remarks = remarksArea.getText().trim();

        if (patientId.isEmpty() || patientName.isEmpty() || age.isEmpty() || eyeResult == null || bp.isEmpty()) {
            statusLabel.setText("Please fill all required fields.");
            return;
        }

        for (ScreeningRecord record : records) {
            if (record.getPatientId().equalsIgnoreCase(patientId)) {
                statusLabel.setText("Patient ID already exists. Use Update instead.");
                return;
            }
        }

        records.add(new ScreeningRecord(patientId, patientName, age, eyeResult, bp, remarks));
        clearFields();
        statusLabel.setText("Screening record saved successfully.");
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String patientId = patientIdField.getText().trim();

        if (patientId.isEmpty()) {
            statusLabel.setText("Enter a patient ID to search.");
            return;
        }

        for (ScreeningRecord record : records) {
            if (record.getPatientId().equalsIgnoreCase(patientId)) {
                patientNameField.setText(record.getPatientName());
                ageField.setText(record.getAge());
                eyeResultBox.setValue(record.getEyeResult());
                bpField.setText(record.getBloodPressure());
                remarksArea.setText(record.getRemarks());
                statusLabel.setText("Patient record found.");
                return;
            }
        }

        statusLabel.setText("No record found for Patient ID: " + patientId);
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        String patientId = patientIdField.getText().trim();

        if (patientId.isEmpty()) {
            statusLabel.setText("Enter a patient ID to update.");
            return;
        }

        String patientName = patientNameField.getText().trim();
        String age = ageField.getText().trim();
        String eyeResult = eyeResultBox.getValue();
        String bp = bpField.getText().trim();
        String remarks = remarksArea.getText().trim();

        if (patientName.isEmpty() || age.isEmpty() || eyeResult == null || bp.isEmpty()) {
            statusLabel.setText("Please fill all required fields before updating.");
            return;
        }

        for (int i = 0; i < records.size(); i++) {
            ScreeningRecord record = records.get(i);
            if (record.getPatientId().equalsIgnoreCase(patientId)) {
                records.set(i, new ScreeningRecord(patientId, patientName, age, eyeResult, bp, remarks));
                screeningTable.refresh();
                statusLabel.setText("Screening record updated successfully.");
                return;
            }
        }

        statusLabel.setText("No record found to update.");
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
        statusLabel.setText("Form cleared.");
    }

    @FXML
    private void goBackDashboard(ActionEvent event) {
        try {
            SceneSwitcher.switchTo("mdhossain/NurseDashboard.fxml");
        } catch (IOException e) {
            statusLabel.setText("Failed to load Nurse Dashboard.");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        patientIdField.clear();
        patientNameField.clear();
        ageField.clear();
        eyeResultBox.setValue(null);
        bpField.clear();
        remarksArea.clear();
        screeningTable.getSelectionModel().clearSelection();
    }

    public static class ScreeningRecord {
        private final SimpleStringProperty patientId;
        private final SimpleStringProperty patientName;
        private final SimpleStringProperty age;
        private final SimpleStringProperty eyeResult;
        private final SimpleStringProperty bloodPressure;
        private final SimpleStringProperty remarks;

        public ScreeningRecord(String patientId, String patientName, String age, String eyeResult, String bloodPressure, String remarks) {
            this.patientId = new SimpleStringProperty(patientId);
            this.patientName = new SimpleStringProperty(patientName);
            this.age = new SimpleStringProperty(age);
            this.eyeResult = new SimpleStringProperty(eyeResult);
            this.bloodPressure = new SimpleStringProperty(bloodPressure);
            this.remarks = new SimpleStringProperty(remarks);
        }

        public String getPatientId() {
            return patientId.get();
        }

        public SimpleStringProperty patientIdProperty() {
            return patientId;
        }

        public String getPatientName() {
            return patientName.get();
        }

        public SimpleStringProperty patientNameProperty() {
            return patientName;
        }

        public String getAge() {
            return age.get();
        }

        public SimpleStringProperty ageProperty() {
            return age;
        }

        public String getEyeResult() {
            return eyeResult.get();
        }

        public SimpleStringProperty eyeResultProperty() {
            return eyeResult;
        }

        public String getBloodPressure() {
            return bloodPressure.get();
        }

        public SimpleStringProperty bloodPressureProperty() {
            return bloodPressure;
        }

        public String getRemarks() {
            return remarks.get();
        }

        public SimpleStringProperty remarksProperty() {
            return remarks;
        }
    }
}