package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Random;

public class BookAppointmentcontroller {
    @javafx.fxml.FXML
    private ComboBox<String> timeCB;
    @javafx.fxml.FXML
    private ComboBox<String> doctorCB;
    @javafx.fxml.FXML
    private ComboBox<String> departmentCB;
    @javafx.fxml.FXML
    private DatePicker dateDatepicker;
    @javafx.fxml.FXML
    private Label serialLabel;

    @javafx.fxml.FXML
    public void initialize() {
        departmentCB.getItems().addAll("Eye", "Retina", "Cornea");
        doctorCB.getItems().addAll("Dr.Mehedi", "Dr.Rahim", "Dr.Karim");
        timeCB.getItems().addAll("8.00 AM", "9.00 AM", "10.00 AM", "12.00 PM", "3.00 PM");


    }


    @javafx.fxml.FXML
    public void backbuttonOA(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {

        }
    }

    @javafx.fxml.FXML
    public void confirmbuttonOA(ActionEvent actionEvent) {
        if (departmentCB.getValue() == null || doctorCB.getValue() == null || dateDatepicker.getValue() == null || timeCB.getValue() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please Fill All Information");
            a.showAndWait();
            return;
        }

        Random random = new Random();
        int serial = random.nextInt(100) + 1;
        serialLabel.setText("Serial Number : " + serial);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText("Appointment Booked Successfully");
        a.showAndWait();

    }
}
