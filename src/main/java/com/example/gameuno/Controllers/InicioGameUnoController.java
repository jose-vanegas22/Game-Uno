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

    @FXML
    void onActionInstruccionesBoton (ActionEvent actionEvent) throws IOException {
        InstruccionesView instruccionesView = InstruccionesView.getInstance();
        instruccionesView.show();
    }
}
