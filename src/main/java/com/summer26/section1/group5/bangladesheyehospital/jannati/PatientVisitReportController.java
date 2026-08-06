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

public class PatientVisitReportController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private TextField lastVisitDateTextField;

    @FXML
    private TextArea diseaseTextArea;

    @FXML
    private Label messageLabel;

    private final ArrayList<PatientRecordModelClass> patientList =
            new ArrayList<>();

    private final File dataFolder = new File("data");

    private final File patientFile = new File(dataFolder, "patients.bin");
    @FXML
    private TextField doctorIdTextField;
    @FXML
    private TextField doctorNameTextField;

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientNameTextField.setEditable(false);
        lastVisitDateTextField.setEditable(false);

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
        }
    }


    @FXML
    public void searchButton(ActionEvent actionEvent) {

        String id = patientIdTextField.getText().trim();

        if (id.isEmpty()) {

            messageLabel.setText("Enter Patient ID.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(id);

        } catch (NumberFormatException e) {

            messageLabel.setText("Patient ID must be numeric.");
            return;
        }

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                patientNameTextField.setText(patient.getPatientName());

                lastVisitDateTextField.setText(patient.getAppointmentDate());

                diseaseTextArea.setText(patient.getDisease());

               doctorIdTextField.setText(Integer.toString(patient.getAssignedDoctorId()));

               doctorNameTextField.setText(patient.getAssignedDoctor());

                messageLabel.setText("Patient report generated.");

                found = true;
                break;
            }
        }

        if (!found) {

            patientNameTextField.clear();
            lastVisitDateTextField.clear();

            diseaseTextArea.clear();
            doctorIdTextField.clear();
            doctorNameTextField.clear();

            messageLabel.setText("Patient not found.");
        }
    }
    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        patientNameTextField.clear();
        lastVisitDateTextField.clear();

        diseaseTextArea.clear();
        doctorNameTextField.clear();
        doctorIdTextField.clear();

        messageLabel.setText("");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {

        SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");




}
}