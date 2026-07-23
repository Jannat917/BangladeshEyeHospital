package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.IOException;

public class PatientDashboardcontroller
{
    @javafx.fxml.FXML
    private Label patientdashboardLabel;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void emergencyappointmentOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void givefeedbackOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void payhospitalbillOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void prescriptionOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void bookappointmentOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateprofileOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void doctorscheduleOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void cancelappointmentOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void LogoutOA(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");
    }
}