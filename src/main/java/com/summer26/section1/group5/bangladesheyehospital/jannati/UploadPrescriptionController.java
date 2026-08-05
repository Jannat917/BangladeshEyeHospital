package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.PatientRecordModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.ArrayList;

public class UploadPrescriptionController {

    @FXML
    private ComboBox<String> patientTypeComboBox;

    @FXML
    private TextField patientIdTextField;

    @FXML
    private TextField patientNameTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextArea diagnosisTextArea;

    @FXML
    private TextArea prescriptionTextArea;

    @FXML
    private TextArea recommendationTextArea;

    @FXML
    private Label messageLabel;

    private final File dataFolder = new File("data");

    private final File patientFile =
            new File(dataFolder, "patients.bin");

    private final File prescriptionFile =
            new File(dataFolder, "prescriptions.bin");

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        patientTypeComboBox.getItems().addAll(
                "New Patient",
                "Existing Patient"
        );

        genderComboBox.getItems().addAll(
                "Male",
                "Female",
                "Other"
        );

        patientNameTextField.setDisable(true);
        ageTextField.setDisable(true);
        genderComboBox.setDisable(true);
        phoneTextField.setDisable(true);
        addressTextField.setDisable(true);

        patientIdTextField.setDisable(true);

        messageLabel.setText("");
    }

    @FXML
    public void patientTypeComboBoxOnAction(ActionEvent actionEvent) {

        String type = patientTypeComboBox.getValue();

        clearPatientFields();

        if (type.equals("New Patient")) {

            patientIdTextField.clear();
            patientIdTextField.setDisable(true);

            patientNameTextField.setDisable(false);
            ageTextField.setDisable(false);
            genderComboBox.setDisable(false);
            phoneTextField.setDisable(false);
            addressTextField.setDisable(false);

        } else {

            patientIdTextField.setDisable(false);

            patientNameTextField.setDisable(true);
            ageTextField.setDisable(true);
            genderComboBox.setDisable(true);
            phoneTextField.setDisable(true);
            addressTextField.setDisable(true);
        }
    }

    private int generatePatientId() {

        int id = 5001;

        if (!patientFile.exists()) {
            return id;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(patientFile))) {

            while (true) {

                PatientRecordModelClass patient =
                        (PatientRecordModelClass) ois.readObject();

                if (patient.getPatientId() >= id) {

                    id = patient.getPatientId() + 1;
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (Exception e) {

            e.printStackTrace();
        }

        return id;
    }

    private int generatePrescriptionId() {

        int id = 10001;

        if (!prescriptionFile.exists()) {
            return id;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(prescriptionFile))) {

            while (true) {

                PrescriptionModelClass prescription =
                        (PrescriptionModelClass) ois.readObject();

                if (prescription.getPrescriptionId() >= id) {

                    id = prescription.getPrescriptionId() + 1;
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (Exception e) {

            e.printStackTrace();
        }

        return id;
    }


    @FXML
    public void searchButton(ActionEvent actionEvent) {

        if (patientIdTextField.getText().isEmpty()) {

            messageLabel.setText("Enter Patient ID.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(patientIdTextField.getText());

        } catch (NumberFormatException e) {

            messageLabel.setText("Patient ID must be numeric.");
            return;
        }

        if (!patientFile.exists()) {

            messageLabel.setText("Patient records not found.");
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

                    ageTextField.setText(
                            String.valueOf(patient.getAge()));

                    genderComboBox.setValue(
                            patient.getGender());

                    phoneTextField.setText(
                            patient.getPhoneNumber());

                    addressTextField.setText(
                            patient.getAddress());

                    found = true;
                    break;
                }
            }

        } catch (EOFException e) {

            // End of file

        } catch (Exception e) {

            e.printStackTrace();
            messageLabel.setText("Unable to read patient file.");
            return;
        }

        if (found) {

            messageLabel.setText("Patient found.");

        } else {

            messageLabel.setText("Patient not found.");
            clearPatientFields();
        }
    }


    private void clearPatientFields() {

        patientIdTextField.clear();

        patientNameTextField.clear();

        ageTextField.clear();

        genderComboBox.setValue(null);

        phoneTextField.clear();

        addressTextField.clear();

        diagnosisTextArea.clear();

        prescriptionTextArea.clear();

        recommendationTextArea.clear();
    }


    private String generatePassword(int patientId) {

        return "P" + patientId;
    }


    @FXML
    public void saveButton(ActionEvent actionEvent) {

        if (patientTypeComboBox.getValue() == null) {

            messageLabel.setText("Select Patient Type.");
            return;
        }

        if (patientNameTextField.getText().isEmpty()
                || ageTextField.getText().isEmpty()
                || genderComboBox.getValue() == null
                || phoneTextField.getText().isEmpty()
                || addressTextField.getText().isEmpty()
                || diagnosisTextArea.getText().isEmpty()
                || prescriptionTextArea.getText().isEmpty()
                || recommendationTextArea.getText().isEmpty()) {

            messageLabel.setText("Please fill all required fields.");
            return;
        }

        ArrayList<PatientRecordModelClass> patientList =
                new ArrayList<>();

        ArrayList<PrescriptionModelClass> prescriptionList =
                new ArrayList<>();

        /* ---------------- Read Existing Patients ---------------- */

        if (patientFile.exists()) {

            try (ObjectInputStream ois =
                         new ObjectInputStream(
                                 new FileInputStream(patientFile))) {

                while (true) {

                    patientList.add(
                            (PatientRecordModelClass) ois.readObject());
                }

            } catch (EOFException e) {

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        /* ---------------- Read Existing Prescriptions ---------------- */

        if (prescriptionFile.exists()) {

            try (ObjectInputStream ois =
                         new ObjectInputStream(
                                 new FileInputStream(prescriptionFile))) {

                while (true) {

                    prescriptionList.add(
                            (PrescriptionModelClass) ois.readObject());
                }

            } catch (EOFException e) {

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        int patientId;

        String patientName;

    /* ==========================================================
                       EXISTING PATIENT
       ========================================================== */

        if (patientTypeComboBox.getValue().equals("Existing Patient")) {

            patientId = Integer.parseInt(patientIdTextField.getText());

            boolean found = false;

            for (PatientRecordModelClass patient : patientList) {

                if (patient.getPatientId() == patientId) {

                    patient.setDiagnosis(
                            diagnosisTextArea.getText());

                    patient.setPrescription(
                            prescriptionTextArea.getText());

                    patient.setDoctorRemarks(
                            recommendationTextArea.getText());

                    patientName = patient.getPatientName();

                    PrescriptionModelClass prescription =
                            new PrescriptionModelClass(

                                    generatePrescriptionId(),

                                    patient.getPatientId(),

                                    patientName,

                                    0,

                                    "",

                                    patient.getAppointmentDate(),

                                    diagnosisTextArea.getText(),

                                    prescriptionTextArea.getText(),

                                    recommendationTextArea.getText()
                            );

                    prescriptionList.add(prescription);

                    found = true;

                    break;
                }
            }

            if (!found) {

                messageLabel.setText("Patient not found.");
                return;
            }

        }

    /* ==========================================================
                           NEW PATIENT
       ========================================================== */

        else {

            patientId = generatePatientId();

            patientName = patientNameTextField.getText();

            PatientRecordModelClass patient =
                    new PatientRecordModelClass(

                            patientId,
                            generatePassword(patientId),
                            patientName,
                            Integer.parseInt(ageTextField.getText()),
                            genderComboBox.getValue(),
                            phoneTextField.getText(),
                            addressTextField.getText(),

                            "",                     // appointmentDate
                            "",                     // appointmentTime
                            "",                     // department
                            "",                     // assignedDoctor
                            0,                      // assignedDoctorId
                            0,                      // serialNumber

                            "",                     // disease
                            diagnosisTextArea.getText(),
                            prescriptionTextArea.getText(),
                            "",                     // testReports
                            recommendationTextArea.getText(),

                            "",                     // eyePowerPrescription
                            "",                     // lensType
                            "",                     // glassesRecommendation

                            0.0,                    // doctorFee
                            0.0,                    // testFee
                            0.0,                    // billAmount

                            "Unpaid",
                            ""                      // appointmentType
                    );;

            patientList.add(patient);

            PrescriptionModelClass prescription =
                    new PrescriptionModelClass(

                            generatePrescriptionId(),

                            patientId,

                            patientName,

                            0,

                            "",

                            "",

                            diagnosisTextArea.getText(),

                            prescriptionTextArea.getText(),

                            recommendationTextArea.getText()
                    );

            prescriptionList.add(prescription);
        }

        /* ---------------- Save Patients ---------------- */

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(patientFile))) {

            for (PatientRecordModelClass patient : patientList) {

                oos.writeObject(patient);
            }

        } catch (IOException e) {

            e.printStackTrace();

            messageLabel.setText("Unable to save patient.");

            return;
        }

        /* ---------------- Save Prescriptions ---------------- */

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(prescriptionFile))) {

            for (PrescriptionModelClass prescription : prescriptionList) {

                oos.writeObject(prescription);
            }

        } catch (IOException e) {

            e.printStackTrace();

            messageLabel.setText("Unable to save prescription.");

            return;
        }

        messageLabel.setText("Prescription saved successfully.");

        clearButton(null);
    }

    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientTypeComboBox.setValue(null);

        clearPatientFields();

        patientNameTextField.setDisable(true);
        ageTextField.setDisable(true);
        genderComboBox.setDisable(true);
        phoneTextField.setDisable(true);
        addressTextField.setDisable(true);

        patientIdTextField.setDisable(true);

        messageLabel.setText("");
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {


        SceneSwitcher.switchTo(
                "jannati/doctorDashboard.fxml");


    }
}