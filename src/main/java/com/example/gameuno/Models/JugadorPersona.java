package com.example.gameuno.Models;

/**
 * This class JugadorPersona extends the Jugador class and calls constructor usign super
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class JugadorPersona extends Jugador{

    //Constructor el cual tiene nombre como argumento y super lo que hace es llamar al
    //constructor padre y pasarle el nombre
    public JugadorPersona(String nombre) {
        super(nombre);
    }


}
