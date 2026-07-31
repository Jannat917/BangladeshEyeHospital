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
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AssignDoctorsController {
    @FXML private TextField patientIdField;
    @FXML private ComboBox<String> doctorCombo;
    @FXML private Label statusLabel;
    @FXML private Button printBtn;

    private static final Map<Integer, PatientRecordModelClass> patientDB = new HashMap<>();
    private static final Map<Integer, DoctorModelClass> doctorDB = new HashMap<>();
    private String lastAssignedPatient = "";
    private String lastAssignedDoctor = "";

    static {
        PatientRecordModelClass p1 = new PatientRecordModelClass();
        p1.setPatientId(101);
        p1.setPatientName("Jahirul Islam");
        p1.setAssignedDoctor(null);
        patientDB.put(101, p1);

        PatientRecordModelClass p2 = new PatientRecordModelClass();
        p2.setPatientId(102);
        p2.setPatientName("Fatema Begum");
        p2.setAssignedDoctor(null);
        patientDB.put(102, p2);

        PatientRecordModelClass p3 = new PatientRecordModelClass();
        p3.setPatientId(103);
        p3.setPatientName("Rahim Khan");
        p3.setAssignedDoctor(null);
        patientDB.put(103, p3);

        DoctorModelClass d1 = new DoctorModelClass();
        d1.setDoctorId(1);
        d1.setDoctorName("Dr. Rahman");
        d1.setSpecialization("Eye Specialist");
        d1.setAvailability("Available");
        doctorDB.put(1, d1);

        DoctorModelClass d2 = new DoctorModelClass();
        d2.setDoctorId(2);
        d2.setDoctorName("Dr. Sultana");
        d2.setSpecialization("Retina Specialist");
        d2.setAvailability("Available");
        doctorDB.put(2, d2);

        DoctorModelClass d3 = new DoctorModelClass();
        d3.setDoctorId(3);
        d3.setDoctorName("Dr. Islam");
        d3.setSpecialization("Cornea Specialist");
        d3.setAvailability("Available");
        doctorDB.put(3, d3);
    }

    @FXML
    public void initialize() {
        for (DoctorModelClass d : doctorDB.values()) {
            doctorCombo.getItems().add(d.getDoctorId() + " - " + d.getDoctorName() + " (" + d.getSpecialization() + ")");
        }
        printBtn.setDisable(true);
    }

    @FXML
    public void assignDoctor(ActionEvent event) {
        String patientIdText = patientIdField.getText().trim();
        String doctorSelection = doctorCombo.getValue();

        if (patientIdText.isEmpty() || doctorSelection == null) {
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

        PatientRecordModelClass patient = patientDB.get(patientId);
        if (patient == null) {
            statusLabel.setText("ERROR: Patient not found! Use: 101, 102, 103");
            statusLabel.setStyle("-fx-text-fill: red;");
            printBtn.setDisable(true);
            return;
        }

        String doctorName = doctorSelection.split(" - ")[1].split(" \\(")[0];
        patient.setAssignedDoctor(doctorName);
        lastAssignedPatient = patient.getPatientName();
        lastAssignedDoctor = doctorName;

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String info = "========================================\n";
        info += "        DOCTOR ASSIGNED\n";
        info += "========================================\n";
        info += "  Patient ID : " + patientId + "\n";
        info += "  Patient    : " + patient.getPatientName() + "\n";
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
        Text patientText = new Text("  Patient  : " + lastAssignedPatient);
        Text doctorText = new Text("  Doctor   : " + lastAssignedDoctor);
        Text timeText = new Text("  Assigned At : " + time);
        Text statusText = new Text("  Status   : Active");
        Text line1 = new Text("----------------------------------------");
        Text authText = new Text("  Authorized By: Nurse");
        Text footer = new Text("========================================");

        for (Text t : new Text[]{title1, title2, title3, title4, patientText, doctorText,
                timeText, statusText, line1, authText, footer}) {
            t.setStyle("-fx-font-size: 12px;");
        }
        title2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        title3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        printContent.getChildren().addAll(
                title1, title2, title3, title4,
                patientText, doctorText, timeText, statusText,
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