package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IssueEntryPassController {
    @FXML private TextField visitorIdField;
    @FXML private Label statusLabel;
    @FXML private Label passNumberLabel;

    private static final java.util.Map<String, Visitor> visitorDB = RegisterVisitorController.visitorDB;

    @FXML
    public void issuePass(ActionEvent event) {
        String id = visitorIdField.getText().trim();

        if (id.isEmpty()) {
            statusLabel.setText("ERROR: Enter Visitor ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            passNumberLabel.setText("");
            return;
        }

        Visitor visitor = visitorDB.get(id);

        if (visitor == null) {
            // Try without leading "V" if user entered without it
            Visitor visitorAlt = visitorDB.get("V" + id);
            if (visitorAlt != null) {
                visitor = visitorAlt;
            } else {
                statusLabel.setText("ERROR: Visitor not found! Please register first.");
                statusLabel.setStyle("-fx-text-fill: red;");
                passNumberLabel.setText("");
                return;
            }
        }

        if (visitor.getPassNumber() != null && !visitor.getPassNumber().isEmpty()) {
            statusLabel.setText("Visitor already has a pass: " + visitor.getPassNumber());
            statusLabel.setStyle("-fx-text-fill: #f39c12;");
            passNumberLabel.setText("Existing Pass: " + visitor.getPassNumber());
            return;
        }

        String passNumber = "P" + String.format("%06d", System.currentTimeMillis() % 1000000);
        visitor.setPassNumber(passNumber);

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String info = "========================================\n";
        info += "      ENTRY PASS ISSUED\n";
        info += "----------------------------------------\n";
        info += "  Visitor ID : " + visitor.getVisitorId() + "\n";
        info += "  Name       : " + visitor.getName() + "\n";
        info += "  Phone      : " + visitor.getPhone() + "\n";
        info += "  Pass No.   : " + passNumber + "\n";
        info += "  Issued At  : " + time + "\n";
        info += "  Status     : Active\n";
        info += "========================================";

        statusLabel.setText(info);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        passNumberLabel.setText("Pass Number: " + passNumber);

        visitorIdField.clear();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
    }
}