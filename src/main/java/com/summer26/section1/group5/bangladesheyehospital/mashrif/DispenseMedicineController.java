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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DispenseMedicineController {

    @FXML
    private ComboBox<PharmacyPrescription> prescriptionComboBox;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private ComboBox<PrescriptionMedicine> medicineComboBox;

    @FXML
    private TextField availableStockTextField;

    @FXML
    private TextField prescribedQuantityTextField;

    @FXML
    private TextField dispenseQuantityTextField;

    @FXML
    private TableView<DispensedMedicineRecord> dispensedMedicineTableView;

    @FXML
    private TableColumn<DispensedMedicineRecord, String> prescriptionIdColumn;

    @FXML
    private TableColumn<DispensedMedicineRecord, String> patientNameColumn;

    @FXML
    private TableColumn<DispensedMedicineRecord, String> medicineNameColumn;

    @FXML
    private TableColumn<DispensedMedicineRecord, Integer>
            prescribedQuantityColumn;

    @FXML
    private TableColumn<DispensedMedicineRecord, Integer>
            dispensedQuantityColumn;

    @FXML
    private TableColumn<DispensedMedicineRecord, String> statusColumn;

    @FXML
    private Label statusLabel;

    private final ObservableList<PharmacyPrescription> prescriptionList =
            FXCollections.observableArrayList();

    private final ObservableList<DispensedMedicineRecord> dispensedRecords =
            FXCollections.observableArrayList();

    private final ObservableList<Medicine> medicineStock =
            MedicineData.getMedicineList();

    @FXML
    public void initialize() {

        prescriptionIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("prescriptionId")
        );

        patientNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientName")
        );

        medicineNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("medicineName")
        );

        prescribedQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("prescribedQuantity")
        );

        dispensedQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("dispensedQuantity")
        );

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        dispensedMedicineTableView.setItems(dispensedRecords);

        addSamplePrescriptions();

        prescriptionComboBox.setItems(prescriptionList);

        prescriptionComboBox.setOnAction(event ->
                loadSelectedPrescription()
        );

        medicineComboBox.setOnAction(event ->
                loadSelectedMedicine()
        );

        statusLabel.setText("Select a prescription.");
    }

    private void addSamplePrescriptions() {

        ArrayList<PrescriptionMedicine> firstMedicines =
                new ArrayList<>();

        firstMedicines.add(
                new PrescriptionMedicine(
                        "MED-001",
                        "Lubricating Eye Drop",
                        "2 drops",
                        "Twice daily",
                        7,
                        2,
                        0,
                        250.00
                )
        );

        firstMedicines.add(
                new PrescriptionMedicine(
                        "MED-002",
                        "Antibiotic Eye Drop",
                        "1 drop",
                        "Three times daily",
                        5,
                        1,
                        0,
                        320.00
                )
        );

        prescriptionList.add(
                new PharmacyPrescription(
                        "PR-001",
                        1001,
                        "Rahim Ahmed",
                        501,
                        "Dr. Karim",
                        LocalDate.now(),
                        "Use medicines as instructed.",
                        "Pending",
                        firstMedicines
                )
        );

        ArrayList<PrescriptionMedicine> secondMedicines =
                new ArrayList<>();

        secondMedicines.add(
                new PrescriptionMedicine(
                        "MED-001",
                        "Lubricating Eye Drop",
                        "1 drop",
                        "Twice daily",
                        10,
                        3,
                        0,
                        250.00
                )
        );

        prescriptionList.add(
                new PharmacyPrescription(
                        "PR-002",
                        1002,
                        "Nusrat Jahan",
                        502,
                        "Dr. Sultana",
                        LocalDate.now(),
                        "Continue for ten days.",
                        "Pending",
                        secondMedicines
                )
        );
    }

    private void loadSelectedPrescription() {

        PharmacyPrescription selectedPrescription =
                prescriptionComboBox.getValue();

        medicineComboBox.getItems().clear();
        medicineComboBox.setValue(null);

        patientNameTextField.clear();
        prescribedQuantityTextField.clear();
        availableStockTextField.clear();
        dispenseQuantityTextField.clear();

        if (selectedPrescription == null) {
            return;
        }

        patientNameTextField.setText(
                selectedPrescription.getPatientName()
        );

        medicineComboBox.setItems(
                FXCollections.observableArrayList(
                        selectedPrescription.getPrescribedMedicines()
                )
        );

        statusLabel.setText(
                "Prescription selected: "
                        + selectedPrescription.getPrescriptionId()
        );
    }

    private void loadSelectedMedicine() {

        PrescriptionMedicine selectedMedicine =
                medicineComboBox.getValue();

        prescribedQuantityTextField.clear();
        availableStockTextField.clear();
        dispenseQuantityTextField.clear();

        if (selectedMedicine == null) {
            return;
        }

        prescribedQuantityTextField.setText(
                String.valueOf(selectedMedicine.getRemainingQuantity())
        );

        Medicine stockMedicine = findMedicineInStock(
                selectedMedicine.getMedicineCode()
        );

        if (stockMedicine == null) {
            availableStockTextField.setText("0");
            statusLabel.setText(
                    "This medicine was not found in inventory."
            );
            return;
        }

        availableStockTextField.setText(
                String.valueOf(stockMedicine.getStockQuantity())
        );

        statusLabel.setText(
                "Enter the quantity to dispense."
        );
    }

    @FXML
    private void dispenseButton(ActionEvent event) {

        PharmacyPrescription selectedPrescription =
                prescriptionComboBox.getValue();

        PrescriptionMedicine selectedPrescriptionMedicine =
                medicineComboBox.getValue();

        if (selectedPrescription == null) {
            statusLabel.setText("Select a prescription.");
            return;
        }

        if (selectedPrescriptionMedicine == null) {
            statusLabel.setText("Select a medicine.");
            return;
        }

        String quantityText =
                dispenseQuantityTextField.getText().trim();

        if (quantityText.isEmpty()) {
            statusLabel.setText("Enter the dispense quantity.");
            return;
        }

        try {
            int dispenseQuantity =
                    Integer.parseInt(quantityText);

            if (dispenseQuantity <= 0) {
                statusLabel.setText(
                        "Dispense quantity must be greater than zero."
                );
                return;
            }

            int remainingQuantity =
                    selectedPrescriptionMedicine
                            .getRemainingQuantity();

            if (dispenseQuantity > remainingQuantity) {
                statusLabel.setText(
                        "Quantity cannot exceed the remaining prescribed amount."
                );
                return;
            }

            Medicine stockMedicine = findMedicineInStock(
                    selectedPrescriptionMedicine.getMedicineCode()
            );

            if (stockMedicine == null) {
                statusLabel.setText(
                        "Medicine was not found in inventory."
                );
                return;
            }

            if (stockMedicine.isExpired()) {
                statusLabel.setText(
                        "Expired medicine cannot be dispensed."
                );
                return;
            }

            if (!stockMedicine.hasEnoughStock(dispenseQuantity)) {
                statusLabel.setText(
                        "Not enough medicine available in stock."
                );
                return;
            }

            stockMedicine.removeStock(dispenseQuantity);

            int newDispensedQuantity =
                    selectedPrescriptionMedicine
                            .getDispensedQuantity()
                            + dispenseQuantity;

            selectedPrescriptionMedicine.setDispensedQuantity(
                    newDispensedQuantity
            );

            selectedPrescription.updateStatus();

            dispensedRecords.add(
                    new DispensedMedicineRecord(
                            selectedPrescription.getPrescriptionId(),
                            selectedPrescription.getPatientName(),
                            selectedPrescriptionMedicine.getMedicineName(),
                            selectedPrescriptionMedicine
                                    .getPrescribedQuantity(),
                            dispenseQuantity,
                            selectedPrescriptionMedicine
                                    .isFullyDispensed()
                                    ? "Fully Dispensed"
                                    : "Partially Dispensed"
                    )
            );

            availableStockTextField.setText(
                    String.valueOf(stockMedicine.getStockQuantity())
            );

            prescribedQuantityTextField.setText(
                    String.valueOf(
                            selectedPrescriptionMedicine
                                    .getRemainingQuantity()
                    )
            );

            dispenseQuantityTextField.clear();

            statusLabel.setText(
                    "Medicine dispensed successfully."
            );

        } catch (NumberFormatException exception) {
            statusLabel.setText(
                    "Dispense quantity must be a whole number."
            );
        } catch (IllegalArgumentException exception) {
            statusLabel.setText(exception.getMessage());
        }
    }

    @FXML
    private void clearButton(ActionEvent event) {

        prescriptionComboBox.setValue(null);
        medicineComboBox.setValue(null);
        medicineComboBox.getItems().clear();

        patientNameTextField.clear();
        availableStockTextField.clear();
        prescribedQuantityTextField.clear();
        dispenseQuantityTextField.clear();

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
                    "Could not return to the Pharmacist Dashboard."
            );

            exception.printStackTrace();
        }
    }

    private Medicine findMedicineInStock(String medicineCode) {

        for (Medicine medicine : medicineStock) {
            if (medicine.getMedicineCode()
                    .equalsIgnoreCase(medicineCode)) {

                return medicine;
            }
        }

        return null;
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

    public static class DispensedMedicineRecord {

        private final String prescriptionId;
        private final String patientName;
        private final String medicineName;
        private final int prescribedQuantity;
        private final int dispensedQuantity;
        private final String status;

        public DispensedMedicineRecord(
                String prescriptionId,
                String patientName,
                String medicineName,
                int prescribedQuantity,
                int dispensedQuantity,
                String status
        ) {
            this.prescriptionId = prescriptionId;
            this.patientName = patientName;
            this.medicineName = medicineName;
            this.prescribedQuantity = prescribedQuantity;
            this.dispensedQuantity = dispensedQuantity;
            this.status = status;
        }

        public String getPrescriptionId() {
            return prescriptionId;
        }

        public String getPatientName() {
            return patientName;
        }

        public String getMedicineName() {
            return medicineName;
        }

        public int getPrescribedQuantity() {
            return prescribedQuantity;
        }

        public int getDispensedQuantity() {
            return dispensedQuantity;
        }

        public String getStatus() {
            return status;
        }
    }
}