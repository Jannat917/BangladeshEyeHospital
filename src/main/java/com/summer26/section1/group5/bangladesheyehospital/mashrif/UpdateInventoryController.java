package com.summer26.section1.group5.bangladesheyehospital.mashrif;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class UpdateInventoryController {

    @FXML
    private TextField medicineCodeTextField;

    @FXML
    private TextField medicineNameTextField;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TextField batchNumberTextField;

    @FXML
    private TextField stockQuantityTextField;

    @FXML
    private TextField reorderLevelTextField;

    @FXML
    private TextField unitPriceTextField;

    @FXML
    private DatePicker expiryDatePicker;

    @FXML
    private TextField supplierNameTextField;

    @FXML
    private TableView<Medicine> medicineTableView;

    @FXML
    private TableColumn<Medicine, String> medicineCodeColumn;

    @FXML
    private TableColumn<Medicine, String> medicineNameColumn;

    @FXML
    private TableColumn<Medicine, String> categoryColumn;

    @FXML
    private TableColumn<Medicine, String> batchNumberColumn;

    @FXML
    private TableColumn<Medicine, Integer> stockQuantityColumn;

    @FXML
    private TableColumn<Medicine, Integer> reorderLevelColumn;

    @FXML
    private TableColumn<Medicine, Double> unitPriceColumn;

    @FXML
    private TableColumn<Medicine, LocalDate> expiryDateColumn;

    @FXML
    private TableColumn<Medicine, String> supplierNameColumn;

    @FXML
    private Label statusLabel;

    private final ObservableList<Medicine> medicineList =
            MedicineData.getMedicineList();

    @FXML
    public void initialize() {
        medicineCodeColumn.setCellValueFactory(
                new PropertyValueFactory<>("medicineCode")
        );

        medicineNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("medicineName")
        );

        categoryColumn.setCellValueFactory(
                new PropertyValueFactory<>("category")
        );

        batchNumberColumn.setCellValueFactory(
                new PropertyValueFactory<>("batchNumber")
        );

        stockQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("stockQuantity")
        );

        reorderLevelColumn.setCellValueFactory(
                new PropertyValueFactory<>("reorderLevel")
        );

        unitPriceColumn.setCellValueFactory(
                new PropertyValueFactory<>("unitPrice")
        );

        expiryDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("expiryDate")
        );

        supplierNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("supplierName")
        );

        medicineTableView.setItems(medicineList);

        medicineTableView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, selectedMedicine) -> {
                    if (selectedMedicine != null) {
                        loadSelectedMedicine(selectedMedicine);
                    }
                });
    }

    @FXML
    private void addMedicineButton(ActionEvent event) {
        try {
            String code = medicineCodeTextField.getText().trim();
            String name = medicineNameTextField.getText().trim();
            String category = categoryTextField.getText().trim();
            String batch = batchNumberTextField.getText().trim();
            String supplier = supplierNameTextField.getText().trim();

            int stock = Integer.parseInt(
                    stockQuantityTextField.getText().trim()
            );

            int reorder = Integer.parseInt(
                    reorderLevelTextField.getText().trim()
            );

            double price = Double.parseDouble(
                    unitPriceTextField.getText().trim()
            );

            LocalDate expiryDate = expiryDatePicker.getValue();

            if (code.isEmpty()
                    || name.isEmpty()
                    || category.isEmpty()
                    || batch.isEmpty()
                    || supplier.isEmpty()
                    || expiryDate == null) {

                statusLabel.setText("Complete all fields.");
                return;
            }

            for (Medicine medicine : medicineList) {
                if (medicine.getMedicineCode()
                        .equalsIgnoreCase(code)) {

                    statusLabel.setText(
                            "Medicine code already exists."
                    );
                    return;
                }
            }

            Medicine medicine = new Medicine(
                    code,
                    name,
                    category,
                    batch,
                    stock,
                    reorder,
                    price,
                    expiryDate,
                    supplier
            );

            medicineList.add(medicine);

            clearForm();

            statusLabel.setText(
                    "Medicine added successfully."
            );

        } catch (NumberFormatException exception) {
            statusLabel.setText(
                    "Stock, reorder level, and price must be numbers."
            );
        } catch (IllegalArgumentException exception) {
            statusLabel.setText(exception.getMessage());
        }
    }

    @FXML
    private void updateSelectedButton(ActionEvent event) {
        Medicine selectedMedicine =
                medicineTableView.getSelectionModel().getSelectedItem();

        if (selectedMedicine == null) {
            statusLabel.setText(
                    "Select a medicine from the table."
            );
            return;
        }

        try {
            selectedMedicine.setMedicineCode(
                    medicineCodeTextField.getText().trim()
            );

            selectedMedicine.setMedicineName(
                    medicineNameTextField.getText().trim()
            );

            selectedMedicine.setCategory(
                    categoryTextField.getText().trim()
            );

            selectedMedicine.setBatchNumber(
                    batchNumberTextField.getText().trim()
            );

            selectedMedicine.setStockQuantity(
                    Integer.parseInt(
                            stockQuantityTextField.getText().trim()
                    )
            );

            selectedMedicine.setReorderLevel(
                    Integer.parseInt(
                            reorderLevelTextField.getText().trim()
                    )
            );

            selectedMedicine.setUnitPrice(
                    Double.parseDouble(
                            unitPriceTextField.getText().trim()
                    )
            );

            selectedMedicine.setExpiryDate(
                    expiryDatePicker.getValue()
            );

            selectedMedicine.setSupplierName(
                    supplierNameTextField.getText().trim()
            );

            medicineTableView.refresh();

            clearForm();

            statusLabel.setText(
                    "Medicine updated successfully."
            );

        } catch (NumberFormatException exception) {
            statusLabel.setText(
                    "Stock, reorder level, and price must be numbers."
            );
        } catch (IllegalArgumentException exception) {
            statusLabel.setText(exception.getMessage());
        }
    }

    @FXML
    private void clearButton(ActionEvent event) {
        clearForm();
        medicineTableView.getSelectionModel().clearSelection();
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
                    "Could not return to the dashboard."
            );
            exception.printStackTrace();
        }
    }

    private void loadSelectedMedicine(Medicine medicine) {
        medicineCodeTextField.setText(
                medicine.getMedicineCode()
        );

        medicineNameTextField.setText(
                medicine.getMedicineName()
        );

        categoryTextField.setText(
                medicine.getCategory()
        );

        batchNumberTextField.setText(
                medicine.getBatchNumber()
        );

        stockQuantityTextField.setText(
                String.valueOf(medicine.getStockQuantity())
        );

        reorderLevelTextField.setText(
                String.valueOf(medicine.getReorderLevel())
        );

        unitPriceTextField.setText(
                String.valueOf(medicine.getUnitPrice())
        );

        expiryDatePicker.setValue(
                medicine.getExpiryDate()
        );

        supplierNameTextField.setText(
                medicine.getSupplierName()
        );
    }

    private void clearForm() {
        medicineCodeTextField.clear();
        medicineNameTextField.clear();
        categoryTextField.clear();
        batchNumberTextField.clear();
        stockQuantityTextField.clear();
        reorderLevelTextField.clear();
        unitPriceTextField.clear();
        expiryDatePicker.setValue(null);
        supplierNameTextField.clear();
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