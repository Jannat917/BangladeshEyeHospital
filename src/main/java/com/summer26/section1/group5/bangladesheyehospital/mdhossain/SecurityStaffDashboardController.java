package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class SecurityStaffDashboardController {

    @FXML
    public void verifyAppointmentButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/verifyAppointment.fxml");
    }

    @FXML
    public void registerVisitorButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/registerVisitor.fxml");
    }

    @FXML
    public void issueEntryPassButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/issueEntryPass.fxml");
    }

    @FXML
    public void logAmbulanceButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/logAmbulance.fxml");
    }

    @FXML
    public void checkWheelchairButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/checkWheelchair.fxml");
    }

    @FXML
    public void shiftHandoverButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/shiftHandover.fxml");
    }

    @FXML
    public void reportIncidentButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/reportIncident.fxml");
    }

    @FXML
    public void recordExitButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/recordExit.fxml");
    }

    @FXML
    public void logoutButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");
    }
}