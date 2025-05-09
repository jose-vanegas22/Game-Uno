package com.example.gameuno.Controllers;

import com.example.gameuno.Models.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
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

    @FXML
    private Label labelNumeroCartas;

    @FXML
    private Label labelCambioColor;

    private Partida partida;
    private JugadorPersona jugadorPersona;
    private JugadorMaquina jugadorMaquina;

    private boolean juegoFinalizado = false;




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

        // Se agregan los jugadores a la lista
        partida.agregarJugador(jugadorPersona);
        partida.agregarJugador(jugadorMaquina);

        // Iniciar partida (repartir cartas)
        partida.iniciarPartida();

        // Colocar la carta inicial en la mesa y mostrarla visualmente
        partida.colocarCartaInicial();
        mostrarCartaCentro(partida.getCartaCentral());


        // Hace que cuando inicie partida y la del centro sea +2 le sume 2 cartas a jugadorPersona
        if (partida.getCartaCentral().cartaMas2()){
            for (int i = 0; i < 2; i++){
                partida.robarCartaJugadorPersona();
            }
        }

        // Cuando inicie la partida y empieza con una carta cambio de color el usuario debe de escoger el color inicial
        if (partida.getCartaCentral().cartaCambioColor()){
            elegirColorParaCartaCC(partida.getCartaCentral());
        }

        // Cuando inicie la partida y la carta del centro sea un +4 el jugadorPersona escoje color y la maquina come 4 cartas
        if (partida.getCartaCentral().cartaMas4()){
            partida.efectorCartaMas4();
            elegirColorParaCartaMas4(partida.getCartaCentral());
        }


        // Mostrar las cartas de los jugadores
        mostrarCartasPersona();
        mostrarCartasMaquina(jugadorMaquina.getMano().size()); // El argumento cantidasCartas es el tamaño de la mano del jugadorMaquina

        // Mostrar mazo visualmente, en este caso una imagen de cartas que estan boca abajo
        mostrarMazo();

        // Preparar evento de robar carta
        robarCartaDelMazo();

        manejarTurno(); // Siempre va a empezar el jugadorPersona


        // Fuerza la actualización inicial
        Platform.runLater(() -> {
            habilitarInterfazJugadorPersona(partida.esTurnoJugadorPersona()); // Muestra la interfaz del jugadorPersona si es su turno
            actualizarTurnoUI();
        });

        //Contar las cartas
        actualizarContadorCartas("red"); // Codigo pendiente para eliminar

        // Define si hay un ganador aunque al iniciar siempre va a ser null
        ganador();
    }



    /**
     * This method clears the container where the cards will be placed, and using a for-each loop, it goes
     * through the entire array of cards and adds them to the player's container
     *
     */
    // Este metodo mantiene actualizada la mano del jugadorPersona visualmente segun lo que vaya pasando en la partida
    public void mostrarCartasPersona() {
        HBoxCartsContainer.getChildren().clear(); // Siempre que entra borra el contenedor para poner las cartas de la mano actualizada
        JugadorPersona jugador = partida.getJugadorPersona(); // Se guarda el objeto del unico JugadorPersona que existe para siempre usar el mismo
        List<Carta> mano = jugador.getMano(); // Se guardan todas las cartas que tiene el jugadorPersona en esta lista

        System.out.println("Mano antes de mostrar cartas: " + mano);

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
                    HBoxCartsContainer.getChildren().remove(imageView); // Se elimina visualmente la carta que se jugo
                    mostrarCartaCentro(carta); // La carta que se jugo se muestra en el centro visualmente

                    // Si la carta que se jugo es cartaMas2 actualiza visualmente la cantidad de cartas de jugadorMaquina en mano
                    if(carta.cartaMas2()) {
                        // Mostrar el efecto visual simplificado
                        //mostrarCartasRobadasPorMas2();
                        mostrarCartasMaquina(partida.getJugadorMaquina().getMano().size());
                    }

                    // Si la carta que se jugo es cartaCambioColor se ejecuta el metodo del color que se escogio
                    if (carta.cartaCambioColor()){
                        elegirColorParaCartaCC(carta);
                        return; // Garantiza que se escoja un color antes de continuar
                    } else {
                        labelCambioColor.setText(""); // Si no es la carta que se jugo limpia el label del color escogido
                    }

                    // Si la carta que se jugo es una cartaMas4 se actualiza la mano de la maquina con las 4 cartas y se escoge un color
                    if (carta.cartaMas4()){
                        mostrarCartasMaquina(partida.getJugadorMaquina().getMano().size());
                        elegirColorParaCartaMas4(carta);
                        return; // Garantiza que se escoja un color antes de continuar
                    } else {
                        labelCambioColor.setText("");
                    }

                    // Cuando se ejecute lo de arriba para concluir con el turno se cede el turno
                    ganador();
                    partida.pasarTurno(); // Pasa el turno a jugadorMaquina
                    actualizarTurnoUI(); // Actualiza el label para que se muestre el turno del siguiente jugador
                    manejarTurno();
                } else{
                    System.out.println("Jugada no valida!!!!");

                }
            });

            HBoxCartsContainer.getChildren().add(imageView); // Añade las cartas al contenedor
        }
    }

    /**
     *
     */
    // Este metodo nos permite que el usuario robe una carta del mazo, haciendo click en una imagen
    private void robarCartaDelMazo() {
        // Evento para sacar carta del mazo
        imagenViewMazo.setOnMouseClicked(event -> {
            if(!partida.esTurnoJugadorPersona()) return;

            Carta cartaRobada = partida.robarCartaJugadorPersona(); // La accion de robar carta del jugadorPersona se guarda en la variable

            if(cartaRobada != null) { // Si existe una cartaRobada se ejecuta este codigo visualmente
                mostrarCartasPersona();
                partida.pasarTurno(); // Cambia de turno para que le toque al jugadorMaquina
                manejarTurno(); // Para saber si sigue jugando jugadorPersona o es el turno de jugadorMaquina
                actualizarTurnoUI(); // Actualiza en el label de quien es el turno para que se vea graficamente
                actualizarContadorCartas("red");
                //ganador();
            }
        });
    }


    /**
     * This method clears the container where the cards will be placed, and using a for-each loop, it goes
     * through the entire array of cards and adds them macine container but it only adds back-facing cards
     * visually
     * @param cantidadCartas
     */
    // Este metodo muestra las cartas de la maquina boca abajo segun el tamaño de su lista o mano
    public void mostrarCartasMaquina(int cantidadCartas) {
        // Siempre que entra se limpia el contenedor para mostrar la nueva lista despues de cada jugada
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
     *
     * @param carta
     */
    // Este metodo nos muestra una alerta en donde podemos escoger un color cuando el jugadorPersona juega
    // la carta cambiarColor
    private void elegirColorParaCartaCC(Carta carta){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Elegir color para la carta");
        alert.setHeaderText("Selecciona el nuevo color");

        // Aqui se crean los 4 botones con sus respectivos nombres y textos
        ButtonType red = new ButtonType("Red");
        ButtonType blue  = new ButtonType("Blue");
        ButtonType green = new ButtonType("Green");
        ButtonType yellow = new ButtonType("Yellow");

        // Se reemplazan todos los botones por defecto del alert y se ponen los personalizados
        alert.getButtonTypes().setAll(red, blue, green, yellow);

        // En esta parte lo que se hace es que la ventana este ejecutandose hasta que el usuario oprima un boton
        alert.showAndWait().ifPresent(button -> {
            String colorElegido = button.getText().toLowerCase(); // toLowerCase (covierte el texto en minuscula)
            labelCambioColor.setText("Color humano: " + colorElegido);

            // Creamos una nueva carta con el color temporal ya puesto
            // Pense en crear una nueva carta para no modificar la original
            //Carta nuevaCartaColor = new Carta( colorElegido, "cambioColor", "cambiarColor_negro.png");
            //nuevaCartaColor.setColorTemporal(colorElegido);
            //partida.setCartaDelCnetro(nuevaCartaColor);
            //mostrarCartaCentro(nuevaCartaColor);
            //partida.turnoJugadorPersona(nuevaCartaColor);

            carta.setColorTemporal(colorElegido); // cambia el color de la carta cambiarColor
            partida.setCartaDelCnetro(carta); // modifica la carta del centro con el set y la pone en el centro
            mostrarCartaCentro(carta); // la muestra visualmente
            mostrarCartasPersona(); // Actualiza las cartas del jugadorPersona sin la carta que puso
            ganador(); // Revisa si hay un ganador para finalizar
            partida.pasarTurno(); // Cambia de turno
            actualizarTurnoUI(); // Actualiza el label del turno
            manejarTurno(); // Revisa si se sigue jugando o cambia para que juegue jugadorMaquina
        });
    }

    /**
     *
     * @param carta
     */
    // Este metodo nos muestra una alerta en donde el jugadorPersona tiene la opcion de escoger un color
    // cuando juega la carta +4
    private void elegirColorParaCartaMas4(Carta carta){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Elegir color para la carta");
        alert.setHeaderText("Selecciona el nuevo color");

        // Aqui se crean los 4 botones con sus respectivos nombres
        ButtonType red = new ButtonType("Red");
        ButtonType blue  = new ButtonType("Blue");
        ButtonType green = new ButtonType("Green");
        ButtonType yellow = new ButtonType("Yellow");

        // Se cambian los botones que vienen por defecto y se ponen los
        alert.getButtonTypes().setAll(red, blue, green, yellow);

        alert.showAndWait().ifPresent(button -> {
            String colorElegido = button.getText().toLowerCase(); // toLowerCase (Lo que hace es volver todas las palabras minusculas)
            labelCambioColor.setText("Color humano: " + colorElegido);
            //Carta nuevaCartaColor = new Carta(colorElegido, "+4", "+4_negro.png");
            //nuevaCartaColor.setColorTemporalMas4(colorElegido);
            //partida.setCartaDelCnetro(nuevaCartaColor);
            //mostrarCartaCentro(nuevaCartaColor);
            //partida.turnoJugadorPersona(nuevaCartaColor);

            carta.setColorTemporal(colorElegido); // Pone el color escogido a la carta mediante el set
            partida.setCartaDelCnetro(carta); // Pone la carta del centro la modifica con el set
            mostrarCartaCentro(carta);
            partida.turnoJugadorPersona(carta);
            mostrarCartasPersona();
            ganador();
            partida.pasarTurno();
            actualizarTurnoUI();
            manejarTurno();
        });
    }


    /**
     * This method recives a card, finds its image, and places it in the center of the table
     * @param carta
     */
    // Este metodo muestra la carta del centro
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


    /**
     *
     */
    // Este metodo lo que hace es controlar la logica de turnos si es el turno del jugadorPersona espera
    // hasta que realice una accion y si es el turno del jugadorMaquina realiza una jugada
    public void manejarTurno(){
        if (juegoFinalizado) return;

        if (partida.esTurnoJugadorPersona()) {
            habilitarInterfazJugadorPersona(true);
            System.out.println(">> Turno JUGADOR - Esperando acción...");
            // Solo esperar interacción del jugador
        } else {
            habilitarInterfazJugadorPersona(false);
            System.out.println(">> Turno MAQUINA - Jugando...");

            boolean turnoCompletado = partida.turnoJugadorMaquina();

            // Si es el turnoJugadorMaquina ejecuta una jugada y lo muestra visualmente
            // Solo un turno de máquina por llamada
            if (turnoCompletado) {
                // Visualmente espera 1 seg para mostrar la jugada que realizo
                PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
                pause2.setOnFinished(event -> {

                    ganador();

                    if (juegoFinalizado) return; // Si el juego termino no se ejecutan las lineas de codigo

                    mostrarCartaCentro(partida.getCartaCentral()); // Muestra la carta que jugo en el centro
                    mostrarCartasMaquina(partida.getJugadorMaquina().getMano().size()); // Actualiza la mano visualmente despues de jugar la carta
                    Carta cartaCentral = partida.getCartaCentral(); // Captura la carta centrar que jugo para hacer validaciones y ejecutar los efectos de las cartas

                    // Esta parte lo que hace es poner el color en el label que escogio la maquina con la
                    // carta cambio de color o con la del +4
                    if (cartaCentral != null && cartaCentral.cartaCambioColor()) {
                        String colorEscogidoMaquina = partida.getColorEscogidoMaquina();
                        labelCambioColor.setText("Color maquina: " + colorEscogidoMaquina);
                    } else if (cartaCentral != null && cartaCentral.cartaMas4()){
                        String colorEscogidoMaquinaMas4 = partida.getColorEscogidoMaquinaMas4();
                        labelCambioColor.setText("Color maquina: " +  colorEscogidoMaquinaMas4);
                        mostrarCartasPersona();
                    }
                    else {
                        labelCambioColor.setText("");
                    }


                    if (cartaCentral != null && cartaCentral.cartaMas2()){
                        mostrarCartasPersona();
                    }


                    // hasta aqui se ejecutan todas estas acciones en 1 seg
                }); pause2.play();

                // Pasar turno después de 5 segundos para que visualmente se entienda mas
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(event -> {
                    if (juegoFinalizado) return; // Si el juego finalizo no se ejecutan las lineas de codigo
                    ganador(); // Si fue su ultima carta lo que hace es detener y mostrar que gano
                    partida.pasarTurno(); // Pasa el turno para que siga el jugadorPersona
                    manejarTurno(); // Pasa a la logica del jugadorPersona
                    actualizarTurnoUI(); // Actualiza el turno visualmente

                    // Termina la pausa de 5 segundos y ejecuta estas lineas del codigo y sigue el turno de jugadorPersona
                }); pause.play();
            } else{
                System.err.println("Error: La máquina no pudo jugar");
                partida.pasarTurno();  // Fuerza cambio si hay error
                manejarTurno();
                actualizarTurnoUI();
                ganador();
            }
        }
    }


    /**
     *
     * @param habilitado
     */
    // Este metodo permite que sea visual o no la interfaz del JugadorPersona segun sea su parametro (segun el turno en el que se encuentre)
    // se habilita con el boolean que arroje esTurnoJugadorPersona
    private void habilitarInterfazJugadorPersona(boolean habilitado){

        // Si es el turno de jugadorPersona y se arroja True
        imagenViewMazo.setDisable(!habilitado); // Arroja false o sea se habilita
        imagenViewMazo.setVisible(true); // Siempre va a ser visible pero con una opacidad dependiendo el caso
        imagenViewMazo.setOpacity(habilitado ? 1.0 : 0.5); // "condicion ternaria", forma de evaluar de manera compacta (Si es true : Si es false)

        // Estado de las cartas del jugador segun si es su turno o no
        // Node es cualquier elemento visual ya que heredan de la clase Node
        for (Node carta : HBoxCartsContainer.getChildren()) { // Contenedor del jugadorPersona y el getChildren ayuda a obtener todos los nodos o cartas
            if (carta != null) {
                carta.setDisable(!habilitado); // Arroja false y por lo tanto se muestra si se sigue el mismo ejemplo de arriba
                carta.setOpacity(habilitado ? 1.0 : 0.5); // Es una condcion con if-else si es true tiene opacidad de 1.0 y si es false de 0.5
                carta.setEffect(habilitado ? new DropShadow(10, Color.GOLD) : null); // Pasa lo mismo de arriba pero pone un color oro
            }
        }
    }


    /**
     *
     */
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


    /**
     *
     */
    // Metodo que ayuda a determinar si ya alguien se quedo sin cartas y si lo hay muestra un mensaje con un alert
    private void ganador(){
        Jugador ganador = partida.verificarGanador(); // Guarda lo que lanza verificarGanador en la variable
        if (ganador != null) { // Si hay un ganador
            juegoFinalizado = true; // Cambia el valor para que se detenga cualquier metodo o parte del codigo
            mostrarMensajeGanador(ganador); // Muestra el alert
            habilitarInterfazJugadorPersona(false); // Deshabilita la interfaz del jugadorPersona
            //deshabilitarInteraccion(); // Deshabilita la interfaz de jugadorPersona
        }
    }

    /**
     *
     * @param ganador
     */
    // Metodo que muestra un alert en donde se evidencia el ganador del juego
    private void mostrarMensajeGanador(Jugador ganador){
        String mensaje = ganador instanceof JugadorPersona ? "Has ganado" : "La maquina ha ganado";
        Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("FIN DEL JUEGO!!!!!");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        });
    }





    private void actualizarContadorCartas(String color){
        int cantidad = partida.contarCartasEnJuego(color);
        labelNumeroCartas.setText(Integer.toString(cantidad));
    }



    /**
     // Metodo el cual deshabilita la interfaz del usuario
     private void deshabilitarInteraccion(){
     // Deshabilitar la interacción con las cartas y el mazo
     imagenViewMazo.setDisable(true);
     for (Node cartaNode : HBoxCartsContainer.getChildren()) {
     cartaNode.setDisable(true);
     }
     }


    private void mostrarCartasRobadasPorMas2() {
        // Solo actualiza la visualización de las cartas
        if(partida.esTurnoJugadorPersona()) {
            // Si es turno del humano, la máquina roba (mostramos sus cartas)
            mostrarCartasMaquina(partida.getJugadorMaquina().getMano().size());
        } else {
            // Si es turno de la máquina, el humano roba (actualizamos su mano)
            mostrarCartasPersona();
        }
    }

    private void mostrarCartasRobadasPorMas4(){
        if (partida.esTurnoJugadorPersona()) {
            mostrarCartasMaquina(partida.getJugadorMaquina().getMano().size());
        } else {
            mostrarCartasPersona();
        }
    }
     **/


}
