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

public class DeliverEyeglassController {
    @FXML private TableView<EyeglassOrder> orderTableView;
    @FXML private TableColumn<EyeglassOrder, String> orderIdColumn;
    @FXML private TableColumn<EyeglassOrder, String> patientNameColumn;
    @FXML private TableColumn<EyeglassOrder, String> frameNameColumn;
    @FXML private TableColumn<EyeglassOrder, String> statusColumn;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        frameNameColumn.setCellValueFactory(new PropertyValueFactory<>("frameName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderTableView.setItems(MashrifData.getEyeglassOrders());
    }

    @FXML private void deliverSelectedButton(ActionEvent event) {
        EyeglassOrder order = orderTableView.getSelectionModel().getSelectedItem();
        if (order == null) {
            statusLabel.setText("Select an order.");
            return;
        }
        if (!"Ready".equalsIgnoreCase(order.getStatus())) {
            statusLabel.setText("Only ready orders can be delivered.");
            return;
        }

        order.setStatus("Delivered");
        String saleId = "OS-" + String.format("%03d", MashrifData.getOpticalSales().size() + 1);
        MashrifData.getOpticalSales().add(new OpticalSale(
                saleId, order.getOrderId(), order.getPatientName(),
                order.getFrameName(), LocalDate.now(), order.getTotalAmount()));

        orderTableView.refresh();
        statusLabel.setText("Eyeglass delivered and sale recorded.");
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
