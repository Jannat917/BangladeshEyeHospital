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

public class OpticalReportController {
    @FXML private Label totalPrescriptionsLabel;
    @FXML private Label totalFramesLabel;
    @FXML private Label pendingOrdersLabel;
    @FXML private Label deliveredOrdersLabel;
    @FXML private Label totalSalesLabel;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        refreshReport();
    }

    @FXML private void refreshButton(ActionEvent event) {
        refreshReport();
        statusLabel.setText("Report refreshed.");
    }

    private void refreshReport() {
        int pending = 0;
        int delivered = 0;
        for (EyeglassOrder order : MashrifData.getEyeglassOrders()) {
            if ("Delivered".equalsIgnoreCase(order.getStatus())) delivered++;
            else pending++;
        }

        double totalSales = 0;
        for (OpticalSale sale : MashrifData.getOpticalSales()) totalSales += sale.getAmount();

        totalPrescriptionsLabel.setText(String.valueOf(MashrifData.getOpticalPrescriptions().size()));
        totalFramesLabel.setText(String.valueOf(MashrifData.getOpticalFrames().size()));
        pendingOrdersLabel.setText(String.valueOf(pending));
        deliveredOrdersLabel.setText(String.valueOf(delivered));
        totalSalesLabel.setText(String.format("%.2f", totalSales));
    }

    @FXML private void backButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                    "/com/summer26/section1/group5/bangladesheyehospital/mashrif/OpticianDashboard.fxml"));
            ((Stage)((Node)event.getSource()).getScene().getWindow()).setScene(new Scene(root));
        } catch (IOException e) {
            statusLabel.setText("Could not return to dashboard.");
        }
    }
}
