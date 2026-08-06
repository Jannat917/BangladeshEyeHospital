package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class OpticianDashboard {
    @FXML private Label statusLabel;

    @FXML private void dashboardButton(ActionEvent event) {
        statusLabel.setText("Optician dashboard.");
    }

    @FXML private void viewPrescriptionButton(ActionEvent event) { open(event, "ViewOpticalPrescriptions.fxml"); }
    @FXML private void recommendFrameButton(ActionEvent event) { open(event, "RecommendFrame.fxml"); }
    @FXML private void createOrderButton(ActionEvent event) { open(event, "CreateEyeglassOrder.fxml"); }
    @FXML private void trackProductionButton(ActionEvent event) { open(event, "TrackProduction.fxml"); }
    @FXML private void deliverEyeglassButton(ActionEvent event) { open(event, "DeliverEyeglass.fxml"); }
    @FXML private void inventoryButton(ActionEvent event) { open(event, "OpticalInventory.fxml"); }
    @FXML private void frameSaleButton(ActionEvent event) { open(event, "FrameSale.fxml"); }
    @FXML private void opticalReportButton(ActionEvent event) { open(event, "OpticalReport.fxml"); }

    @FXML
    private void logoutButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/summer26/section1/group5/bangladesheyehospital/common/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException exception) {
            statusLabel.setText("Could not open login page.");
            exception.printStackTrace();
        }
    }

    private void open(ActionEvent event, String fileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/summer26/section1/group5/bangladesheyehospital/mashrif/" + fileName));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException exception) {
            statusLabel.setText("Could not open " + fileName);
            exception.printStackTrace();
        }
    }
}
