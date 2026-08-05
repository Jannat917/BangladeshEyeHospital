package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class CheckMedicineStockController {

    @FXML
    private TextField searchTextField;

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
    private TableColumn<Medicine, Double> unitPriceColumn;

    @FXML
    private TableColumn<Medicine, LocalDate> expiryDateColumn;

    @FXML
    private TableColumn<Medicine, String> stockStatusColumn;

    @FXML
    private Label statusLabel;

    private final ObservableList<Medicine> medicineList =
            FXCollections.observableArrayList();

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

        unitPriceColumn.setCellValueFactory(
                new PropertyValueFactory<>("unitPrice")
        );

        expiryDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("expiryDate")
        );

        stockStatusColumn.setCellValueFactory(
                new PropertyValueFactory<>("stockStatus")
        );

        addSampleMedicines();

        medicineTableView.setItems(medicineList);

        statusLabel.setText(
                medicineList.size() + " medicine records loaded."
        );
    }

    private void addSampleMedicines() {
        medicineList.add(
                new Medicine(
                        "MED-001",
                        "Lubricating Eye Drop",
                        "Eye Drop",
                        "BATCH-001",
                        50,
                        10,
                        250.00,
                        LocalDate.of(2027, 5, 20),
                        "ABC Pharmaceuticals"
                )
        );

        medicineList.add(
                new Medicine(
                        "MED-002",
                        "Antibiotic Eye Drop",
                        "Eye Drop",
                        "BATCH-002",
                        8,
                        10,
                        320.00,
                        LocalDate.of(2027, 2, 15),
                        "Dhaka Medicine Supplier"
                )
        );

        medicineList.add(
                new Medicine(
                        "MED-003",
                        "Vitamin A Capsule",
                        "Capsule",
                        "BATCH-003",
                        0,
                        15,
                        80.00,
                        LocalDate.of(2027, 8, 10),
                        "Health Care Limited"
                )
        );

        medicineList.add(
                new Medicine(
                        "MED-004",
                        "Allergy Eye Drop",
                        "Eye Drop",
                        "BATCH-004",
                        25,
                        10,
                        290.00,
                        LocalDate.of(2025, 12, 15),
                        "ABC Pharmaceuticals"
                )
        );
    }

    @FXML
    private void searchMedicineButton(ActionEvent event) {
        String searchText = searchTextField
                .getText()
                .trim()
                .toLowerCase();

        if (searchText.isEmpty()) {
            statusLabel.setText(
                    "Enter a medicine code or medicine name."
            );

            return;
        }

        ObservableList<Medicine> searchResults =
                FXCollections.observableArrayList();

        for (Medicine medicine : medicineList) {
            boolean codeMatches = medicine
                    .getMedicineCode()
                    .toLowerCase()
                    .contains(searchText);

            boolean nameMatches = medicine
                    .getMedicineName()
                    .toLowerCase()
                    .contains(searchText);

            if (codeMatches || nameMatches) {
                searchResults.add(medicine);
            }
        }

        medicineTableView.setItems(searchResults);

        if (searchResults.isEmpty()) {
            statusLabel.setText(
                    "No matching medicine was found."
            );
        } else {
            statusLabel.setText(
                    searchResults.size()
                            + " matching medicine record(s) found."
            );
        }
    }

    @FXML
    private void showAllButton(ActionEvent event) {
        searchTextField.clear();
        medicineTableView.setItems(medicineList);

        statusLabel.setText(
                medicineList.size()
                        + " medicine records displayed."
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
                    "Could not return to the Pharmacist Dashboard."
            );

            exception.printStackTrace();
        }
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