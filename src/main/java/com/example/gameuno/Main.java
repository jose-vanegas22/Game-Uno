package com.example.gameuno;

import com.example.gameuno.Views.GameUnoView;
import com.example.gameuno.Views.InicioGameUnoView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /**
        GameUnoView gameUnoView = GameUnoView.getInstance();
        gameUnoView.show();
         **/

        InicioGameUnoView inicioGameUnoView = InicioGameUnoView.getInstance();
        inicioGameUnoView.show();
    }

    public static void main(String[] args) {
        launch();
    }
}