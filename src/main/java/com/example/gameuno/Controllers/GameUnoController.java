package com.example.gameuno.Controllers;

import com.example.gameuno.Models.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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

    @FXML
    private Label labelTurno;

    private Partida partida;
    private JugadorPersona jugadorPersona;
    private JugadorMaquina jugadorMaquina;



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
        jugadorMaquina = new JugadorMaquina("Máquina");

        partida.agregarJugador(jugadorPersona);
        partida.agregarJugador(jugadorMaquina);

        // Iniciar partida (repartir cartas)
        partida.iniciarPartida();

        // Colocar la carta inicial en la mesa
        partida.colocarCartaInicial();
        mostrarCartaCentro(partida.getCartaCentral());

        // Mostrar las cartas
        mostrarCartasPersona();
        mostrarCartasMaquina(jugadorMaquina.getMano().size()); // El argumento cantidasCartas es el tamaño de la mano del jugadorMaquina

        // Mostrar mazo
        //imagenViewMazo.setVisible(true);
        mostrarMazo();

        // Preparar evento de robar carta
        robarCartaDelMazo();
        manejarTurno();
        //actualizarTurnoUI();

        // Fuerza la actualización inicial
        Platform.runLater(() -> {
            habilitarInterfazHumano(partida.esTurnoJugadorPersona());
            actualizarTurnoUI();
        });

    }



    /**
     * This method clears the container where the cards will be placed, and using a for-each loop, it goes
     * through the entire array of cards and adds them to the player's container
     * @param
     */
    public void mostrarCartasPersona() {
        HBoxCartsContainer.getChildren().clear();
        //List<Carta> mano = jugadorPersona.getMano();  // Lista actual del jugador
        JugadorPersona jugador = partida.getJugadorPersona(); // Se guarda el objeto del unico JugadorPersona que existe para siempre usar el mismo
        List<Carta> mano = jugador.getMano();

        System.out.println("DEBUG - Mano antes de mostrar cartas: " + mano);

        // Con este For-each lo que se hace es recorrer toda la lista de la mano para crear visualmente su carta y ponerla en el contenedor
        for (Carta  carta : mano) {
            String ruta = "/com/example/gameuno/Images/Cards-uno/" + carta.getNombreArchivo();
            ImageView imageView = new ImageView(new Image(getClass().getResource(ruta).toExternalForm()));
            imageView.setFitWidth(60);
            imageView.setPreserveRatio(true);

            // Evento para jugar cartas, evento justamente creado adentro para que cada que se cree una carta asignarle su propio evento
            imageView.setOnMouseClicked(event -> {

                if(!partida.esTurnoJugadorPersona()) return;

                System.out.println("Intentando jugar: " + carta.getNombreArchivo());
                System.out.println("Carta central actual: " + partida.getCartaCentral().getNombreArchivo());
                if(partida.turnoJugadorPersona(carta)){
                    System.out.println("¡Jugada válida!");
                    HBoxCartsContainer.getChildren().remove(imageView);
                    mostrarCartaCentro(carta);
                    partida.pasarTurno();
                    actualizarTurnoUI();
                    manejarTurno();

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
            if(!partida.esTurnoJugadorPersona()) return;

            Carta cartaRobada = partida.robarCartaJugadorPersona();

            if(cartaRobada != null) {
                mostrarCartasPersona();
                partida.pasarTurno();
                //actualizarTurnoUI();
                manejarTurno();
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


    public void manejarTurno(){


        if (partida.esTurnoJugadorPersona()) {
            habilitarInterfazHumano(true);
            System.out.println(">> Turno JUGADOR - Esperando acción...");
            // Solo esperar interacción del jugador
        } else {
            habilitarInterfazHumano(false);
            System.out.println(">> Turno MÁQUINA - Jugando...");

            boolean turnoCompletado = partida.turnoJugadorMaquina();

            // Solo un turno de máquina por llamada
            if (turnoCompletado) {
                PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
                pause2.setOnFinished(event -> {
            mostrarCartaCentro(partida.getCartaCentral());
            mostrarCartasMaquina(partida.getJugadorMaquina().getMano().size());
                }); pause2.play();

            // Pasar turno después de 3 segundos
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                partida.pasarTurno();
                manejarTurno(); // Manejar siguiente turno
                actualizarTurnoUI();
            });
            pause.play();
            } else{
                System.err.println("Error: La máquina no pudo jugar");
                partida.pasarTurno();  // Fuerza cambio si hay error
                manejarTurno();
                actualizarTurnoUI();
            }
        }
    }

    // Este metodo permite que sea visual o no la interfaz del JugadorPersona segun sea su parametro
    private void habilitarInterfazHumano(boolean habilitado){

        imagenViewMazo.setDisable(!habilitado);
        imagenViewMazo.setVisible(true);
        imagenViewMazo.setOpacity(habilitado ? 1.0 : 0.5); // "condicion ternaria", forma de evaluar de manera compacta (Si es true : Si es false)

        // 2. Estado de las cartas del jugador
        for (Node cartaNode : HBoxCartsContainer.getChildren()) {
            if (cartaNode != null) {
                cartaNode.setDisable(!habilitado);
                cartaNode.setOpacity(habilitado ? 1.0 : 0.5);
                cartaNode.setEffect(habilitado ? new DropShadow(10, Color.GOLD) : null);
            }
        }

        //actualizarTurnoUI();
    }


    // permite mantener actualizado un label para darse cuenta si es turno de JugadorPersona o JugadorMaquina
    private void actualizarTurnoUI() {
        javafx.application.Platform.runLater(() -> { // Para asegurarse que corra en el hilo principal y no existan problemas
        if (partida.esTurnoJugadorPersona()) {
            labelTurno.setText("Tu turno");
            labelTurno.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else {
            labelTurno.setText("Turno de la máquina");
            labelTurno.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
        System.out.println("Turno actualizado: " + labelTurno.getText());
        });
    }

}
