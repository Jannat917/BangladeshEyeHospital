package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.DoctorModelClass;
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
    private final File doctorFile = new File(dataFolder, "doctors.bin");
    private final File patientFile = new File(dataFolder, "patients.bin");

    private List<DoctorModelClass> doctorList = new ArrayList<>();
    private List<PatientRecordModelClass> patientList = new ArrayList<>();

    @FXML
    public void initialize() {
        loadDoctorsFromFile();
        loadPatientsFromFile();
        populateDoctorListView();
    }

    private void loadDoctorsFromFile() {
        doctorList.clear();
        if (!doctorFile.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(doctorFile))) {
            while (true) {
                DoctorModelClass doctor = (DoctorModelClass) ois.readObject();
                doctorList.add(doctor);
            }
        } catch (EOFException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (doctorList.isEmpty()) {
            doctorListView.getItems().add("No doctors available");
            return;
        }
        for (DoctorModelClass d : doctorList) {
            String availability = d.getAvailability() != null ? d.getAvailability() : "Unknown";
            doctorListView.getItems().add(
                    d.getDoctorId() + " - " + d.getDoctorName() +
                            " (" + d.getSpecialization() + ") - " + availability
            );
        }
    }

    private List<String> getPatientQueue() {
        List<String> queue = new ArrayList<>();
        int count = 1;
        for (PatientRecordModelClass p : patientList) {
            queue.add(count + ". " + p.getPatientName() + " (P-" + p.getPatientId() + ")");
            count++;
        }
        return queue;
    }

    @FXML
    public void viewQueue(ActionEvent event) {
        String selected = doctorListView.getSelectionModel().getSelectedItem();
        if (selected == null || doctorList.isEmpty()) {
            statusLabel.setText("ERROR: Select a doctor!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        queueListView.getItems().clear();
        if (patientList.isEmpty()) {
            queueListView.getItems().add("No patients in queue");
            statusLabel.setText("No patients registered yet.");
            statusLabel.setStyle("-fx-text-fill: #f39c12;");
            return;
        }
        List<String> queue = getPatientQueue();
        queueListView.getItems().addAll(queue);
        statusLabel.setText("Queue loaded for: " + selected);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
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