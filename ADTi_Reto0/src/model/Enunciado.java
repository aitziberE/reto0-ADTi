/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import utilidades.Util;

/**
 *
 * @author PAIA Group
 */
public class Enunciado {

    private int id;
    private String descripcion;
    private Dificultad nivel;
    private boolean disponible;
    private String ruta;

    public Enunciado(String descripcion, Dificultad nivel, boolean disponible, String ruta) {
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.disponible = disponible;
        this.ruta = ruta;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Dificultad getNivel() {
        return nivel;
    }

    public void setNivel(Dificultad nivel) {
        this.nivel = nivel;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    /*
    public void setDificultad(String dificultad) {
        boolean validez = false;
        do {
            System.out.println("Dificultad (BAJA, MEDIA, ALTA): ");
            dificultad = Util.introducirCadena().toUpperCase();
            if (dificultad.equals("BAJA") || dificultad.equals("MEDIA") || dificultad.equals("ALTA")) {
                validez = true;
                switch (dificultad) {
                    case "BAJA":
                        this.nivel = Dificultad.valueOf("BAJA");
                        break;
                    case "MEDIA":
                        this.nivel = Dificultad.valueOf("MEDIA");
                        break;
                    case "ALTA":
                        this.nivel = Dificultad.valueOf("ALTA");
                        break;
                }
            } else {
                System.out.println("ERROR. Elige una dificultad disponible");
            }

        } while (!validez);
    }
    */

}
