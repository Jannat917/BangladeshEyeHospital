package com.summer26.section1.group5.bangladesheyehospital.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.EOFException;
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

    @FXML
    public void initialize() {

        roleComboBox.getItems().addAll("Doctor", "Receptionist","Patient","Accountant");
//        roleComboBox.setValue("Doctor");
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

        boolean found = false;

        try {

            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("/Users/jannati/Desktop/BangladeshEyeHospital/users.bin"));

            while (true) {

                UserModelClass user = (UserModelClass) ois.readObject();

                if (user.getUserId() == userId
                        && user.getPassword().equals(passwordTextField.getText())
                        && user.getRole().equals(roleComboBox.getValue())) {

                    found = true;

                    if (user.getRole().equals("Doctor")) {

                        SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");

                    } else if (user.getRole().equals("Receptionist")) {

                        SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");

                    }
                    else if (user.getRole().equals("Patient")) {

                        SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

                    }
                    else if (user.getRole().equals("Accountant")) {

                        SceneSwitcher.switchTo("nisa/AccountantDashboard.fxml");

                    }

                    break;
                }
            }

            ois.close();

        } catch (EOFException e) {

            // End of file reached

        } catch (IOException e) {

            messageLabel.setText("User file not found.");

        } catch (ClassNotFoundException e) {

            messageLabel.setText("User data is corrupted.");
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