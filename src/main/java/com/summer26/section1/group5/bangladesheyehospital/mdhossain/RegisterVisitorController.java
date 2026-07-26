package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegisterVisitorController {
    @FXML private TextField visitorNameField;
    @FXML private TextField phoneField;
    @FXML private TextField nidField;
    @FXML private Label statusLabel;
    @FXML private Label visitorIdLabel;

    @FXML
    public void registerVisitor(ActionEvent actionEvent) {
        String name = visitorNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String nid = nidField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || nid.isEmpty()) {
            statusLabel.setText("ERROR: All fields are required!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!phone.matches("\\d{11}")) {
            statusLabel.setText("ERROR: Phone must be exactly 11 digits!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!nid.matches("\\d{10,17}")) {
            statusLabel.setText("ERROR: Invalid NID format!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String visitorId = "V" + System.currentTimeMillis() % 1000000;
        String entryTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        statusLabel.setText("Visitor registered successfully!");
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        visitorIdLabel.setText("Visitor ID: " + visitorId + " | Entry Time: " + entryTime);

        visitorNameField.clear();
        phoneField.clear();
        nidField.clear();
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/securityStaffDashboard.fxml");
    }
}