/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Aitziber
 */
public class ConvocatoriaExamen {
    private int id;
    private String convocatoria;
    private String descripcion;
    private LocalDate fecha;
    private String curso;
    private Enunciado enunciado;
    
    public ConvocatoriaExamen(){
        
    }
    
    public ConvocatoriaExamen(String convocatoria, String descripcion, LocalDate fecha, String curso, Enunciado enunciado){
        this.convocatoria=convocatoria;
        this.descripcion=descripcion;
        this.fecha=fecha;
        this.curso=curso;
        this.enunciado=enunciado;
    }

    public int getId() {
        return id;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Enunciado getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(Enunciado enunciado) {
        this.enunciado = enunciado;
    }

    @Override
    public String toString() {
        return "ConvocatoriaExamen{" +" convocatoria=" + convocatoria + ", descripcion=" + descripcion + ", fecha=" + fecha + ", curso=" + curso + ", enunciado=" + enunciado + '}';
    }
    
}
