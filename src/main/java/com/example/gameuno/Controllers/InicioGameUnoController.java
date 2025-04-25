package com.example.gameuno.Controllers;

import com.example.gameuno.Models.AlertaIniciarJuego;
import com.example.gameuno.Models.JugadorPersona;
import com.example.gameuno.Views.GameUnoView;
import com.example.gameuno.Views.InstruccionesView;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.IOException;

/**
 * This class InicioGameUnoController handles the interaction and initialization of the
 * start interface
 */
public class InicioGameUnoController {

    @FXML
    private VBox VboxPrincipal;

    @FXML
    private StackPane ContenedorCarta;

    @FXML
    private ImageView cartaFrontal;

    @FXML
    private ImageView cartaTrasera;

    @FXML
    private TextField TextFieldNombre;

    @FXML
    private Label LabelMensaje;


    /**
     * This method is the first to be executed in this interface, it adds a background image and runs
     * the animation of a card
     */
    public void initialize() {
        String imagePath = getClass().getResource("/com/example/gameuno/Images/FondoUno.png").toExternalForm();
        VboxPrincipal.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");


        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        //Esta parte es la animacion de la carta
        String imageCarta = getClass().getResource("/com/example/gameuno/Images/Cards-uno/card_uno.png").toExternalForm();
        Image imagenFrontal = new Image(imageCarta);
        Image imagenTrasera = new Image(imageCarta);
        cartaFrontal.setImage(imagenFrontal);
        cartaTrasera.setImage(imagenTrasera);
        animarCarta();
    }

    /**
     * this method animates the card to make in move
     */
    //Este metodo anima la carta para que tenga un movimiento
    public void animarCarta() {
        ImageView cartaFrontal = (ImageView) ContenedorCarta.lookup("#cartaFrontal");
        ImageView cartaTrasera = (ImageView) ContenedorCarta.lookup("#cartaTrasera");

        RotateTransition rotacion = new RotateTransition(Duration.seconds(1.5), ContenedorCarta);
        rotacion.setAxis(Rotate.Y_AXIS); // Rotación en eje Y
        rotacion.setFromAngle(0);
        rotacion.setToAngle(180);
        rotacion.setCycleCount(Animation.INDEFINITE);
        rotacion.setAutoReverse(true); // Vuelve a su posición original

        rotacion.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            double angle = 180 * newTime.toSeconds() / 1.5;
            if (angle >= 90) {
                cartaFrontal.setVisible(true);
                cartaTrasera.setVisible(false);
            } else {
                cartaFrontal.setVisible(false);
                cartaTrasera.setVisible(true);
            }
        });

        rotacion.play();
    }

    /**
     * Here, the event is handled when the start button is pressed. It validates that a name is entered
     * and shows a confirmation alert. If 'OK' is pressed, it enters the game interface; else
     * it displays a message
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    void onActionIniciarBoton(ActionEvent actionEvent) throws IOException {
        String nombre = TextFieldNombre.getText().trim(); //Captura el nombre y lo guarda en la variable, trim elimina los espacios

        if(nombre.isEmpty()){
            LabelMensaje.setText("El nombre es obligatorio");
            return;
        }
        LabelMensaje.setText("");


        JugadorPersona jugadorPersona = new JugadorPersona(nombre);


        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        AlertaIniciarJuego alert = new AlertaIniciarJuego();

        boolean confirmacion = alert.mostrarAlertaDeConfirmacion
                ("Alerta de iniciar juego", "Esta es una ventana de alerta", "Deseas iniciar el juego?");
        if(confirmacion){
            GameUnoView gameUnoView = GameUnoView.getInstance();
            gameUnoView.getController().setJugador(jugadorPersona);
            gameUnoView.getController().mostrarNombreJugador();
            gameUnoView.show();
        } else{
            LabelMensaje.setText("Decidiste no iniciar el juego");
        }
    }


    /**
     * here the event when the instruction button is pressed, it enters the instruction interface
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    void onActionInstruccionesBoton (ActionEvent actionEvent) throws IOException {
        InstruccionesView instruccionesView = InstruccionesView.getInstance();
        instruccionesView.show();
    }
}
