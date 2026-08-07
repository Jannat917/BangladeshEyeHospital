package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AppointmentConfirmationReceptionistController {

    @FXML
    private TableView<AppointmentModelClass> appointmentTableView;

    @FXML
    private TableColumn<AppointmentModelClass, String> patientIdColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> patientNameColumn;

    @FXML
    private TableColumn<AppointmentModelClass, Integer> doctorIdColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> doctorColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> dateColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> timeColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> typeColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> statusColumn;

    @FXML
    private Label messageLabel;

    private final File dataFolder = new File("data");

    private final File appointmentFile =
            new File(dataFolder, "appointments.bin");

    private final ArrayList<AppointmentModelClass> appointmentList =
            new ArrayList<>();

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientId"));

        patientNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientName"));

        doctorIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("doctorId"));

        doctorColumn.setCellValueFactory(
                new PropertyValueFactory<>("doctorName"));

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentDate"));

        timeColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentTime"));

        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentType"));

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentStatus"));

        loadAppointments();
    }

    private void loadAppointments() {

        appointmentList.clear();
        appointmentTableView.getItems().clear();

        if (!appointmentFile.exists()) {

            messageLabel.setText("No appointments found.");
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(appointmentFile))) {

            while (true) {

                AppointmentModelClass appointment =
                        (AppointmentModelClass) ois.readObject();

                appointmentList.add(appointment);
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to load appointments.");
        }

        appointmentTableView.getItems().addAll(appointmentList);

        messageLabel.setText(
                appointmentList.size() + " appointment(s) loaded.");
    }


    @FXML
    public void confirmButton(ActionEvent actionEvent) {

        AppointmentModelClass selectedAppointment =
                appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {

            messageLabel.setText("Please select an appointment.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

        confirmAlert.setTitle("Confirm Appointment");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText(
                "Appointment ID : " + selectedAppointment.getAppointmentId()
                        + "\nPatient ID : " + selectedAppointment.getPatientId()
                        + "\nPatient Name : " + selectedAppointment.getPatientName()
                        + "\nDoctor : " + selectedAppointment.getDoctorName()
                        + "\nDate : " + selectedAppointment.getAppointmentDate()
                        + "\nTime : " + selectedAppointment.getAppointmentTime()
                        + "\n\nConfirm this appointment?"
        );

        if (confirmAlert.showAndWait().get() == ButtonType.OK) {

            selectedAppointment.setAppointmentStatus("Confirmed");

            try (ObjectOutputStream oos =
                         new ObjectOutputStream(
                                 new FileOutputStream(appointmentFile))) {

                for (AppointmentModelClass appointment : appointmentList) {

                    oos.writeObject(appointment);
                }

            } catch (IOException e) {

                e.printStackTrace();
                messageLabel.setText("Unable to save appointment.");
                return;
            }

            appointmentTableView.refresh();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);

            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText(
                    "Appointment confirmed successfully.");

            successAlert.showAndWait();

            messageLabel.setText("Appointment confirmed.");

        } else {

            messageLabel.setText("Confirmation cancelled.");
        }
    }

    @FXML
    public void refreshButton(ActionEvent actionEvent) {

        loadAppointments();

        messageLabel.setText(
                appointmentList.size() + " appointment(s) loaded.");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo(
                    "jannati/receiptionistDashboard.fxml");

        } catch (IOException e) {

            e.printStackTrace();

            messageLabel.setText(
                    "Unable to open Receptionist Dashboard.");
        }
    }

}