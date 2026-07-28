package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class RecordExitController {
    @FXML private TextField exitIdField;
    @FXML private Label statusLabel;
    @FXML private Label exitTimeLabel;


    private static final Map<Integer, PatientRecordModelClass> patientDB = new HashMap<>();


    private static final Map<String, Visitor> visitorDB = RegisterVisitorController.visitorDB;

    static {

        PatientRecordModelClass p1 = new PatientRecordModelClass();
        p1.setPatientId(101);
        p1.setPatientName("Jahirul Islam");
        p1.setAge(45);
        p1.setGender("Male");
        p1.setPhoneNumber("01712345678");
        p1.setAddress("Dhaka, Bangladesh");
        patientDB.put(101, p1);

        PatientRecordModelClass p2 = new PatientRecordModelClass();
        p2.setPatientId(102);
        p2.setPatientName("Rahim Khan");
        p2.setAge(38);
        p2.setGender("Male");
        p2.setPhoneNumber("01723456789");
        p2.setAddress("Chittagong, Bangladesh");
        patientDB.put(102, p2);

        PatientRecordModelClass p3 = new PatientRecordModelClass();
        p3.setPatientId(103);
        p3.setPatientName("Fatema Begum");
        p3.setAge(52);
        p3.setGender("Female");
        p3.setPhoneNumber("01734567890");
        p3.setAddress("Sylhet, Bangladesh");
        patientDB.put(103, p3);
    }


    private static final Map<Integer, String> patientStatus = new HashMap<>();
    static {
        patientStatus.put(101, "Inside");
        patientStatus.put(102, "Inside");
        patientStatus.put(103, "Outside");
    }

    @FXML
    public void recordExit() {
        String idText = exitIdField.getText().trim();

        if (idText.isEmpty()) {
            statusLabel.setText("ERROR: Enter ID!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }


        try {
            int patientId = Integer.parseInt(idText);
            PatientRecordModelClass patient = patientDB.get(patientId);

            if (patient != null) {
                String currentStatus = patientStatus.get(patientId);
                if (currentStatus != null && currentStatus.equals("Inside")) {
                    patientStatus.put(patientId, "Outside");
                    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    statusLabel.setText("Exit recorded for patient: " + patient.getPatientName());
                    statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    exitTimeLabel.setText("Exit Time: " + time);
                    exitIdField.clear();
                    return;
                } else {
                    statusLabel.setText("ERROR: Patient already exited!");
                    statusLabel.setStyle("-fx-text-fill: red;");
                    return;
                }
            }
        } catch (NumberFormatException e) {

        }


        Visitor visitor = visitorDB.get(idText);
        if (visitor != null) {
            if (visitor.getStatus().equals("Inside")) {
                visitor.setStatus("Left");
                visitor.setExitTime(LocalDateTime.now());
                String time = visitor.getExitTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                statusLabel.setText("Exit recorded for visitor: " + visitor.getName());
                statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                exitTimeLabel.setText("Exit Time: " + time);
                exitIdField.clear();
                return;
            } else {
                statusLabel.setText("ERROR: Visitor already exited!");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }
        }


        statusLabel.setText("ERROR: ID not found!");
        statusLabel.setStyle("-fx-text-fill: red;");
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/securityStaffDashboard.fxml");
    }
}