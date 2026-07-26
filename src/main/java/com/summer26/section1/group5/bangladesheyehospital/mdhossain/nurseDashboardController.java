package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.fxml.FXML;
import java.io.IOException;

public class nurseDashboardController {

    @FXML
    public void initialEyeScreeningButton() throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/initialEyeScreening.fxml");
    }

    @FXML
    public void manageDoctorQueuesButton() throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/doctorQueues.fxml");
    }

    @FXML
    public void retrieveMedicalHistoryButton() throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/medicalHistory.fxml");
    }

    @FXML
    public void assignDoctorsButton() throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/assignDoctors.fxml");
    }

    @FXML
    public void headNurseButton() throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/headNurse.fxml");
    }

    @FXML
    public void trackMedicationsButton() throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/trackMedications.fxml");
    }

    @FXML
    public void dischargeSummariesButton() throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/dischargeSummaries.fxml");
    }

    @FXML
    public void allocateWardBedButton() throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/allocateWardBed.fxml");
    }

    @FXML
    public void nurseLogoutButton() throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");
    }
}

