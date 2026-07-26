package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

public class IssueEntryPassController {
    @FXML private TextField visitorIdField;
    @FXML private Label statusLabel;
    @FXML private Label passNumberLabel;

    @FXML
    public void issuePass(ActionEvent actionEvent) {
        String visitorId = visitorIdField.getText().trim();

        if (visitorId.isEmpty()) {
            statusLabel.setText("ERROR: Please enter Visitor ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!visitorId.matches("V\\d+")) {
            statusLabel.setText("ERROR: Invalid Visitor ID format!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String passNumber = "P" + System.currentTimeMillis() % 1000000;
        statusLabel.setText("Entry pass issued successfully!");
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        passNumberLabel.setText("Pass Number: " + passNumber);
        visitorIdField.clear();
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/securityStaffDashboard.fxml");
    }
}