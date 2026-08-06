package com.summer26.section1.group5.bangladesheyehospital.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoginController {

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField userIdTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label messageLabel;

    // Data folder
    private final File dataFolder = new File("data");

    // Files
    private final File userFile = new File(dataFolder, "users.bin");
    private final File doctorFile = new File(dataFolder, "doctors.bin");
    private final File patientFile = new File(dataFolder, "patients.bin");

    @FXML
    public void initialize() {

        roleComboBox.getItems().addAll("Doctor", "Receptionist", "Patient", "Accountant", "SecurityStaff","Nurse", "Pharmacist", "Optician");

        roleComboBox.setValue("Doctor");
        messageLabel.setText("");
    }

    @FXML
    public void clearButton(ActionEvent actionEvent) {

        roleComboBox.setValue("Doctor");
        userIdTextField.clear();
        passwordTextField.clear();
        messageLabel.setText("");
    }


    @FXML
    public void loginButton(ActionEvent actionEvent) {

        if (userIdTextField.getText().isBlank() || passwordTextField.getText().isBlank()) {
            messageLabel.setText("Please enter User ID and Password.");
            return;
        }

        int userId;

        try {
            userId = Integer.parseInt(userIdTextField.getText().trim());
        } catch (NumberFormatException e) {
            messageLabel.setText("User ID must be numeric.");
            return;
        }

        String password = passwordTextField.getText().trim();
        String role = roleComboBox.getValue();

        if (role == null) {
            messageLabel.setText("Please select a role.");
            return;
        }

        // Doctor Login

        if (role.equals("Doctor")) {

            if (!doctorFile.exists()) {
                messageLabel.setText("Doctor file not found.");
                return;
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(doctorFile))) {

                while (true) {

                    DoctorModelClass doctor = (DoctorModelClass) ois.readObject();

                    if (doctor.getDoctorId() == userId &&
                            doctor.getPassword().equals(password)) {
                              UserSession.setLoggedInUser(
                                doctor.getDoctorId(),
                                doctor.getDoctorName(),
                                "Doctor");

                        SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");
                        return;
                    }
                }

            } catch (EOFException e) {

                messageLabel.setText("Invalid Doctor ID or Password.");

            } catch (Exception e) {

                e.printStackTrace();
                messageLabel.setText(e.getClass().getSimpleName());
            }

            return;
        }

        // Patient Login

        if (role.equals("Patient")) {

            if (!patientFile.exists()) {
                messageLabel.setText("Patient file not found.");
                return;
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {

                while (true) {

                    PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();

                    if (patient.getPatientId() == userId &&
                            patient.getPassword().equals(password)) {

                        UserSession.setLoggedInUser(
                                patient.getPatientId(),
                                patient.getPatientName(),
                                "Patient"
                        );

                        SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");
                        return;
                    }
                }

            } catch (EOFException e) {

                messageLabel.setText("Invalid Patient ID or Password.");

            } catch (Exception e) {

                e.printStackTrace();
                messageLabel.setText(e.getClass().getSimpleName());
            }

            return;
        }


        // Other Users

        if (!userFile.exists()) {

            messageLabel.setText("User file not found.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile))) {

            while (true) {

                UserModelClass user = (UserModelClass) ois.readObject();

                if (user.getUserId() == userId && user.getPassword().equals(password) && user.getRole().equalsIgnoreCase(role)) {

                    UserSession.setLoggedInUser(user.getUserId(), user.getName(), user.getRole()
                    );

                    switch (role) {

                        case "Receptionist":
                            SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");
                            return;

                        case "Accountant":
                            SceneSwitcher.switchTo("nisa/AccountantDashboard.fxml");
                            return;

                        case "SecurityStaff":
                            SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
                            return;

                        case "Nurse":
                            SceneSwitcher.switchTo("mdhossain/nurseDashboard.fxml");
                            return;
                        case "Pharmacist":
                            SceneSwitcher.switchTo("mashrif/PharmacistDashboard.fxml");
                            return;

                        case "Optician":
                            SceneSwitcher.switchTo("mashrif/OpticianDashboard.fxml");
                            return;
                    }
                }
            }

        } catch (EOFException e) {

            messageLabel.setText("Invalid User ID, Password or Role.");

        } catch (Exception e) {

            e.printStackTrace();
            messageLabel.setText(e.getClass().getSimpleName());
        }
    }
    @FXML
    public void signUpButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("common/sign-up.fxml");

    }

}