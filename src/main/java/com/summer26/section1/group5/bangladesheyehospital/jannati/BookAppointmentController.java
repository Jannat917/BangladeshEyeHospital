package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.DoctorModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;

public class BookAppointmentController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private ComboBox<String> doctorComboBox;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private ComboBox<String> appointmentTimeComboBox;

    @FXML
    private ComboBox<String> appointmentTypeComboBox;

    @FXML
    private Label messageLabel;

    private final File dataFolder = new File("data");

    private final File patientFile =
            new File(dataFolder, "patients.bin");

    private final File doctorFile =
            new File(dataFolder, "doctors.bin");

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientNameTextField.setEditable(false);

        departmentComboBox.getItems().addAll(
                "General Eye",
                "Cornea",
                "Retina",
                "Glaucoma",
                "Pediatric Eye",
                "Optometry"
        );

        appointmentTypeComboBox.getItems().addAll(
                "Online",
                "Offline"
        );

        appointmentTimeComboBox.getItems().addAll(
                "09:00 AM - 11:00 AM",
                "11:00 AM - 01:00 PM",
                "02:00 PM - 04:00 PM",
                "04:00 PM - 06:00 PM",
                "06:00 PM - 08:00 PM"
        );

        loadDoctors();

        messageLabel.setText("");
    }

    @FXML
    private void loadDoctors() {

        doctorComboBox.getItems().clear();

        if (!doctorFile.exists()) {
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(doctorFile))) {

            while (true) {

                DoctorModelClass doctor =
                        (DoctorModelClass) ois.readObject();

                doctorComboBox.getItems().add(
                        doctor.getDoctorId()
                                + " - "
                                + doctor.getDoctorName());
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }



    public void searchPatientButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().trim().isEmpty()) {

            messageLabel.setText("Enter Patient ID.");
            return;
        }

        int patientId;

        try {

            patientId =
                    Integer.parseInt(patientIdTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Invalid Patient ID.");
            return;
        }

        if (!patientFile.exists()) {

            messageLabel.setText("No patient records found.");
            return;
        }

        boolean found = false;

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                if (patient.getPatientId() == patientId) {

                    patientNameTextField.setText(
                            patient.getPatientName());

                    messageLabel.setText("Patient found.");

                    found = true;
                    break;
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to read patient data.");
            return;
        }

        if (!found) {

            patientNameTextField.clear();
            messageLabel.setText("Patient not found.");
        }
    }


    @FXML
    public void confirmButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().trim().isEmpty()
                || patientNameTextField.getText().trim().isEmpty()
                || departmentComboBox.getValue() == null
                || doctorComboBox.getValue() == null
                || appointmentDatePicker.getValue() == null
                || appointmentTimeComboBox.getValue() == null
                || appointmentTypeComboBox.getValue() == null) {

            messageLabel.setText("Please fill all fields.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(patientIdTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Invalid Patient ID.");
            return;
        }

        ArrayList<PatientRecordModelClass> patientList = new ArrayList<>();

        boolean found = false;

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                if (patient.getPatientId() == patientId) {

                    patient.setAppointmentDate(
                            appointmentDatePicker.getValue().toString());

                    patient.setAppointmentTime(
                            appointmentTimeComboBox.getValue());

                    patient.setDepartment(
                            departmentComboBox.getValue());

                    String selectedDoctor = doctorComboBox.getValue();

                    String[] doctorInfo = selectedDoctor.split(" - ");

                    patient.setAssignedDoctorId(
                            Integer.parseInt(doctorInfo[0]));

                    patient.setAssignedDoctor(
                            doctorInfo[1]);

                    patient.setAppointmentType(
                            appointmentTypeComboBox.getValue());

                    patient.setAppointmentStatus("Pending");

                    found = true;
                }

                patientList.add(patient);
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to read patient data.");
            return;
        }

        if (!found) {

            messageLabel.setText("Patient not found.");
            return;
        }

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass patient : patientList) {

                oos.writeObject(patient);
            }

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to save appointment.");
            return;
        }

        messageLabel.setText("Appointment booked successfully!");

        clearButton(null);
    }

    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        patientNameTextField.clear();

        departmentComboBox.getSelectionModel().clearSelection();
        doctorComboBox.getSelectionModel().clearSelection();

        appointmentDatePicker.setValue(null);

        appointmentTimeComboBox.getSelectionModel().clearSelection();
        appointmentTypeComboBox.getSelectionModel().clearSelection();

        messageLabel.setText("");

        patientIdTextField.requestFocus();
    }

    @FXML
    public void backButton(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to open Receptionist Dashboard.");
        }
    }
}


