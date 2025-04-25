package com.example.gameuno.Models;

/**
 * This class JugadorMaquina extends the Jugador class and calls the parent constructor using super
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class JugadorMaquina extends Jugador {

    //Este constructor recibe nombre como argumentos y super lo que hace es llamar al constructor de la clase
    //padre y le pasa el nombre
    public JugadorMaquina(String nombre) {
        super(nombre);
    }
}
