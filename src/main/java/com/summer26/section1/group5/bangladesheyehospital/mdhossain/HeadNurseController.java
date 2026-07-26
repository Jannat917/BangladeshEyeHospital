package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HeadNurseController {
    @FXML private CheckBox idVerified;
    @FXML private CheckBox eyemarked;
    @FXML private CheckBox consentsigned;
    @FXML private CheckBox fastingcheck;
    @FXML private TextField nursesignatureField;
    @FXML private Button finalizeBtn;
    @FXML private Label statusOutput;
    @FXML private Label patientInfoLabel;
    @FXML private Label surgeryTypeLabel;

    private String patientName = "Jahirul Islam";
    private String patientId = "P-998";
    private String surgeryType = "Cataract";

    @FXML
    public void initialize() {
        patientInfoLabel.setText("Active Patient: " + patientName + " (ID: " + patientId + ")");
        surgeryTypeLabel.setText("Surgery: " + surgeryType);
        resetForm();
    }

    @FXML
    private void handleFinalize() {
        boolean allChecked = idVerified.isSelected() && eyemarked.isSelected() &&
                consentsigned.isSelected() && fastingcheck.isSelected();
        if (!allChecked || nursesignatureField.getText().isEmpty()) {
            statusOutput.setText("ERROR: Complete all safety flags and sign the form!");
            statusOutput.setStyle("-fx-text-fill: red;");
        } else {
            statusOutput.setText("OT Readiness Verified. Patient ready for entry.");
            statusOutput.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            System.out.println("Authorized by: " + nursesignatureField.getText());
            finalizeBtn.setDisable(true);
        }
    }

    @FXML
    private void resetForm() {
        idVerified.setSelected(false);
        eyemarked.setSelected(false);
        consentsigned.setSelected(false);
        fastingcheck.setSelected(false);
        nursesignatureField.clear();
        finalizeBtn.setDisable(true);
        statusOutput.setText("Pending verification...");
        statusOutput.setStyle("-fx-text-fill: #f39c12;");
    }
}