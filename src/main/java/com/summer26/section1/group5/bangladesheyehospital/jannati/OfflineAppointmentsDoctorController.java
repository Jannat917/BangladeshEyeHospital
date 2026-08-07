package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class OfflineAppointmentsDoctorController {
    @FXML
    private TableColumn<AppointmentModelClass, Integer> doctorIdColumn;

    @FXML
    private TableColumn<AppointmentModelClass, Integer> patientIdColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> patientNameColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> dateColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> timeColumn;

    @FXML
    private TableColumn<AppointmentModelClass, String> statusColumn;

    @FXML
    private TableView<AppointmentModelClass> appointmentTableView;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField doctorIdTextField;

    @FXML
    private Label messageLabel;
    private final ArrayList<AppointmentModelClass> appointmentList =
            new ArrayList<>();

    private final File dataFolder = new File("data");

    private final File appointmentFile =
            new File(dataFolder, "appointments.bin");


    @FXML
    public void initialize() {

        // Create data folder if it does not exist
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        // Connect TableView columns with AppointmentModelClass properties
        patientIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientId"));

        patientNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientName"));

        doctorIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("doctorId"));

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentDate"));

        timeColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentTime"));

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentType"));

        // Add available time slots
        timeComboBox.getItems().addAll(
                "09:00 AM - 11:00 AM",
                "11:00 AM - 01:00 PM",
                "02:00 PM - 04:00 PM",
                "04:00 PM - 06:00 PM",
                "06:00 PM - 08:00 PM"
        );

        // Load offline appointments
        loadAppointments();
    }

    private void loadAppointments() {

        appointmentList.clear();
        appointmentTableView.getItems().clear();

        // Check whether appointment file exists
        if (!appointmentFile.exists()) {

            messageLabel.setText("No appointments found.");
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(appointmentFile))) {

            while (true) {

                AppointmentModelClass appointment =
                        (AppointmentModelClass) ois.readObject();

                // Only load OFFLINE appointments
                if ("Offline".equalsIgnoreCase(
                        appointment.getAppointmentType())) {

                    appointmentList.add(appointment);
                }
            }

        } catch (EOFException e) {

            // End of file reached normally

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText(
                    "Unable to load appointments.");
        }

        // Display loaded appointments in TableView
        appointmentTableView.getItems().addAll(appointmentList);

        // Display appropriate message
        if (appointmentList.isEmpty()) {

            messageLabel.setText(
                    "No offline appointments found.");

        } else {

            messageLabel.setText(
                    appointmentList.size()
                            + " offline appointment(s) found.");
        }
    }


    @FXML
    public void filterButton(ActionEvent actionEvent) {

        loadAppointments();

        appointmentTableView.getItems().clear();

        String doctorIdText = doctorIdTextField.getText().trim();
        LocalDate selectedDate = datePicker.getValue();
        String selectedTime = timeComboBox.getValue();

        // No filter selected
        if (doctorIdText.isEmpty()
                && selectedDate == null
                && (selectedTime == null || selectedTime.isEmpty())) {

            appointmentTableView.getItems().addAll(appointmentList);

            if (appointmentList.isEmpty()) {

                messageLabel.setText("No offline appointments found.");

            } else {

                messageLabel.setText(appointmentList.size() + " appointment(s) found.");
            }

            return;
        }

        int count = 0;
        Integer doctorId = null;

        if (!doctorIdText.isEmpty()) {

            try {

                doctorId = Integer.parseInt(doctorIdText);

            } catch (NumberFormatException e) {

                messageLabel.setText("Doctor ID must be numeric.");
                return;
            }
        }

        for (AppointmentModelClass appointment : appointmentList) {

            boolean match = true;

            // Doctor ID
            if (doctorId != null && appointment.getDoctorId() != doctorId) {

                match = false;
            }

            // Date
            if (selectedDate != null) {

                if (appointment.getAppointmentDate() == null
                        || !appointment.getAppointmentDate().equals(selectedDate)) {

                    match = false;
                }
            }

            // Time
            if (selectedTime != null && !selectedTime.isEmpty()) {

                if (appointment.getAppointmentTime() == null
                        || !appointment.getAppointmentTime().equals(selectedTime)) {

                    match = false;
                }
            }

            if (match) {

                appointmentTableView.getItems().add(appointment);
                count++;
            }
        }

        if (count == 0) {

            messageLabel.setText("No matching appointments found.");

        } else {

            messageLabel.setText(count + " appointment(s) found.");
        }
    }
    @FXML
    public void clearButton(ActionEvent actionEvent) {

        doctorIdTextField.clear();

        datePicker.setValue(null);

        timeComboBox.setValue(null);

        messageLabel.setText("");

        // Reload all offline appointments
        loadAppointments();
    }


    @FXML
    public void backButton(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo(
                    "jannati/doctorDashboard.fxml");

        } catch (IOException e) {

            e.printStackTrace();

            messageLabel.setText(
                    "Unable to open Doctor Dashboard.");
        }
    }
}