package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.print.PrinterJob;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VerifyAppointmentController {
    @FXML private TextField appointmentIdField;
    @FXML private Label patientNameLabel;
    @FXML private Label doctorLabel;
    @FXML private Label timeSlotLabel;
    @FXML private Label statusLabel;
    @FXML private Button confirmEntryBtn;
    @FXML private Button printBtn;

    private boolean appointmentVerified = false;
    private PatientRecordModelClass currentPatient = null;

    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");
    private List<PatientRecordModelClass> patientList = new ArrayList<>();

    @FXML
    public void initialize() {
        printBtn.setDisable(true);
        loadPatientsFromFile();

        // Print available patient IDs to console
        System.out.println("\n========== PATIENT IDs FROM GROUPMATE ==========");
        if (patientList.isEmpty()) {
            System.out.println("No patients found. Ask groupmate to register patients.");
        } else {
            for (PatientRecordModelClass p : patientList) {
                System.out.println("ID: " + p.getPatientId() + " | Name: " + p.getPatientName());
            }
        }
        System.out.println("================================================\n");
    }

    private void loadPatientsFromFile() {
        patientList.clear();
        if (!patientFile.exists()) {
            System.out.println("patients.bin not found.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {
            while (true) {
                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();
                patientList.add(patient);
            }
        } catch (EOFException e) {
            // End of file - normal
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void showAllPatients(ActionEvent event) {
        if (patientList.isEmpty()) {
            statusLabel.setText("No patients found in system!");
            statusLabel.setStyle("-fx-text-fill: #f39c12;");
            return;
        }

        StringBuilder sb = new StringBuilder("Available IDs: ");
        for (PatientRecordModelClass p : patientList) {
            sb.append(p.getPatientId()).append(" (").append(p.getPatientName()).append("), ");
        }
        statusLabel.setText(sb.toString());
        statusLabel.setStyle("-fx-text-fill: #2980b9; -fx-font-size: 13px;");
    }

    @FXML
    public void searchAppointment(ActionEvent actionEvent) {
        String id = appointmentIdField.getText().trim();

        if (id.isEmpty()) {
            statusLabel.setText("ERROR: Please enter an ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            clearPatientLabels();
            return;
        }

        if (!id.matches("\\d+")) {
            statusLabel.setText("ERROR: ID must be numeric!");
            statusLabel.setStyle("-fx-text-fill: red;");
            clearPatientLabels();
            return;
        }

        int patientId = Integer.parseInt(id);
        PatientRecordModelClass patient = findPatientById(patientId);

        if (patient != null) {
            currentPatient = patient;

            patientNameLabel.setText("Patient Name: " + patient.getPatientName());
            patientNameLabel.setStyle("-fx-text-fill: #2c3e50;");

            String doctorName = patient.getAssignedDoctor();
            if (doctorName == null || doctorName.isEmpty()) {
                doctorName = "Not assigned";
            }
            doctorLabel.setText("Doctor: " + doctorName);
            doctorLabel.setStyle("-fx-text-fill: #2c3e50;");

            String timeSlot = patient.getAppointmentDate();
            if (timeSlot == null || timeSlot.isEmpty()) {
                timeSlot = "Not scheduled";
            }
            timeSlotLabel.setText("Time Slot: " + timeSlot);
            timeSlotLabel.setStyle("-fx-text-fill: #2c3e50;");

            String phone = patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "Not provided";
            String address = patient.getAddress() != null ? patient.getAddress() : "Not provided";

            String info = "========================================\n";
            info += "     APPOINTMENT VERIFIED\n";
            info += "========================================\n";
            info += "  Patient ID : " + patient.getPatientId() + "\n";
            info += "  Name       : " + patient.getPatientName() + "\n";
            info += "  Phone      : " + phone + "\n";
            info += "  Age        : " + patient.getAge() + "\n";
            info += "  Gender     : " + patient.getGender() + "\n";
            info += "  Address    : " + address + "\n";
            info += "  Doctor     : " + doctorName + "\n";
            info += "  Time Slot  : " + timeSlot + "\n";
            info += "  Status     : " + (patient.getPaymentStatus() != null ? patient.getPaymentStatus() : "Pending") + "\n";
            info += "========================================";

            statusLabel.setText(info);
            statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
            appointmentVerified = true;
            confirmEntryBtn.setDisable(false);
            printBtn.setDisable(false);
        } else {
            // Show available IDs in error
            StringBuilder availableIds = new StringBuilder();
            for (PatientRecordModelClass p : patientList) {
                availableIds.append(p.getPatientId()).append(", ");
            }
            if (availableIds.length() > 0) {
                availableIds.setLength(availableIds.length() - 2);
            }

            statusLabel.setText("ERROR: Patient not found! Available IDs: " + availableIds.toString());
            statusLabel.setStyle("-fx-text-fill: red;");
            appointmentVerified = false;
            confirmEntryBtn.setDisable(true);
            printBtn.setDisable(true);
            clearPatientLabels();
        }
    }

    @FXML
    public void confirmEntry(ActionEvent actionEvent) {
        if (appointmentVerified && currentPatient != null) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String info = "========================================\n";
            info += "     ENTRY CONFIRMED\n";
            info += "========================================\n";
            info += "  Patient ID : " + currentPatient.getPatientId() + "\n";
            info += "  Name       : " + currentPatient.getPatientName() + "\n";
            info += "  Entry Time : " + time + "\n";
            info += "  Status     : Inside Hospital\n";
            info += "========================================";

            statusLabel.setText(info);
            statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 13px;");
            confirmEntryBtn.setDisable(true);
            printBtn.setDisable(false);
        }
    }

    @FXML
    public void printSlip(ActionEvent actionEvent) {
        if (currentPatient == null) {
            statusLabel.setText("ERROR: No appointment verified to print!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        VBox printContent = new VBox(10);
        printContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New';");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String phone = currentPatient.getPhoneNumber() != null ? currentPatient.getPhoneNumber() : "Not provided";
        String address = currentPatient.getAddress() != null ? currentPatient.getAddress() : "Not provided";
        String doctor = currentPatient.getAssignedDoctor() != null ? currentPatient.getAssignedDoctor() : "Not assigned";
        String timeSlot = currentPatient.getAppointmentDate() != null ? currentPatient.getAppointmentDate() : "Not scheduled";

        Text title1 = new Text("========================================");
        Text title2 = new Text("     BANGLADESH EYE HOSPITAL");
        Text title3 = new Text("     VERIFICATION SLIP");
        Text title4 = new Text("========================================");
        Text patientIdText = new Text("  Patient ID   : " + currentPatient.getPatientId());
        Text patientNameText = new Text("  Patient Name : " + currentPatient.getPatientName());
        Text phoneText = new Text("  Phone        : " + phone);
        Text ageText = new Text("  Age          : " + currentPatient.getAge());
        Text genderText = new Text("  Gender       : " + currentPatient.getGender());
        Text addressText = new Text("  Address      : " + address);
        Text doctorText = new Text("  Doctor       : " + doctor);
        Text timeSlotText = new Text("  Time Slot    : " + timeSlot);
        Text entryTimeText = new Text("  Entry Time   : " + time);
        Text statusText = new Text("  Status       : Verified");
        Text line1 = new Text("----------------------------------------");
        Text authText = new Text("  Authorized By: Security Staff");
        Text dateText = new Text("  Date         : " + time);
        Text footer = new Text("========================================");

        for (Text t : new Text[]{title1, title2, title3, title4, patientIdText, patientNameText,
                phoneText, ageText, genderText, addressText, doctorText, timeSlotText,
                entryTimeText, statusText, line1, authText, dateText, footer}) {
            t.setStyle("-fx-font-size: 12px;");
        }
        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                patientIdText, patientNameText, phoneText, ageText, genderText,
                addressText, doctorText, timeSlotText, entryTimeText, statusText,
                line1, authText, dateText, footer
        );

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(printBtn.getScene().getWindow())) {
            boolean success = job.printPage(printContent);
            if (success) {
                job.endJob();
                statusLabel.setText("Slip printed successfully!");
                statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            } else {
                statusLabel.setText("ERROR: Print failed!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
    }

    private void clearPatientLabels() {
        patientNameLabel.setText("Patient Name: ");
        doctorLabel.setText("Doctor: ");
        timeSlotLabel.setText("Time Slot: ");
    }
}