package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class GenerateBillcontroller
{
    @javafx.fxml.FXML
    private TextField testfeeTF;
    @javafx.fxml.FXML
    private Label totalbilllabel;
    @javafx.fxml.FXML
    private TextField medicinefeeTF;
    @javafx.fxml.FXML
    private TextField patientidTF;
    @javafx.fxml.FXML
    private TextField doctorfeeTF;

    private Bill bill;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void calculatebutton(ActionEvent actionEvent) {

        int patientId = Integer.parseInt(patientidTF.getText());
        double doctorFee = Double.parseDouble(doctorfeeTF.getText());
        double testFee = Double.parseDouble(testfeeTF.getText());
        double medicineFee = Double.parseDouble(medicinefeeTF.getText());

        bill = new Bill(doctorFee, medicineFee, patientId,testFee);

        totalbilllabel.setText(String.valueOf(bill.getTotalBill()));
    }

    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/AccountantDashboard.fxml");

        } catch (IOException e) {
        }
    }

    @javafx.fxml.FXML
    public void clearbutton(ActionEvent actionEvent) {

        patientidTF.clear();
        doctorfeeTF.clear();
        testfeeTF.clear();
        medicinefeeTF.clear();
        totalbilllabel.setText("");

        bill = null;

    }

    @javafx.fxml.FXML
    public void generatebutton(ActionEvent actionEvent) {

        if (bill == null) {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Calculate Bill First");
            a.showAndWait();
            return;
        }

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Bill Generated Successfully");
        a.showAndWait();

    }
}