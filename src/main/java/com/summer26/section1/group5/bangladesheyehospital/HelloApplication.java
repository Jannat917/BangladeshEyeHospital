package com.summer26.section1.group5.bangladesheyehospital;

import com.summer26.section1.group5.bangladesheyehospital.common.SceneSwitcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {


        SceneSwitcher.stage = stage;

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("common/login.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setTitle("Bangladesh Eye Hospital");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}