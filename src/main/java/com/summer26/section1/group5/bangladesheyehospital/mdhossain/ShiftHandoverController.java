package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ShiftHandoverController {
    @FXML private TextField officerIdField;
    @FXML private TextArea notesArea;
    @FXML private Label statusLabel;

    private static final Map<String, ShiftHandover> handoverDB = new HashMap<>();

    @FXML
    public void completeHandover(ActionEvent event) {
        String officerId = officerIdField.getText().trim();
        String notes = notesArea.getText().trim();
        if (officerId.isEmpty()) {
            statusLabel.setText("ERROR: Enter next officer ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        String id = "SH" + System.currentTimeMillis() % 1000000;
        ShiftHandover sh = new ShiftHandover(id, "SEC-001", officerId, notes);
        handoverDB.put(id, sh);
        statusLabel.setText("Handover completed at " +
                sh.getHandoverTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        officerIdField.clear();
        notesArea.clear();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/securityStaffDashboard.fxml");
    }
}