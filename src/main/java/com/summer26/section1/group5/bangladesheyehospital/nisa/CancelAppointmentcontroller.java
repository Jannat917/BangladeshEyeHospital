package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;

public class CancelAppointmentcontroller {
    @javafx.fxml.FXML
    private TableView<BookAppointment> appointmentTable;
    @javafx.fxml.FXML
    private TableColumn<BookAppointment, Integer> patientIdcolumn;
    @javafx.fxml.FXML
    private TableColumn<BookAppointment, String> departmentcolumn;
    @javafx.fxml.FXML
    private TableColumn<BookAppointment, Integer> serialcolumn;
    @javafx.fxml.FXML
    private TableColumn<BookAppointment, String> doctorcolumn;
    @javafx.fxml.FXML
    private TextField patientIdTF;
    @javafx.fxml.FXML
    private TableColumn<BookAppointment, LocalDate> datecolumn;
    @javafx.fxml.FXML
    private TableColumn<BookAppointment, String> timecolumn;

    ObservableList<BookAppointment> list = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        patientIdcolumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        departmentcolumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        doctorcolumn.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timecolumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        serialcolumn.setCellValueFactory(new PropertyValueFactory<>("serial"));

        list.add(new BookAppointment(LocalDate.of(2026, 8, 20), "Eye", "Dr. Rahman", "101", 1, "10:00 AM"));
        list.add(new BookAppointment(LocalDate.of(2026, 8, 21), "Retina", "Dr. Karim", "102", 2, "11:00 AM"));
        list.add(new BookAppointment(LocalDate.of(2026, 8, 22), "Cornea", "Dr. Hasan", "103", 3, "9:00 AM"));

        appointmentTable.setItems(list);
    }

    @javafx.fxml.FXML
    public void cancelappointmentbutton(ActionEvent actionEvent) {
        String patientId = patientIdTF.getText();
        BookAppointment removeAppointment = null;
        for (BookAppointment b : list) {
            if (b.getPatientId().equals(patientId)) {
                removeAppointment = b;
                break;
            }
        }

        if (removeAppointment != null) {
            list.remove(removeAppointment);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Appointment Cancelled Successfully");
            alert.showAndWait();

            patientIdTF.clear();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Patient ID Not Found");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {
        }
    }

    @javafx.fxml.FXML
    public void clearbutton(ActionEvent actionEvent) {
        patientIdTF.clear();
    }
}