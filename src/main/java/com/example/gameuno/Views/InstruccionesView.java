package com.example.gameuno.Views;

import com.example.gameuno.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class represents the view of the game instructions interface, it loads the interface
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class InstruccionesView extends Stage {

    public InstruccionesView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("/com/example/gameuno/InstruccionesView.fxml")
        );
        Scene scene = new Scene(loader.load());
        this.setTitle("Instrucciones UNO!!!");
        this.setScene(scene);
    }

    public static InstruccionesView getInstance() throws IOException {
        if(InstruccionesView.InstruccionesViewHolder.INSTANCE == null){
            InstruccionesView.InstruccionesViewHolder.INSTANCE = new InstruccionesView();
            return InstruccionesView.InstruccionesViewHolder.INSTANCE;
        } else {
            return InstruccionesView.InstruccionesViewHolder.INSTANCE;
        }
    }

    public static class InstruccionesViewHolder{
        private static InstruccionesView INSTANCE;
    }
}
