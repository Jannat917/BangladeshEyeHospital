package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;


public class DoctorSchedulecontroller
{
    @javafx.fxml.FXML
    private TableColumn<DoctorSchedule, String> doctorNamecolumn;
    @javafx.fxml.FXML
    private TableColumn<DoctorSchedule, String> departmentcolumn;
    @javafx.fxml.FXML
    private TableView<DoctorSchedule> scheduletable;
    @javafx.fxml.FXML
    private ComboBox <String> departmentCB;
    @javafx.fxml.FXML
    private TableColumn<DoctorSchedule, Integer> doctorIdcolumn;
    @javafx.fxml.FXML
    private TableColumn<DoctorSchedule, String> daycolumn;
    @javafx.fxml.FXML
    private TableColumn<DoctorSchedule, String> timecolumn;

    ObservableList<DoctorSchedule> list = FXCollections.observableArrayList();


    @javafx.fxml.FXML
    public void initialize() {
        departmentCB.getItems().addAll("Glaucoma", "Retina", "Cornea", "Cataract","Oculoplasty");
        doctorIdcolumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        doctorNamecolumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        departmentcolumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        daycolumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        timecolumn.setCellValueFactory(new PropertyValueFactory<>("time"));


        list.add(new DoctorSchedule("Saturday", "Glaucoma", 101, "Dr.Mehedi", "8:00 AM"));
        list.add(new DoctorSchedule("Sunday", "Retina", 102, "Dr.Rahim", "9:00 AM"));
        list.add(new DoctorSchedule("Monday", "Cornea", 103, "Dr.Karim", "11:00 AM"));
        list.add(new DoctorSchedule("Tuesday", "Cataract", 104, "Dr.Islam", "1:00 PM"));
        list.add(new DoctorSchedule("Wednesday", "Oculoplasty", 105, "Dr.Priya", "5:00 PM"));



    }


    @javafx.fxml.FXML
    public void backbuttonOA(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {
        }
    }
    @javafx.fxml.FXML
    public void refreshbuttonOA(ActionEvent actionEvent) {

        departmentCB.setValue(null);
        scheduletable.getItems().clear();
    }

    @javafx.fxml.FXML
    public void searchbuttonOA(ActionEvent actionEvent) {
        String department = departmentCB.getValue();
        ObservableList<DoctorSchedule> filteredList = FXCollections.observableArrayList();
        for (DoctorSchedule ds : list) {
            if (department != null && ds.getDepartment().equals(department)) {
                filteredList.add(ds);
            }
        }

        if (filteredList.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Search");
            a.setHeaderText(null);
            a.setContentText("No doctor schedule found.");
            a.showAndWait();
        }

        scheduletable.setItems(filteredList);

    }
}