package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class OpticianDashboard {

    @FXML
    private Label pageTitleLabel;

    @FXML
    private Label pageDescriptionLabel;

    @FXML
    private Label pendingOrderCountLabel;

    @FXML
    private Label readyDeliveryCountLabel;

    @FXML
    private Label todaySalesLabel;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        pageTitleLabel.setText("Dashboard Overview");

        pageDescriptionLabel.setText(
                "Welcome to the Optician Dashboard."
        );

        pendingOrderCountLabel.setText("0");
        readyDeliveryCountLabel.setText("0");
        todaySalesLabel.setText("৳0.00");

        statusLabel.setText(
                "Select a menu option to continue."
        );
    }

    @FXML
    private void dashboardButton(ActionEvent event) {
        showFeature(
                "Dashboard Overview",
                "Welcome to the Optician Dashboard."
        );
    }

    @FXML
    private void viewPrescriptionButton(ActionEvent event) {
        showFeature(
                "View Eyeglass Prescription",
                "Search and view a patient's eyeglass prescription."
        );
    }

    @FXML
    private void recommendFrameButton(ActionEvent event) {
        showFeature(
                "Recommend Frame",
                "Recommend a suitable frame for the patient."
        );
    }

    @FXML
    private void createOrderButton(ActionEvent event) {
        showFeature(
                "Create Eyeglass Order",
                "Create a new eyeglass order using a selected lens and frame."
        );
    }

    @FXML
    private void trackProductionButton(ActionEvent event) {
        showFeature(
                "Track Production",
                "View and update the production status of eyeglass orders."
        );
    }

    @FXML
    private void deliverEyeglassButton(ActionEvent event) {
        showFeature(
                "Deliver Eyeglass",
                "Confirm delivery of a completed eyeglass order."
        );
    }

    @FXML
    private void inventoryButton(ActionEvent event) {
        showFeature(
                "Lens and Frame Inventory",
                "Manage available lenses and frames."
        );
    }

    @FXML
    private void frameSaleButton(ActionEvent event) {
        showFeature(
                "Process Frame Sale",
                "Calculate and save a frame sale."
        );
    }

    @FXML
    private void opticalReportButton(ActionEvent event) {
        showFeature(
                "Optical Report",
                "Generate optical sales, order, and inventory reports."
        );
    }

    @FXML
    private void logoutButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Log Out");
        alert.setHeaderText("Do you want to log out?");
        alert.setContentText("You will return to the login page.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                openScene(
                        event,
                        "/com/summer26/section1/group5/"
                                + "bangladesheyehospital/mashrif/login.fxml"
                );
            } catch (IOException exception) {
                showError("Could not open the login page.");
                exception.printStackTrace();
            }
        }
    }

    private void showFeature(String title, String description) {
        pageTitleLabel.setText(title);
        pageDescriptionLabel.setText(description);

        statusLabel.setText(
                title + " scene will be connected later."
        );
    }

    private void openScene(ActionEvent event, String fxmlPath)
            throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlPath)
        );

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}