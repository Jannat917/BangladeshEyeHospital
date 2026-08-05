package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;

public class ReceivePaymentcontroller {
    @javafx.fxml.FXML
    private Label billLabel;
    @javafx.fxml.FXML
    private TextField patientidTF;
    @javafx.fxml.FXML
    private Label statusLabel;
    @javafx.fxml.FXML
    private Label massagelabel;

    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");
    private final ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();

    private PatientRecordModelClass patient;



    @javafx.fxml.FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        loadPatients();
    }

    private void loadPatients() {

        patientList.clear();

        if (!patientFile.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass p =
                        (PatientRecordModelClass) ois.readObject();

                patientList.add(p);
            }

        } catch (EOFException e) {

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    @javafx.fxml.FXML
    public void receivepaymentbutton(ActionEvent actionEvent) {
        if (patient == null) {
            massagelabel.setText("Search Patient First.");
            return;
        }

        patient.setPaymentStatus("Paid");
        statusLabel.setText("Paid");

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass p : patientList) {

                oos.writeObject(p);
            }

            massagelabel.setText("Payment Received Successfully.");

        } catch (IOException e) {

            e.printStackTrace();

            massagelabel.setText("Unable to update payment.");

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
        patientidTF.clear();
        billLabel.setText("");
        statusLabel.setText("");
        massagelabel.setText("");

        patient = null;
    }




    @javafx.fxml.FXML
    public void searchbutton(ActionEvent actionEvent) {

        if (patientidTF.getText().isEmpty()) {

            massagelabel.setText("Enter Patient ID.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(patientidTF.getText());

        } catch (NumberFormatException e) {

            massagelabel.setText("Patient ID must be numeric.");
            return;
        }

        patient = null;

        for (PatientRecordModelClass p : patientList) {

            if (p.getPatientId() == patientId) {

                patient = p;

                billLabel.setText(String.valueOf(p.getBillAmount()));
                statusLabel.setText(p.getPaymentStatus());

                massagelabel.setText("Patient Found.");
                return;
            }
        }

        massagelabel.setText("Patient Not Found.");

    }
}