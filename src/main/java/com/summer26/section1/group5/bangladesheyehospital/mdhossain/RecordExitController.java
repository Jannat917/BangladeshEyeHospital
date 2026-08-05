package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.print.PrinterJob;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordExitController {
    @FXML private TextField exitIdField;
    @FXML private Label statusLabel;
    @FXML private Label exitTimeLabel;
    @FXML private Button printBtn;

    private String currentExitName = "";
    private String currentExitId = "";
    private String currentExitType = "";

    // Data folder
    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    // List to hold all patients from file
    private List<PatientRecordModelClass> patientList = new ArrayList<>();
    private static final Map<Integer, String> patientStatus = new HashMap<>();

    private static final Map<String, Visitor> visitorDB = RegisterVisitorController.visitorDB;

    @FXML
    public void initialize() {
        printBtn.setDisable(true);
        loadPatientsFromFile();
        initializePatientStatus();
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

    private void initializePatientStatus() {
        patientStatus.clear();
        // Set all patients as "Inside" by default
        for (PatientRecordModelClass p : patientList) {
            patientStatus.put(p.getPatientId(), "Inside");
        }
        // You can manually set some as "Outside" if needed
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
    public void recordExit() {
        String idText = exitIdField.getText().trim();

        if (idText.isEmpty()) {
            statusLabel.setText("ERROR: Enter ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            exitTimeLabel.setText("");
            printBtn.setDisable(true);
            return;
        }

        // Try Patient first
        try {
            int patientId = Integer.parseInt(idText);
            PatientRecordModelClass patient = findPatientById(patientId);

            if (patient != null) {
                String currentStatus = patientStatus.get(patientId);
                if (currentStatus != null && currentStatus.equals("Inside")) {
                    patientStatus.put(patientId, "Outside");
                    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    currentExitName = patient.getPatientName();
                    currentExitId = String.valueOf(patientId);
                    currentExitType = "Patient";

                    String phone = patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "Not provided";
                    String address = patient.getAddress() != null ? patient.getAddress() : "Not provided";

                    String info = "========================================\n";
                    info += "        EXIT RECORDED\n";
                    info += "========================================\n";
                    info += "  Patient ID : " + patientId + "\n";
                    info += "  Name       : " + patient.getPatientName() + "\n";
                    info += "  Phone      : " + phone + "\n";
                    info += "  Address    : " + address + "\n";
                    info += "  Exit Time  : " + time + "\n";
                    info += "  Status     : Outside\n";
                    info += "========================================";

                    statusLabel.setText(info);
                    statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
                    exitTimeLabel.setText("Exit Time: " + time);
                    printBtn.setDisable(false);
                    exitIdField.clear();
                    return;
                } else {
                    statusLabel.setText("ERROR: Patient already exited!");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    exitTimeLabel.setText("");
                    printBtn.setDisable(true);
                    return;
                }
            }
        } catch (NumberFormatException e) {
            // Not a patient ID
        }

        // Try Visitor
        Visitor visitor = visitorDB.get(idText);
        if (visitor != null) {
            if (visitor.getStatus().equals("Inside")) {
                visitor.setStatus("Left");
                visitor.setExitTime(LocalDateTime.now());
                String time = visitor.getExitTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                currentExitName = visitor.getName();
                currentExitId = visitor.getVisitorId();
                currentExitType = "Visitor";

                String info = "========================================\n";
                info += "        EXIT RECORDED\n";
                info += "========================================\n";
                info += "  Visitor ID : " + visitor.getVisitorId() + "\n";
                info += "  Name       : " + visitor.getName() + "\n";
                info += "  Phone      : " + visitor.getPhone() + "\n";
                info += "  NID        : " + visitor.getNid() + "\n";
                info += "  Exit Time  : " + time + "\n";
                info += "  Status     : Left\n";
                info += "========================================";

                statusLabel.setText(info);
                statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
                exitTimeLabel.setText("Exit Time: " + time);
                printBtn.setDisable(false);
                exitIdField.clear();
                return;
            } else {
                statusLabel.setText("ERROR: Visitor already exited!");
                statusLabel.setStyle("-fx-text-fill: red;");
                exitTimeLabel.setText("");
                printBtn.setDisable(true);
                return;
            }
        }

        // Not found - show available patient IDs
        StringBuilder availableIds = new StringBuilder();
        for (PatientRecordModelClass p : patientList) {
            availableIds.append(p.getPatientId()).append(", ");
        }
        if (availableIds.length() > 0) {
            availableIds.setLength(availableIds.length() - 2);
        }

        statusLabel.setText("ERROR: ID not found! Try Patient ID (" + availableIds.toString() + ") or Visitor ID (V123456)");
        statusLabel.setStyle("-fx-text-fill: red;");
        exitTimeLabel.setText("");
        printBtn.setDisable(true);
    }

    @FXML
    public void printExit(ActionEvent event) {
        if (currentExitName.isEmpty()) {
            statusLabel.setText("ERROR: No exit to print!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        VBox printContent = new VBox(10);
        printContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New';");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Text title1 = new Text("========================================");
        Text title2 = new Text("     BANGLADESH EYE HOSPITAL");
        Text title3 = new Text("     EXIT SLIP");
        Text title4 = new Text("========================================");
        Text idText = new Text("  " + currentExitType + " ID   : " + currentExitId);
        Text nameText = new Text("  Name         : " + currentExitName);
        Text exitTimeText = new Text("  Exit Time    : " + time);
        Text statusText = new Text("  Status       : Exited");
        Text line1 = new Text("----------------------------------------");
        Text authText = new Text("  Authorized By: Security Staff");
        Text footer = new Text("========================================");

        for (Text t : new Text[]{title1, title2, title3, title4, idText, nameText,
                exitTimeText, statusText, line1, authText, footer}) {
            t.setStyle("-fx-font-size: 12px;");
        }
        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                idText, nameText, exitTimeText, statusText,
                line1, authText, footer
        );

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(printBtn.getScene().getWindow())) {
            boolean success = job.printPage(printContent);
            if (success) {
                job.endJob();
                statusLabel.setText("Exit slip printed successfully!");
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