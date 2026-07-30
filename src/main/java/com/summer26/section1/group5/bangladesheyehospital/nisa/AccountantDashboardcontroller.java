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
    public void financialreportOA(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("nisa/FinancialReport.fxml");
    }

    @javafx.fxml.FXML
    public void receivePaymentOA(ActionEvent actionEvent)  throws IOException {
        SceneSwitcher.switchTo("nisa/ReceivePayment.fxml");
    }

    @javafx.fxml.FXML
    public void manageexpenseOA(ActionEvent actionEvent)  throws IOException {
        SceneSwitcher.switchTo("nisa/ManageExpense.fxml");
    }

    @javafx.fxml.FXML
    public void refundOA(ActionEvent actionEvent)  throws IOException {
        SceneSwitcher.switchTo("nisa/ProcessRefund.fxml");
    }

    @javafx.fxml.FXML
    public void paysupplierOA(ActionEvent actionEvent) throws IOException {
            SceneSwitcher.switchTo("nisa/PaySupplier.fxml");
    }

    @javafx.fxml.FXML
    public void generatebillOA(ActionEvent actionEvent)  throws IOException {
        SceneSwitcher.switchTo("nisa/GenerateBill.fxml");
    }

    @javafx.fxml.FXML
    public void duepaymentOA(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("nisa/DuePayment.fxml");
    }

    @javafx.fxml.FXML
    public void LogoutOA(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("common/login.fxml");

    }


    @javafx.fxml.FXML
    public void employeesalaryOA(ActionEvent actionEvent) throws IOException {
            SceneSwitcher.switchTo("nisa/EmployeeSalary.fxml");

    }
}