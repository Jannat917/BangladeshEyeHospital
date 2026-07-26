package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

public class SecurityStaffDashboardController {

    @FXML
    public void verifyAppointmentButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/verifyAppointment.fxml");
    }

    @FXML
    public void registerVisitorButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/registerVisitor.fxml");
    }

    @FXML
    public void issueEntryPassButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/issueEntryPass.fxml");
    }

    @FXML
    public void logAmbulanceButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/logAmbulance.fxml");
    }

    @FXML
    public void checkWheelchairButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/checkWheelchair.fxml");
    }

    @FXML
    public void shiftHandoverButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/shiftHandover.fxml");
    }

    @FXML
    public void reportIncidentButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/reportIncident.fxml");
    }

    @FXML
    public void recordExitButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/recordExit.fxml");
    }

    @FXML
    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");
    }
}