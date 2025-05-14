package com.example.gameuno.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class controls the instructions window and basically just displays that
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class InstruccionesController {

    @FXML
    private VBox VBoxPrincipal;

    /**
     * The first method to run when starting
     */
    @FXML
    public void initialize(){
        String imagePath = getClass().getResource("/com/example/gameuno/Images/FondoUno.png").toExternalForm();
        VBoxPrincipal.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");
    }

    public void hideInstructionsView(){
        Stage stage = (Stage) VBoxPrincipal.getScene().getWindow();
        stage.hide();
    }
}
