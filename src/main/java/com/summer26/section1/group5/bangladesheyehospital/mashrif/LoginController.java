package com.summer26.section1.group5.bangladesheyehospital.mashrif;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void handleLoginClick(ActionEvent event) {
        // For Object-Oriented Programming (OOP), this is a placeholder.
        // Later, you will validate this using an 'Optician' or 'Pharmacist' Object.
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (user.equals("pharmacist") && pass.equals("123")) {
            try {
                // Switch to the Pharmacist Dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PharmacistDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid Credentials!");
        }
    }
}

