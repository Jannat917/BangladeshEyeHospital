package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;

public class UpdatePatientInfoReceptionistController {

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
    private Label messageLabel;

    private final ArrayList<PatientRecordModelClass> patientList =
            new ArrayList<>();

    private final File dataFolder = new File("data");

    private final File patientFile =
            new File(dataFolder, "patients.bin");

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        genderComboBox.getItems().addAll(
                "Male",
                "Female",
                "Other"
        );

        loadPatients();

        patientNameTextField.setEditable(false);
        ageTextField.setEditable(false);
        phoneTextField.setEditable(false);
        addressTextField.setEditable(false);
        genderComboBox.setDisable(true);

        messageLabel.setText("");
    }

    private void loadPatients() {

        patientList.clear();

        if (!patientFile.exists()) {
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                patientList.add(patient);
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        }
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

        loadPatients();

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                patientNameTextField.setText(patient.getPatientName());

                ageTextField.setText(
                        String.valueOf(patient.getAge()));

                genderComboBox.setValue(
                        patient.getGender());

                phoneTextField.setText(
                        patient.getPhoneNumber());

                addressTextField.setText(
                        patient.getAddress());

                // Enable editing
                patientNameTextField.setEditable(true);
                ageTextField.setEditable(true);
                phoneTextField.setEditable(true);
                addressTextField.setEditable(true);
                genderComboBox.setDisable(false);

                messageLabel.setText("Patient found.");

                found = true;
                break;
            }
        }

        if (!found) {

            messageLabel.setText("Patient not found.");

            patientNameTextField.clear();
            ageTextField.clear();
            genderComboBox.setValue(null);
            phoneTextField.clear();
            addressTextField.clear();

            patientNameTextField.setEditable(false);
            ageTextField.setEditable(false);
            phoneTextField.setEditable(false);
            addressTextField.setEditable(false);
            genderComboBox.setDisable(true);
        }
    }

    @FXML
    public void updateButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().trim().isEmpty()
                || patientNameTextField.getText().trim().isEmpty()
                || ageTextField.getText().trim().isEmpty()
                || genderComboBox.getValue() == null
                || phoneTextField.getText().trim().isEmpty()
                || addressTextField.getText().trim().isEmpty()) {

            messageLabel.setText("Please fill all fields.");
            return;
        }

        int patientId;
        int age;

        try {

            patientId = Integer.parseInt(patientIdTextField.getText().trim());
            age = Integer.parseInt(ageTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Patient ID and Age must be numeric.");
            return;
        }

        loadPatients();

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                patient.setPatientName(
                        patientNameTextField.getText().trim());

                patient.setAge(age);

                patient.setGender(
                        genderComboBox.getValue());

                patient.setPhoneNumber(
                        phoneTextField.getText().trim());

                patient.setAddress(
                        addressTextField.getText().trim());

                found = true;
                break;
            }
        }

        if (!found) {

            messageLabel.setText("Patient not found.");
            return;
        }

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass patient : patientList) {

                oos.writeObject(patient);
            }

            messageLabel.setText("Patient information updated successfully.");

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to update patient information.");
        }
    }


    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        patientNameTextField.clear();
ageTextField.clear();
genderComboBox.setValue(null);
phoneTextField.clear();


        messageLabel.setText("");
    }




    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");

    }
}