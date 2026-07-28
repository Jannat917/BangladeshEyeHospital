package com.summer26.section1.group5.bangladesheyehospital.nisa;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class GiveFeedbackcontroller
{
    @javafx.fxml.FXML
    private ComboBox<Integer> ratingCB;
    @javafx.fxml.FXML
    private TextArea feedbackTA;
    @javafx.fxml.FXML
    private TextField patientidTF;

    ObservableList<Feedback> list = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        ratingCB.getItems().addAll(1,2,3,4,5);
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
        patientidTF.clear();
        feedbackTA.clear();
        ratingCB.setValue(null);

    }

    @javafx.fxml.FXML
    public void submitbutton(ActionEvent actionEvent) {

        if(patientidTF.getText().isEmpty()
                || feedbackTA.getText().isEmpty()
                || ratingCB.getValue()==null){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fill up all fields");
            alert.showAndWait();
            return;
        }

        int patientId = Integer.parseInt(patientidTF.getText());
        Feedback feedback = new Feedback(
                feedbackTA.getText(),
                patientId,
                ratingCB.getValue());

        list.add(feedback);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Feedback Submitted Successfully");
        alert.showAndWait();

        patientidTF.clear();
        feedbackTA.clear();
        ratingCB.setValue(null);

    }
}