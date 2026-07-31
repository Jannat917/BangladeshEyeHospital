package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.print.PrinterJob;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class HeadNurseController {
    @FXML private CheckBox idVerified;
    @FXML private CheckBox eyemarked;
    @FXML private CheckBox consentsigned;
    @FXML private CheckBox fastingcheck;
    @FXML private Button finalizeBtn;
    @FXML private Label statusOutput;
    @FXML private Label patientInfoLabel;
    @FXML private Label surgeryTypeLabel;
    @FXML private Button printBtn;

    private static final Map<Integer, PatientRecordModelClass> patientDB = InitialEyeScreeningController.patientDB;
    private int currentPatientId = 101;
    private boolean isFinalized = false;

    @FXML
    public void initialize() {
        PatientRecordModelClass patient = patientDB.get(currentPatientId);
        if (patient != null) {
            patientInfoLabel.setText("Patient: " + patient.getPatientName() + " (ID: " + patient.getPatientId() + ")");
            surgeryTypeLabel.setText("Surgery: Cataract");
        }
        resetForm();
        printBtn.setDisable(true);
    }

    @FXML
    public void handleFinalize(ActionEvent event) {
        boolean allChecked = idVerified.isSelected() && eyemarked.isSelected() &&
                consentsigned.isSelected() && fastingcheck.isSelected();

        if (!allChecked) {
            statusOutput.setText("ERROR: Complete all safety flags!");
            statusOutput.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            isFinalized = false;
        } else {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String info = "========================================\n";
            info += "     OT READINESS VERIFIED\n";
            info += "========================================\n";
            info += "  Patient ID : " + currentPatientId + "\n";
            info += "  Name       : " + patientDB.get(currentPatientId).getPatientName() + "\n";
            info += "  Surgery    : Cataract\n";
            info += "  Verified At: " + time + "\n";
            info += "  Status     : Ready for Surgery\n";
            info += "========================================";

            statusOutput.setText(info);
            statusOutput.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
            finalizeBtn.setDisable(true);
            printBtn.setDisable(false);
            isFinalized = true;
        }
    }

    @FXML
    public void printChecklist() {
        if (!isFinalized) {
            statusOutput.setText("ERROR: Finalize the checklist first!");
            statusOutput.setStyle("-fx-text-fill: red;");
            return;
        }

        VBox printContent = new VBox(10);
        printContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New';");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Text title1 = new Text("========================================");
        Text title2 = new Text("     BANGLADESH EYE HOSPITAL");
        Text title3 = new Text("     OT CHECKLIST");
        Text title4 = new Text("========================================");

        PatientRecordModelClass patient = patientDB.get(currentPatientId);
        Text patientText = new Text("  Patient ID   : " + currentPatientId);
        Text nameText = new Text("  Patient Name : " + patient.getPatientName());
        Text surgeryText = new Text("  Surgery Type : Cataract");
        Text idCheck = new Text("  ID Verified  : " + (idVerified.isSelected() ? "Yes" : "No"));
        Text eyeCheck = new Text("  Eye Marked   : " + (eyemarked.isSelected() ? "Yes" : "No"));
        Text consentCheck = new Text("  Consent Signed: " + (consentsigned.isSelected() ? "Yes" : "No"));
        Text fastingCheck = new Text("  Fasting Check: " + (fastingcheck.isSelected() ? "Yes" : "No"));
        Text verifiedText = new Text("  Verified At  : " + time);
        Text statusText = new Text("  Status       : Ready for Surgery");

        Text line1 = new Text("----------------------------------------");
        Text authText = new Text("  Authorized By: Head Nurse");
        Text footer = new Text("========================================");

        for (Text t : new Text[]{title1, title2, title3, title4, patientText, nameText,
                surgeryText, idCheck, eyeCheck, consentCheck, fastingCheck, verifiedText,
                statusText, line1, authText, footer}) {
            t.setStyle("-fx-font-size: 12px;");
        }
        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                patientText, nameText, surgeryText,
                idCheck, eyeCheck, consentCheck, fastingCheck,
                verifiedText, statusText,
                line1, authText, footer
        );

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(printBtn.getScene().getWindow())) {
            boolean success = job.printPage(printContent);
            if (success) {
                job.endJob();
                statusOutput.setText("OT Checklist printed successfully!");
                statusOutput.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            } else {
                statusOutput.setText("ERROR: Print failed!");
                statusOutput.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    public void resetForm(ActionEvent event) {
        resetForm();
    }

    private void resetForm() {
        idVerified.setSelected(false);
        eyemarked.setSelected(false);
        consentsigned.setSelected(false);
        fastingcheck.setSelected(false);
        finalizeBtn.setDisable(false);
        printBtn.setDisable(true);
        isFinalized = false;
        statusOutput.setText("Pending verification...");
        statusOutput.setStyle("-fx-text-fill: #f39c12;");
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/nurseDashboard.fxml");
    }
}