package com.summer26.section1.group5.bangladesheyehospital.nisa;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Logincontroller
{
    @javafx.fxml.FXML
    private TextField usernameTF;
    @javafx.fxml.FXML
    private PasswordField passwordfield;
    @javafx.fxml.FXML
    private Label messagelabel;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void loginbuttonOA(ActionEvent actionEvent) {
        String UserName = usernameTF.getText();
        String Password = passwordfield.getText();

        if (UserName.equals("Patient") && Password.equals("12345")){
            messagelabel.setText("Patient Login Successful");
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("com.summer26.section1.group5.bangladesheyehospital.nisa.PatientDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        else if(UserName.equals("Accountant") && Password.equals("12345")){
            messagelabel.setText("Accountant Login Successful");
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("com.summer26.section1.group5.bangladesheyehospital.nisa.AccountantDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Login Failed");
            a.setHeaderText(null);
            a.setContentText("Wrong Username or Password");
            a.showAndWait();
        }
    }
}