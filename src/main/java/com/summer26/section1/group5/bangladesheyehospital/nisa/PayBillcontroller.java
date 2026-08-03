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

public class PayBillcontroller {
    @javafx.fxml.FXML
    private Label totalbilllabel;
    @javafx.fxml.FXML
    private Label doctorfeelabel;
    @javafx.fxml.FXML
    private TextField patientidTF;
    @javafx.fxml.FXML
    private Label testfeelabel;
    @javafx.fxml.FXML
    private Label messagelabel;
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

                PatientRecordModelClass p = (PatientRecordModelClass) ois.readObject();

                patientList.add(p);
            }

        } catch (EOFException e) {

            // End of file

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @javafx.fxml.FXML
    public void calculatebutton(ActionEvent actionEvent) {
        if (patient == null) {

            messagelabel.setText("Search patient first.");
            return;
        }

        totalbilllabel.setText(String.valueOf(patient.getBillAmount()));


    }

    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {
        }
    }

    @javafx.fxml.FXML
    public void paybutton(ActionEvent actionEvent) {
        if (patient == null) {

            messagelabel.setText("Search patient first.");
            return;
        }

        patient.setPaymentStatus("Paid");

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass p : patientList) {

                oos.writeObject(p);
            }

            messagelabel.setText("Payment Successful.");

        } catch (IOException e) {

            e.printStackTrace();
            messagelabel.setText("Payment failed.");
        }



    }

    @javafx.fxml.FXML
    public void enterbutton(ActionEvent actionEvent) {
        if (patientidTF.getText().isEmpty()) {

            messagelabel.setText("Enter Patient ID.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(patientidTF.getText());

        } catch (NumberFormatException e) {

            messagelabel.setText("Patient ID must be numeric.");
            return;
        }

        patient = null;

        for (PatientRecordModelClass p : patientList) {

            if (p.getPatientId() == patientId) {

                patient = p;

                double doctorFee = p.getBillAmount() * 0.40;
                double testFee = p.getBillAmount() * 0.30;

                doctorfeelabel.setText(String.valueOf(doctorFee));
                testfeelabel.setText(String.valueOf(testFee));

                messagelabel.setText("Patient Found.");

                return;
            }
        }

        messagelabel.setText("Patient ID not found.");
    }

}



