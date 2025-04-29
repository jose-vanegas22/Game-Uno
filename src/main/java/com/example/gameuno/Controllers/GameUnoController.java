package com.example.gameuno.Controllers;

import com.example.gameuno.Models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * This class GameUnoController manages the interaction between the GameUno.fxml interface and the
 * game logic. it updates the interface according to the actions that occur
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class GameUnoController {

    @FXML
    private VBox VBoxPrincipal;

    @FXML
    private HBox HBoxCartsContainer;

    @FXML
    private HBox HBoxCartsMaquinaContainer;

    @FXML
    private ImageView ImagenViewCartasCentro;

    @FXML
    private ImageView imagenViewMazo;

    @FXML
    private Label LabelNombreJugador;

    private Partida partida;
    private JugadorPersona jugadorPersona;
    private JugadorMaquina maquina;


    /**
     * This method runs first when entering this interface, it adds a background image and initializes
     * everything needed to start the game
     */
    public void initialize() {

        String imagePath = getClass().getResource("/com/example/gameuno/Images/FondoUno.png").toExternalForm();
        VBoxPrincipal.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");


        // Crear partida y jugador
        partida = new Partida();
        jugadorPersona = new JugadorPersona("Jugador");
        maquina = new JugadorMaquina("Máquina");

        partida.agregarJugador(jugadorPersona);
        partida.agregarJugador(maquina);

        // Iniciar partida (repartir cartas)
        partida.iniciarPartida();

        // Colocar la carta inicial en la mesa
        partida.colocarCartaInicial();
        mostrarCartaCentro(partida.getCartaCentral());

        // Mostrar las cartas
        mostrarCartasPersona();
        mostrarCartasMaquina(maquina.getMano().size());

        // Mostrar mazo
        mostrarMazo();

        // Preparar evento de robar carta
        robarCartaDelMazo();

    }



    /**
     * This method clears the container where the cards will be placed, and using a for-each loop, it goes
     * through the entire array of cards and adds them to the player's container
     * @param
     */


    public void mostrarCartasPersona() {
        HBoxCartsContainer.getChildren().clear();
        //List<Carta> mano = jugadorPersona.getMano();  // Lista actual del jugador
        JugadorPersona jugador = partida.getJugadorPersona();
        List<Carta> mano = jugador.getMano();
        System.out.println("DEBUG - Mano antes de mostrar cartas: " + mano);
        for (Carta  carta : mano) {
            String ruta = "/com/example/gameuno/Images/Cards-uno/" + carta.getNombreArchivo();
            ImageView imageView = new ImageView(new Image(getClass().getResource(ruta).toExternalForm()));
            imageView.setFitWidth(60);
            imageView.setPreserveRatio(true);

            // Evento para jugar cartas
            imageView.setOnMouseClicked(event -> {
                System.out.println("Intentando jugar: " + carta.getNombreArchivo());
                System.out.println("Carta central actual: " + partida.getCartaCentral().getNombreArchivo());
                if(partida.turnoJugadorPersona(carta)){
                    System.out.println("¡Jugada válida!");
                    HBoxCartsContainer.getChildren().remove(imageView);
                    mostrarCartaCentro(carta);
                } else{
                    System.out.println("Jugada no valida!!!!");

                }
            });

            HBoxCartsContainer.getChildren().add(imageView); // Añade las cartas al contenedor
        }
    }


    private void robarCartaDelMazo() {
        // Evento para sacar carta del mazo
        imagenViewMazo.setOnMouseClicked(event -> {

            Carta cartaRobada = partida.robarCartaJugadorPersona2();

            if(cartaRobada != null) {
                mostrarCartasPersona();
            }
        });
    }






    /**
     * This method clears the container where the cards will be placed, and using a for-each loop, it goes
     * through the entire array of cards and adds them macine container but it only adds back-facing cards
     * visually
     * @param cantidadCartas
     */
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

    /**
     * This method recives a card, finds its image, and places it in the center of the table
     * @param carta
     */
    public void mostrarCartaCentro(Carta carta) {
        if(carta != null) {
            String ruta = "/com/example/gameuno/Images/Cards-uno/" + carta.getNombreArchivo();
            Image image = new Image(getClass().getResource(ruta).toExternalForm());
            ImagenViewCartasCentro.setImage(image);
            System.out.println("Carta del centro: " + carta.getNombreArchivo());
        }
    }

    /**
     * This method displays an image that represents the deck
     */
    //Permite mostrar la imagen del mazo
    public void mostrarMazo(){
        String ruta = "/com/example/gameuno/Images/Cards-uno/deck_of_cards.png";
        Image image = new Image(getClass().getResource(ruta).toExternalForm());
        imagenViewMazo.setImage(image);
        imagenViewMazo.setVisible(true);
        imagenViewMazo.setFitWidth(60);
        imagenViewMazo.setPreserveRatio(true);
    }

    /**
     * This method receives a player of type JugadorPersona and stores it in the jugador atribute
     * @param jugador
     */
    public void setJugador(JugadorPersona jugador) {
        this.jugadorPersona = jugador;
    }

    /**
     * This method displays the name of JugadorPersona in label
     */
    public void mostrarNombreJugador(){
        LabelNombreJugador.setText(jugadorPersona.getNombre());
    }
}
