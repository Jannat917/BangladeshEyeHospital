package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Billcontroller {
    @javafx.fxml.FXML
    private Label totalbilllabel;
    @javafx.fxml.FXML
    private Label doctorfeelabel;
    @javafx.fxml.FXML
    private Label medicinefeelabel;
    @javafx.fxml.FXML
    private TextField patientidTF;
    @javafx.fxml.FXML
    private Label testfeelabel;

    private Bill bill;

    ObservableList<Bill> list = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        list.add(new Bill(500,650,101,400));
        list.add(new Bill(1000,750,102,500));
        list.add(new Bill(2000,800,103,1000));
    }

    @javafx.fxml.FXML
    public void calculatebutton(ActionEvent actionEvent) {
        if (bill == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please Calculate Bill First");
            a.showAndWait();
            return;


        }
        totalbilllabel.setText(String.valueOf(bill.getTotalBill()));



    }

    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {
        }
    }

    @javafx.fxml.FXML
    public void paybutton(ActionEvent actionEvent) {

        if (bill == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please Calculate Bill First");
            a.showAndWait();
            return;
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Payment Successfully");
        a.showAndWait();



    }

    @javafx.fxml.FXML
    public void enterbutton(ActionEvent actionEvent) {
        int patientId = Integer.parseInt(patientidTF.getText());

        for(Bill b : list){
            if(b.getPatientId() == patientId){
                bill = b;

                doctorfeelabel.setText(String.valueOf(b.getDoctorFee()));
                testfeelabel.setText(String.valueOf(b.getTestFee()));
                medicinefeelabel.setText(String.valueOf(b.getMedicineFee()));

                return;

            }
        }
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("PatientId Not Found");
        a.showAndWait();

    }
}