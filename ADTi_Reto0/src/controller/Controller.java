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

    /**
     * Consultar los enunciados de una unidad didáctica concreta (nombre)
     */
    final String SELECTenunciado = "SELECT e.* FROM Enunciado e JOIN UnidadDidactica_Enunciado ue "
            + "ON e.id = ue.enunciado_id JOIN UnidadDidactica ud ON ud.id = ue.unidad_didactica_id WHERE ud.acronimo = ?";

    /**
     * Consultar en que convocatorias se ha utilizado un enunciado concreto.
     */
    final String SELECTconvocatoria = "SELECT c.* FROM ConvocatoriaExamen c JOIN Enunciado e ON c.enunciado_id = e.id WHERE e.id = ?";
    final String SELECTdescripcion = "SELECT descripcion FROM Enunciado WHERE id = ?";

    
    final String UPDATEconvocatoria = "UPDATE ConvocatoriaExamen SET enunciado_id WHERE convocatoria = ?";
    
	public void registro(UnidadDidactica ud) throws CreateException {
		// Abrimos la conexión
		con = conection.openConnection();
		try {
			stmt = con.prepareStatement(INSERTjugador);

			stmt.setString(1, usuario.getDni());
			stmt.setString(2, usuario.getNombre());
			stmt.setString(3, usuario.getApellido());
			stmt.setString(4, usuario.getUsername());
			stmt.setString(5, usuario.getContrasena());
			stmt.setString(6, usuario.getSexo());
			stmt.setDate(7, usuario.getNacimiento());

			// Ejecuto la actualización de la base de datos
			stmt.executeUpdate();

		} catch (SQLException e1) {

			System.out.println("Error al ejecutar la query");
			String error = "Error al registrar el jugador";
			CreateException ex = new CreateException(error);
			throw ex;

		} finally {
			// Cierro la conexión con la base de datos
			conection.closeConnection(stmt, con);
		}

	}
    
}
