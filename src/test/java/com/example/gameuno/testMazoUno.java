package com.example.gameuno;

import com.example.gameuno.Models.Carta;
import com.example.gameuno.Models.MazoUno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Stack;

public class testMazoUno {

    @Test
    public void testMazoVacioRecargarMazoCartasMeza(){
        MazoUno mazo = new MazoUno();
        Stack<Carta> cartasJugadas = new Stack<>();

        mazo.setMazo(new Stack<>());


        Carta carta = new Carta("blue", "5", "5_blue.png");
        Carta carta1 = new Carta("red", "5", "5_red.png");
        Carta carta2 = new Carta("green", "3", "3_green.png");
        Carta cartaFinal = new Carta("yellow", "1", "1_yellow.png");

        cartasJugadas.add(carta);
        cartasJugadas.add(carta1);
        cartasJugadas.add(carta2);
        cartasJugadas.add(cartaFinal);

        mazo.mazoVacioRecargarMazoCartasMeza(cartasJugadas);

        Assertions.assertEquals(1, cartasJugadas.size(), "Solo debe quedar una carta en la mesa de cartas jugadas");
    }


    @Test
    public void testMazoVacioRecargarMazoCartasMeza2(){
        MazoUno mazo = new MazoUno();
        Stack<Carta> cartasJugadas = new Stack<>();

        mazo.setMazo(new Stack<>());


        Carta carta = new Carta("blue", "5", "5_blue.png");
        Carta carta1 = new Carta("red", "5", "5_red.png");
        Carta carta2 = new Carta("green", "3", "3_green.png");
        Carta cartaFinal = new Carta("yellow", "1", "1_yellow.png");

        cartasJugadas.add(carta);
        cartasJugadas.add(carta1);
        cartasJugadas.add(carta2);
        cartasJugadas.add(cartaFinal);

        mazo.mazoVacioRecargarMazoCartasMeza(cartasJugadas);

        Assertions.assertEquals(3, mazo.cantidadCartas(), "El mazo deberia de tener las otras 3 cartas");
    }


    @Test
    public void testRobarCarta(){
        MazoUno mazoUno = new MazoUno();
        Stack<Carta> mazo = new Stack<>();

        Carta carta = new Carta("blue", "5", "5_blue.png");
        Carta carta1 = new Carta("red", "5", "5_red.png");
        Carta carta2 = new Carta("green", "3", "3_green.png");
        Carta cartaFinal = new Carta("yellow", "1", "1_yellow.png");

        mazo.add(carta);
        mazo.add(carta1);
        mazo.add(carta2);
        mazo.add(cartaFinal);

        mazoUno.setMazo(mazo); // Se asigna el mazo creado con este set se modifica el mazo

        mazoUno.robarCarta();

        Assertions.assertEquals(3,mazo.size(), "El mazo deberia de solo tener 3 cartas porque ya se saco una");

    }

    @Test
    public void testPrepararMazo(){
        MazoUno mazo = new MazoUno();

        int totalMazo = mazo.getMazo().size();

        Assertions.assertTrue(totalMazo > 0);
    }
}
