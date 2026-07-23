package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AccountantDashboardcontroller
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void financialreportOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void receivePaymentOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void manageexpenseOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void refundOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void salaryOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void paysupplierOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void generatebillOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void duepaymentOA(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void LogoutOA(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");

    }
}