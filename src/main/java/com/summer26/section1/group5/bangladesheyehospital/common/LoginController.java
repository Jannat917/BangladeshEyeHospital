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

    // Shared data folder
    private final File dataFolder = new File("data");
    private final File userFile = new File(dataFolder, "users.bin");

    @FXML
    public void initialize() {

        roleComboBox.getItems().addAll(
                "Doctor",
                "Receptionist",
                "Patient",
                "Accountant",
                "SecurityStaff",
                "Nurse"
        );

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

        if (userIdTextField.getText().isEmpty()
                || passwordTextField.getText().isEmpty()) {

            messageLabel.setText("Please enter User ID and Password.");
            return;
        }

        int userId;

        try {

            userId = Integer.parseInt(userIdTextField.getText());

        } catch (NumberFormatException e) {

            messageLabel.setText("User ID must be numeric.");
            return;
        }

        if (!userFile.exists()) {

            messageLabel.setText("No users registered yet.");
            return;
        }

        boolean found = false;

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(userFile))) {

            while (true) {

                UserModelClass user = (UserModelClass) ois.readObject();

                if (user.getUserId() == userId
                        && user.getPassword().equals(passwordTextField.getText())
                        && user.getRole().equals(roleComboBox.getValue())) {

                    found = true;

                    switch (user.getRole()) {

                        case "Doctor":
                            SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");
                            break;

                        case "Receptionist":
                            SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");
                            break;

                        case "Patient":
                            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");
                            break;

                        case "Accountant":
                            SceneSwitcher.switchTo("nisa/AccountantDashboard.fxml");
                            break;

                        case "SecurityStaff":
                            SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
                            break;

                        case "Nurse":
                            SceneSwitcher.switchTo("mdhossain/nurseDashboard.fxml");
                            break;
                    }

                    break;
                }
            }

        } catch (EOFException e) {

            // End of file reached

        } catch (IOException e) {

            messageLabel.setText("Unable to read user data.");
            e.printStackTrace();

        } catch (ClassNotFoundException e) {

            messageLabel.setText("User data is corrupted.");
            e.printStackTrace();
        }

        if (!found) {

            messageLabel.setText("Invalid User ID, Password or Role.");
        }
    }

    @FXML
    public void signUpButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("common/sign-up.fxml");
    }
}