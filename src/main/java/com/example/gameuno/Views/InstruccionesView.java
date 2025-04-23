package com.example.gameuno.Views;

import com.example.gameuno.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
