package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class CreateEyeglassOrderController {
    @FXML private ComboBox<OpticalPrescription> prescriptionComboBox;
    @FXML private ComboBox<OpticalFrame> frameComboBox;
    @FXML private TextField patientNameTextField;
    @FXML private TextField totalAmountTextField;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        prescriptionComboBox.setItems(MashrifData.getOpticalPrescriptions());
        frameComboBox.setItems(MashrifData.getOpticalFrames());

        prescriptionComboBox.setOnAction(e -> {
            OpticalPrescription p = prescriptionComboBox.getValue();
            patientNameTextField.setText(p == null ? "" : p.getPatientName());
        });

        frameComboBox.setOnAction(e -> {
            OpticalFrame frame = frameComboBox.getValue();
            totalAmountTextField.setText(frame == null ? "" : String.format("%.2f", frame.getPrice()));
        });
    }

    @FXML private void createOrderButton(ActionEvent event) {
        OpticalPrescription p = prescriptionComboBox.getValue();
        OpticalFrame frame = frameComboBox.getValue();

        if (p == null || frame == null) {
            statusLabel.setText("Select a prescription and a frame.");
            return;
        }
        if (!frame.isAvailable()) {
            statusLabel.setText("Selected frame is out of stock.");
            return;
        }

        String orderId = "EO-" + String.format("%03d", MashrifData.getEyeglassOrders().size() + 1);
        EyeglassOrder order = new EyeglassOrder(
                orderId, p.getPrescriptionId(), p.getPatientName(),
                frame.getFrameId(), frame.getFrameName(),
                LocalDate.now(), "Ordered", frame.getPrice());

        MashrifData.getEyeglassOrders().add(order);
        frame.removeStock(1);
        p.setStatus("Order Created");
        statusLabel.setText("Order created: " + orderId);
    }

    @FXML private void clearButton(ActionEvent event) {
        prescriptionComboBox.setValue(null);
        frameComboBox.setValue(null);
        patientNameTextField.clear();
        totalAmountTextField.clear();
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
