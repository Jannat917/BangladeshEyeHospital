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

public class Prescriptioncontroller
{
    @javafx.fxml.FXML
    private TableColumn<Prescription,String> doctornamecolumn;
    @javafx.fxml.FXML
    private TableColumn<Prescription,String> medicinecolumn;
    @javafx.fxml.FXML
    private TableView<Prescription> prescriptiontable;
    @javafx.fxml.FXML
    private TableColumn<Prescription,String> advicecolumn;
    @javafx.fxml.FXML
    private TableColumn<Prescription,String> diseasecolumn;
    @javafx.fxml.FXML
    private TextField patientidTF;
    @javafx.fxml.FXML
    private TableColumn<Prescription,Integer> patientidcolumn;

    ObservableList<Prescription> list = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {

        patientidcolumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        doctornamecolumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        diseasecolumn.setCellValueFactory(new PropertyValueFactory<>("disease"));
        medicinecolumn.setCellValueFactory(new PropertyValueFactory<>("medicine"));
        advicecolumn.setCellValueFactory(new PropertyValueFactory<>("advice"));

        list.add(new Prescription("Use twice daily","Cataract","Dr.Mehedi","Eye Drop",101));
        list.add(new Prescription("4 times daily","Dry Eye","Dr.Rahim","Artificial Tears",102));
        list.add(new Prescription("After meal","glaucoma","Dr.Karim","Tablet",103));
        list.add(new Prescription("Use daily","Cataract","Dr.Islam","Eye Drop",104));

    }

    @javafx.fxml.FXML
    public void backbutton(ActionEvent actionEvent) {
        try {
            SceneSwitcher.switchTo("nisa/PatientDashboard.fxml");

        } catch (IOException e) {
        }
    }

    @javafx.fxml.FXML
    public void searchbutton(ActionEvent actionEvent) {
        int patientId = Integer.parseInt(patientidTF.getText());
        ObservableList<Prescription> filteredList = FXCollections.observableArrayList();

        for(Prescription p : list){
            if(p.getPatientId() == patientId){
                filteredList.add(p);
            }
        }

        prescriptiontable.setItems((filteredList));

        if (filteredList.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("No Prescription found");
            a.showAndWait();
        }



    }

    @javafx.fxml.FXML
    public void refreshbutton(ActionEvent actionEvent) {
        patientidTF.clear();
        prescriptiontable.getItems().clear();
    }
}