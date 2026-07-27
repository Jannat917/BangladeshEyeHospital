package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterVisitorController {
    @FXML private TextField visitorNameField, phoneField, nidField;
    @FXML private Label statusLabel, visitorIdLabel;

    public static final Map<String, Visitor> visitorDB = new HashMap<>();

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

        String id = "V" + System.currentTimeMillis() % 1000000;
        Visitor visitor = new Visitor(id, name, phone, nid);
        visitorDB.put(id, visitor);

        statusLabel.setText("Visitor registered successfully!");
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        visitorIdLabel.setText("Visitor ID: " + id);
        visitorNameField.clear();
        phoneField.clear();
        nidField.clear();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/securityStaffDashboard.fxml");
    }
}