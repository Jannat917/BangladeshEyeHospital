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

public class TrackProductionController {
    @FXML private TableView<EyeglassOrder> orderTableView;
    @FXML private TableColumn<EyeglassOrder, String> orderIdColumn;
    @FXML private TableColumn<EyeglassOrder, String> patientNameColumn;
    @FXML private TableColumn<EyeglassOrder, String> frameNameColumn;
    @FXML private TableColumn<EyeglassOrder, LocalDate> orderDateColumn;
    @FXML private TableColumn<EyeglassOrder, String> statusColumn;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        frameNameColumn.setCellValueFactory(new PropertyValueFactory<>("frameName"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderTableView.setItems(MashrifData.getEyeglassOrders());
        statusComboBox.getItems().addAll("Ordered", "In Production", "Ready");
    }

    @FXML private void updateStatusButton(ActionEvent event) {
        EyeglassOrder order = orderTableView.getSelectionModel().getSelectedItem();
        String newStatus = statusComboBox.getValue();
        if (order == null || newStatus == null) {
            statusLabel.setText("Select an order and a status.");
            return;
        }
        order.setStatus(newStatus);
        orderTableView.refresh();
        statusLabel.setText("Order status updated.");
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
