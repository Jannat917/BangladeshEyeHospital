package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;

public class Prescriptioncontroller
{
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass,String> doctornamecolumn;
    @javafx.fxml.FXML
    private TableView<PatientRecordModelClass> prescriptiontable;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass,String> diseasecolumn;
    @javafx.fxml.FXML
    private TextField patientidTF;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass,Integer> patientidcolumn;
    @javafx.fxml.FXML
    private TableColumn<PatientRecordModelClass, String> diagnosiscolumn;
    @javafx.fxml.FXML
    private Label messagelabel;

    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    private final ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();


    @javafx.fxml.FXML
    public void initialize() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientidcolumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        doctornamecolumn.setCellValueFactory(new PropertyValueFactory<>("assignedDoctor"));
        diseasecolumn.setCellValueFactory(new PropertyValueFactory<>("disease"));
        diagnosiscolumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));

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

            // End of File

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {
        }
    }

    @javafx.fxml.FXML
    public void searchbutton(ActionEvent actionEvent) {

        if (patientidTF.getText().isEmpty()) {

            messagelabel.setText("Please enter Patient ID.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(patientidTF.getText());

        } catch (NumberFormatException e) {

            messagelabel.setText("Patient ID must be numeric.");
            return;
        }

        prescriptiontable.getItems().clear();

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                prescriptiontable.getItems().add(patient);
                found = true;
                break;
            }
        }

        if (found) {

            messagelabel.setText("Prescription Found.");

        } else {

            messagelabel.setText("No Prescription Found.");
        }

        }





    @javafx.fxml.FXML
    public void refreshbutton(ActionEvent actionEvent) {

        patientidTF.clear();
        messagelabel.setText("");
        prescriptiontable.getItems().clear();

    }
}