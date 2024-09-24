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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Enunciado;

/**
 *
 * @author Ander
 */
public class Controller implements IController{

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

    @Override
    public void crearUnidadDidactica(String acronimo, String titulo, String evaluacion, String descripcion) throws CreateException {
        // Abrimos la conexión
        con = conection.openConnection();
        try {
            stmt = con.prepareStatement(INSERTunidaddidactica);
            
            stmt.setString(1, acronimo);
            stmt.setString(2, titulo);
            stmt.setString(3, evaluacion);
            stmt.setString(4, descripcion);

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

    @Override
    public void crearConvocatoria(String convocatoria, String descripcion, LocalDate fecha, String curso, int enunciadoId) throws CreateException {
        // Abrimos la conexión
        con = conection.openConnection();
        try {
            stmt = con.prepareStatement(INSERTunidaddidactica);

            stmt.setString(1, convocatoria);
            stmt.setString(2, descripcion);
            stmt.setString(3, fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            stmt.setString(4, curso);
            stmt.setInt(5, enunciadoId);

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

    @Override
    public void crearEnunciado(String descripcion, String nivel, boolean disponible, String ruta) throws CreateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Enunciado consultarEnunciado(int enunciadoId) throws CreateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String consultarConvocatoriaPorEnunciado(int enunciadoId) throws CreateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String consultarDescripcionEnunciado(int enunciadoId) throws CreateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void asignarEnunciadoAConvocatoria(int enunciadoId, String convocatoria) throws CreateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
