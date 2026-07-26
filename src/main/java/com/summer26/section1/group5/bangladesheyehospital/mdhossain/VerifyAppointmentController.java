package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

public class VerifyAppointmentController {
    @FXML
    private TextField appointmentIdField;
    @FXML
    private Label patientNameLabel;
    @FXML
    private Label doctorLabel;
    @FXML
    private Label timeSlotLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button confirmEntryBtn;

    private boolean appointmentVerified = false;

    @FXML
    public void searchAppointment(ActionEvent actionEvent) {
        String id = appointmentIdField.getText().trim();

        if (id.isEmpty()) {
            statusLabel.setText("ERROR: Please enter an ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!id.matches("\\d+")) {
            statusLabel.setText("ERROR: ID must be numeric!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Simulate database verification (VR)
        if (id.equals("12345")) {
            patientNameLabel.setText("Patient Name: Jahirul Islam");
            doctorLabel.setText("Doctor: Dr. Rahman");
            timeSlotLabel.setText("Time Slot: 10:30 AM");
            statusLabel.setText("Appointment verified successfully!");
            statusLabel.setStyle("-fx-text-fill: #27ae60;");
            appointmentVerified = true;
            confirmEntryBtn.setDisable(false);
        } else {
            statusLabel.setText("ERROR: Appointment not found!");
            statusLabel.setStyle("-fx-text-fill: red;");
            appointmentVerified = false;
            confirmEntryBtn.setDisable(true);
        }
    }

    @FXML
    public void confirmEntry(ActionEvent actionEvent) {
        if (appointmentVerified) {
            statusLabel.setText("Patient verified and allowed entry!");
            statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            confirmEntryBtn.setDisable(true);
        }
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/securityStaffDashboard.fxml");
    }


}