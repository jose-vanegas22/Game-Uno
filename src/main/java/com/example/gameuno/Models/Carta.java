package com.example.gameuno.Models;

import javafx.scene.image.Image;

/**
 * This class Carta contains its attributes such as color, valor, nombreArchivo and getters
 * This class Carta contains methods that differentiate the types of cards as well as getters and setters
 * methods for temporary colors
 *
 * @author vaneg
 * @author Alejandro Medina
 * @version 1.0
 */
public class Carta {

    private String color;
    private String valor;
    private Image imagen;
    private String nombreArchivo;
    private String colorTemporal;
    private String colorTemporalMas4;


    /**
     * This is the constructor of the Carta class
     * @param color
     * @param valor
     * @param nombreArchivo
     */
    public Carta(String color, String valor, String nombreArchivo) {
        this.color = color;
        this.valor = valor;
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * Access method to retrieve and use this value in other classes
     * @return colorTemporal or color
     */
    // Metodo de acceso para obtener y usar este valor en otras clases
    public String getColor() {
        return (colorTemporal != null) ? colorTemporal : color; // es una forma mas compacta de hacer un if-else
        //return color;
    }

    /**
     * Access method to get this value in other classes
     * @return
     */
    // Metodo de acceso para obtener este valor en otras clases
    public String getValor() {
        return valor;
    }

    /**
     *
     * @return
     */
    public String getNombreArchivo() {
        String colorFinal = (colorTemporal != null) ? colorTemporal : color;
        if (valor.equals("cambioColor")) {
            return "cambioColor_" +  colorFinal + ".png";
        } else if (valor.equals("+4")){
            return "+4_" +  colorFinal + ".png";
        }
        else{
            return valor + "_" +  colorFinal + ".png";
        }
        //return nombreArchivo;
    }

    @Override
    public String toString() {

        String colorFinal = (colorTemporal != null) ? colorTemporal : color;
        if ("cambioColor".equals(valor)) {
            return "cambioColor_" + colorFinal + ".png";
        } else if ("+4".equals(valor)) {
            return "+4_" + colorFinal + ".png";
        }
        else {
            return valor + "_" + colorFinal + ".png";
        }
        //return valor + "_" + color + ".png";
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Method that checks if the card is a +2 card
     * @return true or false
     */
    // Metodo que sirve para verificar si la carta es una cartaMas2
    public boolean cartaMas2(){
        return "+2".equals(valor);
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Method that checks if the card is a skip-turn card
     * @return true or false
     */
    // Metodo que sirve para verificar si la carta es una cartaCederturno
    public boolean cartaCederTurno(){
        return "skip".equals(valor);
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Method that checks if the card is a cartaCambioColor and black color
     * @return true or false
     */
    // Metodo que sirve para verificar si la carta es una cartaCambioColor y de color negro
    public boolean cartaCambioColor(){
        return "cambioColor".equals(valor) && "negro".equals(color);
    }

    // Metodo para obtener el color temporal
    public String getColorTemporal(){
        return colorTemporal;
    }

    // Metodo para modificar el color
    public void setColorTemporal(String colorTemporal){
        this.colorTemporal = colorTemporal;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Method that verifies if the card is a cartaMas4 card and black color
     * @return  true or false
     */
    // Metodo que sirve para verificar si la carta es cartaMas4 y de color negro
    public boolean cartaMas4(){
        return "+4".equals(valor) && "negro".equals(color);
    }

    // Metodo para obtener el color temporal
    public String getColorTemporalMas4(){
        return colorTemporalMas4;
    }

    // Metodo para modificar el color
    public void setColorTemporalMas4(String colorTemporalMas4){
        this.colorTemporalMas4 = colorTemporalMas4;
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Metodo que sirve para pasar las cartas cambioDeColor y +4 a su color original o sea "negro", para que
    // su logica siga funcionando

    public void restaurarColorCarta(){
        if (this.valor.equals("cambioColor") ||  this.valor.equals("+4")) {
            this.color = "negro"; // Restaura al color original


            // Limpia los valores temporales
            this.colorTemporal = null;
            this.colorTemporalMas4 = null;
        }
    }




}
