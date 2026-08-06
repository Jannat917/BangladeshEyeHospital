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

    public void consultationHistoryButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("jannati/consultation-history-doctor.fxml");
    }

    @javafx.fxml.FXML
    public void updatePatientButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("jannati/update-patient-progress-doctor.fxml");

    }

    @javafx.fxml.FXML
    public void uploadPrescriptionButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("jannati/upload-prescription.fxml");
    }



    @javafx.fxml.FXML
    public void viewPatientRecordsButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("jannati/patient-record-doctor.fxml");
    }

    @javafx.fxml.FXML
    public void surgeryScheduleButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("jannati/surgery.fxml");
    }

    @javafx.fxml.FXML
    public void onlineAppointmentButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("jannati/online-appointments-doctor.fxml");
    }

    @javafx.fxml.FXML
    public void accessReportsButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("jannati/diagonstic-reports.fxml");
    }

    @javafx.fxml.FXML
    public void OfflineAppointmentsButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("jannati/offline-appointments-doctor.fxml");

    }
}

