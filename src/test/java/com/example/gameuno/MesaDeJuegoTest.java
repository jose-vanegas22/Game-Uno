package com.example.gameuno;

import com.example.gameuno.Models.Carta;
import com.example.gameuno.Models.MesaDeJuego;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MesaDeJuegoTest {


    @Test
    public void testPonerCartaColorNumero(){
        MesaDeJuego mesa = new MesaDeJuego();
        Carta cartaCentral = new Carta("blue", "2", "2_blue.png");
        Carta cartaJugador = new Carta("red", "2", "2_red.png");

        mesa.colocarCarta(cartaCentral);

        Assertions.assertTrue(mesa.ponerCartaColorNumero(cartaJugador));
    }

    @Test
    public void testPonerCartaColorNumero2(){
        MesaDeJuego mesa = new MesaDeJuego();
        Carta cartaCentral = new Carta("blue", "2", "2_blue.png");
        Carta cartaJugador = new Carta("red", "3", "3_red.png");

        mesa.colocarCarta(cartaCentral);

        Assertions.assertFalse(mesa.ponerCartaColorNumero(cartaJugador));
    }
}
