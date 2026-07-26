package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class RecordExitController {
    @FXML private TextField exitIdField;
    @FXML private Label statusLabel;
    @FXML private Label exitTimeLabel;

    private Map<String, Boolean> visitorStatus = new HashMap<>();

    @FXML
    public void initialize() {
        visitorStatus.put("P-12345", true);
        visitorStatus.put("V-67890", true);
        visitorStatus.put("P-11111", true);
    }

    @FXML
    public void recordExit(ActionEvent actionEvent) {
        String id = exitIdField.getText().trim();

        if (id.isEmpty()) {
            statusLabel.setText("ERROR: Please enter ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!visitorStatus.containsKey(id)) {
            statusLabel.setText("ERROR: ID not found or already exited!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!visitorStatus.get(id)) {
            statusLabel.setText("ERROR: This ID has already been recorded as exited!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        visitorStatus.put(id, false);
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        statusLabel.setText("Exit recorded successfully!");
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        exitTimeLabel.setText("Exit Time: " + time);
        exitIdField.clear();
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/securityStaffDashboard.fxml");
    }
}