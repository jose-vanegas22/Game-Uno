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
    private int turnoActual = 0; // 0 = JugadorPersona, 1 = JugadorMaquina
    private boolean partiaIniciada = false; // Variable para saber si la partida esta en curso o termino


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


    /**
     * This method allows any other class to request the player without creating new instances
     * @return
     */
    public JugadorPersona getJugadorPersona() {
        for (Jugador jugador : jugadores) { // Recorre toda la lista de jugadores
            if (jugador instanceof JugadorPersona) { // Aqui se pregunta si jugador es una instancia de JugadorPersona
                return (JugadorPersona) jugador; // Hace un casteo, fuerza a java a entender que jugador es de tipo JugadorPersona, a Java le toca confiar
            }
        }
        return null; // No encontrado
    }


    public JugadorMaquina getJugadorMaquina() {
        for (Jugador jugador : jugadores) {
            if (jugador instanceof JugadorMaquina) {
                return (JugadorMaquina) jugador;
            }
        }
        return null;
    }


    //- - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Metodos que sirven para los turnos en el juego

    public boolean turnoJugadorPersona(Carta carta){
        JugadorPersona jugadorPersona = getJugadorPersona();
       // if (!esTurnoHumano() || !partiaIniciada) return false;

        if (mesa.ponerCartaColorNumero(carta)){
            mesa.colocarCarta(carta);
            System.out.println("DEBUG - Mano antes de eliminar: " + jugadorPersona.getMano());
            jugadorPersona.getMano().remove(carta);
            System.out.println("DEBUG - Mano despues de eliminar: " + jugadorPersona.getMano());
            //pasarTurno();
            return true;
        }
        return false;
    }


    public Carta robarCartaJugadorPersona(){

        if (esTurnoHumano() && partiaIniciada && !mazoUno.isEmpty()) {
            Carta cartaRobada = mazoUno.robarCarta();
            JugadorPersona jugadorPersona = getJugadorPersona();

            if(jugadorPersona != null) {
                jugadorPersona.recibirCarta(cartaRobada);

                System.out.println("Mano del jugadorPersona antes de robar: " + jugadorPersona.getMano());
                System.out.println("Carta robada: " + cartaRobada);

                pasarTurno(); // Cuando se saca una carta del mazo de una se cambia el turno

                System.out.println("Mano del jugadorPersona despues de robar: " +  jugadorPersona.getMano());
                return cartaRobada;

            }
        }
        return null;
    }

    public Carta robarCartaJugadorPersona2(){
        Carta cartaRobada = mazoUno.robarCarta();

        JugadorPersona jugadorPersona = getJugadorPersona();
        jugadorPersona.recibirCarta(cartaRobada);

        System.out.println("Carta robada: " + cartaRobada);
        System.out.println("Nueva mano: " + jugadorPersona.getMano());

        return cartaRobada;
    }

    public void turnoMaquina(){
        if(!esTurnoMaquina() || !partiaIniciada) return;

        JugadorMaquina jugadorMaquina = getJugadorMaquina();
        Carta cartaCentral = mesa.getCartaSuperior();
        Carta cartaJugada = jugadorMaquina.jugarCarta(cartaCentral);

        if(cartaJugada != null) {
           mesa.colocarCarta(cartaJugada);
        } else{
            Carta cartaRobada = mazoUno.robarCarta();
            jugadorMaquina.recibirCarta(cartaRobada);
            System.out.println("[Máquina] Robó carta: " + cartaRobada);
        }
        pasarTurno();
    }


    private void pasarTurno(){
        turnoActual = 1 - turnoActual; // Metodo que cambia a otro turno entre 0 y 1
    }



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Metodos para consultar a quien le toca el turno

    public boolean esTurnoHumano(){
        return turnoActual == 0;
    }

    public boolean esTurnoMaquina(){
        return turnoActual == 1;
    }


}