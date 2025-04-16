package com.example.gameuno.Controllers;

import com.example.gameuno.Models.Carts;
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

    public void initialize() {

        String imagePath = getClass().getResource("/com/example/gameuno/Images/FondoUno.png").toExternalForm();
        VBoxPrincipal.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");

        List<String> cartasAleatorias = Carts.getRandomCards(5);
        mostrarCartas(cartasAleatorias);
    }

    public void mostrarCartas(List<String> cartas) {
        HBoxCartsContainer.getChildren().clear();  // Limpia antes de añadir nuevas
        for (String carta : cartas) {
            Image image = new Image(getClass().getResource(carta).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(60);
            imageView.setPreserveRatio(true);
            HBoxCartsContainer.getChildren().add(imageView);
        }
    }
}
