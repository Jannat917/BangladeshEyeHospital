package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class DuePaymentcontroller
{
    @javafx.fxml.FXML
    private TableColumn<Payment, String> statuscolumn;
    @javafx.fxml.FXML
    private TableView<Payment> paymenttable;
    @javafx.fxml.FXML
    private TableColumn<Payment, Integer> patientidcolumn;
    @javafx.fxml.FXML
    private TableColumn<Payment, Double> totalbillcolumn;
    ObservableList<Payment> list = FXCollections.observableArrayList();


    @javafx.fxml.FXML
    public void initialize() {
        patientidcolumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        totalbillcolumn.setCellValueFactory(new PropertyValueFactory<>("totalBill"));
        statuscolumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        list.add(new Payment(101,"Due",1500));
        list.add(new Payment(102,"Paid",2000));
        list.add(new Payment(103,"Due",1800));
        list.add(new Payment(104,"Due",2500));

    }


    @javafx.fxml.FXML
    public void viewduepaymentbutton(ActionEvent actionEvent) {
        ObservableList<Payment> dueList = FXCollections.observableArrayList();

        for(Payment p : list){
            if(p.getPaymentStatus().equals("Due")){
                dueList.add(p);

            }
        }
        paymenttable.setItems(dueList);

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
        paymenttable.getItems().clear();
    }
}