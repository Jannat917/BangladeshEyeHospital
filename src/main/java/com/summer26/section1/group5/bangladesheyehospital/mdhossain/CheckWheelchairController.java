package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckWheelchairController {
    @FXML private TextField wheelchairCodeField, patientIdField;
    @FXML private Label availableLabel, statusLabel;

    private static final Map<String, Wheelchair> wheelchairDB = new HashMap<>();
    static {
        wheelchairDB.put("W-001", new Wheelchair("W-001", "Standard", "Good"));
        wheelchairDB.put("W-002", new Wheelchair("W-002", "Standard", "Good"));
        wheelchairDB.put("W-003", new Wheelchair("W-003", "Bariatric", "Good"));
        wheelchairDB.put("W-004", new Wheelchair("W-004", "Standard", "Good"));
        wheelchairDB.put("W-005", new Wheelchair("W-005", "Standard", "Good"));
        wheelchairDB.get("W-003").assignTo("P-999");
    }

    @FXML
    public void checkAvailability(ActionEvent event) {
        int available = (int) wheelchairDB.values().stream().filter(Wheelchair::isAvailable).count();
        availableLabel.setText("Available Wheelchairs: " + available + " / " + wheelchairDB.size());
        availableLabel.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold;");
    }

    @FXML
    public void assignWheelchair(ActionEvent event) {
        String code = wheelchairCodeField.getText().trim();
        String patientId = patientIdField.getText().trim();
        if (code.isEmpty() || patientId.isEmpty()) {
            statusLabel.setText("ERROR: Enter both!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        Wheelchair w = wheelchairDB.get(code);
        if (w == null || !w.isAvailable()) {
            statusLabel.setText("ERROR: Invalid or already assigned!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        w.assignTo(patientId);
        statusLabel.setText("Assigned successfully!");
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        wheelchairCodeField.clear();
        patientIdField.clear();
        checkAvailability(null);
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/securityStaffDashboard.fxml");
    }
}