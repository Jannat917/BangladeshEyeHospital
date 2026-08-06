package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ViewOpticalPrescriptionsController {
    @FXML private TextField searchTextField;
    @FXML private TableView<OpticalPrescription> prescriptionTableView;
    @FXML private TableColumn<OpticalPrescription, String> prescriptionIdColumn;
    @FXML private TableColumn<OpticalPrescription, Integer> patientIdColumn;
    @FXML private TableColumn<OpticalPrescription, String> patientNameColumn;
    @FXML private TableColumn<OpticalPrescription, String> doctorNameColumn;
    @FXML private TableColumn<OpticalPrescription, LocalDate> prescriptionDateColumn;
    @FXML private TableColumn<OpticalPrescription, String> statusColumn;
    @FXML private TextField rightEyePowerTextField;
    @FXML private TextField leftEyePowerTextField;
    @FXML private TextArea doctorNotesTextArea;
    @FXML private Label statusLabel;

    private final ObservableList<OpticalPrescription> prescriptions = MashrifData.getOpticalPrescriptions();

    @FXML
    public void initialize() {
        prescriptionIdColumn.setCellValueFactory(new PropertyValueFactory<>("prescriptionId"));
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        prescriptionDateColumn.setCellValueFactory(new PropertyValueFactory<>("prescriptionDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        prescriptionTableView.setItems(prescriptions);

        prescriptionTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, p) -> {
                    if (p != null) {
                        rightEyePowerTextField.setText(p.getRightEyePower());
                        leftEyePowerTextField.setText(p.getLeftEyePower());
                        doctorNotesTextArea.setText(p.getDoctorNotes());
                    }
                });
        statusLabel.setText(prescriptions.size() + " prescription(s) loaded.");
    }

    @FXML private void searchButton(ActionEvent event) {
        String key = searchTextField.getText().trim().toLowerCase();
        if (key.isEmpty()) {
            statusLabel.setText("Enter a prescription ID or patient name.");
            return;
        }
        ObservableList<OpticalPrescription> results = FXCollections.observableArrayList();
        for (OpticalPrescription p : prescriptions) {
            if (p.getPrescriptionId().toLowerCase().contains(key)
                    || p.getPatientName().toLowerCase().contains(key)) {
                results.add(p);
            }
        }
        prescriptionTableView.setItems(results);
        statusLabel.setText(results.size() + " result(s) found.");
    }

    @FXML private void showAllButton(ActionEvent event) {
        searchTextField.clear();
        prescriptionTableView.setItems(prescriptions);
        statusLabel.setText(prescriptions.size() + " prescription(s) displayed.");
    }

    @FXML private void backButton(ActionEvent event) { openDashboard(event); }

    private void openDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                    "/com/summer26/section1/group5/bangladesheyehospital/mashrif/OpticianDashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            statusLabel.setText("Could not return to dashboard.");
            e.printStackTrace();
        }
    }
}
