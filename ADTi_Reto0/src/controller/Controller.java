/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import excepciones.CreateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.ConvocatoriaExamen;
import model.Enunciado;
import model.UnidadDidactica;

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
     * Crear un enunciado de examen agregando las unidades didácticas que va a referir. También se asociará a este enunciado la convocatoria para la que se crea.
     */
    final String INSERTenunciado = "INSERT INTO Enunciado VALUES (?, ?, ?, ?, ?)";

    /**
     * Consultar los enunciados de una unidad didáctica concreta (nombre)
     */
    final String SELECTenunciado = "SELECT e.* FROM Enunciado e JOIN UnidadDidactica_Enunciado ue "
            + "ON e.id = ue.enunciado_id JOIN UnidadDidactica ud ON ud.id = ue.unidad_didactica_id WHERE ud.acronimo = ?";
    final String SELECTIdEnunciado = "SELECT id FROM Enunciado WHERE id = ?";
    /**
     * Consultar en que convocatorias se ha utilizado un enunciado concreto.
     */
    final String SELECTconvocatoria = "SELECT c.* FROM ConvocatoriaExamen c JOIN Enunciado e ON c.enunciado_id = e.id WHERE e.id = ?";
    final String SELECTdescripcion = "SELECT descripcion FROM Enunciado WHERE id = ?";

    final String UPDATEconvocatoria = "UPDATE ConvocatoriaExamen SET enunciado_id WHERE convocatoria = ?";

    /**
     * crearUnidadDidactica crearConvocatoria crearEnunciado
     *
     * consultarEnunciado consultarConvocatoriaPorEnunciado consultarDescripcionEnunciado
     *
     * asignarEnunciadoAConvocatoria
     *
     * @param ud
     * @throws CreateException
     */
    public void crearUnidadDidactica(UnidadDidactica ud) throws CreateException {
        // Abrimos la conexión
        con = conection.openConnection();
        try {
            stmt = con.prepareStatement(INSERTunidaddidactica);

            stmt.setInt(1, ud.getId());
            stmt.setString(2, ud.getAcronimo());
            stmt.setString(3, ud.getTitulo());
            stmt.setString(4, ud.getEvaluacion());
            stmt.setString(5, ud.getDescripcion());

            // Ejecuto la actualización de la base de datos
            stmt.executeUpdate();

        } catch (SQLException e1) {

            System.out.println("Error al ejecutar la query");
            String error = "Error al crear la UD";
            CreateException ex = new CreateException(error);
            throw ex;

        } finally {
            // Cierro la conexión con la base de datos
            conection.closeConnection(stmt, con);
        }

    }
    
    public void comprobarEnunciado(){
        
    }

    public void crearConvocatoria(ConvocatoriaExamen ca, LocalDateTime fecha, Enunciado enu) throws CreateException {
        // Abrimos la conexión
        con = conection.openConnection();
        try {
            stmt = con.prepareStatement(INSERTunidaddidactica);

            stmt.setString(1, ca.getConvocatoria());
            stmt.setString(2, ca.getDescripcion());
            stmt.setString(3, fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            stmt.setString(4, ca.getCurso());
            stmt.setInt(5, enu.getId());

            // Ejecuto la actualización de la base de datos
            stmt.executeUpdate();

        } catch (SQLException e1) {

            System.out.println("Error al ejecutar la query");
            String error = "Error al crear la UD";
            CreateException ex = new CreateException(error);
            throw ex;

        } finally {
            // Cierro la conexión con la base de datos
            conection.closeConnection(stmt, con);
        }

    }

}
