/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Aitziber
 */
public class Enunciado {

    private int id;
    private String descripcion;
    private Dificultad nivel;
    private boolean disponible;
    private String ruta;
    private ArrayList<ConvocatoriaExamen> convocatoriaExamenList;
    private ArrayList<UnidadDidactica> unidadDidacticaList;
    
    public Enunciado(){
        this.convocatoriaExamenList=new ArrayList<>();
        this.unidadDidacticaList=new ArrayList<>();
    }
    
    
    public Enunciado(String descripcion, Dificultad nivel,boolean disponible, String ruta, ArrayList<ConvocatoriaExamen> convocatoriaExamenList, ArrayList<UnidadDidactica> unidadDidacticaList){
        this.descripcion=descripcion;
        this.nivel=nivel;
        this.disponible=disponible;
        this.ruta=ruta;
        this.convocatoriaExamenList=convocatoriaExamenList;
        this.unidadDidacticaList=unidadDidacticaList;
    }

    public Enunciado(String descripcion, Dificultad nivel, boolean disponible, String ruta) {
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.disponible = disponible;
        this.ruta = ruta;
        this.convocatoriaExamenList=new ArrayList<>();
        this.unidadDidacticaList=new ArrayList<>();
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
    
    public ArrayList<ConvocatoriaExamen> getConvocatoriaExamenList() {
        return convocatoriaExamenList;
    }

    public void setConvocatoriaExamenList(ArrayList<ConvocatoriaExamen> convocatoriaExamenList) {
        this.convocatoriaExamenList = convocatoriaExamenList;
    }

    public ArrayList<UnidadDidactica> getUnidadDidacticaList() {
        return unidadDidacticaList;
    }

    public void setUnidadDidacticaList(ArrayList<UnidadDidactica> unidadDidacticaList) {
        this.unidadDidacticaList = unidadDidacticaList;
    }

    @Override
    public String toString() {
        return "Enunciado{" + " descripcion=" + descripcion + ", nivel=" + nivel + ", disponible=" + disponible + ", ruta=" + ruta + ", convocatoriaExamenList=" + convocatoriaExamenList + ", unidadDidacticaList=" + unidadDidacticaList + '}';
    }
    
}
