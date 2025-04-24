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
        String[] colores2 = {"wild_draw_blue", "wild_draw_green", "wild_draw_red", "wild_draw_yellow"};
        String[] valores2 = {"2"};
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


        //Esta parte añade una carta de reserved por color
        String[] colores4 = {"blue", "green", "red", "yellow"};
        String[] valores4 = {"reserve"};
        for (String color : colores4) {
            for (String valor : valores4) {
                String nombreArchivo = valor + "_" + color + ".png";
                mazo.push(new Carta(color, valor, nombreArchivo));
            }
        }

        //Esta parte añade 4 cartas +4 al mazo
        String[] colores5 = {"wild_draw"};
        String[] valores5 = {"4"};
        for (String color : colores5) {
            for (String valor : valores5) {
                for (int i = 0; i < 4; i++) {
                    String nombreArchivo = valor + "_" + color + ".png";
                    mazo.push(new Carta(color, valor, nombreArchivo));
                }
            }
        }


        //Esta parte añade 4 cartas de cambio de color
        String[] colores6 = {"wild"};
        String[] valores6 = {"1"};
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


    public boolean isEmpty() {
        return true;
    }
}


