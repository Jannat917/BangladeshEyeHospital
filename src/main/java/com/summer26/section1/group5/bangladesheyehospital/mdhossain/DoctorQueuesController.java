package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorQueuesController {
    @FXML private ListView<String> doctorListView;
    @FXML private ListView<String> queueListView;
    @FXML private Label statusLabel;

    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    private List<PatientRecordModelClass> patientList = new ArrayList<>();

    @FXML
    public void initialize() {
        loadPatientsFromFile();
        populateDoctorListView();
    }

    private void loadPatientsFromFile() {
        patientList.clear();
        if (!patientFile.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {
            while (true) {
                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();
                patientList.add(patient);
            }
        } catch (EOFException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateDoctorListView() {
        doctorListView.getItems().clear();
        List<String> uniqueDoctors = new ArrayList<>();

        for (PatientRecordModelClass patient : patientList) {
            String doctor = patient.getAssignedDoctor();
            if (doctor != null && !doctor.isEmpty() && !uniqueDoctors.contains(doctor)) {
                uniqueDoctors.add(doctor);
            }
        }

        if (uniqueDoctors.isEmpty()) {
            doctorListView.getItems().add("No doctors assigned to patients");
        } else {
            doctorListView.getItems().addAll(uniqueDoctors);
        }
    }

    @FXML
    public void viewQueue(ActionEvent event) {
        String selected = doctorListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("ERROR: Select a doctor!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        queueListView.getItems().clear();

        if (patientList.isEmpty()) {
            queueListView.getItems().add("No patients in queue");
            statusLabel.setText("No patients registered.");
            statusLabel.setStyle("-fx-text-fill: #f39c12;");
            return;
        }

        int count = 1;
        for (PatientRecordModelClass patient : patientList) {
            String doctor = patient.getAssignedDoctor();
            if (doctor != null && doctor.equals(selected)) {
                queueListView.getItems().add(count + ". " + patient.getPatientName() + " (P-" + patient.getPatientId() + ")");
                count++;
            }
        }

        if (queueListView.getItems().isEmpty()) {
            queueListView.getItems().add("No patients assigned to this doctor");
            statusLabel.setText("No patients for: " + selected);
            statusLabel.setStyle("-fx-text-fill: #f39c12;");
        } else {
            statusLabel.setText("Queue loaded for: " + selected);
            statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
        }
    }

    @FXML
    public void callNext(ActionEvent event) {
        if (queueListView.getItems().isEmpty()) {
            statusLabel.setText("Queue is empty!");
            statusLabel.setStyle("-fx-text-fill: #f39c12;");
            return;
        }
        String next = queueListView.getItems().get(0);
        queueListView.getItems().remove(0);
        statusLabel.setText("Calling: " + next);
        statusLabel.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold;");
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/nurseDashboard.fxml");
    }
}