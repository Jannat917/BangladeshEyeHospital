package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.io.IOException;

public class ReportIncidentController {
    @FXML private ComboBox<String> incidentTypeCombo;
    @FXML private TextArea incidentDetailsArea;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        incidentTypeCombo.getItems().addAll(
                "Security Breach",
                "Medical Emergency",
                "Fire Hazard",
                "Theft",
                "Accident",
                "Other"
        );
    }

    @FXML
    public void submitIncident(ActionEvent actionEvent) {
        String type = incidentTypeCombo.getValue();
        String details = incidentDetailsArea.getText().trim();

        if (type == null || type.isEmpty()) {
            statusLabel.setText("ERROR: Please select an incident type!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (details.isEmpty()) {
            statusLabel.setText("ERROR: Please enter incident details!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String caseId = "C" + System.currentTimeMillis() % 1000000;
        statusLabel.setText("Incident saved with Case ID: " + caseId);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        incidentTypeCombo.setValue(null);
        incidentDetailsArea.clear();
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/securityStaffDashboard.fxml");
    }
}