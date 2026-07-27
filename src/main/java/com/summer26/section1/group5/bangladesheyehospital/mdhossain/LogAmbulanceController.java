package com.summer26.section1.group5.bangladesheyehospital.mdhossain.controllers;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogAmbulanceController {
    @FXML private TextField ambulanceNumberField;
    @FXML private Label statusLabel, timeLabel;

    private static final List<String> ambulanceLog = new ArrayList<>();

    @FXML
    public void logAmbulance(ActionEvent event) {
        String number = ambulanceNumberField.getText().trim();
        if (number.isEmpty()) {
            statusLabel.setText("ERROR: Enter ambulance number!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ambulanceLog.add("Ambulance " + number + " logged at " + time);
        statusLabel.setText("Emergency logged & Nurse notified!");
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        timeLabel.setText("Arrival Time: " + time);
        ambulanceNumberField.clear();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/securityStaffDashboard.fxml");
    }
}