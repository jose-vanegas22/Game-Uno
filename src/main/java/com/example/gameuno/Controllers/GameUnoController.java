package com.example.gameuno.Controllers;

import com.example.gameuno.Models.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class GameUnoController {

    @FXML
    private VBox VBoxPrincipal;

    @FXML
    private HBox HBoxCartsContainer;

    @FXML
    private HBox HBoxCartsMaquinaContainer;

    private Partida partida;
    private JugadorPersona jugador;
    private JugadorMaquina maquina;


    public void initialize() {

        String imagePath = getClass().getResource("/com/example/gameuno/Images/FondoUno.png").toExternalForm();
        VBoxPrincipal.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");

        // Crear partida y jugador
        partida = new Partida();
        jugador = new JugadorPersona("Jugador 1");
        maquina = new JugadorMaquina("Máquina");

        partida.agregarJugador(jugador);
        partida.agregarJugador(maquina);

        partida.iniciarPartida();

        // Mostrar las cartas del jugador
        mostrarCartas(jugador.getMano());

        //Mostrar las cartas de la maquina
        mostrarCartasMaquina(maquina.getMano().size());
    }

    public void mostrarCartas(List<Carta> cartas) {
        HBoxCartsContainer.getChildren().clear();  // Limpia antes de añadir nuevas

        for (Carta carta : cartas) {
            String ruta = "/com/example/gameuno/Images/Cards-uno/" + carta.getNombreArchivo();
            Image image = new Image(getClass().getResource(ruta).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(60);
            imageView.setPreserveRatio(true);
            HBoxCartsContainer.getChildren().add(imageView);
        }
    }


    public void mostrarCartasMaquina(int cantidadCartas) {
        HBoxCartsMaquinaContainer.getChildren().clear();

        // Imagen de reverso
        String ruta = "/com/example/gameuno/Images/Cards-uno/card_uno.png";
        Image image = new Image(getClass().getResource(ruta).toExternalForm());

        for (int i = 0; i < cantidadCartas; i++) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(60);
            imageView.setPreserveRatio(true);
            HBoxCartsMaquinaContainer.getChildren().add(imageView);
        }
    }
}
