package com.example.gameuno.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private MesaDeJuego mesa;
    private int turnoActual = 0; // 0 = JugadorPersona, 1 = JugadorMaquina
    private boolean partiaIniciada = false; // Variable para saber si la partida esta en curso o termino
    private String ColorEscogidoMaquina; // Variable para guardar y mostrar el color escogigo por la maquina en la carta cambio de color
    private String ColorEscogidoMaquinaMas4; // Variable para guardar y mostrar el color escogido por la maquina en la carta +4

    //private int winner;
    private JugadorPersona jugadorPersona;
    private JugadorMaquina jugadorMaquina;

    /**
     * This constructor initializes the main components when a new Partida object
     */
    public Partida() {
        mazoUno = new MazoUno();  // Crear y preparar el mazo
        jugadores = new ArrayList<>(); //Lista vacia donde se almacenan los jugadores
        mesa = new MesaDeJuego();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Parte que gestiona los jugadores

    /**
     * This method adds players to the list of players
     *
     * @param jugador
     */
    // Permite añadir los jugadores a la lista o partida en este caso solo serian 2 jugadorPersona y jugadorMaquina
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    /**
     * This method allows any other class to request the player without creating new instances
     *
     * @return jugador
     */
    // Busca en la lista de jugadores al que sea una instancia de JugadorPersona, garantiza de siempre trabajar con el objeto correcto
    public JugadorPersona getJugadorPersona() {
        for (Jugador jugador : jugadores) { // Recorre toda la lista de jugadores
            if (jugador instanceof JugadorPersona) { // Aqui se pregunta si jugador es una instancia de JugadorPersona
                return (JugadorPersona) jugador; // Hace un casteo, fuerza a java a entender que jugador es de tipo JugadorPersona, a Java le toca confiar
            }
        }
        return null; // No encontrado
    }

    public void setJugadorPersona(JugadorPersona jugador) {
        this.jugadorPersona = jugador;
        this.jugadores.add(jugador);
    }

    /**
     * This method allows any other class to request the machine without creating new instances
     *
     * @return jugador
     */
    // Busca en la lista de jugadores al que sea una instancia de JugadorMaquina, garantiza que siempre se use el mismo objeto
    public JugadorMaquina getJugadorMaquina() {
        for (Jugador jugador : jugadores) {
            if (jugador instanceof JugadorMaquina) {
                return (JugadorMaquina) jugador;
            }
        }
        return null;
    }

    public void setJugadorMaquina(JugadorMaquina jugador) {
        this.jugadorMaquina = jugador;
        this.jugadores.add(jugador);
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Aqui estan todos los metodos sobre el inicio de partida

    /**
     * This method deals the initial cards to the players
     *
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
     * This method prepares the game by dealing 5 cards to each player, placing the first card on the table,
     * and starting the match
     */
    // Este metodo lo que hace es preparar el juego repartiendo 5 cartas a cada jugador, colocando la
    // primera carta en la mesa e iniciando la partida
    public void iniciarPartida() {
        System.out.println("Cantidad de cartas en el mazo antes de repartir: " + mazoUno.getMazo().size());

        // Reparte 5 cartas a cada jugador
        repartirCartasIniciales(5);
        colocarCartaInicial();
        partiaIniciada = true;

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
     * This method is used to place the first card from the deck on the table
     */
    // Este metodo lo que hace es que si no esta vacio el mazo en la pila mesa pone la ultima carta de la pila mazo
    public void colocarCartaInicial() {
        if (!mazoUno.getMazo().isEmpty()) {
            mesa.colocarCarta(mazoUno.getMazo().pop());
        }
    }

    /**
     * This method returns the last card added to the tablet's stack
     *
     * @return
     */
    // Este metodo devuelve la carta que esta en la mesa, la ultima
    public Carta getCartaCentral() {
        return mesa.getCartaSuperior();
    }

    //- - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Metodos que sirven para las mecanicas de los turnos, lo que se hace en el turno de cada jugador

    /**
     * This method runs when it's the jugadorPersona turn (to play a card)
     *
     * @param carta
     * @return true or false
     */
    // Este metodo se ejecuta cuando llega el turno del jugadorPersona (para jugar una carta)
    public boolean turnoJugadorPersona(Carta carta){
        JugadorPersona jugadorPersona = getJugadorPersona(); // Se crea una referencia del unico objeto que puede existir
        if (!esTurnoJugadorPersona() || !partiaIniciada) return false; // Para que JugadorPersona no pueda jugar

        if (mesa.ponerCartaColorNumero(carta)){ // Evalua si se puede poner una carta (valor o color y la nueva version con los comodines)
            // Si se puede poner se ejecuta el codigo de abajo
            mesa.colocarCarta(carta); // Pone la carta jugada en la mesa
            System.out.println("Mano antes de eliminar: " + jugadorPersona.getMano());
            jugadorPersona.getMano().remove(carta); // Remueve o elimina la carta de la mano del jugadorPersona esta solo es la parte logica
            System.out.println("Mano despues de eliminar: " + jugadorPersona.getMano());

            // Si se uso una cartaMas2 aplica el efecto
            if (carta.cartaMas2()){
                efectoCartaMas2();
            }

            // Si se uso una cartaCederTurno se aplica el efecto
            if (carta.cartaCederTurno()){
                efectoCartaCederTurno();
            }

            // Si se uso una cartaMas4 se aplica el efecto
            if (carta.cartaMas4()) {
                efectorCartaMas4();
            }

            return true; // Para saber que toda la jugada se completo y fue exitosa
        }
        return false; // Para saber que la carta no se pudo poner porque no cumplio con las reglas
    }

    /**
     * This method is used to draw a card from the deck when needed for the jugadorPersona
     *
     * @return cartaRobada or null
     */
    // Este metodo sirve para agarrar una carta del mazo cuando se necesite en el caso de jugadorPersona
    public Carta robarCartaJugadorPersona(){

        if (esTurnoJugadorPersona() && partiaIniciada) {

            if (mazoUno.isEmpty()){ // Si el mazo esta vacio las recicla o sea pone las cartas de la mesa menos las ultimas en el mazo
                mazoUno.mazoVacioRecargarMazoCartasMeza(mesa.getCartasJugadas());
            }

            if (!mazoUno.isEmpty()) {
                Carta cartaRobada = mazoUno.robarCarta(); // Saca una carta del mazo y esa carta se guarda en la variable
                JugadorPersona jugadorPersona = getJugadorPersona(); // Se crea referencia del unico objeto que puede existir

                if (jugadorPersona != null) {
                    System.out.println("Mano del jugadorPersona antes de robar: " + jugadorPersona.getMano());
                    jugadorPersona.recibirCarta(cartaRobada); // Se añade la cartaRobada en la mano del jugadorPersona
                    System.out.println("Carta robada: " + cartaRobada);
                    System.out.println("Mano del jugadorPersona despues de robar: " + jugadorPersona.getMano());
                    return cartaRobada;
                }
            }
        }
        return null;
    }

    /**
     * This runs when it's the jugadorMaquina turn to play its cards
     *
     * @return true or false
     */
    // Se ejecuta cuando llega el turno de JugadorMaquina (Para jugar sus cartas)
    public boolean turnoJugadorMaquina(){
        if(!esTurnoJugadorMaquina() || !partiaIniciada) return false; // Si se cumple retorna false y no se ejecuta el resto del codigo

        JugadorMaquina jugadorMaquina = getJugadorMaquina(); // Se asigna el unico objeto que puede existir para siempre usar el mismo
        System.out.println("Mano de la maquina: " + getJugadorMaquina().getMano());
        Carta cartaCentral = mesa.getCartaSuperior(); // Devuelve la ultima carta de la pila mesa y la guarda en la variable
        Carta cartaJugada = jugadorMaquina.jugarCarta(cartaCentral); // La logica de como juega una carta la maquina segun la
                                                                     // referencia de la carta del centro y esa carta que jugo la guarda
                                                                     // en la variable para usar los efectos


        if(cartaJugada != null) {

            // Si la carta que decidio jugar es cartaMas4 escoge un color al azar y lo modifica con el set
            if (cartaJugada.cartaMas4()){
                String[] colores = {"red", "blue", "green", "yellow"};
                String elegido = colores[new Random().nextInt(colores.length)]; // color al azar de la lista
                cartaJugada.setColorTemporalMas4(elegido); // ejemplo: +4_negro.png, +4_red.png
                efectorCartaMas4(); // aplica el efecto de esta carta
                setColorEscogidoMaquinaMas4(elegido); // modifica el color escogido para luego usar un get y mostrarlo en un label
            }

            // Si la carta que decidio jugar es cartaCambioColor escoge un color al azar y lo modifica con el set
            if (cartaJugada.cartaCambioColor()){
                String[] colores = {"red", "blue", "green", "yellow"};
                String elegido = colores[new Random().nextInt(colores.length)];
                cartaJugada.setColorTemporal(elegido); // modifica el color y le pone el que escogio al azar
                setColorEscogidoMaquina(elegido); // modifica y permite despues mostrar con un get visualmente en un label
            }


           // Esta parte lo que hace es aplicar el efecto si la carta jugada es +2
            if (cartaJugada.cartaMas2()){
                efectoCartaMas2();
            }

            // Esta parte lo que hace es aplicar el efecto de ceder turno si la carta jugada es cartaCederTurno
            if (cartaJugada.cartaCederTurno()){
                efectoCartaCederTurno();
            }


            mesa.colocarCarta(cartaJugada); // coloca en la pila de la mesa la carta que decidio jugar

        } else{  // si la carta es nula porque no se puedo jugar ninguna hace esto
            if (mazoUno.isEmpty()){
                mazoUno.mazoVacioRecargarMazoCartasMeza(mesa.getCartasJugadas());
            }
            // Esto se aplica si no tiene cartas para jugar validas en la mano, lo obliga a robar una carta del mazo

            Carta cartaRobada = mazoUno.robarCarta(); // Saca una carta del mazo y se guarda en la variable
            jugadorMaquina.recibirCarta(cartaRobada); // Esa carta robada se añade a la mano de la maquina
            System.out.println("[Máquina] Robó carta: " + cartaRobada);
        }
        return true; // Para determinar que el turno de la maquina ya se termino de ejecutar sin importar la accion
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Estos metodos manejan los efectos que tienen las cartas o sea lo que se hace cuando se ponen

    /**
     * This method applies the +2 card effect
     */
    // Este metodo aplica el efecto de la carta +2
    public void efectoCartaMas2(){

        // Determina quien es el jugador afectado, si es el turno de jugadorPersona se le aplica a la maquina
        // y de lo contrario se le aplica al jugadorPersona
        Jugador jugadorAfectado = esTurnoJugadorPersona() ? getJugadorMaquina() : getJugadorPersona(); // funciona como un if-else

        for (int i = 0; i < 2; i++){ // Aplica dos veces el castigo (comer 2 cartas)
            // Si el mazo esta vacio lo recarga para que se pueda realizar el castigo
            if (mazoUno.isEmpty()){
                mazoUno.mazoVacioRecargarMazoCartasMeza(mesa.getCartasJugadas());
            }
            Carta cartaRobada = mazoUno.robarCarta(); // Saca una carta del mazo y la guarda en la variable
            jugadorAfectado.recibirCarta(cartaRobada); // Le añade a la mano la carta robada al jugador afectado
        }
        pasarTurno();
    }

    /**
     * This method applies the +4 card effect
     */
    // Este metodo aplica el efecto de la carta +4
    public void efectorCartaMas4(){

        Jugador jugadorAfectado = esTurnoJugadorPersona() ? getJugadorMaquina() : getJugadorPersona(); // Funciona como un if-else

        for (int i = 0; i < 4; i++){ // Aplica 4 veces el castigo (comer 4 cartas)
            // Si el mazo esta vacio recarga el mazo con las cartas de la mesa menos la ultima
            if (mazoUno.isEmpty()){
                mazoUno.mazoVacioRecargarMazoCartasMeza(mesa.getCartasJugadas());
            }
            Carta cartaRobada = mazoUno.robarCarta(); // Saca la ultima carta del mazo y la guarda en la variable
            jugadorAfectado.recibirCarta(cartaRobada); // Se añade la carta robada en la mano del jugador afectado
        }
        pasarTurno();
    }

    /**
     * This method skips the turn so the player who played the card gets to play again
     */
    // Este metodo lo que hace es pasar el turno para que el jugador que la puso le vuelva a tocar jugar
    public void efectoCartaCederTurno(){
        pasarTurno();
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Metodos para consultar a quien le toca el turno, controlar los turnos y conrolar el juego

    /**
     * This method determines whose turn it is, and modifies its value each time it's called
     */
    // Este metodo lo que hace es determinar de quien es el turno, cada que se hace el llamado en alguna parte modifica su valor
    public void pasarTurno(){
        // 1-0 = 1 y cuando se vuelve a llamar 1-1 = 0
        turnoActual = 1 - turnoActual; // Metodo que cambia a otro turno entre 0 y 1
    }

    /**
     * This method determines that it's the jugadorPersona turn when the value is 0
     *
     * @return true or false
     */
    // Este metodo determina que el turno del jugadorPersona es cuando este en 0
    public boolean esTurnoJugadorPersona(){
        return turnoActual == 0;
    }

    /**
     * This method determines that it's the jugadorMaquina turn when the value is 1
     *
     * @return true or false
     */
    // Este metodo determina que el turno del jugadorMaquina es cuando este en 1
    public boolean esTurnoJugadorMaquina(){
        return turnoActual == 1;
    }

    /**
     * This method determines the winner by checking if their hand is empty
     *
     * @return jugadorPersona or jugadorMaquina or null
     */
    // Este metodo determinar quien es el ganador segun si tiene la mano vacia
    public Jugador verificarGanador(){
        JugadorPersona jugadorPersona = getJugadorPersona(); // Se asigna el unico objeto de JugadorPersona que puede exisitr
        JugadorMaquina jugadorMaquina = getJugadorMaquina(); // Se asigna el unico objeto de JugadorMaquina que puede existir

        if (jugadorPersona.getMano().isEmpty() && jugadorPersona.getUnoState()){
            return jugadorPersona;
        } else if (jugadorMaquina.getMano().isEmpty() && jugadorMaquina.getUnoState()){
            return jugadorMaquina;
        }

        return null; // Aun  no hay ganadores
    }




    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Esta parte contiene los setters y getters


    // Este metodo me ayuda a cambiar desde otra clases la carta del centro con una nueva instancia que tenga
    // el color que se escogio temporalmente
    public void setCartaDelCnetro(Carta carta){
        mesa.colocarCarta(carta);
    }

    public void setColorEscogidoMaquina(String colorEscogidoMaquina) {
        this.ColorEscogidoMaquina = colorEscogidoMaquina;
    }

    public String getColorEscogidoMaquina() {
        return ColorEscogidoMaquina;
    }


    // Estos metodos capturan el color escogido para mostrarlo en otro lado
    public void setColorEscogidoMaquinaMas4(String colorEscogidoMaquinaMas4) {
        this.ColorEscogidoMaquinaMas4 = colorEscogidoMaquinaMas4;
    }

    public String getColorEscogidoMaquinaMas4() {
        return ColorEscogidoMaquinaMas4;
    }



    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Codigo sin importancia y pendiente por borrar

    public int contarCartasEnJuego(String colorCarta){
        int cont = 0;

        //Esta parte va a capturar las cartas de la mano del JugadorPersona
        JugadorPersona jugadorPersona = getJugadorPersona();
        if (jugadorPersona != null){
            for (Carta carta : jugadorPersona.getMano()){
                if (carta.getColor().equals(colorCarta)){
                    cont++;
                }
            }
        }

        //Esta parte va a capturar las cartas de la mano del JugadorMaquina
        JugadorMaquina jugadorMaquina = getJugadorMaquina();
        if (jugadorMaquina != null){
            for (Carta carta : jugadorMaquina.getMano()){
                if (carta.getColor().equals(colorCarta)){
                    cont++;
                }
            }
        }

        return cont;
    }


    /**
     * This method returns the created mazoUno object
     *
     * @return
     */
    public MazoUno getMazoUno() {

        return this.mazoUno;
    }

    public MesaDeJuego getMesa() {

        return mesa;
    }

    /**
     * Function that creates a thread to work the UNO logic for the Machine player and update the UNO state for players if needed
     */
    public void iniciarHiloVerificadorUNO(){
        Thread hiloUNO = new Thread(() -> {
            while (partiaIniciada){
                try {
                    Thread.sleep(1500);

                    if(jugadorPersona.getManoSize() == 1 && !jugadorPersona.getUnoState()){
                        Thread.sleep(2000);
                        checkUNO(jugadorPersona, jugadorMaquina);
                    }
                    else if (jugadorMaquina.getManoSize() == 1 && !jugadorMaquina.getUnoState()) {
                        Thread.sleep(2000);
                        singUNO(jugadorMaquina);
                    }
                    else if (jugadorMaquina.getManoSize() != 1 && jugadorMaquina.getUnoState()) {
                        jugadorMaquina.setUnoState(false);
                    }
                    else if (jugadorPersona.getManoSize() != 1 && jugadorPersona.getUnoState()) {
                        jugadorPersona.setUnoState(false);
                    }
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        hiloUNO.setDaemon(true);
        hiloUNO.start();
    }

    /**
     * Function that sings UNO for a player.
     * @param jugador: player who sings UNO
     */
    public void singUNO(Jugador jugador) {
        if (jugador.getMano().size() == 1){
            jugador.setUnoState(true);
            System.out.println("¡" + jugador.getNombre() + " ha cantado UNO!");
        }
        else {
            System.out.println("No puedes cantar UNO si tienes más de una carta.");
        }
    }

    /**
     * Function that sings UNO to the oponent player if they haven't done so, making them take a card
     * @param jugador: player afected by the UNO
     * @param oponente: player who sings the UNO
     */
    public void checkUNO(Jugador jugador, Jugador oponente){
        if (jugador.getMano().size() == 1 && !jugador.getUnoState()){

            Carta cartaRobada = mazoUno.robarCarta();
            jugador.recibirCarta(cartaRobada);
            System.out.println(oponente.getNombre() + " ha cantado UNO a "  + jugador.getNombre() );
        }

        else {
            System.out.println("Movimiento invalido.");
        }

    }

}