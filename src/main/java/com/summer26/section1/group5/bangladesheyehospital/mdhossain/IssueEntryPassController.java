package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.print.PrinterJob;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IssueEntryPassController {
    @FXML private TextField visitorIdField;
    @FXML private Label statusLabel;
    @FXML private Label passNumberLabel;
    @FXML private Button printBtn;

    private Visitor currentVisitor = null;
    private PatientRecordModelClass currentPatient = null;
    private String currentPassNumber = "";
    private String currentType = "";

    private static final Map<String, Visitor> visitorDB = RegisterVisitorController.visitorDB;

    // Data folder
    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    // List to hold all patients from file
    private List<PatientRecordModelClass> patientList = new ArrayList<>();

    @FXML
    public void initialize() {
        printBtn.setDisable(true);
        loadPatientsFromFile();
    }

    private void loadPatientsFromFile() {
        patientList.clear();

        if (!patientFile.exists()) {
            System.out.println("patients.bin not found. Please register patients first.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {
            while (true) {
                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();
                patientList.add(patient);
            }
        } catch (EOFException e) {
            // End of file reached - normal
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Loaded " + patientList.size() + " patients from file.");
    }

    private PatientRecordModelClass findPatientById(int id) {
        for (PatientRecordModelClass patient : patientList) {
            if (patient.getPatientId() == id) {
                return patient;
            }
        }
        return null;
    }

    @FXML
    public void issuePass(ActionEvent event) {
        String id = visitorIdField.getText().trim();

        if (id.isEmpty()) {
            statusLabel.setText("ERROR: Enter ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            passNumberLabel.setText("");
            printBtn.setDisable(true);
            currentVisitor = null;
            currentPatient = null;
            return;
        }

        // First, try as Visitor
        Visitor visitor = visitorDB.get(id);
        if (visitor == null) {
            Visitor visitorAlt = visitorDB.get("V" + id);
            if (visitorAlt != null) {
                visitor = visitorAlt;
            }
        }

        if (visitor != null) {
            currentVisitor = visitor;
            currentPatient = null;
            currentType = "Visitor";

            if (visitor.getPassNumber() != null && !visitor.getPassNumber().isEmpty()) {
                statusLabel.setText("Visitor already has a pass: " + visitor.getPassNumber());
                statusLabel.setStyle("-fx-text-fill: #f39c12;");
                passNumberLabel.setText("Existing Pass: " + visitor.getPassNumber());
                currentPassNumber = visitor.getPassNumber();
                printBtn.setDisable(false);
                return;
            }

            String passNumber = "P" + String.format("%06d", System.currentTimeMillis() % 1000000);
            visitor.setPassNumber(passNumber);
            currentPassNumber = passNumber;
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
            printBtn.setDisable(false);
            visitorIdField.clear();
            return;
        }

        // If not Visitor, try as Patient
        try {
            int patientId = Integer.parseInt(id);
            PatientRecordModelClass patient = findPatientById(patientId);

            if (patient != null) {
                currentPatient = patient;
                currentVisitor = null;
                currentType = "Patient";

                String passNumber = "P" + String.format("%06d", System.currentTimeMillis() % 1000000);
                currentPassNumber = passNumber;
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                String phone = patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "Not provided";
                String address = patient.getAddress() != null ? patient.getAddress() : "Not provided";

                String info = "========================================\n";
                info += "      ENTRY PASS ISSUED\n";
                info += "----------------------------------------\n";
                info += "  Patient ID : " + patient.getPatientId() + "\n";
                info += "  Name       : " + patient.getPatientName() + "\n";
                info += "  Phone      : " + phone + "\n";
                info += "  Age        : " + patient.getAge() + "\n";
                info += "  Gender     : " + patient.getGender() + "\n";
                info += "  Address    : " + address + "\n";
                info += "  Doctor     : " + (patient.getAssignedDoctor() != null ? patient.getAssignedDoctor() : "Not assigned") + "\n";
                info += "  Pass No.   : " + passNumber + "\n";
                info += "  Issued At  : " + time + "\n";
                info += "  Status     : Active\n";
                info += "========================================";

                statusLabel.setText(info);
                statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
                passNumberLabel.setText("Pass Number: " + passNumber);
                printBtn.setDisable(false);
                visitorIdField.clear();
                return;
            }
        } catch (NumberFormatException e) {
            // Not a numeric ID
        }

        // Not found - show available patient IDs
        StringBuilder availableIds = new StringBuilder();
        for (PatientRecordModelClass p : patientList) {
            availableIds.append(p.getPatientId()).append(", ");
        }
        if (availableIds.length() > 0) {
            availableIds.setLength(availableIds.length() - 2);
        }

        statusLabel.setText("ERROR: ID not found! Try Visitor ID (V123456) or Patient ID (" + availableIds.toString() + ")");
        statusLabel.setStyle("-fx-text-fill: red;");
        passNumberLabel.setText("");
        printBtn.setDisable(true);
        currentVisitor = null;
        currentPatient = null;
    }

    @FXML
    public void printPass(ActionEvent event) {
        if (currentVisitor == null && currentPatient == null) {
            statusLabel.setText("ERROR: No pass to print!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        VBox printContent = new VBox(10);
        printContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New';");

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Text title1 = new Text("========================================");
        Text title2 = new Text("     BANGLADESH EYE HOSPITAL");
        Text title3 = new Text("     ENTRY PASS");
        Text title4 = new Text("========================================");

        Text idText, nameText, phoneText, infoText;

        if (currentVisitor != null) {
            idText = new Text("  Visitor ID   : " + currentVisitor.getVisitorId());
            nameText = new Text("  Name         : " + currentVisitor.getName());
            phoneText = new Text("  Phone        : " + currentVisitor.getPhone());
            infoText = new Text("  Type         : Visitor");
        } else {
            String phone = currentPatient.getPhoneNumber() != null ? currentPatient.getPhoneNumber() : "Not provided";
            idText = new Text("  Patient ID   : " + currentPatient.getPatientId());
            nameText = new Text("  Name         : " + currentPatient.getPatientName());
            phoneText = new Text("  Phone        : " + phone);
            infoText = new Text("  Type         : Patient");
        }

        Text passText = new Text("  Pass Number  : " + currentPassNumber);
        Text issuedText = new Text("  Issued At    : " + time);
        Text statusText = new Text("  Status       : Active");
        Text validText = new Text("  Valid Until  : " + LocalDateTime.now().plusHours(24).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Text line1 = new Text("----------------------------------------");
        Text authText = new Text("  Authorized By: Security Staff");
        Text footer = new Text("========================================");

        for (Text t : new Text[]{title1, title2, title3, title4, idText, nameText,
                phoneText, infoText, passText, issuedText, statusText, validText, line1, authText, footer}) {
            t.setStyle("-fx-font-size: 12px;");
        }
        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                idText, nameText, phoneText, infoText, passText, issuedText, statusText, validText,
                line1, authText, footer
        );

        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null && job.showPrintDialog(printBtn.getScene().getWindow())) {
            boolean success = job.printPage(printContent);
            if (success) {
                job.endJob();
                statusLabel.setText("Pass printed successfully!");
                statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            } else {
                statusLabel.setText("ERROR: Print failed!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
    }
}