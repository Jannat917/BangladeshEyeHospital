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
import java.util.ArrayList;

public class ViewPharmacyPrescriptionsController {

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<PharmacyPrescription> prescriptionTableView;

    @FXML
    private TableColumn<PharmacyPrescription, String> prescriptionIdColumn;

    @FXML
    private TableColumn<PharmacyPrescription, Integer> patientIdColumn;

    @FXML
    private TableColumn<PharmacyPrescription, String> patientNameColumn;

    @FXML
    private TableColumn<PharmacyPrescription, String> doctorNameColumn;

    @FXML
    private TableColumn<PharmacyPrescription, LocalDate> prescriptionDateColumn;

    @FXML
    private TableColumn<PharmacyPrescription, String> statusColumn;

    @FXML
    private Label statusLabel;

    private final ObservableList<PharmacyPrescription> prescriptionList =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        prescriptionIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("prescriptionId")
        );

        patientIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientId")
        );

        patientNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientName")
        );

        doctorNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("doctorName")
        );

        prescriptionDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("prescriptionDate")
        );

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );

        addSamplePrescriptions();

        prescriptionTableView.setItems(prescriptionList);

        statusLabel.setText(
                prescriptionList.size() + " prescription record(s) loaded."
        );
    }

    private void addSamplePrescriptions() {

        prescriptionList.add(
                new PharmacyPrescription(
                        "PR-001",
                        1001,
                        "Rahim Ahmed",
                        501,
                        "Dr. Karim",
                        LocalDate.of(2026, 8, 5),
                        "Use eye drops twice daily.",
                        "Pending",
                        new ArrayList<>()
                )
        );

        prescriptionList.add(
                new PharmacyPrescription(
                        "PR-002",
                        1002,
                        "Nusrat Jahan",
                        502,
                        "Dr. Sultana",
                        LocalDate.of(2026, 8, 6),
                        "Complete the full medicine course.",
                        "Pending",
                        new ArrayList<>()
                )
        );
    }

    @FXML
    private void searchButton(ActionEvent event) {

        String searchText = searchTextField
                .getText()
                .trim()
                .toLowerCase();

        if (searchText.isEmpty()) {
            statusLabel.setText(
                    "Enter a prescription ID or patient name."
            );
            return;
        }

        ObservableList<PharmacyPrescription> searchResults =
                FXCollections.observableArrayList();

        for (PharmacyPrescription prescription : prescriptionList) {

            String prescriptionId =
                    prescription.getPrescriptionId().toLowerCase();

            String patientName =
                    prescription.getPatientName().toLowerCase();

            if (prescriptionId.contains(searchText)
                    || patientName.contains(searchText)) {

                searchResults.add(prescription);
            }
        }

        prescriptionTableView.setItems(searchResults);

        statusLabel.setText(
                searchResults.size() + " matching record(s) found."
        );
    }

    @FXML
    private void showAllButton(ActionEvent event) {

        searchTextField.clear();

        prescriptionTableView.setItems(prescriptionList);

        statusLabel.setText(
                prescriptionList.size() + " prescription record(s) displayed."
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
                    "Could not return to dashboard."
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