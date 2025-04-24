package com.example.gameuno.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.gameuno.Main;

import java.io.IOException;

/**
 *This class represents the view of the game start interface, it loads the interface
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class InicioGameUnoView extends Stage {

    public InicioGameUnoView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("/com/example/gameuno/InicioGameUno.fxml"));
        Scene scene = new Scene(loader.load());
        this.setTitle("Inicio Game Uno");
        this.setScene(scene);
    }

    public static InicioGameUnoView getInstance() throws IOException {
        if(InicioGameUnoView.InicioGameUnoViewHolder.INSTANCE == null) {
            InicioGameUnoView.InicioGameUnoViewHolder.INSTANCE = new InicioGameUnoView();
            return  InicioGameUnoView.InicioGameUnoViewHolder.INSTANCE;
        } else  {
            return InicioGameUnoView.InicioGameUnoViewHolder.INSTANCE;
        }
    }

    public static class InicioGameUnoViewHolder{
        private static InicioGameUnoView INSTANCE;
    }
}
