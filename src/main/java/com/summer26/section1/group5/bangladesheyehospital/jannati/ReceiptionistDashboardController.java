package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.IOException;

public class ReceiptionistDashboardController {
    @javafx.fxml.FXML
    private Label messageLabel;

    @javafx.fxml.FXML
    public void updatePatientInformationButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void generateTestBillButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void managePaymentButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void appointmentConfirmationButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void checkDoctorButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void registerPatientButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("jannati/register-patient.fxml");
    }

    @javafx.fxml.FXML
    public void logoutButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");
    }

    @javafx.fxml.FXML
    public void bookAppointmentButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void patientVisitReportButton(ActionEvent actionEvent) {
    }
}
