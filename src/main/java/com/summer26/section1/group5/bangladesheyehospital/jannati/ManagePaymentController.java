package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;

public class ManagePaymentController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private TextField paymentAmountTextField;

    @FXML
    private TableView<PatientRecordModelClass> paymentTableView;

    @FXML
    private TableColumn<PatientRecordModelClass, Integer> patientIdColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> patientNameColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, Double> billAmountColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> paymentStatusColumn;

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

        patientNameTextField.setEditable(false);

        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));

        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        billAmountColumn.setCellValueFactory(new PropertyValueFactory<>("billAmount"));

        paymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        loadPatients();
    }

    private void loadPatients() {

        patientList.clear();
        paymentTableView.getItems().clear();

        if (!patientFile.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();

                patientList.add(patient);
            }

        } catch (EOFException e) {

//

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to load patient records.");
        }

        paymentTableView.getItems().addAll(patientList);
    }


    @FXML
    public void searchButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().trim().isEmpty()) {

            messageLabel.setText("Enter Patient ID.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(patientIdTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Patient ID must be numeric.");
            return;
        }

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                patientNameTextField.setText(patient.getPatientName());

                found = true;
                break;
            }
        }

        if (found) {

            messageLabel.setText("Patient found.");

        } else {

            patientNameTextField.clear();
            paymentAmountTextField.clear();

            messageLabel.setText("Patient not found.");
        }
    }


    @FXML
    public void donePaymentButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().trim().isEmpty() || patientNameTextField.getText().trim().isEmpty() || paymentAmountTextField.getText().trim().isEmpty()) {
            messageLabel.setText("Please complete all fields.");
            return;
        }

        int patientId;
        double paymentAmount;

        try {

            patientId = Integer.parseInt(patientIdTextField.getText().trim());
            paymentAmount = Double.parseDouble(paymentAmountTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Invalid Patient ID or Payment Amount.");
            return;
        }

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                patient.setBillAmount(paymentAmount);
                patient.setPaymentStatus("Paid");

                found = true;
                break;
            }
        }

        if (!found) {

            messageLabel.setText("Patient not found.");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass patient : patientList) {
                oos.writeObject(patient);
            }

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to save payment.");
            return;
        }

        loadPatients();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Payment Successful");
        alert.setHeaderText(null);
        alert.setContentText("Payment completed successfully!");

        alert.showAndWait();

        messageLabel.setText("");
    }



    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        patientNameTextField.clear();
        paymentAmountTextField.clear();
        paymentTableView.getSelectionModel().clearSelection();

        messageLabel.setText("");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
            SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");

    }
}