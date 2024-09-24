/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ander
 */
public class UnidadDidactica {
    private int id;
    private String acronimo;
    private String titulo;
    private String evaluacion;
    private String descripcion;

    public UnidadDidactica(String acronimoInput, String tituloInput, String evaluacionInput, String descripcionInput){
        //TODO Meter un ID autoincremental que lo asigne en base a las UD que haya ya creadas.
        this.acronimo = acronimoInput;
        this.titulo= tituloInput;
        this.evaluacion = evaluacionInput;
        this.descripcion = descripcionInput;
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
    
}