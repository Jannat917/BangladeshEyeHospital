package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MedicineSalesData {

    private static final ObservableList<MedicineSale> salesList =
            FXCollections.observableArrayList();

    private MedicineSalesData() {
    }

    public static ObservableList<MedicineSale> getSalesList() {
        return salesList;
    }

    public static void addSale(MedicineSale sale) {
        if (sale != null) {
            salesList.add(sale);
        }
    }
}