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

public class PharmacistDashboard {

    @FXML
    private Label pageTitleLabel;

    @FXML
    private Label pageDescriptionLabel;

    @FXML
    private Label pendingPrescriptionCountLabel;

    @FXML
    private Label lowStockCountLabel;

    @FXML
    private Label todaySalesLabel;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        pageTitleLabel.setText("Dashboard Overview");
        pageDescriptionLabel.setText(
                "Welcome to the Pharmacist Dashboard."
        );

        pendingPrescriptionCountLabel.setText("0");
        lowStockCountLabel.setText("0");
        todaySalesLabel.setText("৳0.00");

        statusLabel.setText(
                "Select a menu option to continue."
        );
    }

    @FXML
    private void dashboardButton(ActionEvent event) {
        showFeature(
                "Dashboard Overview",
                "Welcome to the Pharmacist Dashboard."
        );
    }

    @FXML
    private void viewPrescriptionButton(ActionEvent event) {
        try {
            openScene(
                    event,
                    "/com/summer26/section1/group5/"
                            + "bangladesheyehospital/mashrif/"
                            + "ViewPharmacyPrescriptions.fxml"
            );
        } catch (IOException exception) {
            showError("Could not open prescriptions.");
            exception.printStackTrace();
        }
    }

    @FXML
    private void checkMedicineStockButton(ActionEvent event) {
        try {
            openScene(
                    event,
                    "/com/summer26/section1/group5/"
                            + "bangladesheyehospital/mashrif/"
                            + "CheckMedicineStock.fxml"
            );
        } catch (IOException exception) {
            showError("Could not open Check Medicine Stock.");
            exception.printStackTrace();
        }
    }

    @FXML
    private void updateInventoryButton(ActionEvent event) {
        try {
            openScene(
                    event,
                    "/com/summer26/section1/group5/"
                            + "bangladesheyehospital/mashrif/"
                            + "UpdateInventory.fxml"
            );
        } catch (IOException exception) {
            showError("Could not open Update Inventory.");
            exception.printStackTrace();
        }
    }

    @FXML
    private void dispenseMedicineButton(ActionEvent event) {
        try {
            openScene(
                    event,
                    "/com/summer26/section1/group5/"
                            + "bangladesheyehospital/mashrif/"
                            + "DispenseMedicine.fxml"
            );
        } catch (IOException exception) {
            showError("Could not open Dispense Medicine.");
            exception.printStackTrace();
        }
    }

    @FXML
    private void generateMedicineBillButton(ActionEvent event) {
        try {
            openScene(
                    event,
                    "/com/summer26/section1/group5/"
                            + "bangladesheyehospital/mashrif/"
                            + "GenerateMedicineBill.fxml"
            );
        } catch (IOException exception) {
            showError("Could not open Generate Medicine Bill.");
            exception.printStackTrace();
        }
    }

    @FXML
    private void expiredMedicineButton(ActionEvent event) {
        try {
            openScene(
                    event,
                    "/com/summer26/section1/group5/"
                            + "bangladesheyehospital/mashrif/"
                            + "ExpiredMedicine.fxml"
            );
        } catch (IOException exception) {
            showError("Could not open Expired Medicine.");
            exception.printStackTrace();
        }
    }

    @FXML
    private void orderMedicineButton(ActionEvent event) {
        try {
            openScene(
                    event,
                    "/com/summer26/section1/group5/"
                            + "bangladesheyehospital/mashrif/"
                            + "OrderMedicine.fxml"
            );
        } catch (IOException exception) {
            showError("Could not open Order Medicine.");
            exception.printStackTrace();
        }
    }

    @FXML
    private void medicineSalesButton(ActionEvent event) {
        try {
            openScene(
                    event,
                    "/com/summer26/section1/group5/"
                            + "bangladesheyehospital/mashrif/"
                            + "MedicineSales.fxml"
            );
        } catch (IOException exception) {
            showError("Could not open Medicine Sales.");
            exception.printStackTrace();
        }
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
                                + "bangladesheyehospital/common/login.fxml"
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