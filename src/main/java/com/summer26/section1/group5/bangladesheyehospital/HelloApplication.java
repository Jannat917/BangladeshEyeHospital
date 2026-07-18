package com.summer26.section1.group5.bangladesheyehospital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    public void start(Stage stage) throws IOException {

        FXMLLoader fxMLLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxMLLoader.load());
        stage.setTitle("Hello");
        stage.setScene(scene);
        stage.show();
    }
}
