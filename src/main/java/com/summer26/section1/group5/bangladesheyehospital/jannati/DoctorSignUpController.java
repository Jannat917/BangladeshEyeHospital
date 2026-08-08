package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.DoctorModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.ArrayList;

public class DoctorSignUpController {

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> departmentCombobox;

    @FXML
    private ComboBox<String> spealizationCombobox;

    @FXML
    private Label messageLabel;

    @FXML
    private CheckBox sat;

    @FXML
    private CheckBox sun;

    @FXML
    private CheckBox mon;

    @FXML
    private CheckBox tues;

    @FXML
    private CheckBox wed;

    @FXML
    private CheckBox thurs;

    @FXML
    private CheckBox fri;

    private final File dataFolder = new File("data");
    private final File doctorFile = new File(dataFolder, "doctors.bin");





    @FXML
    private ComboBox<String> doctorAvailableTimeCombobox;
    @FXML
    private ComboBox<String> availabilityOnCombobox;

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        doctorAvailableTimeCombobox.getItems().addAll("09:00 AM - 11:00 AM", "11:00 AM - 01:00 PM", "02:00 PM - 04:00 PM", "04:00 PM - 06:00 PM", "06:00 PM - 08:00 PM"
        );
        departmentCombobox.getItems().addAll( "Cornea", "Retina", "Glaucoma", "Pediatric Eye", "Optometry");
        availabilityOnCombobox.getItems().addAll("Online","Offline");

        spealizationCombobox.getItems().addAll("Ophthalmologist", "Retina Specialist", "Cornea Specialist", "Glaucoma Specialist", "Pediatric Ophthalmologist", "Optometrist");

        messageLabel.setText("");
    }
    @FXML
    public void registerButton(ActionEvent actionEvent) {

        if (userIdTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || passwordField.getText().isEmpty() || departmentCombobox.getValue() == null || spealizationCombobox.getValue() == null || doctorAvailableTimeCombobox.getValue().isEmpty() || availabilityOnCombobox.getValue()== null) {
            messageLabel.setText("Please fill all fields.");
            return;
        }

        int doctorId;

        try {
            doctorId = Integer.parseInt(userIdTextField.getText());
        }
        catch (NumberFormatException e) {
            messageLabel.setText("Doctor ID must be numeric.");
            return;
        }

        ArrayList<DoctorModelClass> doctorList = new ArrayList<>();

        if (doctorFile.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(doctorFile))) {

                while (true) {

                    DoctorModelClass doctor = (DoctorModelClass) ois.readObject();
                    doctorList.add(doctor);
                }

            }
            catch (EOFException e) {

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


        for (DoctorModelClass doctor : doctorList) {
            if (doctor.getDoctorId() == doctorId) {

                messageLabel.setText("Doctor ID already exists.");
                return;
            }
        }


        String days = "";

        if (sat.isSelected()) days += "Saturday ";
        if (sun.isSelected()) days += "Sunday ";
        if (mon.isSelected()) days += "Monday ";
        if (tues.isSelected()) days += "Tuesday ";
        if (wed.isSelected()) days += "Wednesday ";
        if (thurs.isSelected()) days += "Thursday ";
        if (fri.isSelected()) days += "Friday ";


        DoctorModelClass doctor = new DoctorModelClass(
                doctorId, nameTextField.getText(), passwordField.getText(),
                spealizationCombobox.getValue().toString(),
                "",
                "",
                "", availabilityOnCombobox.getValue(), departmentCombobox.getValue().toString(), days.trim(),
                doctorAvailableTimeCombobox.getValue() );

        doctorList.add(doctor);


        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(doctorFile))) {

            for (DoctorModelClass d : doctorList) {

                oos.writeObject(d);
            }

            messageLabel.setText("Doctor Registered Successfully!");
            userIdTextField.clear();
            nameTextField.clear();
            passwordField.clear();
            doctorAvailableTimeCombobox.setValue(null);
            availabilityOnCombobox.setValue(null);
            departmentCombobox.setValue(null);
            spealizationCombobox.setValue(null);

            sat.setSelected(false);
            sun.setSelected(false);
            mon.setSelected(false);
            tues.setSelected(false);
            wed.setSelected(false);
            thurs.setSelected(false);
            fri.setSelected(false);

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Error saving doctor.");
        }
    }
    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");
    }
}