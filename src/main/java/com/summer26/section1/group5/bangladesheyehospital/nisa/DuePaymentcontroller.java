package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;

public class DuePaymentcontroller
{
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass, String> statuscolumn;
    @javafx.fxml.FXML
    private TableView<PatientRecordModelClass> paymenttable;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass, Integer> patientidcolumn;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass, Double> totalbillcolumn;

    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    private final ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();



    @javafx.fxml.FXML
    public void initialize() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientidcolumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        totalbillcolumn.setCellValueFactory(new PropertyValueFactory<>("billAmount"));
        statuscolumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        loadPatients();
    }

    private void loadPatients() {

        patientList.clear();

        if (!patientFile.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                patientList.add(patient);
            }

        } catch (EOFException e) {

            // End of file

        } catch (Exception e) {

            e.printStackTrace();


        }
    }


    @javafx.fxml.FXML
    public void viewduepaymentbutton(ActionEvent actionEvent) {
        paymenttable.getItems().clear();

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPaymentStatus().equalsIgnoreCase("Unpaid")) {

                paymenttable.getItems().add(patient);
            }
        }

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