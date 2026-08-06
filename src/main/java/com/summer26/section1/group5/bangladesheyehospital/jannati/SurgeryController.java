package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;

public class SurgeryController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private ComboBox<String> surgeryTypeComboBox;

    @FXML
    private TextField roomNumberTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private TableView<SurgeryScheduleModelClass> surgeryTableView;

    @FXML
    private TableColumn<SurgeryScheduleModelClass, Integer> patientIdColumn;

    @FXML
    private TableColumn<SurgeryScheduleModelClass, String> patientNameColumn;

    @FXML
    private TableColumn<SurgeryScheduleModelClass, String> surgeryTypeColumn;

    @FXML
    private TableColumn<SurgeryScheduleModelClass, String> roomColumn;

    @FXML
    private TableColumn<SurgeryScheduleModelClass, String> dateColumn;

    @FXML
    private TableColumn<SurgeryScheduleModelClass, String> timeColumn;

    @FXML
    private Label messageLabel;

    private final ArrayList<SurgeryScheduleModelClass> surgeryList =
            new ArrayList<>();

    private final File dataFolder = new File("data");

    private final File patientFile = new File(dataFolder, "patients.bin");

    private final File surgeryFile = new File(dataFolder, "surgerySchedule.bin");

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientNameTextField.setEditable(false);

        surgeryTypeComboBox.getItems().addAll("Cataract Surgery", "LASIK", "Retinal Surgery", "Corneal Transplant", "Glaucoma Surgery", "Vitrectomy");

        timeComboBox.getItems().addAll("09:00 AM", "11:00 AM", "01:00 PM", "03:00 PM", "05:00 PM");

        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));

        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        surgeryTypeColumn.setCellValueFactory(new PropertyValueFactory<>("surgeryType"));

        roomColumn.setCellValueFactory(new PropertyValueFactory<>("operationRoom"));

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("surgeryDate"));

        timeColumn.setCellValueFactory(new PropertyValueFactory<>("surgeryTime"));

        loadSchedules();
    }

    private void loadSchedules() {

        surgeryList.clear();
        surgeryTableView.getItems().clear();

        if (!surgeryFile.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(surgeryFile))) {

            while (true) {

                SurgeryScheduleModelClass schedule = (SurgeryScheduleModelClass) ois.readObject();

                surgeryList.add(schedule);
            }

        } catch (EOFException e) {

            // End of file reached

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to load schedules.");
        }

        surgeryTableView.getItems().addAll(surgeryList);
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

        if (!patientFile.exists()) {

            messageLabel.setText("No patient records found.");
            return;
        }

        boolean found = false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();

                if (patient.getPatientId() == patientId) {

                    patientNameTextField.setText(patient.getPatientName());

                    found = true;
                    break;
                }
            }

        } catch (EOFException e) {

            // End of file reached

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to search patient.");
            return;
        }

        if (found) {

            messageLabel.setText("Patient found.");

        } else {

            patientNameTextField.clear();
            messageLabel.setText("Patient not found.");
        }
    }

    @FXML
    public void doneButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().isEmpty() || patientNameTextField.getText().isEmpty() || surgeryTypeComboBox.getValue() == null || roomNumberTextField.getText().isEmpty() || datePicker.getValue() == null || timeComboBox.getValue() == null) {

            messageLabel.setText("Please fill all fields.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(patientIdTextField.getText());

        } catch (NumberFormatException e) {

            messageLabel.setText("Invalid Patient ID.");
            return;
        }

        int doctorId = 0;
        String doctorName = "";

        // Finding assigned doctor from patient record
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient = (PatientRecordModelClass) ois.readObject();

                if (patient.getPatientId() == patientId) {
                    doctorId = patient.getAssignedDoctorId();
                    doctorName = patient.getAssignedDoctor();
                    break;
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to read patient information.");
            return;
        }

        // Reading existing schedules
        ArrayList<SurgeryScheduleModelClass> scheduleList = new ArrayList<>();

        if (surgeryFile.exists()) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(surgeryFile))) {

                while (true) {

                    scheduleList.add((SurgeryScheduleModelClass) ois.readObject());

                }

            } catch (EOFException e) {

                // End of file

            } catch (IOException | ClassNotFoundException e) {

                e.printStackTrace();
            }
        }

        int surgeryId = scheduleList.size() + 1;

        SurgeryScheduleModelClass schedule = new SurgeryScheduleModelClass(surgeryId, patientId, patientNameTextField.getText(), doctorId, doctorName, surgeryTypeComboBox.getValue(), roomNumberTextField.getText(), datePicker.getValue().toString(), timeComboBox.getValue(), "Scheduled");

        scheduleList.add(schedule);


        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(surgeryFile))) {

            for (SurgeryScheduleModelClass s : scheduleList) {

                oos.writeObject(s);
            }

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to save surgery schedule.");
            return;
        }

        loadSchedules();

        messageLabel.setText("Surgery scheduled successfully.");

        clearButton(null);
    }

    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientIdTextField.clear();
        patientNameTextField.clear();

        surgeryTypeComboBox.setValue(null);
        roomNumberTextField.clear();

        datePicker.setValue(null);
        timeComboBox.setValue(null);

        messageLabel.setText("");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");


    }


}