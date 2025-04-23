package com.example.gameuno.Controllers;

import com.example.gameuno.Models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML
    private ImageView ImagenViewCartasCentro;

    @FXML
    private ImageView imagenViewMazo;

    @FXML
    private Label LabelNombreJugador;

    private Partida partida;
    private JugadorPersona jugador;
    private JugadorMaquina maquina;


    public void initialize() {

        String imagePath = getClass().getResource("/com/example/gameuno/Images/FondoUno.png").toExternalForm();
        VBoxPrincipal.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");

        // Crear partida y jugador
        partida = new Partida();
        jugador = new JugadorPersona("Jugador");
        maquina = new JugadorMaquina("Máquina");

        partida.agregarJugador(jugador);
        partida.agregarJugador(maquina);

        partida.iniciarPartida();

        // Mostrar las cartas del jugador
        mostrarCartas(jugador.getMano());

        //Mostrar las cartas de la maquina
        mostrarCartasMaquina(maquina.getMano().size());

        //Muestra la primer carta en el centro sacada del mazo
        partida.colocarCartaInicial();
        mostrarCartaCentro(partida.getCartaCentral());


        //Esta parte lo que hace es que se puede sacar una carta del mazo y agregarla a la mano
        imagenViewMazo.setOnMouseClicked((event) -> {
           if(partida.getMazoUno().isEmpty()) {
               Carta cartaSacada = partida.getMazoUno().getMazo().pop();
               jugador.recibirCarta(cartaSacada);
               mostrarCartas(jugador.getMano());

               System.out.println("Mano actual del jugador: " + jugador.getMano());
           }
        });

        //Permite mostrar la imagen del mazo
        mostrarMazo();
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

    public void mostrarCartaCentro(Carta carta) {
        if(carta != null) {
            String ruta = "/com/example/gameuno/Images/Cards-uno/" + carta.getNombreArchivo();
            Image image = new Image(getClass().getResource(ruta).toExternalForm());
            ImagenViewCartasCentro.setImage(image);
        }
    }

    //Permite mostrar la imagen del mazo
    public void mostrarMazo(){
        String ruta = "/com/example/gameuno/Images/Cards-uno/deck_of_cards.png";
        Image image = new Image(getClass().getResource(ruta).toExternalForm());
        imagenViewMazo.setImage(image);
        imagenViewMazo.setVisible(true);
        imagenViewMazo.setFitWidth(60);
        imagenViewMazo.setPreserveRatio(true);
    }

    public void setJugador(JugadorPersona jugador) {
        this.jugador = jugador;
    }

    public void mostrarNombreJugador(){
        LabelNombreJugador.setText(jugador.getNombre());
    }
}
