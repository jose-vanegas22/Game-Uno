package com.example.gameuno.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class Partida contains everything to the game's mechanics or logic
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class Partida {

    private MazoUno mazoUno; //Instancia de MazoUno
    private List<Jugador> jugadores;
    private Carta cartaCentral;
    private MesaDeJuego mesa;


    /**
     *This constructor initializes the main components when a new Partida object
     * is created
     */
    public Partida() {
        mazoUno = new MazoUno();  // Crear y preparar el mazo
        jugadores = new ArrayList<>(); //Lista vacia donde se almacenan los jugadores
        mesa = new MesaDeJuego();
    }

    /**
     * This method adds players to the list of players
     * @param jugador
     */
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    /**
     *This method deals the initial cards to the players
     * @param cantidad
     */
    //Este metodo reparte las cartas de los jugadores que estan en la lista, en este caso a la persona
    //y a la maquina
    public void repartirCartasIniciales(int cantidad) { //Debe recibir una cantidad de cartas
        for (Jugador jugador : jugadores) { //Recorre toda la lista de jugadores para asignarle 5 cartas a c/u
            for (int i = 0; i < cantidad; i++) {
                if (!mazoUno.getMazo().isEmpty()) { //Si el mazo no esta vacio
                    Carta cartaRepartida = mazoUno.getMazo().pop();
                    jugador.recibirCarta(cartaRepartida); //.pop saca una carta y la elimina de la pila
                    System.out.println("Carta repartida a " + jugador.getNombre() + ": " + cartaRepartida);
                }
            }
        }
    }

    /**
     *
     */
    public void iniciarPartida() {

        System.out.println("Cartas en el mazo antes de repartir: " + mazoUno.getMazo().size());


        // Reparte 5 cartas a cada jugador
        repartirCartasIniciales(5);


        // Mostrar las cartas de cada jugador en consola
        for (Jugador jugador : jugadores) {
            System.out.println("Cartas de " + jugador.getNombre() + " (" + jugador.getClass().getSimpleName() + "):");
            for (Carta carta : jugador.getMano()) {
                System.out.println(carta);
            }
            System.out.println("--------------------------");
        }


    }

    /**
     *This method is used to place the first card from the deck on the table
     */
    public void colocarCartaInicial(){
        if (!mazoUno.getMazo().isEmpty()){
            mesa.colocarCarta(mazoUno.getMazo().pop());
        }
    }

    /**
     * This method returns the last card added to the tablet's stack
     * @return
     */
    public Carta getCartaCentral(){

        return mesa.getCartaSuperior();
    }

    /**
     *This method returns the created mazoUno object
     * @return
     */
    public MazoUno getMazoUno(){

        return this.mazoUno;
    }

    public MesaDeJuego getMesa(){
        return mesa;
    }






}