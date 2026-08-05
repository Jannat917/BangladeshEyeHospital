package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.DoctorModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.print.PrinterJob;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AssignDoctorsController {
    @FXML private TextField patientIdField;
    @FXML private ComboBox<String> doctorCombo;
    @FXML private Label statusLabel;
    @FXML private Button printBtn;

    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");
    private final File doctorFile = new File(dataFolder, "doctors.bin");

    private List<PatientRecordModelClass> patientList = new ArrayList<>();
    private List<DoctorModelClass> doctorList = new ArrayList<>();
    private String lastAssignedPatient = "";
    private String lastAssignedDoctor = "";
    private int lastAssignedPatientId = 0;

    @FXML
    public void initialize() {
        printBtn.setDisable(true);
        loadPatientsFromFile();
        loadDoctorsFromFile();
        populateDoctorCombo();
    }

    private void loadPatientsFromFile() {
        patientList.clear();
        if (!patientFile.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {
            while (true) {
                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();
                patientList.add(patient);
            }
        } catch (EOFException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDoctorsFromFile() {
        doctorList.clear();
        if (!doctorFile.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(doctorFile))) {
            while (true) {
                DoctorModelClass doctor = (DoctorModelClass) ois.readObject();
                doctorList.add(doctor);
            }
        } catch (EOFException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateDoctorCombo() {
        doctorCombo.getItems().clear();
        if (doctorList.isEmpty()) {
            doctorCombo.getItems().add("No doctors available");
            return;
        }
        for (DoctorModelClass d : doctorList) {
            String availability = d.getAvailability() != null ? d.getAvailability() : "Unknown";
            doctorCombo.getItems().add(
                    d.getDoctorId() + " - " + d.getDoctorName() +
                            " (" + d.getSpecialization() + ") - " + availability
            );
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
    public void assignDoctor(ActionEvent event) {
        String patientIdText = patientIdField.getText().trim();
        String doctorSelection = doctorCombo.getValue();

        if (patientIdText.isEmpty() || doctorSelection == null || doctorList.isEmpty()) {
            statusLabel.setText("ERROR: Enter Patient ID and select Doctor!");
            statusLabel.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            return;
        }

        int patientId;
        try {
            patientId = Integer.parseInt(patientIdText);
        } catch (NumberFormatException e) {
            statusLabel.setText("ERROR: Patient ID must be numeric!");
            statusLabel.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            return;
        }

        PatientRecordModelClass patient = findPatientById(patientId);
        if (patient == null) {
            StringBuilder availableIds = new StringBuilder();
            for (PatientRecordModelClass p : patientList) {
                availableIds.append(p.getPatientId()).append(", ");
            }
            if (availableIds.length() > 0) {
                availableIds.setLength(availableIds.length() - 2);
            }
            statusLabel.setText("ERROR: Patient not found! Available IDs: " + availableIds.toString());
            statusLabel.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            return;
        }

        String doctorName = doctorSelection.split(" - ")[1].split(" \\(")[0];
        patient.setAssignedDoctor(doctorName);
        lastAssignedPatient = patient.getPatientName();
        lastAssignedDoctor = doctorName;
        lastAssignedPatientId = patientId;

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String phone = patient.getPhoneNumber() != null ? patient.getPhoneNumber() : "Not provided";

        String info = "========================================\n";
        info += "        DOCTOR ASSIGNED\n";
        info += "========================================\n";
        info += "  Patient ID : " + patientId + "\n";
        info += "  Patient    : " + patient.getPatientName() + "\n";
        info += "  Phone      : " + phone + "\n";
        info += "  Doctor     : " + doctorName + "\n";
        info += "  Time       : " + time + "\n";
        info += "  Status     : Assigned\n";
        info += "========================================";

        statusLabel.setText(info);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        printBtn.setDisable(false);

        patientIdField.clear();
        doctorCombo.setValue(null);
    }

    @FXML
    public void printAssignment() {
        if (lastAssignedPatient.isEmpty() || lastAssignedDoctor.isEmpty()) {
            statusLabel.setText("ERROR: No assignment to print!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        VBox printContent = new VBox(10);
        printContent.setStyle("-fx-padding: 20; -fx-font-family: 'Courier New';");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Text title1 = new Text("========================================");
        Text title2 = new Text("     BANGLADESH EYE HOSPITAL");
        Text title3 = new Text("     DOCTOR ASSIGNMENT SLIP");
        Text title4 = new Text("========================================");
        Text patientText = new Text("  Patient ID  : " + lastAssignedPatientId);
        Text patientNameText = new Text("  Patient     : " + lastAssignedPatient);
        Text doctorText = new Text("  Doctor      : " + lastAssignedDoctor);
        Text timeText = new Text("  Assigned At : " + time);
        Text statusText = new Text("  Status      : Active");
        Text line1 = new Text("----------------------------------------");
        Text authText = new Text("  Authorized By: Nurse");
        Text footer = new Text("========================================");

        for (Text t : new Text[]{title1, title2, title3, title4, patientText, patientNameText,
                doctorText, timeText, statusText, line1, authText, footer}) {
            t.setStyle("-fx-font-size: 12px;");
        }
        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                patientText, patientNameText, doctorText, timeText, statusText,
                line1, authText, footer
        );

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(printBtn.getScene().getWindow())) {
            boolean success = job.printPage(printContent);
            if (success) {
                job.endJob();
                statusLabel.setText("Assignment slip printed successfully!");
                statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            } else {
                statusLabel.setText("ERROR: Print failed!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/nurseDashboard.fxml");
    }
}