package com.example.gameuno.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class Jugador contains its attributes such a nombre and a list of cards
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class Jugador {

    private String nombre;
    private List<Carta> mano; //Lista la cual guarda las cartas que tiene el jugador
    private boolean UNOState;


    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.UNOState = false;
    }

    /**
     *This method is used to add a card to the player's hand
     * @param carta
     */
    //Este metodo sirve para añadir una carta a la mano del jugador
    public void recibirCarta(Carta carta) {
        this.mano.add(carta);
    }

    public String getNombre() {
        return nombre;
    }

    /**
     *This method dissplays the cards the player has in their hand
     * @return
     */
    //Este metodo muestra las cartas que tiene el jugador en mano
    public List<Carta> getMano() {
        return mano;
    }

    public void setUnoState(boolean unoState) {
        this.UNOState = unoState;
    }

    public List<Carta> SetMano(List<Carta> mano) {
        return this.mano = mano;
    }

    public int getManoSize() {
        return this.mano.size();
    }

    public boolean getUnoState() {
        return UNOState;
    }
}
