package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.DoctorModelClass;
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
    @javafx.fxml.FXML
    private Label messageLabel;


    private final File dataFolder = new File("data");
    private final File patientFile = new File(dataFolder, "patients.bin");
    private final File doctorFile = new File(dataFolder, "doctors.bin");


    @javafx.fxml.FXML
    public void initialize() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        loadDoctorData();
    }


    private void loadDoctorData() {

        departmentCB.getItems().clear();
        doctorCB.getItems().clear();
        timeCB.getItems().clear();

        if (!doctorFile.exists()) {
            messageLabel.setText("Doctor data not found.");
            return;
        }

        ArrayList<DoctorModelClass> doctorList = new ArrayList<>();

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(doctorFile))) {

            while (true) {

                DoctorModelClass doctor = (DoctorModelClass) ois.readObject();

                doctorList.add(doctor);
            }

        } catch (EOFException e) {

        } catch (Exception e) {

            e.printStackTrace();
            messageLabel.setText("Unable to load doctor data.");
            return;
        }

        for (DoctorModelClass doctor : doctorList) {

            if (doctor.getDepartment() != null
                    && !departmentCB.getItems()
                    .contains(doctor.getDepartment())) {

                departmentCB.getItems()
                        .add(doctor.getDepartment());
            }
        }

        departmentCB.setOnAction(event -> {

            String selectedDepartment =
                    departmentCB.getValue();

            doctorCB.getItems().clear();
            timeCB.getItems().clear();

            if (selectedDepartment == null) {
                return;
            }

            for (DoctorModelClass doctor : doctorList) {

                if (selectedDepartment.equals(
                        doctor.getDepartment())) {

                    if (doctor.getDoctorName() != null
                            && !doctorCB.getItems()
                            .contains(doctor.getDoctorName())) {

                        doctorCB.getItems()
                                .add(doctor.getDoctorName());
                    }

                    if (doctor.getTime() != null
                            && !timeCB.getItems()
                            .contains(doctor.getTime())) {

                        timeCB.getItems()
                                .add(doctor.getTime());
                    }
                }
            }



        });
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

            messageLabel.setText(
                    "Please fill all fields."
            );

            return;
        }

        int patientId;

        try {

            patientId =
                    Integer.parseInt(
                            patientIdTF.getText()
                    );

        } catch (NumberFormatException e) {

            messageLabel.setText(
                    "Patient ID must be numeric."
            );

            return;
        }

        ArrayList<PatientRecordModelClass> patientList =
                new ArrayList<>();

        if (patientFile.exists()) {

            try (ObjectInputStream ois =
                         new ObjectInputStream(
                                 new FileInputStream(
                                         patientFile))) {

                while (true) {

                    PatientRecordModelClass patient =
                            (PatientRecordModelClass)
                                    ois.readObject();

                    patientList.add(patient);
                }

            } catch (EOFException e) {

            } catch (Exception e) {

                e.printStackTrace();

                messageLabel.setText(
                        "Unable to read patient data."
                );

                return;
            }
        }

        Random random = new Random();

        int serial =
                random.nextInt(100) + 1;

        boolean found = false;

        for (PatientRecordModelClass patient :
                patientList) {

            if (patient.getPatientId() == patientId) {

                patient.setDepartment(
                        departmentCB.getValue()
                );

                patient.setAssignedDoctor(
                        doctorCB.getValue()
                );

                patient.setAppointmentDate(
                        dateDatepicker
                                .getValue()
                                .toString()
                );

                patient.setAppointmentTime(
                        timeCB.getValue()
                );

                patient.setSerialNumber(
                        serial
                );

                found = true;

                break;
            }
        }

        if (!found) {

            messageLabel.setText(
                    "Patient ID not found."
            );

            return;
        }

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(
                                     patientFile))) {

            for (PatientRecordModelClass patient :
                    patientList) {

                oos.writeObject(patient);
            }

            serialLabel.setText(
                    String.valueOf(serial)
            );

            messageLabel.setText(
                    "Appointment booked successfully."
            );

        } catch (IOException e) {

            e.printStackTrace();

            messageLabel.setText(
                    "Unable to save appointment."
            );
        }

    }


    @javafx.fxml.FXML
    public void clearbutton(ActionEvent actionEvent) {
        patientIdTF.clear();

        departmentCB.setValue(null);

        doctorCB.getItems().clear();
        timeCB.getItems().clear();

        doctorCB.setValue(null);
        timeCB.setValue(null);

        dateDatepicker.setValue(null);

        serialLabel.setText("");

        messageLabel.setText("");


    }
}