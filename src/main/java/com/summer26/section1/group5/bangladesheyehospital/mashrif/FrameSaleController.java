package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class FrameSaleController {
    @FXML private TableView<OpticalSale> saleTableView;
    @FXML private TableColumn<OpticalSale, String> saleIdColumn;
    @FXML private TableColumn<OpticalSale, String> orderIdColumn;
    @FXML private TableColumn<OpticalSale, String> patientNameColumn;
    @FXML private TableColumn<OpticalSale, String> frameNameColumn;
    @FXML private TableColumn<OpticalSale, LocalDate> saleDateColumn;
    @FXML private TableColumn<OpticalSale, Double> amountColumn;
    @FXML private Label totalSalesLabel;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        saleIdColumn.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        frameNameColumn.setCellValueFactory(new PropertyValueFactory<>("frameName"));
        saleDateColumn.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        saleTableView.setItems(MashrifData.getOpticalSales());
        updateTotal();
    }

    @FXML private void refreshButton(ActionEvent event) {
        saleTableView.refresh();
        updateTotal();
        statusLabel.setText("Sales refreshed.");
    }

    private void updateTotal() {
        double total = 0;
        for (OpticalSale sale : MashrifData.getOpticalSales()) total += sale.getAmount();
        totalSalesLabel.setText(String.format("%.2f", total));
    }

    @FXML private void backButton(ActionEvent event) { back(event); }
    private void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                    "/com/summer26/section1/group5/bangladesheyehospital/mashrif/OpticianDashboard.fxml"));
            ((Stage)((Node)event.getSource()).getScene().getWindow()).setScene(new Scene(root));
        } catch (IOException e) {
            statusLabel.setText("Could not return to dashboard.");
        }
    }
}
