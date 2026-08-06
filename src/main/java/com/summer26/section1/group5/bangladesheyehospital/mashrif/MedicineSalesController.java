package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class MedicineSalesController {

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TableView<MedicineSale> salesTableView;

    @FXML
    private TableColumn<MedicineSale, String> saleIdColumn;

    @FXML
    private TableColumn<MedicineSale, LocalDate> saleDateColumn;

    @FXML
    private TableColumn<MedicineSale, String> prescriptionIdColumn;

    @FXML
    private TableColumn<MedicineSale, String> patientNameColumn;

    @FXML
    private TableColumn<MedicineSale, String> medicineNameColumn;

    @FXML
    private TableColumn<MedicineSale, Integer> quantityColumn;

    @FXML
    private TableColumn<MedicineSale, Double> totalAmountColumn;

    @FXML
    private Label totalSalesLabel;

    @FXML
    private Label totalItemsLabel;

    @FXML
    private Label statusLabel;

    private final ObservableList<MedicineSale> salesList =
            MashrifData.getMedicineSales();

    @FXML
    public void initialize() {

        saleIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("saleId")
        );

        saleDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("saleDate")
        );

        prescriptionIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("prescriptionId")
        );

        patientNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientName")
        );

        medicineNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("medicineName")
        );

        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );

        totalAmountColumn.setCellValueFactory(
                new PropertyValueFactory<>("totalAmount")
        );

        salesTableView.setItems(salesList);

        updateSummary(salesList);
    }

    @FXML
    private void filterButton(ActionEvent event) {

        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        if (fromDate == null || toDate == null) {
            statusLabel.setText("Select both dates.");
            return;
        }

        if (fromDate.isAfter(toDate)) {
            statusLabel.setText(
                    "From date cannot be after To date."
            );
            return;
        }

        ObservableList<MedicineSale> filteredSales =
                FXCollections.observableArrayList();

        for (MedicineSale sale : salesList) {

            LocalDate saleDate = sale.getSaleDate();

            if (saleDate != null
                    && !saleDate.isBefore(fromDate)
                    && !saleDate.isAfter(toDate)) {

                filteredSales.add(sale);
            }
        }

        salesTableView.setItems(filteredSales);

        updateSummary(filteredSales);

        statusLabel.setText(
                filteredSales.size() + " sale record(s) found."
        );
    }

    @FXML
    private void showAllButton(ActionEvent event) {

        fromDatePicker.setValue(null);
        toDatePicker.setValue(null);

        salesTableView.setItems(salesList);

        updateSummary(salesList);

        statusLabel.setText(
                salesList.size() + " sale record(s) displayed."
        );
    }

    @FXML
    private void backButton(ActionEvent event) {

        try {
            openScene(
                    event,
                    "/com/summer26/section1/group5/"
                            + "bangladesheyehospital/mashrif/"
                            + "PharmacistDashboard.fxml"
            );

        } catch (IOException exception) {

            statusLabel.setText(
                    "Could not return to Pharmacist Dashboard."
            );

            exception.printStackTrace();
        }
    }

    private void updateSummary(
            ObservableList<MedicineSale> list
    ) {
        double totalSales = 0;
        int totalItems = 0;

        for (MedicineSale sale : list) {
            totalSales += sale.getTotalAmount();
            totalItems += sale.getQuantity();
        }

        totalSalesLabel.setText(
                String.format("%.2f", totalSales)
        );

        totalItemsLabel.setText(
                String.valueOf(totalItems)
        );
    }

    private void openScene(
            ActionEvent event,
            String fxmlPath
    ) throws IOException {

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
}