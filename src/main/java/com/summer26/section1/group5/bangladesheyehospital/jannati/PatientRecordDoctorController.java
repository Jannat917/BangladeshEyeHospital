package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;

public class PatientRecordDoctorController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TableView<PatientRecordModelClass> patientTableView;

    @FXML
    private TableColumn<PatientRecordModelClass, Integer> patientIdColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> patientNameColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, Integer> ageColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> genderColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> doctorColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> appointmentDateColumn;

    @FXML
    private Label messageLabel;

    private final ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();

    private final File patientFile = new File("/Users/jannati/Desktop/BangladeshEyeHospital/data/patients.bin");

    @FXML
    public void initialize() {

        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("assignedDoctor"));
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

        loadPatients();
    }

    private void loadPatients() {

        patientList.clear();
        patientTableView.getItems().clear();

        if (!patientFile.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();
                patientList.add(patient);
            }

        } catch (EOFException e) {


        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        }

        patientTableView.getItems().addAll(patientList);
    }

    @FXML
    public void searchButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().isEmpty()) {
            messageLabel.setText("Please enter Patient ID.");
            return;
        }

        int searchId;

        try {

            searchId = Integer.parseInt(patientIdTextField.getText());

        } catch (NumberFormatException e) {

            messageLabel.setText("Patient ID must be Numbers.");
            return;
        }

        patientTableView.getItems().clear();

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == searchId) {
                patientTableView.getItems().add(patient);
                found = true;
                break;
            }
        }

        if (found) {
            messageLabel.setText("Patient found.");
        } else {
            messageLabel.setText("Patient not found.");
        }
    }

    @FXML
    public void refreshButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        messageLabel.setText("");

        loadPatients();
    }

    @FXML
    public void viewHistoryButton(ActionEvent actionEvent) {

        PatientRecordModelClass patient =
                patientTableView.getSelectionModel().getSelectedItem();

        if (patient == null) {

            messageLabel.setText("Please select a patient.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Patient History");
        alert.setHeaderText(patient.getPatientName());

        alert.setContentText(
                "Patient ID : " + patient.getPatientId() +
                        "\n\nAge : " + patient.getAge() +
                        "\nGender : " + patient.getGender() +
                        "\nPhone : " + patient.getPhoneNumber() +
                        "\nAddress : " + patient.getAddress() +
                        "\nAssigned Doctor : " + patient.getAssignedDoctor() +
                        "\nAppointment Date : " + patient.getAppointmentDate() +
                        "\n\nDisease : " + patient.getDisease() +
                        "\nDiagnosis : " + patient.getDiagnosis() +
                        "\nPrescription : " + patient.getPrescription() +
                        "\nTest Reports : " + patient.getTestReports() +
                        "\nDoctor Remarks : " + patient.getDoctorRemarks() +
                        "\nEye Power : " + patient.getEyePowerPrescription() +
                        "\nLens Type : " + patient.getLensType() +
                        "\nGlasses Recommendation : " + patient.getGlassesRecommendation() +
                        "\nBill Amount : " + patient.getBillAmount() +
                        "\nPayment Status : " + patient.getPaymentStatus()
        );

        alert.showAndWait();
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");
    }
}