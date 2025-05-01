package com.example.gameuno.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class JugadorMaquina extends the Jugador class and calls the parent constructor using super
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class JugadorMaquina extends Jugador {

    private Random random = new Random();

    //Este constructor recibe nombre como argumentos y super lo que hace es llamar al constructor de la clase
    //padre y le pasa el nombre
    public JugadorMaquina(String nombre) {

        super(nombre);
    }

    public Carta jugarCarta(Carta cartaCentral){
        List<Carta> cartasPermitidas = new ArrayList<>(); // En esta es una sublista de todas las cartas
                                                          // que tiene la maquina, se guardan las cartas que puede jugar

        for(Carta carta : this.getMano()){ // Recorre toda la lista (mano) de la maquina

            if(carta.getColor().equals(cartaCentral.getColor()) ||
            carta.getValor().equals(cartaCentral.getValor())){
                cartasPermitidas.add(carta); // Si las cartas que tienen alguna coincide la guarda en lista cartasPermitidas
            }

        }

        // Si hay alguna carta en la lista de permitidas juega una de esas al azar
        if (!cartasPermitidas.isEmpty()){
            Carta cartaJugada = cartasPermitidas.get(random.nextInt(cartasPermitidas.size()));
            System.out.println("La carta que la maquina acaba de jugar es: " + cartaJugada);
            this.getMano().remove(cartaJugada);
            return cartaJugada;
        }

        return null; // Si no tiene cartas para jugar retorna este null que funciona para que robe cartas del mazo
    }



}
