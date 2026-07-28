package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UpdateProfilecontroller {
    @javafx.fxml.FXML
    private TextField phoneTF;
    @javafx.fxml.FXML
    private TextField emailTF;
    @javafx.fxml.FXML
    private TextField addressTF;
    @javafx.fxml.FXML
    private TextField patientnameTF;
    @javafx.fxml.FXML
    private TextField patientidTF;

    ObservableList<UpdateProfile> list = FXCollections.observableArrayList();
    private UpdateProfile patient;

    @javafx.fxml.FXML
    public void initialize() {

        list.add(new UpdateProfile("Dhaka", "Nisa@gmail.com", 101, "Nisa", "01758890000"));
        list.add(new UpdateProfile("Barishal", "Lina@gmail.com", 102, "Lina", "01758990800"));
    }

    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {
        }
    }

    @javafx.fxml.FXML
    public void updatebutton(ActionEvent actionEvent) {
        if (patient == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Search Patient First");
            a.showAndWait();
            return;
        }

        patient.setPatientName(patientnameTF.getText());
        patient.setPhone(phoneTF.getText());
        patient.setEmail(emailTF.getText());
        patient.setAddress(addressTF.getText());

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText("Profile Update Successfully");
        a.showAndWait();


    }

    @javafx.fxml.FXML
    public void searchbutton(ActionEvent actionEvent) {
        int patientId = Integer.parseInt(patientidTF.getText());

        for (UpdateProfile p : list) {
            if (p.getPatientId() == patientId) {
                patient = p;
                patientnameTF.setText(p.getPatientName());
                phoneTF.setText(p.getPhone());
                emailTF.setText(p.getEmail());
                addressTF.setText(p.getAddress());
                return;


            }
        }

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText("Patient Not found");
        a.showAndWait();
    }

    @javafx.fxml.FXML
    public void clearbutton(ActionEvent actionEvent) {

        patientidTF.clear();
        patientnameTF.clear();
        emailTF.clear();
        phoneTF.clear();
        addressTF.clear();

        patient = null;
    }
}