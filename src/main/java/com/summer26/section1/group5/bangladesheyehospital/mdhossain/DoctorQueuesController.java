package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.DoctorModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DoctorQueuesController {
    @FXML private ListView<String> doctorListView;
    @FXML private ListView<String> queueListView;
    @FXML private Label statusLabel;

    private static final Map<Integer, DoctorModelClass> doctorDB = new HashMap<>();
    private static final Map<String, String> queueDB = new HashMap<>();

    static {
        DoctorModelClass d1 = new DoctorModelClass();
        d1.setDoctorId(1);
        d1.setDoctorName("Dr. Rahman");
        d1.setSpecialization("Eye Specialist");
        d1.setAvailability("Available");
        doctorDB.put(1, d1);

        DoctorModelClass d2 = new DoctorModelClass();
        d2.setDoctorId(2);
        d2.setDoctorName("Dr. Sultana");
        d2.setSpecialization("Retina Specialist");
        d2.setAvailability("Available");
        doctorDB.put(2, d2);

        DoctorModelClass d3 = new DoctorModelClass();
        d3.setDoctorId(3);
        d3.setDoctorName("Dr. Islam");
        d3.setSpecialization("Cornea Specialist");
        d3.setAvailability("Available");
        doctorDB.put(3, d3);
    }

    @FXML
    public void initialize() {
        for (DoctorModelClass d : doctorDB.values()) {
            doctorListView.getItems().add(d.getDoctorId() + " - " + d.getDoctorName() + " (" + d.getSpecialization() + ")");
        }
    }

    @FXML
    public void viewQueue(ActionEvent event) {
        String selected = doctorListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("ERROR: Select a doctor!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        queueListView.getItems().clear();
        queueListView.getItems().addAll(
                "1. Jahirul Islam (P-101)",
                "2. Fatema Begum (P-102)",
                "3. Rahim Khan (P-103)"
        );

        statusLabel.setText("Queue loaded for: " + selected);
        statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 13px;");
    }

    @FXML
    public void callNext(ActionEvent event) {
        if (queueListView.getItems().isEmpty()) {
            statusLabel.setText("Queue is empty!");
            statusLabel.setStyle("-fx-text-fill: #f39c12;");
            return;
        }
        String next = queueListView.getItems().get(0);
        queueListView.getItems().remove(0);
        statusLabel.setText("Calling: " + next);
        statusLabel.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold;");
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        SceneSwitcher.switchTo("mdhossain/nurseDashboard.fxml");
    }
}
