package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

public class SecurityStaffDashboardController {
    @FXML private Label messageLabel;

    @FXML
    public void verifyAppointmentButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/verifyAppointment.fxml");
    }

    @FXML
    public void registerVisitorButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/registerVisitor.fxml");
    }

    @FXML
    public void issueEntryPassButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/issueEntryPass.fxml");
    }

    @FXML
    public void logAmbulanceButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/logAmbulance.fxml");
    }

    @FXML
    public void checkWheelchairButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/checkWheelchair.fxml");
    }

    @FXML
    public void shiftHandoverButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/shiftHandover.fxml");
    }

    @FXML
    public void reportIncidentButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/reportIncident.fxml");
    }

    @FXML
    public void recordExitButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("/com/summer26/section1/group5/bangladesheyehospital/mdhossain/fxml/recordExit.fxml");
    }

    @FXML
    public void logoutButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");
    }
}