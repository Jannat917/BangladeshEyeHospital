package com.summer26.section1.group5.bangladesheyehospital.mashrif;

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

public class OpticalInventoryController {
    @FXML private TextField frameIdTextField;
    @FXML private TextField frameNameTextField;
    @FXML private TextField shapeTextField;
    @FXML private TextField materialTextField;
    @FXML private TextField colorTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField stockTextField;
    @FXML private TableView<OpticalFrame> frameTableView;
    @FXML private TableColumn<OpticalFrame, String> frameIdColumn;
    @FXML private TableColumn<OpticalFrame, String> frameNameColumn;
    @FXML private TableColumn<OpticalFrame, String> frameShapeColumn;
    @FXML private TableColumn<OpticalFrame, String> materialColumn;
    @FXML private TableColumn<OpticalFrame, String> colorColumn;
    @FXML private TableColumn<OpticalFrame, Double> priceColumn;
    @FXML private TableColumn<OpticalFrame, Integer> stockQuantityColumn;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        frameIdColumn.setCellValueFactory(new PropertyValueFactory<>("frameId"));
        frameNameColumn.setCellValueFactory(new PropertyValueFactory<>("frameName"));
        frameShapeColumn.setCellValueFactory(new PropertyValueFactory<>("frameShape"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        frameTableView.setItems(MashrifData.getOpticalFrames());

        frameTableView.getSelectionModel().selectedItemProperty().addListener((obs, old, frame) -> {
            if (frame != null) {
                frameIdTextField.setText(frame.getFrameId());
                frameNameTextField.setText(frame.getFrameName());
                shapeTextField.setText(frame.getOpticalFrameshape());
                materialTextField.setText(frame.getMaterial());
                colorTextField.setText(frame.getColor());
                priceTextField.setText(String.valueOf(frame.getPrice()));
                stockTextField.setText(String.valueOf(frame.getStockQuantity()));
            }
        });
    }

    @FXML private void addFrameButton(ActionEvent event) {
        try {
            OpticalFrame frame = new OpticalFrame(
                    frameIdTextField.getText().trim(),
                    frameNameTextField.getText().trim(),
                    shapeTextField.getText().trim(),
                    materialTextField.getText().trim(),
                    colorTextField.getText().trim(),
                    Double.parseDouble(priceTextField.getText().trim()),
                    Integer.parseInt(stockTextField.getText().trim()));
            MashrifData.getOpticalFrames().add(frame);
            clear();
            statusLabel.setText("Frame added.");
        } catch (NumberFormatException e) {
            statusLabel.setText("Price and stock must be numbers.");
        }
    }

    @FXML private void updateSelectedButton(ActionEvent event) {
        OpticalFrame frame = frameTableView.getSelectionModel().getSelectedItem();
        if (frame == null) {
            statusLabel.setText("Select a frame.");
            return;
        }
        try {
            frame.setFrameId(frameIdTextField.getText().trim());
            frame.setFrameName(frameNameTextField.getText().trim());
            frame.setFrameShape(shapeTextField.getText().trim());
            frame.setMaterial(materialTextField.getText().trim());
            frame.setColor(colorTextField.getText().trim());
            frame.setPrice(Double.parseDouble(priceTextField.getText().trim()));
            frame.setStockQuantity(Integer.parseInt(stockTextField.getText().trim()));
            frameTableView.refresh();
            statusLabel.setText("Frame updated.");
        } catch (NumberFormatException e) {
            statusLabel.setText("Price and stock must be numbers.");
        }
    }

    @FXML private void clearButton(ActionEvent event) { clear(); statusLabel.setText("Form cleared."); }
    private void clear() {
        frameIdTextField.clear(); frameNameTextField.clear(); shapeTextField.clear();
        materialTextField.clear(); colorTextField.clear(); priceTextField.clear(); stockTextField.clear();
        frameTableView.getSelectionModel().clearSelection();
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
