package com.example.gameuno.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class InstruccionesController {

    @FXML
    private VBox VBoxPrincipal;

    @FXML
    public void initialize(){
        String imagePath = getClass().getResource("/com/example/gameuno/Images/FondoUno.png").toExternalForm();
        VBoxPrincipal.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");
    }
}
