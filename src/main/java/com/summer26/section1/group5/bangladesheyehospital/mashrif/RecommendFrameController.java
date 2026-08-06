package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class RecommendFrameController {
    @FXML private ComboBox<OpticalPrescription> prescriptionComboBox;
    @FXML private TextField patientNameTextField;
    @FXML private ComboBox<String> faceShapeComboBox;
    @FXML private ComboBox<String> materialComboBox;
    @FXML private TextField budgetTextField;
    @FXML private TableView<OpticalFrame> frameTableView;
    @FXML private TableColumn<OpticalFrame, String> frameIdColumn;
    @FXML private TableColumn<OpticalFrame, String> frameNameColumn;
    @FXML private TableColumn<OpticalFrame, String> frameShapeColumn;
    @FXML private TableColumn<OpticalFrame, String> materialColumn;
    @FXML private TableColumn<OpticalFrame, String> colorColumn;
    @FXML private TableColumn<OpticalFrame, Double> priceColumn;
    @FXML private TableColumn<OpticalFrame, Integer> stockQuantityColumn;
    @FXML private Label statusLabel;

    private final ObservableList<OpticalFrame> frames = MashrifData.getOpticalFrames();

    @FXML
    public void initialize() {
        frameIdColumn.setCellValueFactory(new PropertyValueFactory<>("frameId"));
        frameNameColumn.setCellValueFactory(new PropertyValueFactory<>("frameName"));
        frameShapeColumn.setCellValueFactory(new PropertyValueFactory<>("frameShape"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));

        prescriptionComboBox.setItems(MashrifData.getOpticalPrescriptions());
        faceShapeComboBox.getItems().addAll("Round", "Oval", "Square", "Heart", "Diamond");
        materialComboBox.getItems().addAll("Metal", "Plastic", "Titanium");
        frameTableView.setItems(frames);

        prescriptionComboBox.setOnAction(e -> {
            OpticalPrescription p = prescriptionComboBox.getValue();
            patientNameTextField.setText(p == null ? "" : p.getPatientName());
        });
    }

    @FXML private void findFramesButton(ActionEvent event) {
        String material = materialComboBox.getValue();
        double budget = Double.MAX_VALUE;
        try {
            if (!budgetTextField.getText().trim().isEmpty()) {
                budget = Double.parseDouble(budgetTextField.getText().trim());
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Budget must be a number.");
            return;
        }

        ObservableList<OpticalFrame> results = FXCollections.observableArrayList();
        for (OpticalFrame frame : frames) {
            boolean materialMatch = material == null || frame.getMaterial().equalsIgnoreCase(material);
            if (materialMatch && frame.getPrice() <= budget && frame.isAvailable()) {
                results.add(frame);
            }
        }
        frameTableView.setItems(results);
        statusLabel.setText(results.size() + " frame(s) found.");
    }

    @FXML private void recommendSelectedButton(ActionEvent event) {
        OpticalPrescription prescription = prescriptionComboBox.getValue();
        OpticalFrame frame = frameTableView.getSelectionModel().getSelectedItem();
        if (prescription == null || frame == null) {
            statusLabel.setText("Select a prescription and a frame.");
            return;
        }
        prescription.setStatus("Frame Recommended");
        statusLabel.setText(frame.getFrameName() + " recommended for " + prescription.getPatientName() + ".");
    }

    @FXML private void showAllButton(ActionEvent event) { frameTableView.setItems(frames); }
    @FXML private void clearButton(ActionEvent event) {
        prescriptionComboBox.setValue(null);
        patientNameTextField.clear();
        faceShapeComboBox.setValue(null);
        materialComboBox.setValue(null);
        budgetTextField.clear();
        frameTableView.setItems(frames);
        statusLabel.setText("Form cleared.");
    }
    @FXML private void backButton(ActionEvent event) { back(event); }

    private void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                    "/com/summer26/section1/group5/bangladesheyehospital/mashrif/OpticianDashboard.fxml"));
            ((Stage)((Node)event.getSource()).getScene().getWindow()).setScene(new Scene(root));
        } catch (IOException e) {
            statusLabel.setText("Could not return to dashboard.");
        }
    }
}
