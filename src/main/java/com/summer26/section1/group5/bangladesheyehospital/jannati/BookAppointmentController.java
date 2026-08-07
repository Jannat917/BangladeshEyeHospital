package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.DoctorModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toBinaryString;

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

    private final File appointmentFile =
            new File(dataFolder, "appointments.bin");

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

    private void loadDoctors() {

        doctorComboBox.getItems().clear();

        if (!doctorFile.exists()) {
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(doctorFile))) {

            while (true) {

                DoctorModelClass doctor =
                        (DoctorModelClass) ois.readObject();

                doctorComboBox.getItems().add(
                        doctor.getDoctorId()
                                + " - "
                                + doctor.getDoctorName()
                );
            }

        } catch (EOFException e) {

            // End of file

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private String generateAppointmentId() {

        int id = 7000;

        if (!appointmentFile.exists()) {
            return "A7001";
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(appointmentFile))) {

            while (true) {

                AppointmentModelClass appointment =
                        (AppointmentModelClass) ois.readObject();

                int currentId =
                        Integer.parseInt(
                                appointment.getAppointmentId().replace("A", "")
                        );

                if (currentId > id) {
                    id = currentId;
                }
            }

        } catch (EOFException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "A" + (id + 1);
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

            patientId = parseInt(patientIdTextField.getText().trim());

        } catch (NumberFormatException e) {

            messageLabel.setText("Invalid Patient ID.");
            return;
        }

        ArrayList<AppointmentModelClass> appointmentList = new ArrayList<>();

        boolean found = false;

        if (!patientFile.exists()) {

            messageLabel.setText("No patient records found.");
            return;
        }





        // Read patient information
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                if (patient.getPatientId() == patientId) {

                    AppointmentModelClass appointment =
                            new AppointmentModelClass();

                    appointment.setPatientId(String.valueOf(patient.getPatientId()));
                    appointment.setPatientName(patient.getPatientName());

                    appointment.setAppointmentId(generateAppointmentId());

                    appointment.setAppointmentDate(
                            appointmentDatePicker.getValue());

                    appointment.setAppointmentTime(
                            appointmentTimeComboBox.getValue());

                    String selectedDoctor = doctorComboBox.getValue();

                    String[] doctorInfo = selectedDoctor.split(" - ");

                    appointment.setDoctorId(
                            parseInt(doctorInfo[0]));

                    appointment.setDoctorName(doctorInfo[1]);

                    appointment.setAppointmentType(
                            appointmentTypeComboBox.getValue());

                    appointment.setAppointmentStatus("Pending");

                    appointmentList.add(appointment);

                    found = true;

                    break;
                }
            }

        } catch (EOFException e) {

            // End of file reached

        } catch (Exception e) {

            e.printStackTrace();
            messageLabel.setText("Unable to read patient data.");
            return;
        }

        if (!found) {

            messageLabel.setText("Patient not found.");
            return;
        }

        // Load old appointments if the file already exists
        if (appointmentFile.exists()) {

            ArrayList<AppointmentModelClass> oldAppointments =
                    new ArrayList<>();

            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(appointmentFile))) {

                while (true) {

                    AppointmentModelClass appointment =
                            (AppointmentModelClass) ois.readObject();

                    oldAppointments.add(appointment);
                }

            } catch (EOFException e) {

                // End of file

            } catch (Exception e) {

                e.printStackTrace();
            }

            oldAppointments.addAll(appointmentList);

            appointmentList = oldAppointments;
        }

        // Save all appointments
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(appointmentFile))) {

            for (AppointmentModelClass appointment : appointmentList) {

                oos.writeObject(appointment);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Booked");
            alert.setHeaderText(null);
            alert.setContentText("Appointment booked successfully!");
            alert.showAndWait();

            clearButton(null);

        } catch (IOException e) {

            e.printStackTrace();

            messageLabel.setText("Unable to save appointment.");
        }
    }
        @FXML
        public void clearButton (ActionEvent actionEvent){

            patientIdTextField.clear();
            patientNameTextField.clear();

            departmentComboBox.setValue(null);
            doctorComboBox.setValue(null);

            appointmentDatePicker.setValue(null);
            appointmentTimeComboBox.setValue(null);
            appointmentTypeComboBox.setValue(null);

            messageLabel.setText("");
        }

        @FXML
        public void backButton (ActionEvent actionEvent){

            try {

                SceneSwitcher.switchTo("jannati/receiptionistDashboard.fxml");

            } catch (IOException e) {

                e.printStackTrace();
                messageLabel.setText("Unable to open Receptionist Dashboard.");
            }
        }

    @FXML

        public void searchPatientButton(ActionEvent actionEvent) {

            if (patientIdTextField.getText().trim().isEmpty()) {

                messageLabel.setText("Enter Patient ID.");
                return;
            }

            int patientId;

            try {

                patientId = Integer.parseInt(patientIdTextField.getText().trim());

            } catch (NumberFormatException e) {

                messageLabel.setText("Patient ID must be numeric.");
                return;
            }

            if (!patientFile.exists()) {

                messageLabel.setText("No patient records found.");
                return;
            }

            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(patientFile))) {

                while (true) {

                    PatientRecordModelClass patient =
                            (PatientRecordModelClass) ois.readObject();

                    if (patient.getPatientId() == patientId) {

                        patientNameTextField.setText(patient.getPatientName());

                        messageLabel.setText("Patient found.");

                        return;
                    }
                }

            } catch (EOFException e) {

                // Patient not found

            } catch (Exception e) {

                e.printStackTrace();
                messageLabel.setText("Unable to read patient data.");
                return;
            }

            patientNameTextField.clear();
            messageLabel.setText("Patient not found.");
        }
    }



