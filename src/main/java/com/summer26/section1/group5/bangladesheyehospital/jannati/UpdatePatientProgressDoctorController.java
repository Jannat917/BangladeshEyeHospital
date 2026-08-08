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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UpdatePatientProgressDoctorController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private TextArea medicationTextArea;

    @FXML
    private TextArea remarksTextArea;

    @FXML
    private Label messageLabel;

    private final ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();
    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");
    @FXML
    private TextArea diagnosisTextArea;

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        loadPatients();
    }

    private void loadPatients() {

        patientList.clear();

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
        }
    }

    @FXML
    public void searchButton(ActionEvent actionEvent) {

        String id = patientIdTextField.getText().trim();

        if (id.isEmpty()) {

            messageLabel.setText("Enter Patient ID.");
            return;
        }

        int patientId;

        try {
            patientId = Integer.parseInt(id);

        } catch (NumberFormatException e) {
            messageLabel.setText("Patient ID must be numeric.");
            return;
        }

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {
                patientNameTextField.setText(patient.getPatientName());
                diagnosisTextArea.setText(patient.getDiagnosis());
                medicationTextArea.setText(patient.getPrescription());
                remarksTextArea.setText(patient.getDoctorRemarks());

                found = true;
                break;
            }
        }

        if (found) {

            messageLabel.setText("Patient record found.");

        } else {

            messageLabel.setText("Patient not found.");
            patientNameTextField.clear();
            diagnosisTextArea.clear();
            medicationTextArea.clear();
            remarksTextArea.clear();
        }
    }

    @FXML
    public void saveUpdateButton(ActionEvent actionEvent) {

        String id = patientIdTextField.getText().trim();

        if (id.isEmpty()) {

            messageLabel.setText("Search a patient first.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(id);

        } catch (NumberFormatException e) {

            messageLabel.setText("Patient ID must be numeric.");
            return;
        }

        boolean updated = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                patient.setDiagnosis(diagnosisTextArea.getText().trim());
                patient.setPrescription(medicationTextArea.getText().trim());
                patient.setDoctorRemarks(remarksTextArea.getText().trim());
                updated = true;

                break;
            }
        }

        if (!updated) {

            messageLabel.setText("Patient not found.");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass patient : patientList) {

                oos.writeObject(patient);
            }

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to save update.");
            return;
        }

        messageLabel.setText("Patient progress updated successfully.");
    }

    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        patientNameTextField.clear();
        diagnosisTextArea.clear();
        medicationTextArea.clear();
        remarksTextArea.clear();

        messageLabel.setText("");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");

    }
}