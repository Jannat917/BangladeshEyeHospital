package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.DoctorModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;

public class RegisterPatientController {

    @FXML
    private TextField patientNameTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private ComboBox<String> doctorComboBox;

    @FXML
    private Label messageLabel;

    private final File patientFile =
            new File("/Users/jannati/Desktop/BangladeshEyeHospital/data/patients.bin");

    private final File doctorFile =
            new File("/Users/jannati/Desktop/BangladeshEyeHospital/data/doctors.bin");

    @FXML
    public void initialize() {

        genderComboBox.getItems().addAll(
                "Male",
                "Female",
                "Other"
        );

        loadDoctors();
    }

    private void loadDoctors() {

        doctorComboBox.getItems().clear();

        if (!doctorFile.exists()) {
            return;
        }

        try {

            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(doctorFile));

            while (true) {

                DoctorModelClass doctor =
                        (DoctorModelClass) ois.readObject();

                doctorComboBox.getItems().add(doctor.getDoctorName());
            }

        } catch (EOFException e) {

            // End of file

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private int generatePatientId() {

        int id = 5000;

        if (!patientFile.exists()) {
            return id;
        }

        try {

            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(patientFile));

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                if (patient.getPatientId() >= id) {
                    id = patient.getPatientId() + 1;
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (Exception e) {

            e.printStackTrace();
        }

        return id;
    }

    @FXML
    public void confirmButton(ActionEvent actionEvent) {

        if (patientNameTextField.getText().isEmpty()
                || ageTextField.getText().isEmpty()
                || phoneNumberTextField.getText().isEmpty()
                || addressTextField.getText().isEmpty()
                || genderComboBox.getValue() == null
                || doctorComboBox.getValue() == null
                || appointmentDatePicker.getValue() == null) {

            messageLabel.setText("Please fill all fields.");
            return;
        }

        int age;

        try {

            age = Integer.parseInt(ageTextField.getText());

        } catch (NumberFormatException e) {

            messageLabel.setText("Age must be numeric.");
            return;
        }

        ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();

        if (patientFile.exists()) {

            try {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile));

                while (true) {

                    PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();

                    patientList.add(patient);
                }

            } catch (EOFException e) {

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        int patientId = generatePatientId();

        PatientRecordModelClass patient = new PatientRecordModelClass(

                        patientId,
                        patientNameTextField.getText(),
                        age,
                        genderComboBox.getValue(),
                        phoneNumberTextField.getText(),
                        addressTextField.getText(),
                        appointmentDatePicker.getValue().toString(),
                        doctorComboBox.getValue(),

                        "", // disease
                        "", // diagnosis
                        "", // prescription
                        "", // reports
                        "", // remarks

                        "", // eye power
                        "", // lens type
                        "", // glass

                        0.0,
                        "Unpaid"
                );

        patientList.add(patient);

        try {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(patientFile));

            for (PatientRecordModelClass p : patientList) {

                oos.writeObject(p);
            }

            oos.close();

            messageLabel.setText(
                    "Patient Registered Successfully.\nPatient ID : " + patientId);

            clearButton(null);

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to save patient.");
        }
    }

    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientNameTextField.clear();
        ageTextField.clear();
        phoneNumberTextField.clear();
        addressTextField.clear();

        genderComboBox.setValue(null);
        doctorComboBox.setValue(null);

        appointmentDatePicker.setValue(null);
    }

    @FXML
    public void backButton(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");

        } catch (IOException e) {

            messageLabel.setText("Unable to open dashboard.");
        }
    }
}