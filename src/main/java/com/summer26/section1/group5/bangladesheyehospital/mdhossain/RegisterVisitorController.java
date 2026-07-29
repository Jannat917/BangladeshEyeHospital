package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class RegisterVisitorController {
    @FXML private TextField visitorNameField;
    @FXML private TextField phoneField;
    @FXML private TextField nidField;
    @FXML private Label statusLabel;
    @FXML private Label visitorIdLabel;
    @FXML private TextArea visitorLogArea;
    @FXML private Button loadSampleBtn;

    public static final Map<String, Visitor> visitorDB = new HashMap<>();
    private static int visitorCount = 0;

    private static final String[][] SAMPLE_VISITORS = {
            {"Md Ismail", "01712345678", "1234567890123"},
            {"Md Kamal", "01723456789", "2345678901234"},
            {"Most Rumi", "01734567890", "3456789012345"}
    };
    private int sampleIndex = 0;

    @FXML
    public void initialize() {
        loadSampleVisitor();
    }

    @FXML
    public void loadSampleVisitor() {
        if (sampleIndex < SAMPLE_VISITORS.length) {
            visitorNameField.setText(SAMPLE_VISITORS[sampleIndex][0]);
            phoneField.setText(SAMPLE_VISITORS[sampleIndex][1]);
            nidField.setText(SAMPLE_VISITORS[sampleIndex][2]);
            sampleIndex++;
            statusLabel.setText("Sample visitor loaded. Click 'Register Visitor' to add.");
            statusLabel.setStyle("-fx-text-fill: #2980b9; -fx-font-size: 13px;");
        } else {
            statusLabel.setText("All 3 sample visitors have been loaded!");
            statusLabel.setStyle("-fx-text-fill: #f39c12; -fx-font-size: 13px;");
            loadSampleBtn.setDisable(true);
        }
    }

    @FXML
    public void registerVisitor(ActionEvent event) {
        String name = visitorNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String nid = nidField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || nid.isEmpty()) {
            statusLabel.setText("ERROR: All fields required!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        if (!phone.matches("\\d{11}")) {
            statusLabel.setText("ERROR: Phone must be 11 digits!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        visitorCount++;
        // AUTO-GENERATE Visitor ID
        String id = "V" + String.format("%06d", System.currentTimeMillis() % 1000000);
        Visitor visitor = new Visitor(id, name, phone, nid);
        visitorDB.put(id, visitor);

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // ===== DISPLAY ID PROMINENTLY =====
        String info = "========================================\n";
        info += "   >>> VISITOR ID : " + id + " <<<\n";   // <-- ID info added here
        info += "========================================\n";
        info += "  Name       : " + name + "\n";
        info += "  Phone      : " + phone + "\n";
        info += "  NID        : " + nid + "\n";
        info += "  Entry Time : " + time + "\n";
        info += "  Status     : Inside\n";
        info += "  Total Registered : " + visitorCount + "\n";
        info += "========================================";

        statusLabel.setText(info);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        visitorIdLabel.setText("Visitor ID: " + id);  // Also shown separately

        String logEntry = "[" + time + "] " + name + " (ID: " + id + ") registered\n";
        visitorLogArea.appendText(logEntry);

        visitorNameField.clear();
        phoneField.clear();
        nidField.clear();

        if (sampleIndex < SAMPLE_VISITORS.length) {
            loadSampleVisitor();
        } else {
            loadSampleBtn.setDisable(true);
            statusLabel.setText("All 3 sample visitors registered!");
            statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        }
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
    }
}