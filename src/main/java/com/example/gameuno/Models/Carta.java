package com.example.gameuno.Models;

import javafx.scene.image.Image;

/**
 * This class Card contains its attributes such as color, valor, nombreArchivo and getters
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


    public Carta(String color, String valor, String nombreArchivo) {
        this.color = color;
        this.valor = valor;
        this.nombreArchivo = nombreArchivo;
    }

    public String getColor() {
        return color;
    }

    public String getValor() {
        return valor;
    }

    public Image getRutaImagen() {
        return imagen;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    @Override
    public String toString() {
        return valor + "_" + color + ".png";
    }

}
