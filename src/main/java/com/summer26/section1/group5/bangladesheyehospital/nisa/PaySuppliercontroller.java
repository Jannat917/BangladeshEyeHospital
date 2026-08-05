package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class PaySuppliercontroller
{
    @javafx.fxml.FXML
    private Label dueamountlabel;
    @javafx.fxml.FXML
    private TableColumn<Supplier, String > suppliernamecolumn;
    @javafx.fxml.FXML
    private TableView<Supplier> suppliertable;
    @javafx.fxml.FXML
    private TableColumn<Supplier, Double> dueamountcolumn;
    @javafx.fxml.FXML
    private TableColumn<Supplier, Integer> supplieridcolumn;
    @javafx.fxml.FXML
    private TextField paymentamountTF;
    @javafx.fxml.FXML
    private ComboBox<String > paymentmethodCB;
    @javafx.fxml.FXML
    private TableColumn<Supplier, Integer> invoicenumbercolumn;

    private Supplier supplier;
    ObservableList<Supplier> list = FXCollections.observableArrayList();


    @javafx.fxml.FXML
    public void initialize() {
        supplieridcolumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        suppliernamecolumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        invoicenumbercolumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        dueamountcolumn.setCellValueFactory(new PropertyValueFactory<>("dueAmount"));

        paymentmethodCB.getItems().addAll("Cash", "Card", "Bank Transfer");

        list.add(new Supplier(20000,10001,"","Due",101,"Beximco"));
        list.add(new Supplier(30000,10002,"","Due",102,"ABC Pharma"));
        list.add(new Supplier(35000,10004,"","Due",103,"Square Pharma"));



        suppliertable.setItems(list);
    }

    @javafx.fxml.FXML
    public void selectbutton(ActionEvent actionEvent) {
        supplier = suppliertable.getSelectionModel().getSelectedItem();
    if (supplier == null) {

    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText("Please Select a Supplier");
    alert.showAndWait();
    return;
}
    dueamountlabel.setText(String.valueOf(supplier.getDueAmount()));
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

        paymentamountTF.clear();
        paymentmethodCB.setValue(null);
        dueamountlabel.setText("");
        suppliertable.getSelectionModel().clearSelection();
        supplier = null;
    }

    @javafx.fxml.FXML
    public void paybutton(ActionEvent actionEvent) {

        if (supplier == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select Supplier First");
            alert.showAndWait();
            return;
        }

        if (paymentamountTF.getText().isEmpty()
                || paymentmethodCB.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter Payment Amount and Select Payment Method");
            alert.showAndWait();
            return;
        }

        double paymentAmount = Double.parseDouble(paymentamountTF.getText());
        if (paymentAmount > supplier.getDueAmount()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Payment Amount Exceeds Due Amount");
            alert.showAndWait();
            return;
        }
        supplier.setPaymentMethod(paymentmethodCB.getValue());
        double remainingDue = supplier.getDueAmount() - paymentAmount;
        supplier.setDueAmount(remainingDue);

        if (remainingDue == 0) {
            supplier.setPaymentStatus("Paid");
        } else {
            supplier.setPaymentStatus("Partially Paid");
        }

        dueamountlabel.setText(String.valueOf(remainingDue));

        suppliertable.refresh();

        Alert receipt = new Alert(Alert.AlertType.INFORMATION);

        receipt.setHeaderText("Payment Receipt");

        receipt.setContentText(
                "Supplier ID : " + supplier.getSupplierId()
                        + "\nSupplier Name : " + supplier.getSupplierName()
                        + "\nInvoice No : " + supplier.getInvoiceNumber()
                        + "\nPayment Amount : " + paymentAmount
                        + "\nPayment Method : " + supplier.getPaymentMethod()
                        + "\nRemaining Due : " + remainingDue
                        + "\nPayment Status : " + supplier.getPaymentStatus()
                        + "\n\nSupplier Payment Successful"
        );

        receipt.showAndWait();
    }
}