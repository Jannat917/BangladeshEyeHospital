
package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorQueuesController implements Initializable {
    @FXML private ComboBox<String> doctorComboBox;
    @FXML private ListView<QueuePatient> patientQueueListView;
    @FXML private TextArea patientDetailsArea;
    @FXML private Label queueStatsLabel;
    @FXML private Label statusLabel;
    @FXML private Button callNextBtn;
    @FXML private Button refreshBtn;
    @FXML private Button resetBtn;
    @FXML private Button backBtn;

    private ObservableList<QueuePatient> queuePatients = FXCollections.observableArrayList();
    private ObservableList<String> doctors = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        doctors.addAll("Dr. Ahmed - Ophthalmology", "Dr. Rahman - Retina Specialist",
                "Dr. Khan - Cornea Specialist", "Dr. Ali - Glaucoma Specialist");
        doctorComboBox.setItems(doctors);
        loadSampleQueue();
        patientQueueListView.setItems(queuePatients);
        patientQueueListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayPatientDetails(newSelection);
            }
        });
        updateQueueStats();
    }

    private void loadSampleQueue() {
        queuePatients.add(new QueuePatient("P001", "John Doe", "Waiting", "10:00 AM"));
        queuePatients.add(new QueuePatient("P002", "Jane Smith", "Waiting", "10:15 AM"));
        queuePatients.add(new QueuePatient("P003", "Robert Johnson", "In Progress", "10:30 AM"));
        queuePatients.add(new QueuePatient("P004", "Mary Williams", "Waiting", "10:45 AM"));
        queuePatients.add(new QueuePatient("P005", "James Brown", "Completed", "11:00 AM"));
    }

    private void displayPatientDetails(QueuePatient patient) {
        patientDetailsArea.setText(
                "Patient ID: " + patient.getPatientId() + "\n" +
                        "Name: " + patient.getName() + "\n" +
                        "Status: " + patient.getStatus() + "\n" +
                        "Arrival Time: " + patient.getArrivalTime() + "\n" +
                        "Doctor: " + doctorComboBox.getValue() + "\n" +
                        "Queue Position: " + (queuePatients.indexOf(patient) + 1)
        );
    }

    private void updateQueueStats() {
        long waiting = queuePatients.stream().filter(p -> "Waiting".equals(p.getStatus())).count();
        long inProgress = queuePatients.stream().filter(p -> "In Progress".equals(p.getStatus())).count();
        long completed = queuePatients.stream().filter(p -> "Completed".equals(p.getStatus())).count();
        queueStatsLabel.setText("Total: " + queuePatients.size() +
                " | Waiting: " + waiting +
                " | In Progress: " + inProgress +
                " | Completed: " + completed);
    }

    @FXML
    private void handleDoctorSelection() {
        if (doctorComboBox.getValue() != null) {
            statusLabel.setText("Status: Selected doctor - " + doctorComboBox.getValue());
            statusLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
        }
    }

    @FXML
    private void handleCallNextPatient() {
        QueuePatient nextPatient = queuePatients.stream()
                .filter(p -> "Waiting".equals(p.getStatus()))
                .findFirst()
                .orElse(null);
        if (nextPatient != null) {
            nextPatient.setStatus("In Progress");
            patientQueueListView.refresh();
            updateQueueStats();
            statusLabel.setText("Status: Called patient - " + nextPatient.getName());
            statusLabel.setStyle("-fx-text-fill: #FF9800; -fx-font-weight: bold;");
            displayPatientDetails(nextPatient);
        } else {
            statusLabel.setText("Status: No waiting patients in queue!");
            statusLabel.setStyle("-fx-text-fill: #F44336; -fx-font-weight: bold;");
        }
    }

    @FXML
    private void handleRefresh() {
        patientQueueListView.refresh();
        updateQueueStats();
        statusLabel.setText("Status: Queue refreshed");
        statusLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
    }

    @FXML
    private void handleResetQueue() {
        queuePatients.clear();
        loadSampleQueue();
        updateQueueStats();
        patientDetailsArea.clear();
        statusLabel.setText("Status: Queue reset to default");
        statusLabel.setStyle("-fx-text-fill: #FF9800; -fx-font-weight: bold;");
    }

    @FXML
    private void handleBack() {
        statusLabel.setText("Status: Returning to dashboard");
        statusLabel.setStyle("-fx-text-fill: #2196F3; -fx-font-weight: bold;");
    }

    public static class QueuePatient {
        private final javafx.beans.property.SimpleStringProperty patientId;
        private final javafx.beans.property.SimpleStringProperty name;
        private final javafx.beans.property.SimpleStringProperty status;
        private final javafx.beans.property.SimpleStringProperty arrivalTime;

        public QueuePatient(String patientId, String name, String status, String arrivalTime) {
            this.patientId = new javafx.beans.property.SimpleStringProperty(patientId);
            this.name = new javafx.beans.property.SimpleStringProperty(name);
            this.status = new javafx.beans.property.SimpleStringProperty(status);
            this.arrivalTime = new javafx.beans.property.SimpleStringProperty(arrivalTime);
        }

        public String getPatientId() { return patientId.get(); }
        public void setPatientId(String value) { patientId.set(value); }
        public javafx.beans.property.StringProperty patientIdProperty() { return patientId; }

        public String getName() { return name.get(); }
        public void setName(String value) { name.set(value); }
        public javafx.beans.property.StringProperty nameProperty() { return name; }

        public String getStatus() { return status.get(); }
        public void setStatus(String value) { status.set(value); }
        public javafx.beans.property.StringProperty statusProperty() { return status; }

        public String getArrivalTime() { return arrivalTime.get(); }
        public void setArrivalTime(String value) { arrivalTime.set(value); }
        public javafx.beans.property.StringProperty arrivalTimeProperty() { return arrivalTime; }

        @Override
        public String toString() {
            return patientId.get() + " - " + name.get() + " (" + status.get() + ")";
        }
    }
}