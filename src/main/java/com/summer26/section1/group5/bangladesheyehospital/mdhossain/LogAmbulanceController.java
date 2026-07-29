package com.summer26.section1.group5.bangladesheyehospital.mdhossain;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class LogAmbulanceController {
    @FXML
    private TextField ambulanceNumberField;
    @FXML
    private Label statusLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label ambulanceInfoLabel;

    private static final Map<String, Ambulance> ambulanceDB = new HashMap<>();

    static {
        ambulanceDB.put("AMB-101", new Ambulance(
                "AMB-101",
                "Rapid Response",
                "Abdul jalil",
                "Dhaka Medical College Hospital",
                "01712345678",
                "Available"
        ));

        ambulanceDB.put("AMB-202", new Ambulance(
                "AMB-202",
                "Life Support",
                "Abu Hanif",
                "Bangladesh Eye Hospital",
                "01723456789",
                "On Route"
        ));

        ambulanceDB.put("AMB-303", new Ambulance(
                "AMB-303",
                "Emergency Care",
                "Jarif uddin",
                "Combined Military Hospital",
                "01734567890",
                "Available"
        ));
    }

    @FXML
    public void logAmbulance(ActionEvent event) {
        String number = ambulanceNumberField.getText().trim();

        if (number.isEmpty()) {
            statusLabel.setText("ERROR: Enter ambulance number!");
            statusLabel.setStyle("-fx-text-fill: red;");
            ambulanceInfoLabel.setText("");
            return;
        }

        Ambulance ambulance = ambulanceDB.get(number);

        if (ambulance != null) {
            String info = "========== AMBULANCE INFORMATION ==========\n";
            info += "Vehicle Number: " + ambulance.getNumber() + "\n";
            info += "Model: " + ambulance.getModel() + "\n";
            info += "Driver: " + ambulance.getDriver() + "\n";
            info += "Hospital: " + ambulance.getHospital() + "\n";
            info += "Contact: " + ambulance.getContact() + "\n";
            info += "Status: " + ambulance.getStatus() + "\n";
            info += "==============================================";

            ambulanceInfoLabel.setText(info);
            ambulanceInfoLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 13px;");

            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            statusLabel.setText("Ambulance " + number + " logged successfully!");
            statusLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            timeLabel.setText("Arrival Time: " + time);

            ambulance.setStatus("Arrived");

        } else {
            ambulanceInfoLabel.setText("");
            statusLabel.setText("ERROR: Ambulance not found!");
            statusLabel.setStyle("-fx-text-fill: red;");
            timeLabel.setText("");
        }

        ambulanceNumberField.clear();
    }

    @FXML
//
    public void backButton(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.switchTo("mdhossain/securitystaffDashboard.fxml");
    }
    private static class Ambulance {
        private String number;
        private String model;
        private String driver;
        private String hospital;
        private String contact;
        private String status;

        public Ambulance(String number, String model, String driver, String hospital, String contact, String status) {
            this.number = number;
            this.model = model;
            this.driver = driver;
            this.hospital = hospital;
            this.contact = contact;
            this.status = status;
        }

        public String getNumber() { return number; }
        public String getModel() { return model; }
        public String getDriver() { return driver; }
        public String getHospital() { return hospital; }
        public String getContact() { return contact; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

}


