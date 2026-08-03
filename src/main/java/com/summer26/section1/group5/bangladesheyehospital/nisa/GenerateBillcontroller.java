package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;

public class GenerateBillcontroller
{
    @javafx.fxml.FXML
    private TextField testfeeTF;
    @javafx.fxml.FXML
    private Label totalbilllabel;
    @javafx.fxml.FXML
    private TextField patientidTF;
    @javafx.fxml.FXML
    private TextField doctorfeeTF;

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
    public void calculatebutton(ActionEvent actionEvent) {
        if (doctorfeeTF.getText().isEmpty() || testfeeTF.getText().isEmpty()) {
            messagelabel.setText("Enter Doctor Fee and Test Fee.");
            return;
        }

        try {

            double doctorFee = Double.parseDouble(doctorfeeTF.getText());
            double testFee = Double.parseDouble(testfeeTF.getText());

            double total = doctorFee + testFee;

            totalbilllabel.setText(String.valueOf(total));

        } catch (NumberFormatException e) {

            messagelabel.setText("Fee must be numeric.");


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
        doctorfeeTF.clear();
        testfeeTF.clear();

        totalbilllabel.setText("");
        messagelabel.setText("");
    }




    @javafx.fxml.FXML
    public void generatebutton(ActionEvent actionEvent) {
        if (patientidTF.getText().isEmpty()) {

            messagelabel.setText("Enter Patient ID.");
            return;
        }

        try {

            int patientId = Integer.parseInt(patientidTF.getText());

            double total = Double.parseDouble(totalbilllabel.getText());

            boolean found = false;

            for (PatientRecordModelClass patient : patientList) {

                if (patient.getPatientId() == patientId) {

                    patient.setBillAmount(total);
                    patient.setPaymentStatus("Due");

                    found = true;
                    break;
                }
            }

            if (!found) {

                messagelabel.setText("Patient not found.");
                return;
            }

            try (ObjectOutputStream oos =
                         new ObjectOutputStream(new FileOutputStream(patientFile))) {

                for (PatientRecordModelClass patient : patientList) {

                    oos.writeObject(patient);
                }
            }

            messagelabel.setText("Bill Generated Successfully.");

        } catch (Exception e) {

            messagelabel.setText("Invalid input.");
        }



    }
}