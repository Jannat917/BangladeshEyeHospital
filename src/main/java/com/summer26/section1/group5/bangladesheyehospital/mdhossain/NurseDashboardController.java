package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class NurseDashboardController {

    @FXML private Label subtitleLabel;
    @FXML private Label todayLabel;
    @FXML private Label timeLabel;

    @FXML private Label waitingPatientsLabel;
    @FXML private Label activeQueuesLabel;
    @FXML private Label medicationsLabel;
    @FXML private Label occupiedBedsLabel;

    @FXML private Label statusLabel;

    @FXML
    private void initialize() {
        todayLabel.setText(LocalDate.now().toString());
        timeLabel.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));

        // Sample dashboard values. Replace later with real data if needed.
        waitingPatientsLabel.setText("24");
        activeQueuesLabel.setText("6");
        medicationsLabel.setText("18");
        occupiedBedsLabel.setText("12");

        statusLabel.setText("System ready. Select a task from the sidebar.");
        subtitleLabel.setText("Manage screening, queues, history, medication, discharge, and bed allocation.");
    }

    @FXML
    private void goDashboard(ActionEvent event) {
        // Dashboard button can simply refresh the current page if needed.
        statusLabel.setText("You are already on the Nurse Dashboard.");
    }

    @FXML
    private void openInitialEyeScreening(ActionEvent event) {
        SceneSwitcher.switchTo("mdhossain/InitialEyeScreening.fxml", event);
    }

    @FXML
    private void openDoctorQueues(ActionEvent event) {
        SceneSwitcher.switchTo("mdhossain/DoctorQueue.fxml", event);
    }

    @FXML
    private void openMedicalHistory(ActionEvent event) {
        SceneSwitcher.switchTo("mdhossain/MedicalHistory.fxml", event);
    }

    @FXML
    private void openAssignDoctor(ActionEvent event) {
        SceneSwitcher.switchTo("mdhossain/AssignDoctor.fxml", event);
    }

    @FXML
    private void openHeadNurse(ActionEvent event) {
        SceneSwitcher.switchTo("mdhossain/HeadNurse.fxml", event);
    }

    @FXML
    private void openMedicationTracking(ActionEvent event) {
        SceneSwitcher.switchTo("mdhossain/Medication.fxml", event);
    }

    @FXML
    private void openDischargeSummaries(ActionEvent event) {
        SceneSwitcher.switchTo("mdhossain/Discharge.fxml", event);
    }

    @FXML
    private void openBedAllocation(ActionEvent event) {
        SceneSwitcher.switchTo("mdhossain/BedAllocation.fxml", event);
    }

    @FXML
    private void logout(ActionEvent event) {
        SceneSwitcher.switchTo("common/login.fxml");
    }
}