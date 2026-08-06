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

    private final File dataFolder = new File("data");

    private final File patientFile =
            new File(dataFolder, "patients.bin");
    @FXML
    private Label successLabel;

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

        successLabel.setText("");
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

            successLabel.setText("Please fill all fields.");
            return;
        }

        int age;

        try {

            age = Integer.parseInt(ageTextField.getText().trim());

        } catch (NumberFormatException e) {

            successLabel.setText("Age must be numeric.");
            return;
        }

        if (age <= 0 || age > 120) {

            successLabel.setText("Enter a valid age.");
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

                successLabel.setText("Phone number already exists.");
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

                "",     // Doctor Remarks

                "",     // Eye Power Prescription
                "",     // Glasses Recommendation


                0.0,    // Bill Amount

                "Unpaid",
                "" ,


                "Pending"// Appointment Type
        );

        patientList.add(patient);

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass p : patientList) {

                oos.writeObject(p);
            }
          clearButton(null);

            successLabel.setText(
                            "Patient registered Successfully\n"
                            + "Patient ID : " + patientId
                            + "\nPassword : " + password
            );



        } catch (IOException e) {

            e.printStackTrace();
            successLabel.setText("Unable to save patient.");
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
            successLabel.setText("Unable to open dashboard.");
        }
    }

}
