package com.example.gameuno.Models;

import java.util.Stack;

public class MesaDeJuego {

    Stack<Carta> cartasJugadas = new Stack<>();

    //Este metodo lo que hace es poner o agregar una carta en la pila o mesa en este caso
    public void colocarCarta(Carta carta) {
        cartasJugadas.push(carta);
    }

    //Este metodo lo que hace es devolver la ultima carta que se puso en la pila
    //Se pone public Carta porque va a retornar un objeto tipo carta
    public Carta getCartaSuperior(){
        if (!cartasJugadas.isEmpty()){
            return cartasJugadas.pop();
        }
        return null;
    }

}
