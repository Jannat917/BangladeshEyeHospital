package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class MedicineData {

    private static final ObservableList<Medicine> medicineList =
            FXCollections.observableArrayList();

    static {
        medicineList.add(
                new Medicine(
                        "MED-001",
                        "Lubricating Eye Drop",
                        "Eye Drop",
                        "BATCH-001",
                        50,
                        10,
                        250.00,
                        LocalDate.of(2027, 5, 20),
                        "ABC Pharmaceuticals"
                )
        );

        medicineList.add(
                new Medicine(
                        "MED-002",
                        "Antibiotic Eye Drop",
                        "Eye Drop",
                        "BATCH-002",
                        8,
                        10,
                        320.00,
                        LocalDate.of(2027, 2, 15),
                        "Dhaka Medicine Supplier"
                )
        );
    }

    private MedicineData() {
    }

    public static ObservableList<Medicine> getMedicineList() {
        return medicineList;
    }
}