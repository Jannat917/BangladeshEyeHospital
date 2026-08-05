package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;

public class FinancialReportcontroller
{
    @javafx.fxml.FXML
    private Label expenseLabel;
    @javafx.fxml.FXML
    private Label profitLabel;
    @javafx.fxml.FXML
    private Label incomeLabel;

    private Expense FinancialReport;

    @javafx.fxml.FXML
    public void initialize() {
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
        incomeLabel.setText("");
        expenseLabel.setText("");
        profitLabel.setText("");

        FinancialReport = null;
    }

    @javafx.fxml.FXML
    public void generatebutton(ActionEvent actionEvent) {

        FinancialReport = new Expense( 25000, 101, "Hospital Expense", 50000);

        incomeLabel.setText(String.valueOf(FinancialReport.getIncome()));
        expenseLabel.setText(String.valueOf(FinancialReport.getExpense()));
        profitLabel.setText(String.valueOf(FinancialReport.getProfit()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Financial Report Generated Successfully");
        alert.showAndWait();

    }
}