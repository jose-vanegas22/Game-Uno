package com.example.gameuno;

import com.example.gameuno.Models.Carta;
import com.example.gameuno.Models.Jugador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JugadorTest {


    @Test
    public void testRecibirCarta(){
        Jugador jugador = new Jugador("Jugador");

        Carta carta = new Carta("blue", "2", "2_blue.png");
        jugador.recibirCarta(carta);
        jugador.recibirCarta(carta);

        Assertions.assertEquals(2, jugador.getMano().size());
    }


    @Test
    public void testRecibirCarta2(){
        Jugador jugador = new Jugador("Jugador");
        Carta carta = new Carta("blue", "2", "2_blue.png");
        jugador.recibirCarta(carta);
        Assertions.assertNotEquals(2, jugador.getMano().size());
    }

}
