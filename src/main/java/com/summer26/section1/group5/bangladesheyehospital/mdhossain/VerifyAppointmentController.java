package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VerifyAppointmentController {
    @FXML
    private TextField appointmentIdField;
    @FXML
    private Label patientNameLabel;
    @FXML
    private Label doctorLabel;
    @FXML
    private Label timeSlotLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button confirmEntryBtn;

    private boolean appointmentVerified = false;
    private String currentPatientId = "";

    // Patient Database with 3 patients
    private static final Map<String, PatientInfo> patientDB = new HashMap<>();

    static {
        // Patient 1
        PatientInfo p1 = new PatientInfo();
        p1.setPatientId("12345");
        p1.setPatientName("Jahirul Islam");
        p1.setDoctor("Dr. Rahman");
        p1.setTimeSlot("10:30 AM");
        p1.setPhone("01712345678");
        p1.setAge("45");
        p1.setGender("Male");
        patientDB.put("12345", p1);

        // Patient 2
        PatientInfo p2 = new PatientInfo();
        p2.setPatientId("23456");
        p2.setPatientName("Fatema Begum");
        p2.setDoctor("Dr. Sultana");
        p2.setTimeSlot("11:00 AM");
        p2.setPhone("01723456789");
        p2.setAge("52");
        p2.setGender("Female");
        patientDB.put("23456", p2);

        // Patient 3
        PatientInfo p3 = new PatientInfo();
        p3.setPatientId("34567");
        p3.setPatientName("Rahim Khan");
        p3.setDoctor("Dr. Islam");
        p3.setTimeSlot("2:30 PM");
        p3.setPhone("01734567890");
        p3.setAge("38");
        p3.setGender("Male");
        patientDB.put("34567", p3);
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

        PatientInfo patient = patientDB.get(id);

        if (patient != null) {
            currentPatientId = id;
            patientNameLabel.setText("Patient Name: " + patient.getPatientName());
            patientNameLabel.setStyle("-fx-text-fill: #2c3e50;");
            doctorLabel.setText("Doctor: " + patient.getDoctor());
            doctorLabel.setStyle("-fx-text-fill: #2c3e50;");
            timeSlotLabel.setText("Time Slot: " + patient.getTimeSlot());
            timeSlotLabel.setStyle("-fx-text-fill: #2c3e50;");

            String info = "========================================\n";
            info += "Appointment Verified!\n";
            info += "----------------------------------------\n";
            info += "Patient ID: " + patient.getPatientId() + "\n";
            info += "Name: " + patient.getPatientName() + "\n";
            info += "Phone: " + patient.getPhone() + "\n";
            info += "Age: " + patient.getAge() + "\n";
            info += "Gender: " + patient.getGender() + "\n";
            info += "Doctor: " + patient.getDoctor() + "\n";
            info += "Time: " + patient.getTimeSlot() + "\n";
            info += "========================================";

            statusLabel.setText(info);
            statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
            appointmentVerified = true;
            confirmEntryBtn.setDisable(false);
        } else {
            statusLabel.setText("ERROR: Appointment not found! Use IDs: 12345, 23456, or 34567");
            statusLabel.setStyle("-fx-text-fill: red;");
            appointmentVerified = false;
            confirmEntryBtn.setDisable(true);
            clearPatientLabels();
        }
    }

    @FXML
    public void confirmEntry(ActionEvent actionEvent) {
        if (appointmentVerified) {
            statusLabel.setText("Patient verified and allowed entry!");
            statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            confirmEntryBtn.setDisable(true);
        }
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/securityStaffDashboard.fxml");
    }

    private void clearPatientLabels() {
        patientNameLabel.setText("Patient Name: ");
        doctorLabel.setText("Doctor: ");
        timeSlotLabel.setText("Time Slot: ");
    }

    // Inner class for Patient Information
    private static class PatientInfo {
        private String patientId;
        private String patientName;
        private String doctor;
        private String timeSlot;
        private String phone;
        private String age;
        private String gender;

        public String getPatientId() { return patientId; }
        public void setPatientId(String patientId) { this.patientId = patientId; }
        public String getPatientName() { return patientName; }
        public void setPatientName(String patientName) { this.patientName = patientName; }
        public String getDoctor() { return doctor; }
        public void setDoctor(String doctor) { this.doctor = doctor; }
        public String getTimeSlot() { return timeSlot; }
        public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAge() { return age; }
        public void setAge(String age) { this.age = age; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
    }
}