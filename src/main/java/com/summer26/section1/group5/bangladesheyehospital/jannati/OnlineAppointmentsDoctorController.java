package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class OnlineAppointmentsDoctorController {

    @FXML
    private TextField doctorIdTextField;

    @FXML
    private TableView<AppointmentModelClass> appointmentTableView;

    @FXML
    private TableColumn<AppointmentModelClass, String> patientIdColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> patientNameColumn;

    @FXML
    private TableColumn<AppointmentModelClass, Integer> doctorIdColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> doctorNameColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> appointmentDateColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> appointmentTimeColumn;

    @FXML
    private Label messageLabel;

    private final File dataFolder = new File("data");

    private final File appointmentFile =
            new File(dataFolder, "appointments.bin");

    private final ArrayList<AppointmentModelClass> appointmentList =
            new ArrayList<>();
    @FXML
    private Button searchButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button backButton;
    @FXML
    private Button confirmAppointmentButton;

    @FXML
    public void initialize() {

        patientIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientId"));

        patientNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientName"));

        doctorIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("doctorId"));

        doctorNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("doctorName"));

        appointmentDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentDate"));

        appointmentTimeColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentTime"));

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

                if ("Online".equalsIgnoreCase(
                        appointment.getAppointmentType())) {

                    appointmentList.add(appointment);
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to load appointments.");
        }

        appointmentTableView.getItems().addAll(appointmentList);

        if (appointmentList.isEmpty()) {

            messageLabel.setText("No online appointments found.");

        } else {

            messageLabel.setText(
                    appointmentList.size()
                            + " online appointment(s) loaded.");
        }
    }









    @FXML
    public void refreshButton(ActionEvent actionEvent) {

        doctorIdTextField.clear();

        appointmentTableView.getItems().clear();

        loadAppointments();
    }


    @FXML
    public void confirmAppointmentButton(ActionEvent actionEvent) {

        AppointmentModelClass selectedAppointment =
                appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {

            messageLabel.setText("Please select an appointment.");
            return;
        }

        Alert confirmAlert =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmAlert.setTitle("Confirm Appointment");
        confirmAlert.setHeaderText("Online Appointment");

        confirmAlert.setContentText(
                "Do you want to confirm the appointment?\n\n"
                        + "Appointment ID : " + selectedAppointment.getAppointmentId()
                        + "\nPatient ID : " + selectedAppointment.getPatientId()
                        + "\nPatient Name : " + selectedAppointment.getPatientName()
                        + "\nDoctor : " + selectedAppointment.getDoctorName()
        );

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            selectedAppointment.setAppointmentStatus("Confirmed");

            try (ObjectOutputStream oos =
                         new ObjectOutputStream(new FileOutputStream(appointmentFile))) {

                for (AppointmentModelClass appointment : appointmentList) {

                    oos.writeObject(appointment);
                }

            } catch (IOException e) {

                e.printStackTrace();
                messageLabel.setText("Unable to save appointment.");
                return;
            }

            Alert successAlert =
                    new Alert(Alert.AlertType.INFORMATION);

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
    public void searchButton(ActionEvent actionEvent) {

        if (doctorIdTextField.getText().trim().isEmpty()) {

            messageLabel.setText("Enter Doctor ID.");
            return;
        }

        int doctorId;

        try {

            doctorId =
                    Integer.parseInt(doctorIdTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Doctor ID must be numeric.");
            return;
        }

        appointmentTableView.getItems().clear();

        int count = 0;

        for (AppointmentModelClass appointment : appointmentList) {

            if (appointment.getDoctorId() == doctorId
                    && "Online".equalsIgnoreCase(appointment.getAppointmentType())) {

                appointmentTableView.getItems().add(appointment);
                count++;
            }
        }

        if (count == 0) {

            messageLabel.setText("No online appointments found.");

        } else {

            messageLabel.setText(count + " appointment(s) found.");
        }
    }


    @FXML
    public void backButton(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to open Doctor Dashboard.");
        }
    }

}