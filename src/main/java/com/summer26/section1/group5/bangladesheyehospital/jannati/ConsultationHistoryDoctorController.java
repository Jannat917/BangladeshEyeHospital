package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ConsultationHistoryDoctorController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private TextArea historyTextArea;

    @FXML
    private Label messageLabel;

    private final ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();

    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");

    @FXML
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

                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();

                patientList.add(patient);
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to load patient records.");
        }
    }

    @FXML
    public void searchButton(ActionEvent actionEvent) {

        loadPatients();

        String id = patientIdTextField.getText().trim();

        if (id.isEmpty()) {

            messageLabel.setText("Enter Patient ID.");

            patientNameTextField.clear();
            historyTextArea.clear();

            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(id);

        } catch (NumberFormatException e) {

            messageLabel.setText("Patient ID must be numeric.");

            patientNameTextField.clear();
            historyTextArea.clear();

            return;
        }

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {
            if (patient.getPatientId() == patientId) {

                patientNameTextField.setText(patient.getPatientName());
                historyTextArea.setText(
                        "Diagnosis : " + patient.getDiagnosis() +

                                "\n\nPrescription : " + patient.getPrescription() +

                                "\n\nDoctor Remarks : " + patient.getDoctorRemarks()
                );

                found = true;
                break;
            }
        }

        if (found) {

            messageLabel.setText("Consultation history loaded.");

        } else {

            messageLabel.setText("Patient not found.");

            patientNameTextField.clear();
            historyTextArea.clear();
        }
    }

    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        patientNameTextField.clear();
        historyTextArea.clear();
        messageLabel.setText("");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");


    }
}