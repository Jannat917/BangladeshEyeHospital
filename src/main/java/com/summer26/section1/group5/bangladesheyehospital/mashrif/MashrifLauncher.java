package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MashrifLauncher extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                MashrifLauncher.class.getResource(
                        "/com/summer26/section1/group5/"
                                + "bangladesheyehospital/mashrif/"
                                + "PharmacistDashboard.fxml"
                )
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle(
                "Bangladesh Eye Hospital - Pharmacist Dashboard"
        );

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}