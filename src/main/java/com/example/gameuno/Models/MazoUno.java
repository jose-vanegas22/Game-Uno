package com.example.gameuno.Models;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.Stack;

/**
 *This class MazoUno contains a stack that stores the cards
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class MazoUno {


    Stack<Carta> mazo = new Stack<>(); //Pila la cual contiene el maso, funciona con logica LIFO

    public MazoUno() {
        mazo = new Stack<>(); //Aqui se inicializa la pila
        prepararMazo(); //Se crea el mazo
    }


    /**
     * This method prepares the deck by storing the location of the cards, combining the contents of two
     * arrays, and using two nested for-each loops to create all possible combinations
     */
    //Este metodo es el que crear el mazo, guardando cada combinacion posible y aceptable en la pila mazo
    private void prepararMazo() {


        //Esta parte crea las cartas numericas normales
        String[] colores = {"blue", "green", "red", "yellow"}; //Arreglo de colores
        String[] valores = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}; //Arreglo de valores
        for (String color : colores) { //For-each tipo, variable, coleccion
            for (String valor : valores) { //Recorre ambas listas y las combina ej(blue 1)
                String nombreArchivo = valor + "_" + color + ".png"; //Guarda los valores como una direccion
                mazo.push(new Carta(color, valor, nombreArchivo)); //Crear un objeto carta y la guarda en la pila

            }
        }




        //Esta parte añade la carta +2 dos veces por cada color
        String[] colores2 = {"blue", "green", "red", "yellow"};
        String[] valores2 = {"+2"};
        for (String color : colores2) {
            for (String valor : valores2) {
                String nombreArchivo = valor + "_" + color + ".png";
                mazo.push(new Carta(color, valor, nombreArchivo));
                mazo.push(new Carta(color, valor, nombreArchivo));
            }
        }


        //Esta parte añade una carta de ceder turno por color
        String[] colores3 = {"blue", "green", "red", "yellow"};
        String[] valores3 = {"skip"};
        for (String color : colores3) {
            for (String valor : valores3) {
                String nombreArchivo = valor + "_" + color + ".png";
                mazo.push(new Carta(color, valor, nombreArchivo));
            }
        }



        /**
        //Esta parte añade una carta de reserved por color
        String[] colores4 = {"blue", "green", "red", "yellow"};
        String[] valores4 = {"reserve"};
        for (String color : colores4) {
            for (String valor : valores4) {
                String nombreArchivo = valor + "_" + color + ".png";
                mazo.push(new Carta(color, valor, nombreArchivo));
            }
        }
         **/



        //Esta parte añade 4 cartas +4 al mazo
        String[] colores5 = {"negro"};
        String[] valores5 = {"+4"};
        for (String color : colores5) {
            for (String valor : valores5) {
                for (int i = 0; i < 4; i++) {
                    String nombreArchivo = valor + "_" + color + ".png";
                    mazo.push(new Carta(color, valor, nombreArchivo));
                }
            }
        }



        //Esta parte añade 4 cartas de cambio de color
        String[] colores6 = {"negro"};
        String[] valores6 = {"cambioColor"};
        for (String color : colores6) {
            for (String valor : valores6) {
                for (int i = 0; i < 4; i++) {
                    String nombreArchivo = valor + "_" + color + ".png";
                    mazo.push(new Carta(color, valor, nombreArchivo));
                }
            }
        }


        //Al final cuando ya la pila esta creada aqui se reorganiza de forma aleatoria
        Collections.shuffle(mazo);
    }

    //Metodo el cual devuelve la pila que se creo
    public Stack<Carta> getMazo() {
        return mazo;
    }

    // Retorna true si el mazo esta vacio
    public boolean isEmpty() {
        return this.mazo.isEmpty();
    }

    /**
     * This method draws the top card from the deck, used when a player needs to draw a card
     *
     * @return mazo.pop()
     */
    // Este metodo saca la ultima carta del mazo, necesario para cuando se necesita comer una carta del mazo
    public Carta robarCarta() {
        return this.mazo.pop(); // Saca la carta de la pila y la elimina
    }

    public int cantidadCartas() {
        return this.mazo.size();
    }

    /**
     * When deck is empty, this method reshuffles played cards into it except the last table card
     *
     * @param cartasJugadas
     */
    // Este metodo sirve para recargar la pila del mazo con las cartas jugadas en la pila de la mesa cuando
    // el mazo se encuentra vacio pero vuelve a poner la ultima carta que tenia la mesa
    public void mazoVacioRecargarMazoCartasMeza(Stack<Carta> cartasJugadas) {
        if(isEmpty() && cartasJugadas.size() > 0){ // Metodo boolean isEmpty y asegura que existan cartas en la pila cartas recicladas


            // Guarda la ultima carta de la mesa y la elimina de esa pila (Se hace para luego de que se
            // quiten todas las cartas volverla a poner en la pila MesaDeJuego)
            Carta ultimaCarta = cartasJugadas.pop();

            // Mueve las cartas de la mesa al mazo una por una y las elimina hasta que la mesa quede vacia
            while (!cartasJugadas.isEmpty()){
                Carta  carta = cartasJugadas.pop();
                // Restaura las cartas cambiarColor y +4 a su color origina negro antes de volverlas a ingresar al mazo
                if (carta.getValor().equals("cambioColor") || carta.getValor().equals("+4")){
                    carta.restaurarColorCarta();
                }
                this.mazo.push(cartasJugadas.pop());
            }

            //
            cartasJugadas.push(ultimaCarta);

            // Por ultimo baraja las cartas
            Collections.shuffle(this.mazo);

            System.out.println("------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------");
            System.out.println("Se recargo el mazo con las cartas de la mesa de manera exitosa!!!!");
            System.out.println("Esta es la carta que se vuelve a poner en el centro: " + ultimaCarta);
            System.out.println("------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------");
            System.out.println("------------------------------------------------------------------");
            }
        }
    }



