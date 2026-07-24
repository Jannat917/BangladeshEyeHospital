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

    private final File userFile =
            new File("/Users/jannati/Desktop/BangladeshEyeHospital/data/users.bin");

    private final File doctorFile =
            new File("/Users/jannati/Desktop/BangladeshEyeHospital/data/doctors.bin");

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
    public void registerButton(ActionEvent actionEvent) {

        if (userIdTextField.getText().isEmpty()
                || nameTextField.getText().isEmpty()
                || passwordField.getText().isEmpty()) {

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
        if (userFile.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile))) {
                while (true) {
                    UserModelClass user = (UserModelClass) ois.readObject();
                    userList.add(user);
                }
            } catch (EOFException e) {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        for (UserModelClass user : userList) {
            if (user.getUserId() == userId) {
                messageLabel.setText("User ID already exists.");
                return;
            }
        }

        UserModelClass newUser = new UserModelClass(userId, nameTextField.getText(), "", passwordField.getText(), roleComboBox.getValue());
        userList.add(newUser);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile))) {
            for (UserModelClass user : userList) {

                oos.writeObject(user);
            }

        } catch (IOException e) {
            messageLabel.setText("Error saving user.");
            e.printStackTrace();
            return;
        }


        if (roleComboBox.getValue().equals("Doctor")) {
            ArrayList<DoctorModelClass> doctorList = new ArrayList<>();

            if (doctorFile.exists()) {

                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(doctorFile))) {
                    while (true) {

                        DoctorModelClass doctor = (DoctorModelClass) ois.readObject();
                        doctorList.add(doctor);
                    }

                } catch (EOFException e) {

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            for (DoctorModelClass doctor : doctorList) {

                if (doctor.getDoctorId() == userId) {
                    messageLabel.setText("Doctor ID already exists.");
                    return;
                }
            }

            DoctorModelClass newDoctor = new DoctorModelClass(userId, nameTextField.getText(), passwordField.getText(), "", "", "", "", "");
            doctorList.add(newDoctor);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(doctorFile))) {

                for (DoctorModelClass doctor : doctorList) {

                    oos.writeObject(doctor);
                }

            } catch (IOException e) {

                messageLabel.setText("Error saving doctor.");
                e.printStackTrace();
                return;
            }
        }

        messageLabel.setText("Registration Successful!");
        userIdTextField.clear();
        nameTextField.clear();
        passwordField.clear();
        roleComboBox.setValue("Doctor");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");
    }
}