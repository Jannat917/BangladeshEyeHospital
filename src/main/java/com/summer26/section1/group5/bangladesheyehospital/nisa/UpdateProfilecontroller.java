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

public class UpdateProfilecontroller {
    @javafx.fxml.FXML
    private TextField phoneTF;
    @javafx.fxml.FXML
    private TextField addressTF;
    @javafx.fxml.FXML
    private TextField patientnameTF;
    @javafx.fxml.FXML
    private TextField patientidTF;
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
    public void updatebutton(ActionEvent actionEvent) {
        if (patient == null) {

            messagelabel.setText("Search Patient First.");
            return;
        }

        patient.setPatientName(patientnameTF.getText());
        patient.setPhoneNumber(phoneTF.getText());
        patient.setAddress(addressTF.getText());

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass p : patientList) {

                oos.writeObject(p);
            }

            messagelabel.setText("Profile Updated Successfully.");

        } catch (IOException e) {

            e.printStackTrace();

            messagelabel.setText("Update Failed.");
        }



    }

    @javafx.fxml.FXML
    public void searchbutton(ActionEvent actionEvent) {

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

                patientnameTF.setText(p.getPatientName());
                phoneTF.setText(p.getPhoneNumber());
                addressTF.setText(p.getAddress());

                messagelabel.setText("Patient Found.");

                return;
            }
        }

        messagelabel.setText("Patient Not Found.");
    }



    @javafx.fxml.FXML
    public void clearbutton(ActionEvent actionEvent) {
        patientidTF.clear();
        patientnameTF.clear();
        phoneTF.clear();
        addressTF.clear();
        patient = null;
        messagelabel.setText("");


    }

    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {
        }
    }
}