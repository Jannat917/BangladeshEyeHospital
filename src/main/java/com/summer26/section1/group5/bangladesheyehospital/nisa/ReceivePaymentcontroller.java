package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ReceivePaymentcontroller
{
    @javafx.fxml.FXML
    private Label billLabel;
    @javafx.fxml.FXML
    private TextField patientidTF;
    @javafx.fxml.FXML
    private Label statusLabel;

    ObservableList<Payment> list = FXCollections.observableArrayList();
    private Payment payment;

    @javafx.fxml.FXML
    public void initialize() {
        list.add(new Payment(101,"Due",1000));
        list.add(new Payment(102,"Paid",2000));
        list.add(new Payment(103,"Due",3000));
    }

    @javafx.fxml.FXML
    public void receivepaymentbutton(ActionEvent actionEvent) {
        if(payment==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Search Patient First");
            alert.showAndWait();
            return;
        }
        payment.setPaymentStatus("Paid");
        statusLabel.setText(payment.getPaymentStatus());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Payment Received Successfully");
        alert.showAndWait();
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
        billLabel.setText("");
        statusLabel.setText("");
        payment = null;
    }

    @javafx.fxml.FXML
    public void searchbutton(ActionEvent actionEvent) {
        int patientId = Integer.parseInt(patientidTF.getText());
        for(Payment p : list){
            if(p.getPatientId()==patientId){
                payment = p;
                billLabel.setText(String.valueOf(p.getTotalBill()));
                statusLabel.setText(p.getPaymentStatus());

                return;
            }

        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Patient Not Found");
        alert.showAndWait();
    }
}