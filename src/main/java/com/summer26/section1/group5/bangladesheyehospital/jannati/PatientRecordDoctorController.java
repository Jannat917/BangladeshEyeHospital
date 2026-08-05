package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class PatientRecordDoctorController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

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
    private TableColumn<PatientRecordModelClass, String> phoneColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> doctorColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> appointmentColumn;

    @FXML
    private Label messageLabel;

    private final ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();

    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientId"));

        patientNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientName"));

        ageColumn.setCellValueFactory(
                new PropertyValueFactory<>("age"));

        genderColumn.setCellValueFactory(
                new PropertyValueFactory<>("gender"));

        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<>("phoneNumber"));

        doctorColumn.setCellValueFactory(
                new PropertyValueFactory<>("assignedDoctor"));

        appointmentColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentDate"));

        loadPatients();
    }

    private void loadPatients() {

        patientList.clear();
        patientTableView.getItems().clear();

        if (!patientFile.exists()) {
            messageLabel.setText("No patient records found.");
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {
                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                patientList.add(patient);
            }

        } catch (EOFException e) {
            // End of file

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            messageLabel.setText("Unable to load patient records.");
        }

        patientTableView.getItems().addAll(patientList);

        if (patientList.isEmpty()) {
            messageLabel.setText("No patient records found.");
        } else {
            messageLabel.setText("");
        }
    }





    @FXML
    public void searchButton(ActionEvent actionEvent) {

        String id = patientIdTextField.getText().trim();
        String name = patientNameTextField.getText().trim().toLowerCase();

        if (id.isEmpty() && name.isEmpty()) {
            messageLabel.setText("Enter Patient ID or Patient Name.");
            return;
        }

        patientTableView.getItems().clear();

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            boolean match = false;

            if (!id.isEmpty()) {

                try {

                    int searchId = Integer.parseInt(id);

                    if (patient.getPatientId() == searchId) {
                        match = true;
                    }

                } catch (NumberFormatException e) {

                    messageLabel.setText("Patient ID must be numeric.");
                    return;
                }
            }

            if (!name.isEmpty()) {

                if (patient.getPatientName().toLowerCase().contains(name)) {
                    match = true;
                }
            }

            if (match) {

                patientTableView.getItems().add(patient);
                found = true;
            }
        }

        if (found) {
            messageLabel.setText("Search completed.");
        } else {
            messageLabel.setText("No matching patient found.");
        }
    }

    @FXML
    public void showAllButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        patientNameTextField.clear();

        loadPatients();

        messageLabel.setText("Showing all patient records.");
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

        alert.setTitle("Patient Full History");
        alert.setHeaderText(patient.getPatientName());

        alert.setContentText(

                "Patient ID : " + patient.getPatientId() +

                        "\nPatient Name : " + patient.getPatientName() +

                        "\nAge : " + patient.getAge() +

                        "\nGender : " + patient.getGender() +

                        "\nPhone : " + patient.getPhoneNumber() +

                        "\nAddress : " + patient.getAddress() +

                        "\n\nAssigned Doctor : " + patient.getAssignedDoctor() +

                        "\nAppointment Date : " + patient.getAppointmentDate() +

                        "\nAppointment Time : " + patient.getAppointmentTime() +

                        "\nDepartment : " + patient.getDepartment() +

                        "\n\nDisease : " + patient.getDisease() +

                        "\nDiagnosis : " + patient.getDiagnosis() +

                        "\nPrescription : " + patient.getPrescription() +

                        "\nTest Reports : " + patient.getTestReports() +

                        "\nDoctor Remarks : " + patient.getDoctorRemarks() +

                        "\n\nEye Power : " + patient.getEyePowerPrescription() +

                        "\nLens Type : " + patient.getLensType() +

                        "\nGlasses Recommendation : " + patient.getGlassesRecommendation() +

                        "\n\nDoctor Fee : " + patient.getDoctorFee() +

                        "\nTest Fee : " + patient.getTestFee() +

                        "\nBill Amount : " + patient.getBillAmount() +

                        "\nPayment Status : " + patient.getPaymentStatus()
        );

        alert.showAndWait();
    }

    @FXML
    public void backButton(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to open dashboard.");
        }
    }
}
