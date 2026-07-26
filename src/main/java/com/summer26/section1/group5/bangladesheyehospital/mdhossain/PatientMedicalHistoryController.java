package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PatientMedicalHistoryController implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private Label lblName;

    @FXML
    private Label lblAge;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblContact;

    @FXML
    private ListView<String> reportList;

    @FXML
    private ListView<String> prescriptionList;

    @FXML
    private Label statusLabel;

    private final ArrayList<String> reports = new ArrayList<>();
    private final ArrayList<String> prescriptions = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reports.add("Eye Exam - Jan 2026");
        reports.add("Retinal Scan - Feb 2026");
        reports.add("Glaucoma Test - Mar 2026");

        prescriptions.add("Prescription #001 - Jan 2026");
        prescriptions.add("Prescription #002 - Feb 2026");

        if (reportList != null && prescriptionList != null) {
            ObservableList<String> reportsObservable = FXCollections.observableArrayList(reports);
            ObservableList<String> prescriptionsObservable = FXCollections.observableArrayList(prescriptions);

            reportList.setItems(reportsObservable);
            prescriptionList.setItems(prescriptionsObservable);
        }

        statusLabel.setText("Enter ID to search");
    }

    @FXML
    public void searchHistory() {
        if (txtSearch.getText().isEmpty()) {
            statusLabel.setText("Enter patient ID!");
            return;
        }
        lblName.setText("John Doe");
        lblAge.setText("45");
        lblGender.setText("Male");
        lblContact.setText("+880-1234-567890");
        statusLabel.setText(String.format("Found records for ID: %s", txtSearch.getText()));
    }

    @FXML
    public void clearHistory() {
        txtSearch.clear();
        lblName.setText("");
        lblAge.setText("");
        lblGender.setText("");
        lblContact.setText("");
        statusLabel.setText("Cleared");
    }

    @FXML
    public void goBack() {
        statusLabel.setText("Going back...");
    }
}