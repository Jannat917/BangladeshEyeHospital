package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
//import com.summer26.section1.group5.bangladesheyehospital.mdhossain.RegisterVisitorController;
//import com.summer26.section1.group5.bangladesheyehospital.mdhossain.Visitor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

public class IssueEntryPassController {
    @FXML private TextField visitorIdField;
    @FXML private Label statusLabel, passNumberLabel;


    @FXML
    public void issuePass(ActionEvent event) {
        String id = visitorIdField.getText().trim();
        if (id.isEmpty()) {
            statusLabel.setText("ERROR: Enter Visitor ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Visitor visitor = RegisterVisitorController.visitorDB.get(id);
        if (visitor == null) {
            statusLabel.setText("ERROR: Visitor not found!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Generate pass number
        String pass = "P" + (System.currentTimeMillis() % 1000000);
        visitor.setPassNumber(pass);

        statusLabel.setText("Pass issued successfully!");
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");

        // Make sure passNumberLabel is declared at top of class
        passNumberLabel.setText("Pass Number: " + pass);
        visitorIdField.clear();
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/securityStaffDashboard.fxml");
    }
}