package com.summer26.section1.group5.bangladesheyehospital.jannati;

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

    private final ArrayList<AppointmentModelClass> appointmentList =
            new ArrayList<>();

    private final File dataFolder = new File("data");

    private final File appointmentFile =
            new File(dataFolder, "appointments.bin");
    @FXML
    private TextField appointmentIdTextField;

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientNameTextField.setEditable(false);
        lastVisitDateTextField.setEditable(false);
        doctorIdTextField.setEditable(false);
        doctorNameTextField.setEditable(false);
        appointmentIdTextField.setEditable(false);

        loadAppointments();
    }

    private void loadAppointments() {

        appointmentList.clear();

        if (!appointmentFile.exists()) {
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(appointmentFile))) {

            while (true) {

                AppointmentModelClass appointment =
                        (AppointmentModelClass) ois.readObject();

                appointmentList.add(appointment);
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void searchButton(ActionEvent actionEvent) {

        loadAppointments();

        String patientId = patientIdTextField.getText().trim();

        if (patientId.isEmpty()) {

            messageLabel.setText("Enter Patient ID.");
            return;
        }

        AppointmentModelClass latestAppointment = null;

        for (AppointmentModelClass appointment : appointmentList) {

            if (appointment.getPatientId().equals(patientId)) {

                if (latestAppointment == null ||
                        appointment.getAppointmentDate().isAfter(latestAppointment.getAppointmentDate())) {

                    latestAppointment = appointment;
                }
            }
        }

        if (latestAppointment != null) {

            patientNameTextField.setText(latestAppointment.getPatientName());

            if (latestAppointment.getAppointmentDate() != null) {
                lastVisitDateTextField.setText(
                        latestAppointment.getAppointmentDate().toString());
            } else {
                lastVisitDateTextField.clear();
            }

            appointmentIdTextField.setText(
                    latestAppointment.getAppointmentId());

            doctorIdTextField.setText(
                    String.valueOf(latestAppointment.getDoctorId()));

            doctorNameTextField.setText(
                    latestAppointment.getDoctorName());

            messageLabel.setText("Patient report generated.");

        } else {

            patientNameTextField.clear();
            lastVisitDateTextField.clear();
            appointmentIdTextField.clear();
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
        appointmentIdTextField.clear();

        messageLabel.setText("");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");
    }

}