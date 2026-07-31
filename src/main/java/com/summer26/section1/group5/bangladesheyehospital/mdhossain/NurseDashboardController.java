package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.Label;
import java.io.IOException;

public class NurseDashboardController {

    @FXML
    public void initialEyeScreeningButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/initialEyeScreening.fxml");
    }

    @FXML
    public void manageDoctorQueuesButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/DoctorQueues.fxml");
    }

    @FXML
    public void retrieveMedicalHistoryButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/PatientMedicalHistory.fxml");
    }

    @FXML
    public void assignDoctorsButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/assignDoctors.fxml");
    }

    @FXML
    public void headNurseButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/head_nurse_view.fxml");
    }


    @FXML
    public void trackMedicationsButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/trackMedications.fxml");
    }

    @FXML
    public void dischargeSummariesButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/dischargeSummaries.fxml");
    }

    @FXML
    public void allocateWardBedButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/allocateWardBed.fxml");
    }

    @FXML
    public void logoutButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");
    }
}