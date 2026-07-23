package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;


import java.io.IOException;

public class DoctorDashboardController {
    @javafx.fxml.FXML
    public void doctorLogoutButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("common/login.fxml");
    }

    @javafx.fxml.FXML
    public void consultationHistoryButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updatePatientButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void uploadPrescriptionButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void viewAppointmentsButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void viewPatientRecordsButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void surgeryScheduleButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onlineAppointmentButton(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void accessReportsButton(ActionEvent actionEvent) {
    }
}
