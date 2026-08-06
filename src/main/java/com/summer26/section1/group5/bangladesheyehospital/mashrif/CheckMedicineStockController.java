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
            MashrifData.getMedicines();

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

        medicineTableView.setItems(medicineList);

        statusLabel.setText(
                medicineList.size() + " medicine record(s) loaded."
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

            String medicineCode = medicine
                    .getMedicineCode()
                    .toLowerCase();

            String medicineName = medicine
                    .getMedicineName()
                    .toLowerCase();

            if (medicineCode.contains(searchText)
                    || medicineName.contains(searchText)) {

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
                        + " medicine record(s) displayed."
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