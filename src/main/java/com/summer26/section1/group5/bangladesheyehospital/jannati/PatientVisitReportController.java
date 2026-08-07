package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class PatientVisitReportController {


    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private TextField lastVisitDateTextField;

    @FXML
    private TextField doctorIdTextField;

    @FXML
    private TextField doctorNameTextField;

    @FXML
    private Label messageLabel;

    private final ArrayList<PatientRecordModelClass> patientList =
            new ArrayList<>();

    private final File dataFolder = new File("data");

    private final File patientFile =
            new File(dataFolder, "patients.bin");

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientNameTextField.setEditable(false);
        lastVisitDateTextField.setEditable(false);
        doctorIdTextField.setEditable(false);
        doctorNameTextField.setEditable(false);

        loadAppointments();
    }

    private void loadAppointments() {

        patientList.clear();

        if (!patientFile.exists()) {
            messageLabel.setText("No patient records found.");
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                patientList.add(patient);
            }

        } catch (EOFException e) {

            // End of file reached

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to read patient data.");
        }
    }


    @FXML
    public void searchButton(ActionEvent actionEvent) {

        loadAppointments();

        String input = patientIdTextField.getText().trim();

        if (input.isEmpty()) {
            messageLabel.setText("Enter Patient ID.");
            return;
        }

        int id;

        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid Patient ID.");
            return;
        }

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == id) {

                patientNameTextField.setText(patient.getPatientName());

                if (patient.getAppointmentDate() != null &&
                        !patient.getAppointmentDate().isEmpty()) {

                    lastVisitDateTextField.setText(patient.getAppointmentDate());

                } else {

                    lastVisitDateTextField.clear();
                }

                doctorIdTextField.setText(
                        String.valueOf(patient.getAssignedDoctorId()));

                if (patient.getAssignedDoctor() != null) {
                    doctorNameTextField.setText(patient.getAssignedDoctor());
                } else {
                    doctorNameTextField.clear();
                }

                messageLabel.setText("Patient report generated.");

                found = true;
                break;
            }
        }

        if (!found) {

            patientNameTextField.clear();
            lastVisitDateTextField.clear();
            doctorIdTextField.clear();
            doctorNameTextField.clear();

            messageLabel.setText("Patient not found.");
        }
    }



    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        patientNameTextField.clear();
        lastVisitDateTextField.clear();
        doctorIdTextField.clear();
        doctorNameTextField.clear();

        messageLabel.setText("");

        // Reload patient records for the next search
        loadAppointments();
    }


    @FXML
    public void backButton(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo(
                    "jannati/receiptionistDashboard.fxml");

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to open Receptionist Dashboard.");
        }
    }


}