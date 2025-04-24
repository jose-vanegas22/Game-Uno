package com.example.gameuno.Models;

import java.util.Stack;

/**
 * This class MesaDeJuego contains a stack where the cards being played a stored
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class MesaDeJuego {

    Stack<Carta> cartasJugadas = new Stack<>();

    /**
     * This method adds a card to the stack
     * @param carta
     */
    //Este metodo lo que hace es poner o agregar una carta en la pila o mesa en este caso
    public void colocarCarta(Carta carta) {
        cartasJugadas.push(carta);
    }

    /**
     * This method returns the last card placed on the stach
     * @return
     */
    //Este metodo lo que hace es devolver la ultima carta que se puso en la pila
    //Se pone public Carta porque va a retornar un objeto tipo carta
    public Carta getCartaSuperior(){
        if (!cartasJugadas.isEmpty()){
            return cartasJugadas.pop();
        }
        return null;
    }

}
