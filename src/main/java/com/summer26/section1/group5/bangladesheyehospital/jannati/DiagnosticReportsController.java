package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.jannati.DiagnosticReportModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DiagnosticReportsController {

    @FXML
    private TextField patientIdTextField;

    @FXML
    private DatePicker reportDatePicker;

    @FXML
    private TextField testNameTextField;

    @FXML
    private TextArea diagnosisTextArea;

    @FXML
    private TextArea resultTextArea;

    @FXML
    private TableView<DiagnosticReportModelClass> reportTableView;

    @FXML
    private TableColumn<DiagnosticReportModelClass, LocalDate> dateColumn;

    @FXML
    private TableColumn<DiagnosticReportModelClass, String> testNameColumn;

    @FXML
    private TableColumn<DiagnosticReportModelClass, String> diagnosisColumn;

    @FXML
    private Label messageLabel;

    private final ArrayList<DiagnosticReportModelClass> reportList = new ArrayList<>();

    private final File dataFolder = new File("data");
    private final File reportFile = new File(dataFolder, "diagnosticReports.bin");

    @FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("reportDate"));

        testNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("testName"));

        diagnosisColumn.setCellValueFactory(
                new PropertyValueFactory<>("diagnosis"));


        loadReports();
    }



    private void loadReports() {

        reportList.clear();
        reportTableView.getItems().clear();

        if (!reportFile.exists()) {
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(reportFile))) {

            while (true) {

                DiagnosticReportModelClass report =
                        (DiagnosticReportModelClass) ois.readObject();

                reportList.add(report);
            }

        } catch (EOFException e) {

            // End of file

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to load reports.");
        }

        reportTableView.getItems().addAll(reportList);
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

        reportTableView.getItems().clear();

        boolean found = false;

        for (DiagnosticReportModelClass report : reportList) {

            if (report.getPatientId() == patientId) {

                reportTableView.getItems().add(report);
                found = true;
            }
        }

        if (found) {

            messageLabel.setText("Reports loaded successfully.");

        } else {

            messageLabel.setText("No reports found for this patient.");
        }
    }    @FXML

    public void saveReportButton(ActionEvent actionEvent) {

        String patientIdText = patientIdTextField.getText().trim();
        LocalDate reportDate = reportDatePicker.getValue();
        String testName = testNameTextField.getText().trim();
        String diagnosis = diagnosisTextArea.getText().trim();
        String result = resultTextArea.getText().trim();

        if (patientIdText.isEmpty()
                || reportDate == null
                || testName.isEmpty()
                || diagnosis.isEmpty()
                || result.isEmpty()) {

            messageLabel.setText("Please fill up all fields.");
            return;
        }

        int patientId;

        try {

            patientId = Integer.parseInt(patientIdText);

        } catch (NumberFormatException e) {

            messageLabel.setText("Patient ID must be numeric.");
            return;
        }




        String patientName = "";
        String doctorRemarks = "";

        DiagnosticReportModelClass report =
                new DiagnosticReportModelClass(
                        patientId,
                        patientName,
                        reportDate,
                        testName,
                        diagnosis,
                        result,
                        doctorRemarks,
                        ""
                );
        // Read existing reports
        ArrayList<DiagnosticReportModelClass> reports = new ArrayList<>();

        if (reportFile.exists()) {

            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(reportFile))) {

                while (true) {

                    reports.add((DiagnosticReportModelClass) ois.readObject());

                }

            } catch (EOFException e) {

                // End of file

            } catch (IOException | ClassNotFoundException e) {

                e.printStackTrace();
            }
        }

        // Add new report
        reports.add(report);

        // Rewrite the entire file
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(reportFile))) {

            for (DiagnosticReportModelClass r : reports) {

                oos.writeObject(r);

            }

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to save report.");
            return;
        }

        loadReports();

        messageLabel.setText("Report saved successfully.");

        clearFields();
    }

    private void clearFields() {

        reportDatePicker.setValue(null);
        testNameTextField.clear();
        diagnosisTextArea.clear();
        resultTextArea.clear();
    }

    @FXML
    public void backButton(ActionEvent actionEvent) {

        try {

            SceneSwitcher.switchTo("jannati/doctorDashboard.fxml");

        } catch (IOException e) {

            e.printStackTrace();
            messageLabel.setText("Unable to open dashboard.");
        }
    }

    @FXML
    public void clearButton(ActionEvent actionEvent) {

        patientIdTextField.clear();

        clearFields();

        reportTableView.getItems().clear();

        messageLabel.setText("");
    }

    @FXML
    public void viewReportButton(ActionEvent actionEvent) {

        DiagnosticReportModelClass report =
                reportTableView.getSelectionModel().getSelectedItem();

        if (report == null) {

            messageLabel.setText("Please select a report.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Diagnostic Report");
        alert.setHeaderText(report.getTestName());

        alert.setContentText(
                "Patient ID : " + report.getPatientId() +

                        "\nPatient Name : " + report.getPatientName() +

                        "\nReport Date : " + report.getReportDate() +

                        "\nTest Name : " + report.getTestName() +

                        "\nDiagnosis : " + report.getDiagnosis() +

                        "\nResult : " + report.getTestResult()

        );

        alert.showAndWait();
    }
}