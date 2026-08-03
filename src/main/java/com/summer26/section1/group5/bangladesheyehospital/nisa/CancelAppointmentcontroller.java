package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CancelAppointmentcontroller {
    @javafx.fxml.FXML
    private TableView<PatientRecordModelClass> appointmentTable;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass, Integer> patientIdcolumn;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass, String> departmentcolumn;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass, Integer> serialcolumn;
    @javafx.fxml.FXML
    private TextField patientIdTF;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass, LocalDate> datecolumn;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass, String> timecolumn;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass, String > doctornamecolumn;
    @javafx.fxml.FXML
    private Label messagelabel;
    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    private final ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();



    @javafx.fxml.FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientIdcolumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        departmentcolumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        doctornamecolumn.setCellValueFactory(new PropertyValueFactory<>("assignedDoctor"));
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        timecolumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        serialcolumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));

        loadAppointments();
    }

    private void loadAppointments() {

        patientList.clear();
        appointmentTable.getItems().clear();

        if (!patientFile.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                patientList.add(patient);
            }

        } catch (EOFException e) {

        } catch (Exception e) {

            e.printStackTrace();
        }

        appointmentTable.getItems().addAll(patientList);

    }

    @javafx.fxml.FXML
    public void cancelappointmentbutton(ActionEvent actionEvent) {
        if (patientIdTF.getText().isEmpty()) {

            messagelabel.setText("Enter Patient ID.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(patientIdTF.getText());

        } catch (NumberFormatException e) {

            messagelabel.setText("Patient ID must be numeric.");
            return;
        }

        PatientRecordModelClass removePatient = null;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                removePatient = patient;
                break;
            }
        }

        if (removePatient == null) {

            messagelabel.setText("Patient ID not found.");
            return;
        }

        patientList.remove(removePatient);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass patient : patientList) {

                oos.writeObject(patient);
            }

            messagelabel.setText("Appointment Cancelled Successfully.");

            loadAppointments();

            patientIdTF.clear();

        } catch (IOException e) {

            e.printStackTrace();

            messagelabel.setText("Unable to cancel appointment.");
        }

    }

    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {
        }
    }

    @javafx.fxml.FXML
    public void clearbutton(ActionEvent actionEvent) {

        patientIdTF.clear();
        messagelabel.setText("");
    }
}