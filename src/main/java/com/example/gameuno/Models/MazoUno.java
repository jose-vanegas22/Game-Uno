package com.example.gameuno.Models;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.Stack;

public class MazoUno {


        Stack<Carta> mazo = new Stack<>();

    public MazoUno() {
        mazo = new Stack<>();
        prepararMazo();
        Collections.shuffle(mazo);
    }

        //Aqui creo un objeto de la clase jugador
       // JugadorPersona persona = new JugadorPersona("Jugador");


        //En esta parte lo que hago es repartir 5 cartas para cada uno
      //  for(int i = 0; i < 5; i++){
        //    persona.recibirCarta(mazo.pop());
        //}


    private void prepararMazo() {

        String[] colores = {"blue", "green", "red", "yellow"};
        String[] valores = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        //Esta parte crea las cartas numericas normales
        for (String color : colores) {
            for (String valor : valores) { //Recorre ambas listas y las combina ej(blue 1)
                String nombreArchivo = valor + "_" + color + ".png";
                mazo.push(new Carta(color, valor, nombreArchivo)); //Mete la carta creada en la pila

            }
        }

        //Añadir la carta +2 dos veces por cada color
        String[] colores2 = {"wild_draw_blue", "wild_draw_green", "wild_draw_red", "wild_draw_yellow"};
        String[] valores2 = {"2"};

        for (String color : colores2) {
            for (String valor : valores2) {
                String nombreArchivo = valor + "_" + color + ".png";
                mazo.push(new Carta(color, valor, nombreArchivo));
                mazo.push(new Carta(color, valor, nombreArchivo));
            }
        }

        //Añadir una carta de ceder turno por color
        String[] colores3 = {"blue", "green", "red", "yellow"};
        String[] valores3 = {"skip"};
        for (String color : colores3) {
            for (String valor : valores3) {
                String nombreArchivo = valor + "_" + color + ".png";
                mazo.push(new Carta(color, valor, nombreArchivo));
            }
        }


        //Añadir una carta de reserved por color
        String[] colores4 = {"blue", "green", "red", "yellow"};
        String[] valores4 = {"reserve"};
        for (String color : colores4) {
            for (String valor : valores4) {
                String nombreArchivo = valor + "_" + color + ".png";
                mazo.push(new Carta(color, valor, nombreArchivo));
            }
        }

        //Añadir 4 cartas +4 al mazo
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


        //Añadir 4 cartas de cambio de color
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
    }

    public Stack<Carta> getMazo() {
        return mazo;
    }



    }


