package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ProcessRefundcontroller
{
    @javafx.fxml.FXML
    private Label totalbilllabel;
    @javafx.fxml.FXML
    private TextField patientidTF;
    @javafx.fxml.FXML
    private Label statuslabel;

    private Payment payment;
    ObservableList<Payment> list = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {

        list.add(new Payment(101,"Paid",1500));
        list.add(new Payment(102,"Paid",2000));
        list.add(new Payment(103,"Due",1800));

    }


    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/AccountantDashboard.fxml");

        } catch (IOException e) {
        }
    }

    @javafx.fxml.FXML
    public void refundbutton(ActionEvent actionEvent) {
        if(payment == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Search Patient First");
            alert.showAndWait();
            return;
        }
        payment.setPaymentStatus("Refunded");
        statuslabel.setText(payment.getPaymentStatus());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Refund Processed Successfully");
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void clearbutton(ActionEvent actionEvent) {
        patientidTF.clear();
        totalbilllabel.setText("");
        statuslabel.setText("");
        payment = null;
    }

    @javafx.fxml.FXML
    public void searchbtton(ActionEvent actionEvent) {
        int patientId = Integer.parseInt(patientidTF.getText());

        for(Payment p : list){
            if(p.getPatientId() == patientId){
                payment = p;
                totalbilllabel.setText(String.valueOf(p.getTotalBill()));
                statuslabel.setText(p.getPaymentStatus());
                return;
            }

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Patient Not Found");
        alert.showAndWait();
    }
}