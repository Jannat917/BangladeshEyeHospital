package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;

public class AppointmentConfirmationReceptionistController {

    @FXML
    private TableView<PatientRecordModelClass> appointmentTableView;

    @FXML
    private TableColumn<PatientRecordModelClass,Integer> patientIdColumn;

    @FXML
    private TableColumn<PatientRecordModelClass,String> patientNameColumn;

    @FXML
    private TableColumn<PatientRecordModelClass,Integer> doctorIdColumn;

    @FXML
    private TableColumn<PatientRecordModelClass,String> doctorColumn;

    @FXML
    private TableColumn<PatientRecordModelClass,String> dateColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> timeColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> typeColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> statusColumn;

    @FXML
    private Label messageLabel;

    private final File dataFolder = new File("data");

    private final File patientFile = new File(dataFolder, "patients.bin");

    private final ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();


    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("assignedDoctorId"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("assignedDoctor"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStatus"));

        loadAppointments();
    }


    private void loadAppointments() {

        patientList.clear();
        appointmentTableView.getItems().clear();

        if (!patientFile.exists()) {

            messageLabel.setText("No appointments found.");
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();

                if (patient.getAppointmentDate() != null && !patient.getAppointmentDate().isEmpty()) {
                    patientList.add(patient);
                }
            }

        } catch (EOFException e) {

            //

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to load appointments.");
        }

        appointmentTableView.getItems().addAll(patientList);
        messageLabel.setText(patientList.size() + " appointment(s) loaded.");
    }



    @FXML
    public void confirmButton(ActionEvent actionEvent) {

        PatientRecordModelClass selectedPatient = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedPatient == null) {
            messageLabel.setText("Please select an appointment.");
            return;
        }

        selectedPatient.setAppointmentStatus("Confirmed");

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == selectedPatient.getPatientId()) {
                patient.setAppointmentStatus("Confirmed");
                break;
            }
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass patient : patientList) {
                oos.writeObject(patient);
            }

            messageLabel.setText("Appointment confirmed successfully.");
            appointmentTableView.refresh();

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to save patient data.");
        }
    }



    @FXML
    public void refreshButton(ActionEvent actionEvent) {

        loadAppointments();

        messageLabel.setText(patientList.size() + " appointment(s) loaded.");
    }
    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo(
                    "jannati/receiptionistDashboard.fxml");

    }

}
