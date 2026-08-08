package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.DoctorModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;


public class DoctorSchedulecontroller {
    @javafx.fxml.FXML
    private TableColumn<DoctorSchedule, String> doctorNamecolumn;
    @javafx.fxml.FXML
    private TableColumn<DoctorSchedule, String> departmentcolumn;
    @javafx.fxml.FXML
    private TableView<DoctorSchedule> scheduletable;
    @javafx.fxml.FXML
    private ComboBox<String> departmentCB;
    @javafx.fxml.FXML
    private TableColumn<DoctorSchedule, Integer> doctorIdcolumn;
    @javafx.fxml.FXML
    private TableColumn<DoctorSchedule, String> daycolumn;
    @javafx.fxml.FXML
    private TableColumn<DoctorSchedule, String> timecolumn;
    private final File dataFolder = new File("data");
    private final File doctorFile = new File(dataFolder, "doctors.bin");

    private final ArrayList<DoctorModelClass> doctorList = new ArrayList<>();


    @javafx.fxml.FXML
    public void initialize() {

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        // Department ComboBox
        departmentCB.getItems().addAll(
                "Glaucoma",
                "Retina",
                "Cornea",
                "Cataract",
                "Oculoplasty"
        );

        // Table columns
        doctorIdcolumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));

        doctorNamecolumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));

        departmentcolumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        daycolumn.setCellValueFactory(new PropertyValueFactory<>("day"));

        timecolumn.setCellValueFactory(new PropertyValueFactory<>("time"));


        loadDoctors();
    }




    private void loadDoctors() {

        doctorList.clear();

        if (!doctorFile.exists()) {
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(doctorFile))) {

            while (true) {

                try {

                    DoctorModelClass doctor =
                            (DoctorModelClass) ois.readObject();

                    doctorList.add(doctor);

                } catch (EOFException e) {


                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }




    private DoctorSchedule createSchedule(
            DoctorModelClass doctor) {

        return new DoctorSchedule(
                doctor.getDay(),
                doctor.getDepartment(),
                doctor.getDoctorId(),
                doctor.getDoctorName(),
                doctor.getTime()
        );
    }




    private void showAllDoctors() {

        ObservableList<DoctorSchedule> scheduleList =
                FXCollections.observableArrayList();

        for (DoctorModelClass doctor : doctorList) {

            scheduleList.add(
                    createSchedule(doctor)
            );
        }

        scheduletable.setItems(scheduleList);


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

        loadDoctors();

        showAllDoctors();



    }


    @javafx.fxml.FXML
    public void searchbuttonOA(ActionEvent actionEvent) {
        String selectedDepartment =
                departmentCB.getValue();

        if (selectedDepartment == null ||
                selectedDepartment.isEmpty()) {

            Alert alert =
                    new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Please select a department."
            );

            alert.showAndWait();

            return;
        }


        ObservableList<DoctorSchedule> filteredList =
                FXCollections.observableArrayList();


        for (DoctorModelClass doctor : doctorList) {

            if (doctor.getDepartment() != null &&
                    doctor.getDepartment()
                            .equals(selectedDepartment)) {

                filteredList.add(
                        createSchedule(doctor)
                );
            }
        }


        scheduletable.setItems(filteredList);


        if (filteredList.isEmpty()) {

            Alert alert =
                    new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText(
                    "No doctor schedule found."
            );

            alert.showAndWait();
        }

    }
}
