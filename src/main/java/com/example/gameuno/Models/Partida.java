package com.example.gameuno.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Partida {

    private MazoUno mazoUno; //Instancia de MazoUno
    private List<Jugador> jugadores;
    private Carta cartaCentral;
    private MesaDeJuego mesa;


    public Partida() {
        mazoUno = new MazoUno();  // Crear y preparar el mazo
        jugadores = new ArrayList<>(); //Lista vacia donde se almacenan los jugadores
        mesa = new MesaDeJuego();
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    //Este metodo reparte las cartas de los jugadores que estan en la lista, en este caso a la persona
    //y a la maquina
    public void repartirCartasIniciales(int cantidad) { //Debe recibir una cantidad de cartas
        for (Jugador jugador : jugadores) { //Recorre toda la lista de jugadores para asignarle 5 cartas a c/u
            for (int i = 0; i < cantidad; i++) {
                if (!mazoUno.getMazo().isEmpty()) { //Si el mazo no esta vacio
                    jugador.recibirCarta(mazoUno.getMazo().pop());
                }
            }
        }
    }

    public void iniciarPartida() {
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

    public void colocarCartaInicial(){
        if (!mazoUno.getMazo().isEmpty()){
            mesa.colocarCarta(mazoUno.getMazo().pop());
        }
    }

    public Carta getCartaCentral(){
        return mesa.getCartaSuperior();
    }

    public MazoUno getMazoUno(){
        return mazoUno;
    }






}