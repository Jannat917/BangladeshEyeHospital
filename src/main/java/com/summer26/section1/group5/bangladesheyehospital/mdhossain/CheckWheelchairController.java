package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CheckWheelchairController {
    @FXML private TextField wheelchairCodeField;
    @FXML private TextField patientIdField;
    @FXML private Label availableLabel;
    @FXML private Label statusLabel;

    private static final Map<String, Wheelchair> wheelchairDB = new HashMap<>();

    static {
        wheelchairDB.put("W-001", new Wheelchair("W-001", "Standard", "Good"));
        wheelchairDB.put("W-002", new Wheelchair("W-002", "Standard", "Good"));
        wheelchairDB.put("W-003", new Wheelchair("W-003", "Bariatric", "Good"));
        wheelchairDB.put("W-004", new Wheelchair("W-004", "Standard", "Good"));
        wheelchairDB.put("W-005", new Wheelchair("W-005", "Standard", "Good"));

        // Mark one as occupied
        Wheelchair w = wheelchairDB.get("W-003");
        w.assignTo("P-999");
    }

    @FXML
    public void checkAvailability(ActionEvent event) {
        int available = 0;
        int total = wheelchairDB.size();

        for (Wheelchair w : wheelchairDB.values()) {
            if (w.isAvailable()) {
                available++;
            }
        }

        availableLabel.setText("Available Wheelchairs: " + available + " / " + total);
        availableLabel.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold;");

        statusLabel.setText("Updated: " + available + " wheelchairs available");
        statusLabel.setStyle("-fx-text-fill: #2980b9; -fx-font-size: 13px;");
    }

    @FXML
    public void assignWheelchair(ActionEvent event) {
        String code = wheelchairCodeField.getText().trim();
        String patientId = patientIdField.getText().trim();

        if (code.isEmpty() || patientId.isEmpty()) {
            statusLabel.setText("ERROR: Enter both wheelchair code and patient ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Wheelchair w = wheelchairDB.get(code);

        if (w == null) {
            statusLabel.setText("ERROR: Invalid wheelchair code! Use W-001 to W-005");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!w.isAvailable()) {
            statusLabel.setText("ERROR: Wheelchair " + code + " is already assigned to " + w.getAssignedToPatientId());
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        w.assignTo(patientId);
        statusLabel.setText("SUCCESS: Wheelchair " + code + " assigned to Patient " + patientId);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");

        wheelchairCodeField.clear();
        patientIdField.clear();

        // Refresh availability
        checkAvailability(null);
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
    }


}