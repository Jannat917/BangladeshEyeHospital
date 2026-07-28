package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReportIncidentController {
    @FXML private ComboBox<String> incidentTypeCombo;
    @FXML private TextArea incidentDetailsArea;
    @FXML private Label statusLabel;

    private static final Map<String, Incident> incidentDB = new HashMap<>();

    @FXML
    public void initialize() {
        incidentTypeCombo.getItems().addAll(
                "Security Breach", "Medical Emergency", "Fire Hazard", "Theft", "Accident", "Other"
        );
    }

    @FXML
    public void submitIncident(ActionEvent event) {
        String type = incidentTypeCombo.getValue();
        String details = incidentDetailsArea.getText().trim();
        if (type == null || details.isEmpty()) {
            statusLabel.setText("ERROR: Select type and enter details!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        String caseId = "C" + System.currentTimeMillis() % 1000000;
        Incident incident = new Incident(caseId, type, details, "Hospital Premises", "Security Staff", "Medium");
        incidentDB.put(caseId, incident);
        statusLabel.setText("Incident saved with Case ID: " + caseId);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        incidentTypeCombo.setValue(null);
        incidentDetailsArea.clear();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/securityStaffDashboard.fxml");
    }
}