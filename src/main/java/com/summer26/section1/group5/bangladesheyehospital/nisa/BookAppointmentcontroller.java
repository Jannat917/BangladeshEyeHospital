package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class BookAppointmentcontroller
{
    @javafx.fxml.FXML
    private ComboBox<String> timeCB;
    @javafx.fxml.FXML
    private ComboBox<String > doctorCB;
    @javafx.fxml.FXML
    private ComboBox<String > departmentCB;
    @javafx.fxml.FXML
    private TextField patientIdTF;
    @javafx.fxml.FXML
    private DatePicker dateDatepicker;
    @javafx.fxml.FXML
    private Label serialLabel;

    private final File dataFolder = new File("data");

    private final File patientFile = new File(dataFolder, "patients.bin");
    @javafx.fxml.FXML
    private Label messageLabel;

    @javafx.fxml.FXML
    public void initialize() {


        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        departmentCB.getItems().addAll(
                "Glaucoma",
                "Retina",
                "Cornea",
                "Cataract"
        );

        doctorCB.getItems().addAll(
                "Dr. Mehedi",
                "Dr. Rahim",
                "Dr. Karim"
        );

        timeCB.getItems().addAll(
                "8.00 AM",
                "9.00 AM",
                "10.00 AM",
                "12.00 PM",
                "3.00 PM"
        );
    }

    @javafx.fxml.FXML
    public void backbuttonOA(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {

            messageLabel.setText("Unable to open dashboard.");
        }
    }

    @javafx.fxml.FXML
    public void confirmbuttonOA(ActionEvent actionEvent) {
        if (patientIdTF.getText().isEmpty()
                || departmentCB.getValue() == null
                || doctorCB.getValue() == null
                || dateDatepicker.getValue() == null
                || timeCB.getValue() == null) {

            messageLabel.setText("Please fill all fields.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(patientIdTF.getText());

        } catch (NumberFormatException e) {

            messageLabel.setText("Patient ID must be numeric.");
            return;
        }

        ArrayList<PatientRecordModelClass> patientList =
                new ArrayList<>();

        if (patientFile.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {

                while (true) {

                    PatientRecordModelClass patient =
                            (PatientRecordModelClass) ois.readObject();

                    patientList.add(patient);
                }

            } catch (EOFException e) {

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        boolean found = false;

        for (PatientRecordModelClass patient : patientList) {

            if (patient.getPatientId() == patientId) {

                patient.setAssignedDoctor(doctorCB.getValue());
                patient.setAppointmentDate(dateDatepicker.getValue().toString());

                found = true;
                break;
            }
        }

        if (!found) {

            messageLabel.setText("Patient ID not found.");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass patient : patientList) {

                oos.writeObject(patient);
            }

            Random random = new Random();

            serialLabel.setText(
                    "Serial Number : " + (random.nextInt(100) + 1));

            messageLabel.setText("Appointment booked successfully.");

        } catch (IOException e) {

            e.printStackTrace();

            messageLabel.setText("Unable to save appointment.");
        }
    }

    @javafx.fxml.FXML
    public void clearbutton(ActionEvent actionEvent) {
        patientIdTF.clear();

        departmentCB.setValue(null);
        doctorCB.setValue(null);
        timeCB.setValue(null);

        dateDatepicker.setValue(null);

        serialLabel.setText("");
        messageLabel.setText("");

    }
}