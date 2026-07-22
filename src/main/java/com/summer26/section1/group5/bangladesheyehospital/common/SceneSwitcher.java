package com.summer26.section1.group5.bangladesheyehospital.common;

import com.summer26.section1.group5.bangladesheyehospital.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static Stage stage;

    public static void switchTo(String fxmlPath) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource(fxmlPath)
        );

        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}