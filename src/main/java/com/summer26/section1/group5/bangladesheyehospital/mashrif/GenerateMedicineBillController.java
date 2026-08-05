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
import java.util.ArrayList;

public class GenerateMedicineBillController {

    @FXML
    private ComboBox<PharmacyPrescription> prescriptionComboBox;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private TextField discountTextField;

    @FXML
    private TextField paidAmountTextField;

    @FXML
    private TableView<BillItem> billTableView;

    @FXML
    private TableColumn<BillItem, String> medicineCodeColumn;

    @FXML
    private TableColumn<BillItem, String> medicineNameColumn;

    @FXML
    private TableColumn<BillItem, Integer> quantityColumn;

    @FXML
    private TableColumn<BillItem, Double> unitPriceColumn;

    @FXML
    private TableColumn<BillItem, Double> subtotalColumn;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label discountLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label changeLabel;

    @FXML
    private TextArea billPreviewTextArea;

    @FXML
    private Label statusLabel;

    private final ObservableList<PharmacyPrescription> prescriptionList =
            FXCollections.observableArrayList();

    private final ObservableList<BillItem> billItems =
            FXCollections.observableArrayList();

    private double currentSubtotal;
    private double currentDiscount;
    private double currentTotal;
    private double currentChange;

    private boolean billCalculated;
    private boolean saleRecorded;

    @FXML
    public void initialize() {

        medicineCodeColumn.setCellValueFactory(
                new PropertyValueFactory<>("medicineCode")
        );

        medicineNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("medicineName")
        );

        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );

        unitPriceColumn.setCellValueFactory(
                new PropertyValueFactory<>("unitPrice")
        );

        subtotalColumn.setCellValueFactory(
                new PropertyValueFactory<>("subtotal")
        );

        billTableView.setItems(billItems);

        addSamplePrescriptions();

        prescriptionComboBox.setItems(prescriptionList);

        prescriptionComboBox.setOnAction(event ->
                loadSelectedPrescription()
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
                        2,
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
                        1,
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
                        "Dispensed",
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
                        3,
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
                        "Dispensed",
                        secondMedicines
                )
        );
    }

    private void loadSelectedPrescription() {

        PharmacyPrescription selectedPrescription =
                prescriptionComboBox.getValue();

        billItems.clear();
        patientNameTextField.clear();
        discountTextField.clear();
        paidAmountTextField.clear();

        clearAmounts();

        billCalculated = false;
        saleRecorded = false;

        if (selectedPrescription == null) {
            statusLabel.setText("Select a prescription.");
            return;
        }

        patientNameTextField.setText(
                selectedPrescription.getPatientName()
        );

        for (PrescriptionMedicine medicine :
                selectedPrescription.getPrescribedMedicines()) {

            int quantity = medicine.getDispensedQuantity();

            if (quantity > 0) {
                billItems.add(
                        new BillItem(
                                medicine.getMedicineCode(),
                                medicine.getMedicineName(),
                                quantity,
                                medicine.getUnitPrice()
                        )
                );
            }
        }

        if (billItems.isEmpty()) {
            statusLabel.setText(
                    "No dispensed medicines found for this prescription."
            );
        } else {
            statusLabel.setText(
                    billItems.size() + " medicine item(s) loaded."
            );
        }
    }

    @FXML
    private void calculateBillButton(ActionEvent event) {

        PharmacyPrescription selectedPrescription =
                prescriptionComboBox.getValue();

        if (selectedPrescription == null) {
            statusLabel.setText("Select a prescription.");
            return;
        }

        if (billItems.isEmpty()) {
            statusLabel.setText(
                    "No medicine items are available for billing."
            );
            return;
        }

        try {
            currentSubtotal = 0;

            for (BillItem item : billItems) {
                currentSubtotal += item.getSubtotal();
            }

            String discountText =
                    discountTextField.getText().trim();

            if (discountText.isEmpty()) {
                currentDiscount = 0;
            } else {
                currentDiscount =
                        Double.parseDouble(discountText);
            }

            if (currentDiscount < 0) {
                statusLabel.setText(
                        "Discount cannot be negative."
                );

                billCalculated = false;
                return;
            }

            if (currentDiscount > currentSubtotal) {
                statusLabel.setText(
                        "Discount cannot exceed subtotal."
                );

                billCalculated = false;
                return;
            }

            currentTotal =
                    currentSubtotal - currentDiscount;

            String paidText =
                    paidAmountTextField.getText().trim();

            if (paidText.isEmpty()) {
                statusLabel.setText(
                        "Enter the paid amount."
                );

                billCalculated = false;
                return;
            }

            double paidAmount =
                    Double.parseDouble(paidText);

            if (paidAmount < 0) {
                statusLabel.setText(
                        "Paid amount cannot be negative."
                );

                billCalculated = false;
                return;
            }

            if (paidAmount < currentTotal) {
                statusLabel.setText(
                        "Paid amount is less than total bill."
                );

                billCalculated = false;
                return;
            }

            currentChange =
                    paidAmount - currentTotal;

            subtotalLabel.setText(
                    String.format("%.2f", currentSubtotal)
            );

            discountLabel.setText(
                    String.format("%.2f", currentDiscount)
            );

            totalLabel.setText(
                    String.format("%.2f", currentTotal)
            );

            changeLabel.setText(
                    String.format("%.2f", currentChange)
            );

            billCalculated = true;
            saleRecorded = false;

            statusLabel.setText(
                    "Bill calculated successfully."
            );

        } catch (NumberFormatException exception) {
            statusLabel.setText(
                    "Discount and paid amount must be numbers."
            );

            billCalculated = false;
        }
    }

    @FXML
    private void generateBillButton(ActionEvent event) {

        PharmacyPrescription selectedPrescription =
                prescriptionComboBox.getValue();

        if (selectedPrescription == null) {
            statusLabel.setText("Select a prescription.");
            return;
        }

        calculateBillButton(event);

        if (!billCalculated) {
            return;
        }

        StringBuilder bill =
                new StringBuilder();

        bill.append("BANGLADESH EYE HOSPITAL\n");
        bill.append("MEDICINE BILL\n");
        bill.append("--------------------------------\n");

        bill.append("Prescription ID: ")
                .append(selectedPrescription.getPrescriptionId())
                .append("\n");

        bill.append("Patient Name: ")
                .append(selectedPrescription.getPatientName())
                .append("\n");

        bill.append("Date: ")
                .append(LocalDate.now())
                .append("\n");

        bill.append("--------------------------------\n");

        for (BillItem item : billItems) {

            bill.append(item.getMedicineName())
                    .append(" x ")
                    .append(item.getQuantity())
                    .append(" = ")
                    .append(
                            String.format(
                                    "%.2f",
                                    item.getSubtotal()
                            )
                    )
                    .append("\n");
        }

        bill.append("--------------------------------\n");

        bill.append("Subtotal: ")
                .append(
                        String.format(
                                "%.2f",
                                currentSubtotal
                        )
                )
                .append("\n");

        bill.append("Discount: ")
                .append(
                        String.format(
                                "%.2f",
                                currentDiscount
                        )
                )
                .append("\n");

        bill.append("Total: ")
                .append(
                        String.format(
                                "%.2f",
                                currentTotal
                        )
                )
                .append("\n");

        bill.append("Change: ")
                .append(
                        String.format(
                                "%.2f",
                                currentChange
                        )
                )
                .append("\n");

        bill.append("--------------------------------\n");
        bill.append("Thank you.");

        billPreviewTextArea.setText(
                bill.toString()
        );

        if (!saleRecorded) {

            for (BillItem item : billItems) {

                String saleId =
                        "SALE-" + String.format(
                                "%03d",
                                MedicineSalesData
                                        .getSalesList()
                                        .size() + 1
                        );

                MedicineSale sale =
                        new MedicineSale(
                                saleId,
                                LocalDate.now(),
                                selectedPrescription
                                        .getPrescriptionId(),
                                selectedPrescription
                                        .getPatientName(),
                                item.getMedicineName(),
                                item.getQuantity(),
                                item.getSubtotal()
                        );

                MedicineSalesData.addSale(sale);
            }

            saleRecorded = true;
        }

        statusLabel.setText(
                "Medicine bill generated successfully."
        );
    }

    @FXML
    private void clearButton(ActionEvent event) {

        prescriptionComboBox.setValue(null);

        patientNameTextField.clear();
        discountTextField.clear();
        paidAmountTextField.clear();

        billItems.clear();
        billPreviewTextArea.clear();

        clearAmounts();

        billCalculated = false;
        saleRecorded = false;

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

    private void clearAmounts() {

        currentSubtotal = 0;
        currentDiscount = 0;
        currentTotal = 0;
        currentChange = 0;

        subtotalLabel.setText("0.00");
        discountLabel.setText("0.00");
        totalLabel.setText("0.00");
        changeLabel.setText("0.00");

        billPreviewTextArea.clear();
    }

    private void openScene(
            ActionEvent event,
            String fxmlPath
    ) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(fxmlPath)
                );

        Parent root =
                loader.load();

        Stage stage =
                (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();

        stage.setScene(
                new Scene(root)
        );

        stage.show();
    }

    public static class BillItem {

        private final String medicineCode;
        private final String medicineName;
        private final int quantity;
        private final double unitPrice;
        private final double subtotal;

        public BillItem(
                String medicineCode,
                String medicineName,
                int quantity,
                double unitPrice
        ) {
            this.medicineCode = medicineCode;
            this.medicineName = medicineName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.subtotal =
                    quantity * unitPrice;
        }

        public String getMedicineCode() {
            return medicineCode;
        }

        public String getMedicineName() {
            return medicineName;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public double getSubtotal() {
            return subtotal;
        }
    }
}