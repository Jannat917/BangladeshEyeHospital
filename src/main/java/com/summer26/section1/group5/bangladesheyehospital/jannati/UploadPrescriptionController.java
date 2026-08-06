package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.ArrayList;

public class UploadPrescriptionController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField phoneTextField;


    @FXML
    private TextField addressTextField;

    @FXML
    private TextArea diagnosisTextArea;

    @FXML
    private TextArea eyePowerTextArea;

    @FXML
    private TextArea recommendationTextArea;

    @FXML
    private Label messageLabel;
    @FXML
    private TextArea medicationTextArea;

    private final ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();

    private final File dataFolder = new File("data");

    private final File patientFile = new File(dataFolder, "patients.bin");
    @FXML
    private TextField diseaseTextField;

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }



        genderComboBox.getItems().addAll("Male", "Female", "Other");

        patientNameTextField.setEditable(false);
        ageTextField.setEditable(false);
        genderComboBox.setDisable(true);
        phoneTextField.setEditable(false);
        addressTextField.setEditable(false);

        messageLabel.setText("");
    }


    private void loadPatients() {

        patientList.clear();

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


        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void searchButton(ActionEvent actionEvent) {

        loadPatients();

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

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                patientNameTextField.setText(patient.getPatientName());
                ageTextField.setText(String.valueOf(patient.getAge()));
                genderComboBox.setValue(patient.getGender());
                phoneTextField.setText(patient.getPhoneNumber());
                addressTextField.setText(patient.getAddress());


                messageLabel.setText("Patient found.");
                return;
            }
        }

        messageLabel.setText("Patient not found.");

        patientNameTextField.clear();
        ageTextField.clear();
        genderComboBox.setValue(null);
        phoneTextField.clear();
        addressTextField.clear();
        diseaseTextField.clear();
        diagnosisTextArea.clear();
        eyePowerTextArea.clear();
        recommendationTextArea.clear();
        medicationTextArea.clear();
    }

    @FXML
    public void saveButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().trim().isEmpty() || diseaseTextField.getText().trim().isEmpty() || diagnosisTextArea.getText().trim().isEmpty() || eyePowerTextArea.getText().trim().isEmpty() || recommendationTextArea.getText().trim().isEmpty() || medicationTextArea.getText().trim().isEmpty()) {
            messageLabel.setText("Please complete all required fields.");
            return;
        }

        loadPatients();

        int patientId;

        try {

            patientId = Integer.parseInt(patientIdTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Invalid Patient ID.");
            return;
        }

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                patient.setDisease(diseaseTextField.getText().trim());

                patient.setDiagnosis(diagnosisTextArea.getText().trim());

                patient.setEyePowerPrescription(eyePowerTextArea.getText().trim());

                patient.setDoctorRemarks(recommendationTextArea.getText().trim());
                patient.setPrescription(medicationTextArea.getText().trim());

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

            messageLabel.setText("Prescription uploaded successfully.");

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to save prescription.");
        }
    }

    @FXML
    public void clearButton(ActionEvent actionEvent) {


        patientIdTextField.clear();

        patientNameTextField.clear();
        ageTextField.clear();
        genderComboBox.setValue(null);
        phoneTextField.clear();
        addressTextField.clear();
        medicationTextArea.clear();

        diseaseTextField.clear();
        diagnosisTextArea.clear();
        eyePowerTextArea.clear();
        recommendationTextArea.clear();

        patientIdTextField.setDisable(false);

        patientNameTextField.setEditable(false);
        ageTextField.setEditable(false);
        genderComboBox.setDisable(true);
        phoneTextField.setEditable(false);
        addressTextField.setEditable(false);

        messageLabel.setText("");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");
    }

}