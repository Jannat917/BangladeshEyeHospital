package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class EmployeeSalarycontroller
{
    @javafx.fxml.FXML
    private TableColumn<Employee, String> namecolumn;
    @javafx.fxml.FXML
    private TableView<Employee> EmployeeTable;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> ststuscolumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, Integer> employeeidcolumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> designationcolumn;
    @javafx.fxml.FXML
    private ComboBox<String> monthCB;
    @javafx.fxml.FXML
    private Label grosssalarylabel;
    @javafx.fxml.FXML
    private TableColumn<Employee, Double> basicsalarycolumn;

    ObservableList<Employee> list = FXCollections.observableArrayList();
    private Employee selectedEmployee;


    @javafx.fxml.FXML
    public void initialize() {
        monthCB.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        employeeidcolumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        namecolumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        designationcolumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        basicsalarycolumn.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        ststuscolumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        list.add(new Employee(30000, "Doctor", 101, "Rahim", "Due"));
        list.add(new Employee(20000, "Receptionist", 102, "Karim", "Due"));
        list.add(new Employee(15000, "Nurse", 103, "Rima", "Due"));
        list.add(new Employee(35000, "Doctor", 104, "Mehedi", "Due"));

        EmployeeTable.setItems(list);
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

        monthCB.setValue(null);
        EmployeeTable.getSelectionModel().clearSelection();
        grosssalarylabel.setText("");
        selectedEmployee = null;

    }

    @javafx.fxml.FXML
    public void approvesalarybutton(ActionEvent actionEvent) {
        if(selectedEmployee == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Calculate Salary First");
            alert.showAndWait();
            return;
        }
        selectedEmployee.setPaymentStatus("Paid");
        EmployeeTable.refresh();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Salary Approved Successfully");
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void generatesalarybutton(ActionEvent actionEvent) {

        if(selectedEmployee == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Approve Salary First");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Salary Slip");
        alert.setContentText(

                "Salary Month : "
                        + monthCB.getValue()

                        + "\n\nEmployee ID : "
                        + selectedEmployee.getEmployeeId()

                        + "\nEmployee Name : "
                        + selectedEmployee.getEmployeeName()

                        + "\nDesignation : "
                        + selectedEmployee.getDesignation()

                        + "\nGross Salary : "
                        + grosssalarylabel.getText()

                        + "\nStatus : "
                        + selectedEmployee.getPaymentStatus()

        );

        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void calculatesalarybutton(ActionEvent actionEvent) {

        selectedEmployee = EmployeeTable.getSelectionModel().getSelectedItem();

        if(selectedEmployee == null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select an Employee");
            alert.showAndWait();
            return;
        }

        double grossSalary = selectedEmployee.getBasicSalary() + 1000;

        grosssalarylabel.setText(String.valueOf(grossSalary));
    }
}