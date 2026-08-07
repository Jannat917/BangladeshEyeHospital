package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
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
    private TableColumn<PatientRecordModelClass, Integer> doctorIdColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, Integer> patientIdColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> patientNameColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> dateColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> timeColumn;

    @FXML
    private TableColumn<PatientRecordModelClass, String> statusColumn;

    @FXML
    private TableView<PatientRecordModelClass> appointmentTableView;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField doctorIdTextField;

    @FXML
    private Label messageLabel;

    private final ArrayList<PatientRecordModelClass> appointmentList =
            new ArrayList<>();

    private final File dataFolder = new File("data");

    private final File patientFile =
            new File(dataFolder, "patients.bin");

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientId"));

        patientNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("patientName"));

        doctorIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("assignedDoctorId"));

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentDate"));

        timeColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentTime"));

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("appointmentStatus"));

        timeComboBox.getItems().addAll(
                "09:00 AM - 11:00 AM",
                "11:00 AM - 01:00 PM",
                "02:00 PM - 04:00 PM",
                "04:00 PM - 06:00 PM",
                "06:00 PM - 08:00 PM"
        );

        loadAppointments();
    }

    private void loadAppointments() {

        appointmentList.clear();
        appointmentTableView.getItems().clear();

        if (!patientFile.exists()) {

            messageLabel.setText("No patient records found.");
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                if ("Offline".equalsIgnoreCase(
                        patient.getAppointmentType())) {

                    appointmentList.add(patient);
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to load appointments.");
        }

        appointmentTableView.getItems().addAll(appointmentList);

        if (appointmentList.isEmpty()) {

            messageLabel.setText("No offline appointments found.");

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

                messageLabel.setText(
                        appointmentList.size()
                                + " appointment(s) found.");
            }

            return;
        }

        Integer doctorId = null;
        int count = 0;

        // Doctor ID
        if (!doctorIdText.isEmpty()) {

            try {

                doctorId = Integer.parseInt(doctorIdText);

            } catch (NumberFormatException e) {

                messageLabel.setText("Doctor ID must be numeric.");
                return;
            }
        }

        for (PatientRecordModelClass patient : appointmentList) {

            boolean match = true;

            // Doctor ID filter
            if (doctorId != null
                    && patient.getAssignedDoctorId() != doctorId) {

                match = false;
            }

            // Date filter
            if (selectedDate != null) {

                if (patient.getAppointmentDate() == null
                        || !patient.getAppointmentDate().equals(selectedDate.toString())) {

                    match = false;
                }
            }

            // Time filter
            if (selectedTime != null
                    && !selectedTime.isEmpty()) {

                if (patient.getAppointmentTime() == null
                        || !patient.getAppointmentTime().equals(selectedTime)) {

                    match = false;
                }
            }

            if (match) {

                appointmentTableView.getItems().add(patient);
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

        timeComboBox.getSelectionModel().clearSelection();

        appointmentTableView.getItems().clear();

        loadAppointments();

        messageLabel.setText("");
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