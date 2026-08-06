package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MashrifData {

    private static final ObservableList<Medicine> medicines =
            FXCollections.observableArrayList();

    private static final ObservableList<MedicineSale> medicineSales =
            FXCollections.observableArrayList();

    private static final ObservableList<PharmacyPrescription>
            pharmacyPrescriptions =
            FXCollections.observableArrayList();

    private static final ObservableList<OpticalPrescription>
            opticalPrescriptions =
            FXCollections.observableArrayList();

    private static final ObservableList<OpticalFrame> opticalFrames =
            FXCollections.observableArrayList();

    private static final ObservableList<EyeglassOrder> eyeglassOrders =
            FXCollections.observableArrayList();

    private static final ObservableList<OpticalSale> opticalSales =
            FXCollections.observableArrayList();

    public static ObservableList<Medicine> getMedicines() {
        return medicines;
    }

    public static ObservableList<MedicineSale> getMedicineSales() {
        return medicineSales;
    }

    public static ObservableList<PharmacyPrescription>
    getPharmacyPrescriptions() {
        return pharmacyPrescriptions;
    }

    public static ObservableList<OpticalPrescription>
    getOpticalPrescriptions() {
        return opticalPrescriptions;
    }

    public static ObservableList<OpticalFrame> getOpticalFrames() {
        return opticalFrames;
    }

    public static ObservableList<EyeglassOrder> getEyeglassOrders() {
        return eyeglassOrders;
    }

    public static ObservableList<OpticalSale> getOpticalSales() {
        return opticalSales;
    }
}