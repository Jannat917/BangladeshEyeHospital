package com.summer26.section1.group5.bangladesheyehospital.mashrif;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MashrifLauncher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // This points strictly to YOUR login page
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bangladesh Eye Hospital - Mashrif Entry");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}