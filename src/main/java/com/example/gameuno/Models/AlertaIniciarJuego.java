package com.example.gameuno.Models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;


/**
 * This class an alert class to start the game, and it implements AlertInterface
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class AlertaIniciarJuego implements AlertaInterface{

    /**
     * This method is a boolean-type alert, it display the alert and waits for a response
     *
     * @param tittle
     * @param header
     * @param content
     * @return true if the response is OK
     */
    @Override
    public boolean mostrarAlertaDeConfirmacion(String tittle, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tittle);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait(); //Muestra la alerta y espera la respuesta

        return result.isPresent() && result.get() == ButtonType.OK; //Retorna true si presiona OK
    }
}
