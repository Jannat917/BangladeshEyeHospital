package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ManageExpensecontroller
{
    @javafx.fxml.FXML
    private Label totalexpenselabel;
    @javafx.fxml.FXML
    private TextField expensetypeTF;
    @javafx.fxml.FXML
    private TextField expenseTF;
    @javafx.fxml.FXML
    private TextField expenseidTF;

    private Expense expense;
    ObservableList<Expense> list = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void savebutton(ActionEvent actionEvent) {
        if (expense == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Calculate Expense First");
            alert.showAndWait();
            return;
        }
        list.add(expense);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Expense Saved Successfully");
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void calculatebutton(ActionEvent actionEvent) {

        int expenseId = Integer.parseInt(expenseidTF.getText());
        String expenseType = expensetypeTF.getText();
        double amount = Double.parseDouble(expenseTF.getText());

        expense = new Expense(amount, expenseId, expenseType, 0);
        totalexpenselabel.setText(String.valueOf(expense.getExpense()));
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

        expenseidTF.clear();
        expensetypeTF.clear();
        expenseTF.clear();
        totalexpenselabel.setText("");
        expense = null;
    }

}