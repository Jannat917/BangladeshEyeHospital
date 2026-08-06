package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class OrderMedicineController {

    @FXML
    private ComboBox<Medicine> medicineComboBox;

    @FXML
    private TextField currentStockTextField;

    @FXML
    private TextField supplierNameTextField;

    @FXML
    private TextField orderQuantityTextField;

    @FXML
    private TextArea notesTextArea;

    @FXML
    private TableView<MedicineOrder> orderTableView;

    @FXML
    private TableColumn<MedicineOrder, String> orderIdColumn;

    @FXML
    private TableColumn<MedicineOrder, String> medicineCodeColumn;

    @FXML
    private TableColumn<MedicineOrder, String> medicineNameColumn;

    @FXML
    private TableColumn<MedicineOrder, String> supplierNameColumn;

    @FXML
    private TableColumn<MedicineOrder, Integer> orderQuantityColumn;

    @FXML
    private TableColumn<MedicineOrder, LocalDate> orderDateColumn;

    @FXML
    private TableColumn<MedicineOrder, String> statusColumn;

    @FXML
    private Label statusLabel;

    private final ObservableList<Medicine> medicineList =
            MashrifData.getMedicines();

    private final ObservableList<MedicineOrder> orderList =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        orderIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderId")
        );

        medicineCodeColumn.setCellValueFactory(
                new PropertyValueFactory<>("medicineCode")
        );

        medicineNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("medicineName")
        );

        supplierNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("supplierName")
        );

        orderQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderQuantity")
        );

        orderDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("orderDate")
        );

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        medicineComboBox.setItems(medicineList);
        orderTableView.setItems(orderList);

        medicineComboBox.setOnAction(event ->
                loadSelectedMedicine()
        );

        statusLabel.setText("Select a medicine.");
    }

    private void loadSelectedMedicine() {

        Medicine selectedMedicine =
                medicineComboBox.getValue();

        currentStockTextField.clear();
        supplierNameTextField.clear();

        if (selectedMedicine == null) {
            return;
        }

        currentStockTextField.setText(
                String.valueOf(selectedMedicine.getStockQuantity())
        );

        supplierNameTextField.setText(
                selectedMedicine.getSupplierName()
        );

        statusLabel.setText(
                "Enter the quantity to order."
        );
    }

    @FXML
    private void placeOrderButton(ActionEvent event) {

        Medicine selectedMedicine =
                medicineComboBox.getValue();

        if (selectedMedicine == null) {
            statusLabel.setText("Select a medicine.");
            return;
        }

        try {
            int quantity = Integer.parseInt(
                    orderQuantityTextField.getText().trim()
            );

            if (quantity <= 0) {
                statusLabel.setText(
                        "Order quantity must be greater than zero."
                );
                return;
            }

            String orderId =
                    "ORD-" + String.format(
                            "%03d",
                            orderList.size() + 1
                    );

            MedicineOrder order = new MedicineOrder(
                    orderId,
                    selectedMedicine.getMedicineCode(),
                    selectedMedicine.getMedicineName(),
                    selectedMedicine.getSupplierName(),
                    quantity,
                    LocalDate.now(),
                    "Ordered",
                    notesTextArea.getText().trim()
            );

            orderList.add(order);

            clearForm();

            statusLabel.setText(
                    "Medicine order placed successfully."
            );

        } catch (NumberFormatException exception) {
            statusLabel.setText(
                    "Order quantity must be a whole number."
            );
        }
    }

    @FXML
    private void receiveSelectedButton(ActionEvent event) {

        MedicineOrder selectedOrder =
                orderTableView
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedOrder == null) {
            statusLabel.setText(
                    "Select an order from the table."
            );
            return;
        }

        if ("Received".equalsIgnoreCase(
                selectedOrder.getStatus()
        )) {
            statusLabel.setText(
                    "This order has already been received."
            );
            return;
        }

        Medicine medicine =
                findMedicine(selectedOrder.getMedicineCode());

        if (medicine == null) {
            statusLabel.setText(
                    "Medicine was not found in inventory."
            );
            return;
        }

        medicine.addStock(
                selectedOrder.getOrderQuantity()
        );

        selectedOrder.setStatus("Received");

        orderTableView.refresh();

        statusLabel.setText(
                "Order received and inventory updated."
        );
    }

    @FXML
    private void clearButton(ActionEvent event) {
        clearForm();
        statusLabel.setText("Form cleared.");
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

    private Medicine findMedicine(String medicineCode) {

        for (Medicine medicine : medicineList) {
            if (medicine.getMedicineCode()
                    .equalsIgnoreCase(medicineCode)) {

                return medicine;
            }
        }

        return null;
    }

    private void clearForm() {
        medicineComboBox.setValue(null);
        currentStockTextField.clear();
        supplierNameTextField.clear();
        orderQuantityTextField.clear();
        notesTextArea.clear();
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

    public static class MedicineOrder {

        private final String orderId;
        private final String medicineCode;
        private final String medicineName;
        private final String supplierName;
        private final int orderQuantity;
        private final LocalDate orderDate;
        private String status;
        private final String notes;

        public MedicineOrder(
                String orderId,
                String medicineCode,
                String medicineName,
                String supplierName,
                int orderQuantity,
                LocalDate orderDate,
                String status,
                String notes
        ) {
            this.orderId = orderId;
            this.medicineCode = medicineCode;
            this.medicineName = medicineName;
            this.supplierName = supplierName;
            this.orderQuantity = orderQuantity;
            this.orderDate = orderDate;
            this.status = status;
            this.notes = notes;
        }

        public String getOrderId() { return orderId; }
        public String getMedicineCode() { return medicineCode; }
        public String getMedicineName() { return medicineName; }
        public String getSupplierName() { return supplierName; }
        public int getOrderQuantity() { return orderQuantity; }
        public LocalDate getOrderDate() { return orderDate; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getNotes() { return notes; }
    }
}