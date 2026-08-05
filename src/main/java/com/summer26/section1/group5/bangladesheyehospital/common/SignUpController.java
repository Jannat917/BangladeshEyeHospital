package com.summer26.section1.group5.bangladesheyehospital.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.ArrayList;

public class SignUpController {

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    // Data Folder
    private final File dataFolder = new File("data");

    // Save Receptionist, Accountant, Nurse & Security Staff
    private final File userFile = new File(dataFolder, "users.bin");

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        roleComboBox.getItems().addAll(
                "Receptionist",
                "Accountant",
                "SecurityStaff",
                "Nurse",
                "Pharmacist",
                "Optician"
        );

        roleComboBox.setValue("Receptionist");

        messageLabel.setText("");
    }
    @FXML
    public void registerButton(ActionEvent actionEvent) {

        if (userIdTextField.getText().isEmpty()
                || nameTextField.getText().isEmpty()
                || passwordField.getText().isEmpty()
                || roleComboBox.getValue() == null) {

            messageLabel.setText("Please fill all fields.");
            return;
        }

        int userId;

        try {

            userId = Integer.parseInt(userIdTextField.getText());

        } catch (NumberFormatException e) {

            messageLabel.setText("User ID must be numeric.");
            return;
        }

        ArrayList<UserModelClass> userList = new ArrayList<>();

        // Read existing users
        if (userFile.exists()) {

            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(userFile))) {

                while (true) {

                    UserModelClass user =
                            (UserModelClass) ois.readObject();

                    userList.add(user);
                }

            } catch (EOFException e) {

                // End of file

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        // Check duplicate User ID
        for (UserModelClass user : userList) {

            if (user.getUserId() == userId) {

                messageLabel.setText("User ID already exists.");
                return;
            }
        }

        // Create new user
        UserModelClass newUser = new UserModelClass(
                userId,
                nameTextField.getText(),
                "",
                passwordField.getText(),
                roleComboBox.getValue()
        );

        userList.add(newUser);

        // Save all users
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(userFile))) {

            for (UserModelClass user : userList) {

                oos.writeObject(user);
            }

            messageLabel.setText("Registration Successful!");

            userIdTextField.clear();
            nameTextField.clear();
            passwordField.clear();
            roleComboBox.setValue("Receptionist");

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Error saving user.");
        }
    }
    @FXML
    public void backButton (ActionEvent actionEvent) throws IOException {


        SceneSwitcher.switchTo("common/login.fxml");

    }

    @FXML
    public void doctorSignUpPageButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("jannati/doctor-signup.fxml");

    }
}