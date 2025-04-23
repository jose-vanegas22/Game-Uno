package com.example.gameuno.Models;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertaIniciarJuego implements AlertaInterface{
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
