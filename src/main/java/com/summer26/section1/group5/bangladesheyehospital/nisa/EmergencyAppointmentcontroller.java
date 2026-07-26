package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EmergencyAppointmentcontroller
{
    @javafx.fxml.FXML
    private TextArea problemtextarea;
    @javafx.fxml.FXML
    private TextField patientnameTF;
    @javafx.fxml.FXML
    private TextField patientidTF;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent){
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {
        }
    }

    @javafx.fxml.FXML
    public void submitbutton(ActionEvent actionEvent) {
        try {
            int patientId = Integer.parseInt((patientidTF.getText()));
            EmergencyAppointment e = new EmergencyAppointment(patientId, patientnameTF.getText(), problemtextarea.getText());

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("Emergency Appointment Submitted Successfully");
            a.showAndWait();


        } catch (NumberFormatException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setContentText("Patient ID must be a number");
            a.showAndWait();

        }
    }



}