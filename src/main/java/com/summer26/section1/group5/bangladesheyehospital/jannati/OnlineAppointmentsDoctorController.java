package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.Optional;

public class OnlineAppointmentsDoctorController {

    @FXML
    private TextField doctorIdTextField;

    @FXML
    private TableView<PatientRecordModelClass> appointmentTableView;

    @FXML
    private TableColumn<PatientRecordModelClass, Integer> patientIdColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> patientNameColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, Integer> doctorIdColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> doctorNameColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> appointmentDateColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> appointmentTimeColumn;

    @FXML
    private Label messageLabel;

    private final File patientFile = new File("data/patients.bin");

    private final ObservableList<PatientRecordModelClass> appointmentList =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        patientIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientId"));

        patientNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientName"));

        doctorIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("assignedDoctorId"));

        doctorNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("assignedDoctor"));

        appointmentDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentDate"));

        appointmentTimeColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentTime"));

        loadAppointments();
    }

    private void loadAppointments() {

        appointmentList.clear();

        if (!patientFile.exists()) {

            messageLabel.setText("No appointments found.");
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                if ("Online".equalsIgnoreCase(patient.getAppointmentType())) {

                    appointmentList.add(patient);
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to load appointments.");
        }

        appointmentTableView.setItems(appointmentList);

        if (appointmentList.isEmpty()) {

            messageLabel.setText("No online appointments found.");

        } else {

            messageLabel.setText("");
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

            doctorId = Integer.parseInt(
                    doctorIdTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Doctor ID must be numeric.");
            return;
        }

        ObservableList<PatientRecordModelClass> filteredList =
                FXCollections.observableArrayList();

        if (!patientFile.exists()) {

            messageLabel.setText("No appointments found.");
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                if ("Online".equalsIgnoreCase(patient.getAppointmentType())
                        && patient.getAssignedDoctorId() == doctorId) {

                    filteredList.add(patient);
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to search appointments.");
            return;
        }

        appointmentTableView.setItems(filteredList);

        if (filteredList.isEmpty()) {

            messageLabel.setText("No online appointments found.");

        } else {

            messageLabel.setText(
                    filteredList.size() + " appointment(s) found.");
        }
    }

    @FXML
    public void refreshButton(ActionEvent actionEvent) {

        doctorIdTextField.clear();

        appointmentTableView.getItems().clear();

        loadAppointments();

        messageLabel.setText("All online appointments loaded.");
    }
    @FXML
    public void confirmAppointmentButton(ActionEvent actionEvent) {

        PatientRecordModelClass selectedPatient =
                appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedPatient == null) {

            messageLabel.setText("Please select a patient.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

        confirmAlert.setTitle("Confirm Appointment");
        confirmAlert.setHeaderText("Online Appointment");
        confirmAlert.setContentText(
                "Do you want to confirm the appointment for\n\n"
                        + "Patient ID : " + selectedPatient.getPatientId()
                        + "\nPatient Name : " + selectedPatient.getPatientName()
                        + "\nDoctor : " + selectedPatient.getAssignedDoctor()
        );

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);

            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText(
                    "Appointment confirmed successfully.");

            successAlert.showAndWait();

            messageLabel.setText(
                    "Appointment confirmed for Patient ID "
                            + selectedPatient.getPatientId());

        } else {

            messageLabel.setText("Confirmation cancelled.");
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