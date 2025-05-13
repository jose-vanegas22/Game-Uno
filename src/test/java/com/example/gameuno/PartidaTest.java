package com.example.gameuno;

import com.example.gameuno.Models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PartidaTest {

    @Test
    public void testVerificarGandor(){
        Partida partida = new Partida();
        JugadorPersona jugadorPersona = new JugadorPersona("Jugador Persona");
        JugadorMaquina jugadorMaquina =  new JugadorMaquina("Jugador Maquina");
        ArrayList<Carta> manoMaquina = new ArrayList<>();
        ArrayList<Carta> manoJugador = new ArrayList<>();

        Carta carta = new Carta("red", "2", "2_red.png");
        Carta carta2 = new Carta("red", "2", "2_red.png");
        Carta carta3 = new Carta("red", "2", "2_red.png");
        Carta carta4 = new Carta("red", "2", "2_red.png");

        manoMaquina.add(carta);
        manoMaquina.add(carta2);
        manoMaquina.add(carta3);
        manoMaquina.add(carta4);

        jugadorPersona.SetMano(manoJugador);
        jugadorMaquina.SetMano(manoMaquina);

        partida.setJugadorPersona(jugadorPersona);
        partida.setJugadorMaquina(jugadorMaquina);

        Jugador ganador = partida.verificarGanador();

        Assertions.assertEquals(jugadorPersona, ganador, "El jugadorPersona deberia de ser el ganador");
    }
}
