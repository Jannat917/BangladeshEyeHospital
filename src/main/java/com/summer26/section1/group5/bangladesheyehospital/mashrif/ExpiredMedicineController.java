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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ExpiredMedicineController {

    @FXML
    private TableView<Medicine> expiredMedicineTableView;

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
    private TableColumn<Medicine, LocalDate> expiryDateColumn;

    @FXML
    private TableColumn<Medicine, String> supplierNameColumn;

    @FXML
    private Label statusLabel;

    private final ObservableList<Medicine> medicineList =
            MedicineData.getMedicineList();

    private final ObservableList<Medicine> expiredMedicineList =
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

        expiryDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("expiryDate")
        );

        supplierNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("supplierName")
        );

        expiredMedicineTableView.setItems(expiredMedicineList);

        loadExpiredMedicines();
    }

    private void loadExpiredMedicines() {

        expiredMedicineList.clear();

        for (Medicine medicine : medicineList) {
            if (medicine.isExpired()) {
                expiredMedicineList.add(medicine);
            }
        }

        statusLabel.setText(
                expiredMedicineList.size()
                        + " expired medicine record(s) found."
        );
    }

    @FXML
    private void refreshButton(ActionEvent event) {
        loadExpiredMedicines();
    }

    @FXML
    private void removeSelectedButton(ActionEvent event) {

        Medicine selectedMedicine =
                expiredMedicineTableView
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedMedicine == null) {
            statusLabel.setText(
                    "Select an expired medicine from the table."
            );
            return;
        }

        medicineList.remove(selectedMedicine);
        expiredMedicineList.remove(selectedMedicine);

        statusLabel.setText(
                "Expired medicine removed successfully."
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