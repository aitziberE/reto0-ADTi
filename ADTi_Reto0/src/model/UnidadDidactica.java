/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Ander, Aitziber
 */
public class UnidadDidactica {
    private int id;
    private String acronimo;
    private String titulo;
    private String evaluacion;
    private String descripcion;
    private ArrayList<Enunciado> enunciadoList;

    public UnidadDidactica(){
        this.enunciadoList=new ArrayList<>();
    }
    public UnidadDidactica(String acronimoInput, String tituloInput, String evaluacionInput, String descripcionInput, ArrayList<Enunciado> enunciadoList){
        //TODO Meter un ID autoincremental que lo asigne en base a las UD que haya ya creadas.
        this.acronimo = acronimoInput;
        this.titulo= tituloInput;
        this.evaluacion = evaluacionInput;
        this.descripcion = descripcionInput;
        this.enunciadoList=enunciadoList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Enunciado> getEnunciadoList() {
        return enunciadoList;
    }

    public void setEnunciadoList(ArrayList<Enunciado> enunciadoList) {
        this.enunciadoList = enunciadoList;
    }

    @Override
    public String toString() {
        return "UnidadDidactica{" + "acronimo=" + acronimo + ", titulo=" + titulo + ", evaluacion=" + evaluacion + ", descripcion=" + descripcion + ", enunciadoList=" + enunciadoList + '}';
    }
    
    
}