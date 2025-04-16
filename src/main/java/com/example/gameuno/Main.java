package com.example.gameuno;

import com.example.gameuno.Views.GameUnoView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameUnoView gameUnoView = GameUnoView.getInstance();
        gameUnoView.show();
    }

    public static void main(String[] args) {
        launch();
    }
}