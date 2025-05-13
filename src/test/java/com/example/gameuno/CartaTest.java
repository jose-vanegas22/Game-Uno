package com.example.gameuno;

import com.example.gameuno.Models.Carta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class CartaTest {

    @Test
    public void testCartaCederTurno(){
        Carta carta = new Carta("red", "skip", "null");
        Assertions.assertTrue(carta.cartaCederTurno());
    }

    @Test
    public void testCartaCederTurno2(){
        Carta carta = new Carta("red", "2", "null");
        Assertions.assertFalse( carta.cartaCederTurno());
    }
}
