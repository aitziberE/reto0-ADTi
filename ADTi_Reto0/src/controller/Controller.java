/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import excepciones.CreateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.ConvocatoriaExamen;
import model.Dificultad;
import model.Enunciado;

/**
 *
 * @author Ander
 * @version 1.0
 */
public class Controller implements IController {

    private Connection con;
    private PreparedStatement stmt;
    private final ConnectionOpenClose conection = new ConnectionOpenClose();

    //INSERTS
    /**
     * Crear una unidad didáctica (Unidad) y convocatoria (Convocatoria) de
     * examen.
     */
    final String INSERTunidaddidactica = "INSERT INTO UnidadDidactica (acronimo, titulo, evaluacion, descripcion) VALUES (?, ?, ?, ?)";
    final String INSERTconvocatoria = "INSERT INTO ConvocatoriaExamen (convocatoria, descripcion, fecha, curso, enunciado_id) VALUES (?, ?, ?, ?, ?)";

    /**
     * Crear un enunciado de examen agregando las unidades didácticas que va a
     * referir. También se asociará a este enunciado la convocatoria para la que
     * se crea.
     */
    final String INSERTenunciado = "INSERT INTO Enunciado (descripcion, nivel, disponible, ruta) VALUES (?, ?, ?, ?)";

    //SELECTS
    /**
     * Consultar los enunciados de una unidad didáctica concreta (nombre)
     */
    final String SELECTenunciado = "SELECT e.* FROM Enunciado e JOIN UnidadDidactica_Enunciado ue "
            + "ON e.id = ue.enunciado_id JOIN UnidadDidactica ud ON ud.id = ue.unidad_didactica_id WHERE ud.acronimo = ?";
    /**
     * Devuelve el enunciado mediante la ID
     */
    final String SELECTIdEnunciado = "SELECT * FROM Enunciado WHERE id = ?";
    /**
     * Consultar en que convocatorias se ha utilizado un enunciado concreto.
     */
    final String SELECTconvocatoria = "SELECT c.* FROM ConvocatoriaExamen c JOIN Enunciado e ON c.enunciado_id = e.id WHERE e.id = ?";
    /**
     * Visualizar el documento de texto asociado a un enunciado
     */
    final String SELECTdescripcion = "SELECT descripcion FROM Enunciado WHERE id = ?";
    
    //UPDATE
    /**
     * Asignar un enunciado a una convocatoria
     */
    final String UPDATEconvocatoria = "UPDATE ConvocatoriaExamen SET enunciado_id = ? WHERE convocatoria = ?";

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
            stmt = con.prepareStatement(INSERTconvocatoria);

            stmt.setString(1, convocatoria);
            stmt.setString(2, descripcion);
            stmt.setString(3, fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            stmt.setString(4, curso);
            stmt.setInt(5, enunciadoId);

            // Ejecuto la actualización de la base de datos
            stmt.executeUpdate();

        } catch (SQLException e1) {

            System.out.println("Error al ejecutar la query");
            String error = "Error al crear la convocatoria";
            CreateException ex = new CreateException(error);
            throw ex;

        } finally {
            // Cierro la conexión con la base de datos
            conection.closeConnection(stmt, con);
        }
    }

    @Override
    public void crearEnunciado(String descripcion, String nivel, boolean disponible, String ruta) throws CreateException {
        // Abrimos la conexión
        con = conection.openConnection();
        try {
            stmt = con.prepareStatement(INSERTenunciado);

            stmt.setString(1, descripcion);
            stmt.setString(2, nivel);
            stmt.setBoolean(3, disponible);
            stmt.setString(4, ruta);

            // Ejecuto la actualización de la base de datos
            stmt.executeUpdate();

        } catch (SQLException e1) {

            System.out.println("Error al ejecutar la query");
            String error = "Error al crear el enunciado";
            CreateException ex = new CreateException(error);
            throw ex;

        } finally {
            // Cierro la conexión con la base de datos
            conection.closeConnection(stmt, con);
        }
    }

    /**
     *
     * @param ud
     * @return
     * @throws CreateException
     */
    @Override
    public ArrayList<Enunciado> consultarEnunciado(String ud) throws CreateException {
        // Tenemos que definir el ResultSet para recoger el resultado de la consulta
        ResultSet rs = null;
        ArrayList<Enunciado> enunciadoList = new ArrayList<>();

        // Abro la conexióm
        con = conection.openConnection();

        try {
            stmt = con.prepareStatement(SELECTenunciado);

            // Cargamos los parametros
            stmt.setString(1, ud);

            rs = stmt.executeQuery();

            if (rs.next()) {
                Enunciado enu = new Enunciado();
                enu.setId(rs.getInt("id"));
                enu.setDescripcion(rs.getString("descripcion"));
                String nivelStr = rs.getString("nivel");
                enu.setNivel(Dificultad.valueOf(nivelStr));
                enu.setDisponible(rs.getBoolean("disponible"));
                enu.setRuta(rs.getString("ruta"));
                
                enunciadoList.add(enu);
            }
        } catch (SQLException e) {

            System.out.println("Error al ejecutar la query");
            String error = "Error al consultar el enunciado";
            CreateException ex = new CreateException(error);
            throw ex;
        } finally {
            // Cerramos ResultSet
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println("Error en cierre del ResultSet");
                    String error = "Error al consultar el enunciado";
                    CreateException e1 = new CreateException(error);
                    throw e1;
                }
            }
            conection.closeConnection(stmt, con);
        }
        return enunciadoList;
    }

    @Override
    public ArrayList consultarConvocatoriaPorEnunciado(int enunciadoId) throws CreateException {
        // Tenemos que definir el ResultSet para recoger el resultado de la consulta
        ResultSet rs = null;
        ArrayList<ConvocatoriaExamen> convocatoriaExamenList = new ArrayList<>();

        // Abro la conexióm
        con = conection.openConnection();

        try {
            stmt = con.prepareStatement(SELECTenunciado);

            // Cargamos los parametros
            stmt.setInt(1, enunciadoId);

            rs = stmt.executeQuery();

            // Iteramos sobre el ResultSet
            while (rs.next()) {
                ConvocatoriaExamen conv = new ConvocatoriaExamen();
                conv.setConvocatoria(rs.getString("convocatoria"));
                conv.setDescripcion(rs.getString("descripcion"));
                conv.setFecha(rs.getDate("fecha").toLocalDate());
                conv.setCurso(rs.getString("curso"));
                Enunciado enunciado = consultarEnunciadoPorId(enunciadoId);
                conv.setEnunciado(enunciado);

                convocatoriaExamenList.add(conv);
            }
        } catch (SQLException e) {

            System.out.println("Error al ejecutar la query");
            String error = "Error al consultar el enunciado";
            CreateException ex = new CreateException(error);
            throw ex;
        } finally {
            // Cerramos ResultSet
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println("Error en cierre del ResultSet");
                    String error = "Error al consultar el enunciado";
                    CreateException e1 = new CreateException(error);
                    throw e1;
                }
            }
            conection.closeConnection(stmt, con);
        }
        return convocatoriaExamenList;
    }

    private Enunciado consultarEnunciadoPorId(int enunciadoId) throws SQLException {
        Enunciado enunciado = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(SELECTIdEnunciado);
            stmt.setInt(1, enunciadoId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                enunciado = new Enunciado();
                enunciado.setId(enunciadoId);
                enunciado.setDescripcion(rs.getString("descripcion"));
                String nivelStr = rs.getString("nivel");
                enunciado.setNivel(Dificultad.valueOf(nivelStr));
                enunciado.setDisponible(rs.getBoolean("disponible"));
                enunciado.setRuta(rs.getString("ruta"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return enunciado;
    }

    @Override
    public String consultarDescripcionEnunciado(int enunciadoId) throws CreateException {
        // Tenemos que definir el ResultSet para recoger el resultado de la consulta
        ResultSet rs = null;
        String texto = null;

        // Abro la conexióm
        con = conection.openConnection();

        try {
            stmt = con.prepareStatement(SELECTdescripcion);

            // Cargamos los parametros
            stmt.setInt(1, enunciadoId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                texto = rs.getString("descripcion");
            } else {
                texto = null;
            }
        } catch (SQLException e) {

            System.out.println("Error al ejecutar la query");
            String error = "Error al consultar el enunciado";
            CreateException ex = new CreateException(error);
            throw ex;
        } finally {
            // Cerramos ResultSet
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println("Error en cierre del ResultSet");
                    String error = "Error al consultar el enunciado";
                    CreateException e1 = new CreateException(error);
                    throw e1;
                }
            }
            conection.closeConnection(stmt, con);
        }
        return texto;
    }

    @Override
    public boolean asignarEnunciadoAConvocatoria(int enunciadoId, String convocatoria) throws CreateException {
        boolean cambios = false;
        // Abrimos la conexión
        con = conection.openConnection();
        try {
            stmt = con.prepareStatement(UPDATEconvocatoria);

            stmt.setInt(1, enunciadoId);
            stmt.setString(2, convocatoria);

            if (stmt.executeUpdate() == 1) {
                cambios = true;
            }
        } catch (SQLException e1) {
            String error = "Error al modificar la convocatoria";
            CreateException ex = new CreateException(error);
            throw ex;
        } finally {
            conection.closeConnection(stmt, con);
        }
        return cambios;
    }

}
