package com.example.gameuno.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Partida {

    private MazoUno mazo;
    private List<Jugador> jugadores;

    public Partida() {
        mazo = new MazoUno();  // Crear y preparar el mazo
        jugadores = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void repartirCartasIniciales(int cantidad) {
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < cantidad; i++) {
                if (!mazo.getMazo().isEmpty()) {
                    jugador.recibirCarta(mazo.getMazo().pop());
                }
            }
        }
    }

    public void iniciarPartida() {
        // Reparte 5 cartas a cada jugador
        repartirCartasIniciales(5);

        // Mostrar las cartas de cada jugador en consola
        for (Jugador jugador : jugadores) {
            System.out.println("Cartas de " + jugador.getNombre() + " (" + jugador.getClass().getSimpleName() + "):");
            for (Carta carta : jugador.getMano()) {
                System.out.println(carta);
            }
            System.out.println("--------------------------");
        }


    }
}