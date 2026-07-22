package com.summer26.section1.group5.bangladesheyehospital.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("Doctor", "Receptionist");
        roleComboBox.setValue("Doctor");
        messageLabel.setText("");
    }

    @FXML
    public void registerButton(ActionEvent actionEvent) {

        if (userIdTextField.getText().isEmpty()||nameTextField.getText().isEmpty()||passwordField.getText().isEmpty()) {
            messageLabel.setText("Please fill all fields.");
            return;
        }
        int userId;

        try {
            userId = Integer.parseInt(userIdTextField.getText());
        }
        catch (NumberFormatException e) {
            messageLabel.setText("User ID must be a number.");
            return;
        }

        ArrayList<UserModelClass> userList = new ArrayList<>();

        File file =new File("/Users/jannati/Desktop/BangladeshEyeHospital/users.bin");

        if (file.exists()) {
            try {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                while (true) {
                    UserModelClass user = (UserModelClass) ois.readObject();
                    userList.add(user);
                }

            }
            catch (EOFException e) {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (UserModelClass u : userList) {

            if (u.getUserId() == userId) {

                messageLabel.setText("User ID already exists.");
                return;
            }
        }


        UserModelClass newUser = new UserModelClass(
                userId, nameTextField.getText(), "", passwordField.getText(), roleComboBox.getValue());
        userList.add(newUser);

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(file));

            for (UserModelClass u : userList) {
                oos.writeObject(u);
            }

            oos.close();

            messageLabel.setText("Registration Successful!");
            userIdTextField.clear();
            nameTextField.clear();
            passwordField.clear();
            roleComboBox.setValue("Doctor");

        } catch (IOException e) {

            messageLabel.setText("Error saving user.");
            e.printStackTrace();
        }
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("common/login.fxml");
    }
}