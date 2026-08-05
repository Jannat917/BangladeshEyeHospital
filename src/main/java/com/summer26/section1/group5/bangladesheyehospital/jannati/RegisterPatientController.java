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
    private Label messageLabel;

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

        messageLabel.setText("");
    }

    private int generatePatientId() {

        int id = 5001;

        if (!patientFile.exists()) {
            return id;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(patientFile))) {

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

        if (patientNameTextField.getText().trim().isEmpty()
                || ageTextField.getText().trim().isEmpty()
                || phoneNumberTextField.getText().trim().isEmpty()
                || addressTextField.getText().trim().isEmpty()
                || genderComboBox.getValue() == null) {

            messageLabel.setText("Please fill all fields.");
            return;
        }

        int age;

        try {

            age = Integer.parseInt(ageTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Age must be numeric.");
            return;
        }

        if (age <= 0 || age > 120) {

            messageLabel.setText("Enter a valid age.");
            return;
        }

        ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();

        if (patientFile.exists()) {

            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(patientFile))) {

                while (true) {

                    PatientRecordModelClass patient =
                            (PatientRecordModelClass) ois.readObject();

                    patientList.add(patient);
                }

            } catch (EOFException e) {

                // End of file

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        // Optional: Prevent duplicate phone numbers
        for (PatientRecordModelClass p : patientList) {

            if (p.getPhoneNumber().equals(phoneNumberTextField.getText().trim())) {

                messageLabel.setText("Phone number already exists.");
                return;
            }
        }

        int patientId = generatePatientId();

        String password = "P" + patientId;

        PatientRecordModelClass patient = new PatientRecordModelClass(

                patientId,
                password,
                patientNameTextField.getText().trim(),
                age,
                genderComboBox.getValue(),
                phoneNumberTextField.getText().trim(),
                addressTextField.getText().trim(),

                "",     // Appointment Date
                "",     // Appointment Time
                "",     // Department
                "",     // Assigned Doctor
                0,      // Assigned Doctor ID
                0,    //serialNumber

                "",     // Disease
                "",     // Diagnosis
                "",     // Prescription
                "",     // Test Reports
                "",     // Doctor Remarks

                "",     // Eye Power Prescription
                "",     // Lens Type
                "",     // Glasses Recommendation

                0.0,    // Doctor Fee
                0.0,    // Test Fee
                0.0,    // Bill Amount

                "Unpaid",
                ""      // Appointment Type
        );

        patientList.add(patient);

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass p : patientList) {

                oos.writeObject(p);
            }

            clearButton(null);

            messageLabel.setText(
                    "Patient Registered Successfully!\n\n"
                            + "Patient ID : " + patientId
                            + "\nPassword : " + password
            );

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

        // Keep the message label unchanged so the receptionist
        // can still see the generated Patient ID and Password.
    }

    @FXML
    public void backButton(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to open dashboard.");
        }
    }

}
