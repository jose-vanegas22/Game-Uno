package com.example.gameuno.Models;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private String nombre;
    private List<Carta> mano; //Lista la cual guarda las cartas que tiene el jugador

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    //Este metodo sirve para añadir una carta a la mano del jugador
    public void recibirCarta(Carta carta) {
        this.mano.add(carta);
    }

    public String getNombre() {
        return nombre;
    }

    //El metodo muestra las cartas que tiene el jugador en mano
    public List<Carta> getMano() {
        return mano;
    }



}
