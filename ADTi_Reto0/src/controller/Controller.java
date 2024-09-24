/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Ander
 */
public class Controller {

    private Connection con;
    private PreparedStatement stmt;
    private ConnectionOpenClose conection = new ConnectionOpenClose();

    /*
    crearUnidadDidactica
    crearConvocatoria
    crearEnunciado

    consultarEnunciado
    consultarConvocatoriaPorEnunciado
    consultarDescripcionEnunciado

    asignarEnunciadoAConvocatoria
     */
    /**
     * Crear una unidad didáctica (Unidad) y convocatoria (Convocatoria) de examen.
     */
    final String INSERTunidaddidactica = "INSERT INTO UnidadDidactica VALUES (?, ?, ?, ?, ?)";
    final String INSERTconvocatoria = "INSERT INTO ConvocatoriaExamen VALUES (?, ?, ?, ?, ?)";
    
    /**
     * Crear un enunciado de examen agregando las unidades didácticas que va a referir. 
     * También se asociará a este enunciado la convocatoria para la que se crea.
     */
    final String INSERTenunciado = "INSERT INTO Enunciado VALUES (?, ?, ?, ?, ?)";

    final String SELECTenunciado = "SELECT * FROM Enunciado WHERE username = ? AND contrasena = ?";

    final String UPDATEcoleccion = "UPDATE Coleccion SET armaFav = ?, skinFav = ?, agenteFav = ? WHERE dni_jugador = ?";

}
