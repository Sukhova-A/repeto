package com.anastasiia.repeto;

import com.anastasiia.repeto.repository.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        DatabaseManager.migrate();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/anastasiia/repeto/MainForm.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Repeto");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}