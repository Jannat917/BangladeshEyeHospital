package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ShiftHandoverController {
    @FXML
    private TextField officerIdField;
    @FXML
    private TextArea notesArea;
    @FXML
    private Label statusLabel;

    private static final Map<String, ShiftHandover> handoverDB = new HashMap<>();
    private static final Map<String, Officer> officerDB = new HashMap<>();

    static {
        // Seed some officers for demo
        officerDB.put("SEC-001", new Officer("SEC-001", "Md. Hossain", "Security Incharge", "Morning Shift"));
        officerDB.put("SEC-002", new Officer("SEC-002", "Rahim Khan", "Security Officer", "Evening Shift"));
        officerDB.put("SEC-003", new Officer("SEC-003", "Karim Ahmed", "Security Officer", "Night Shift"));
        officerDB.put("SEC-004", new Officer("SEC-004", "Jamal Uddin", "Security Officer", "Morning Shift"));
    }

    @FXML
    public void completeHandover(ActionEvent event) {
        String officerId = officerIdField.getText().trim();
        String notes = notesArea.getText().trim();

        if (officerId.isEmpty()) {
            statusLabel.setText("ERROR: Enter next officer ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Check if officer exists
        Officer nextOfficer = officerDB.get(officerId);
        if (nextOfficer == null) {
            statusLabel.setText("ERROR: Officer ID not found! Use SEC-001, SEC-002, SEC-003, or SEC-004");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String id = "SH" + System.currentTimeMillis() % 1000000;
        String currentOfficerId = "SEC-001";
        Officer currentOfficer = officerDB.get(currentOfficerId);

        String currentName = currentOfficer != null ? currentOfficer.getName() : "Unknown";

        ShiftHandover sh = new ShiftHandover(id, currentOfficerId, currentName, officerId, nextOfficer.getName(), notes);
        handoverDB.put(id, sh);

        // Build detailed handover information
        String info = "========== HANDOVER DETAILS ==========\n";
        info += "Handover ID: " + id + "\n";
        info += "Date & Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";
        info += "----------------------------------------\n";
        info += "Current Officer ID: " + currentOfficerId + "\n";
        info += "Current Officer: " + currentName + "\n";
        info += "Current Shift: " + (currentOfficer != null ? currentOfficer.getShift() : "N/A") + "\n";
        info += "----------------------------------------\n";
        info += "Next Officer ID: " + officerId + "\n";
        info += "Next Officer: " + nextOfficer.getName() + "\n";
        info += "Next Shift: " + nextOfficer.getShift() + "\n";
        info += "Next Designation: " + nextOfficer.getDesignation() + "\n";
        info += "----------------------------------------\n";
        info += "Handover Notes: " + (notes.isEmpty() ? "No notes provided" : notes) + "\n";
        info += "==========================================";

        statusLabel.setText(info);
        statusLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 13px;");

        officerIdField.clear();
        notesArea.clear();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
    }

    private static class Officer {
        private String id;
        private String name;
        private String designation;
        private String shift;

        public Officer(String id, String name, String designation, String shift) {
            this.id = id;
            this.name = name;
            this.designation = designation;
            this.shift = shift;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDesignation() {
            return designation;
        }

        public String getShift() {
            return shift;
        }
    }

    private static class ShiftHandover {
        private String handoverId;
        private String currentOfficerId;
        private String currentOfficerName;
        private String nextOfficerId;
        private String nextOfficerName;
        private String notes;
        private LocalDateTime handoverTime;

        public ShiftHandover(String handoverId, String currentOfficerId, String currentOfficerName,
                             String nextOfficerId, String nextOfficerName, String notes) {
            this.handoverId = handoverId;
            this.currentOfficerId = currentOfficerId;
            this.currentOfficerName = currentOfficerName;
            this.nextOfficerId = nextOfficerId;
            this.nextOfficerName = nextOfficerName;
            this.notes = notes;
            this.handoverTime = LocalDateTime.now();
        }

        public String getHandoverId() {
            return handoverId;
        }

        public String getCurrentOfficerId() {
            return currentOfficerId;
        }

        public String getCurrentOfficerName() {
            return currentOfficerName;
        }

        public String getNextOfficerId() {
            return nextOfficerId;
        }

        public String getNextOfficerName() {
            return nextOfficerName;
        }

        public String getNotes() {
            return notes;
        }

        public LocalDateTime getHandoverTime() {
            return handoverTime;
        }
    }
}

