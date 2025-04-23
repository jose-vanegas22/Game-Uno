package com.example.gameuno.Views;

import com.example.gameuno.Controllers.GameUnoController;
import com.example.gameuno.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameUnoView extends Stage {

    private GameUnoController controller;

    public GameUnoView() throws IOException{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource
                ("/com/example/gameuno/GameUno.fxml"));
        Scene scene = new Scene(loader.load());
        this.controller = loader.getController();
        this.setTitle("Game Uno");
        this.setScene(scene);
    }

    public GameUnoController getController() {
        return controller;
    }


    public static GameUnoView getInstance() throws IOException{
        if(GameUnoView.GameUnoViewHolder.INSTANCE == null){
            GameUnoView.GameUnoViewHolder.INSTANCE = new GameUnoView();
            return GameUnoView.GameUnoViewHolder.INSTANCE;
        } else {
            return GameUnoView.GameUnoViewHolder.INSTANCE;
        }
    }

    public static class GameUnoViewHolder{
        private static GameUnoView INSTANCE; //Clase interna, contiene la unica instancia
    }

}
