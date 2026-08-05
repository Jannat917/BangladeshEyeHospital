package com.summer26.section1.group5.bangladesheyehospital.jannati;

import com.summer26.section1.group5.bangladesheyehospital.common.DoctorModelClass;
import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class CheckDoctorAvailabilityController {


    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private ComboBox<String> specializationComboBox;


    @FXML
    private TableView<DoctorModelClass> doctorTableView;


    @FXML
    private TableColumn<DoctorModelClass, String> doctorNameColumn;

    @FXML
    private TableColumn<DoctorModelClass, String> departmentColumn;

    @FXML
    private TableColumn<DoctorModelClass, String> specializationColumn;

    @FXML
    private TableColumn<DoctorModelClass, String> dayColumn;

    @FXML
    private TableColumn<DoctorModelClass, String> timeColumn;

    @FXML
    private TableColumn<DoctorModelClass, String> availabilityColumn;


    @FXML
    private Label messageLabel;


    private ArrayList<DoctorModelClass> doctorList = new ArrayList<>();


    @FXML
    public void initialize() {

        loadDoctors();

        loadDepartments();

        loadSpecializations();


        doctorNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("doctorName")
        );

        departmentColumn.setCellValueFactory(
                new PropertyValueFactory<>("department")
        );

        specializationColumn.setCellValueFactory(
                new PropertyValueFactory<>("specialization")
        );

        dayColumn.setCellValueFactory(
                new PropertyValueFactory<>("day")
        );

        timeColumn.setCellValueFactory(
                new PropertyValueFactory<>("time")
        );

        availabilityColumn.setCellValueFactory(
                new PropertyValueFactory<>("availability")
        );

    }



    private void loadDoctors() {

        File file = new File("data/doctors.bin");


        if (!file.exists()) {

            messageLabel.setText("Doctor data not found!");
            return;

        }


        try(ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(file))) {


            while(true){

                DoctorModelClass doctor =
                        (DoctorModelClass) ois.readObject();

                doctorList.add(doctor);

            }


        }catch(EOFException e){

            // Reading completed


        }catch(IOException | ClassNotFoundException e){

            e.printStackTrace();
            messageLabel.setText("Error loading doctors!");

        }

    }




    private void loadDepartments(){

        HashSet<String> departments = new HashSet<>();

        for(DoctorModelClass doctor : doctorList){

            departments.add(doctor.getDepartment());

        }


        departmentComboBox.getItems()
                .addAll(departments);

    }




    private void loadSpecializations(){

        HashSet<String> specializations = new HashSet<>();

        for(DoctorModelClass doctor : doctorList){

            specializations.add(
                    doctor.getSpecialization()
            );

        }


        specializationComboBox.getItems()
                .addAll(specializations);

    }




    @FXML
    private void searchButton(ActionEvent event){


        String department =
                departmentComboBox.getValue();


        String specialization =
                specializationComboBox.getValue();



        if(department == null || specialization == null){

            messageLabel.setText(
                    "Please select department and specialization"
            );

            return;

        }



        ObservableList<DoctorModelClass> result =
                FXCollections.observableArrayList();



        for(DoctorModelClass doctor : doctorList){


            if(doctor.getDepartment().equals(department)
                    &&
                    doctor.getSpecialization().equals(specialization)){


                result.add(doctor);

            }

        }



        doctorTableView.setItems(result);



        if(result.isEmpty()){

            messageLabel.setText(
                    "No doctor found"
            );

        }
        else{

            messageLabel.setText(
                    result.size()+" doctor(s) found"
            );

        }


    }





    @FXML
    private void clearButton(ActionEvent event){


        departmentComboBox.getSelectionModel()
                .clearSelection();


        specializationComboBox.getSelectionModel()
                .clearSelection();


        doctorTableView.getItems()
                .clear();


        messageLabel.setText("");

    }




    @FXML
    private void backButton(ActionEvent event) throws IOException {


        SceneSwitcher.switchTo(
                "jannati/receiptionistDashboard.fxml"
        );


    }

}