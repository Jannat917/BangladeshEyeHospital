package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;

public class GenerateTestBillController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private TextField totalBillTextField;

    @FXML
    private CheckBox bloodTestCheckBox;

    @FXML
    private CheckBox eyePressureCheckBox;

    @FXML
    private CheckBox visualFieldCheckBox;

    @FXML
    private CheckBox octCheckBox;

    @FXML
    private CheckBox cornealCheckBox;

    @FXML
    private CheckBox retinaScanCheckBox;

    @FXML
    private TableView<TestBillModelClass> billTableView;

    @FXML
    private TableColumn<TestBillModelClass, Integer> patientIdColumn;

    @FXML
    private TableColumn<TestBillModelClass, String> patientNameColumn;

    @FXML
    private TableColumn<TestBillModelClass, String> testsColumn;

    @FXML
    private TableColumn<TestBillModelClass, Double> totalColumn;

    @FXML
    private Label messageLabel;

    private final File dataFolder = new File("data");

    private final File patientFile =
            new File(dataFolder, "patients.bin");

    private final File billFile =
            new File(dataFolder, "testBills.bin");

    private final ArrayList<TestBillModelClass> billList =
            new ArrayList<>();

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientNameTextField.setEditable(false);
        totalBillTextField.setEditable(false);

        patientIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientId"));

        patientNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientName"));

        testsColumn.setCellValueFactory(
                new PropertyValueFactory<>("selectedTests"));

        totalColumn.setCellValueFactory(
                new PropertyValueFactory<>("totalAmount"));

        loadBills();
    }

    private void loadBills() {

        billList.clear();
        billTableView.getItems().clear();

        if (!billFile.exists()) {
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(billFile))) {

            while (true) {

                TestBillModelClass bill =
                        (TestBillModelClass) ois.readObject();

                billList.add(bill);
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        }

        billTableView.getItems().addAll(billList);
    }

    @FXML
    public void searchButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().trim().isEmpty()) {

            messageLabel.setText("Enter Patient ID.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(
                    patientIdTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Patient ID must be numeric.");
            return;
        }

        if (!patientFile.exists()) {

            messageLabel.setText("No patient records found.");
            return;
        }

        boolean found = false;

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                if (patient.getPatientId() == patientId) {

                    patientNameTextField.setText(
                            patient.getPatientName());

                    found = true;
                    break;
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to read patient records.");
            return;
        }

        if (found) {

            messageLabel.setText("Patient found.");

        } else {

            patientNameTextField.clear();
            messageLabel.setText("Patient not found.");
        }
    }

    @FXML
    public void calculateButton(ActionEvent actionEvent) {

        double total = 0;

        StringBuilder selectedTests = new StringBuilder();

        if (bloodTestCheckBox.isSelected()) {

            total += 500;
            selectedTests.append("Blood Test, ");
        }

        if (eyePressureCheckBox.isSelected()) {

            total += 700;
            selectedTests.append("Eye Pressure Test, ");
        }

        if (visualFieldCheckBox.isSelected()) {

            total += 1200;
            selectedTests.append("Visual Field Test, ");
        }

        if (octCheckBox.isSelected()) {

            total += 2500;
            selectedTests.append("OCT, ");
        }

        if (cornealCheckBox.isSelected()) {

            total += 1800;
            selectedTests.append("Corneal Topography, ");
        }

        if (retinaScanCheckBox.isSelected()) {

            total += 2000;
            selectedTests.append("Retina Scan, ");
        }

        if (selectedTests.length() == 0) {

            messageLabel.setText("Please select at least one test.");
            totalBillTextField.clear();
            return;
        }

        totalBillTextField.setText(String.valueOf(total));

        messageLabel.setText("Total bill calculated.");
    }


    @FXML
    public void generateButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().trim().isEmpty()
                || patientNameTextField.getText().trim().isEmpty()) {

            messageLabel.setText("Search a patient first.");
            return;
        }

        if (totalBillTextField.getText().trim().isEmpty()) {

            messageLabel.setText("Calculate the bill first.");
            return;
        }

        StringBuilder tests = new StringBuilder();

        if (bloodTestCheckBox.isSelected()) {
            tests.append("Blood Test, ");
        }

        if (eyePressureCheckBox.isSelected()) {
            tests.append("Eye Pressure Test, ");
        }

        if (visualFieldCheckBox.isSelected()) {
            tests.append("Visual Field Test, ");
        }

        if (octCheckBox.isSelected()) {
            tests.append("OCT, ");
        }

        if (cornealCheckBox.isSelected()) {
            tests.append("Corneal Topography, ");
        }

        if (retinaScanCheckBox.isSelected()) {
            tests.append("Retina Scan, ");
        }

        if (tests.length() > 0) {

            tests.delete(tests.length() - 2, tests.length());
        }

        TestBillModelClass bill = new TestBillModelClass(

                Integer.parseInt(patientIdTextField.getText()),

                patientNameTextField.getText(),

                tests.toString(),

                Double.parseDouble(totalBillTextField.getText())
        );

        billList.add(bill);

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(billFile))) {

            for (TestBillModelClass testBill : billList) {

                oos.writeObject(testBill);
            }

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to save bill.");
            return;
        }

        loadBills();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bill Generated");
        alert.setHeaderText(null);
        alert.setContentText("Test bill generated successfully!");
        alert.showAndWait();

        messageLabel.setText("");
    }


    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        patientNameTextField.clear();
        totalBillTextField.clear();

        bloodTestCheckBox.setSelected(false);
        eyePressureCheckBox.setSelected(false);
        visualFieldCheckBox.setSelected(false);
        octCheckBox.setSelected(false);
        cornealCheckBox.setSelected(false);
        retinaScanCheckBox.setSelected(false);

        messageLabel.setText("");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo(
                    "jannati/receiptionistDashboard.fxml");

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}