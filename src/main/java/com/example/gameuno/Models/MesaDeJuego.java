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
        System.out.println("Cartas de la pila: " + cartasJugadas);
    }

    /**
     * This method returns the last card placed on the stach
     * @return
     */
    //Este metodo lo que hace es devolver la ultima carta que se puso en la pila
    //Se pone public Carta porque va a retornar un objeto tipo carta
    public Carta getCartaSuperior(){
        if (!cartasJugadas.isEmpty()){
            return cartasJugadas.peek(); // La retorna, la muestra pero no la borra
        }
        return null;
    }

    //Este metodo lo que hace es igualar el color y numero de la carta nueva con la anterior para saber
    //si se puede colocar o no
    public boolean ponerCartaColorNumero(Carta cartaActual){
        Carta cartaSuperior = getCartaSuperior();

        String colorCentral;
        if (cartaSuperior.getColor().equals("negro") && cartaSuperior.getColorTemporal() != null){
            colorCentral = cartaSuperior.getColorTemporal();
        } else if(cartaSuperior.getColor().equals("negro") && cartaSuperior.getColorTemporalMas4() != null){
            colorCentral =  cartaSuperior.getColorTemporalMas4();
        } else {
            colorCentral = cartaSuperior.getColor();
        }


        System.out.println("Comparando: " +
                "Centro=" + cartaSuperior.getValor() + "_" + cartaSuperior.getColor() + " vs " +
                "Jugada=" + cartaActual.getValor() + "_" + cartaActual.getColor());

        return cartaActual.getColor().equals("negro") ||
                cartaActual.getColor().equals(colorCentral) ||
                cartaActual.getValor().equals(cartaSuperior.getValor());
    }





    // Permite acceder a la pila desde otras clases
    public Stack<Carta> getCartasJugadas() {
        return cartasJugadas;
    }


    public Stack<Carta> cartasQueSePuedenReciclar() {
        Stack<Carta> cartasRecicladas =  new Stack<>();
        cartasRecicladas.addAll( cartasJugadas );
        if (!cartasRecicladas.isEmpty()){
            cartasRecicladas.pop(); // Elimina la ultima carta de la pila
        }

        // Recorre toda la lista de las cartas recicladas para restaurar el color de las necesarias
        for (Carta carta : cartasRecicladas){
            if (carta.getValor().equals("cambioColor") || carta.getValor().equals("+4")){
                System.out.println("----------------------------------------------------------------");
                System.out.println("----------------------------------------------------------------");
                System.out.println("----------------------------------------------------------------");
                System.out.println("Antes de modificar: " + carta.getValor() + "_" + carta.getColor());
                carta.restaurarColorCarta();
                System.out.println("Despues de modificar: " + carta.getValor() + "_" + carta.getColor());
                System.out.println("----------------------------------------------------------------");
                System.out.println("----------------------------------------------------------------");
                System.out.println("----------------------------------------------------------------");
            }
        }


        return cartasRecicladas;
    }

}
