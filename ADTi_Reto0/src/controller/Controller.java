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
import model.UnidadDidactica;

/**
*
* @author Ander
* @author Aitziber
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
     * Consultar los enunciados de una unidad didáctica concreta
     */
    final String SELECTenunciado = "SELECT e.id, e.descripcion, e.nivel, e.disponible, e.ruta FROM enunciado e JOIN unidaddidactica_enunciado ude ON e.id = ude.enunciado_id JOIN unidaddidactica ud ON ude.unidad_didactica_id = ud.id WHERE ud.acronimo = ?"; 
    
    final String SELECTAllEnunciados = "SELECT * FROM enunciado";
    final String SELECTAllUnidadesDidacticas = "SELECT * FROM unidaddidactica";
    final String SELECTAllConvocatoriasExamen   = "SELECT * FROM convocatoriaexamen";
    /**
     * Devuelve el enunciado mediante la ID
     */
    final String SELECTIdEnunciado = "SELECT * FROM Enunciado WHERE id = ?";
    final String SELECTEnunciadoByDescription = "SELECT * FROM Enunciado WHERE descripcion = ?";
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
    final String UPDATEud_e = "INSERT INTO UnidadDidactica_Enunciado (enunciado_id, unidad_didactica_id) VALUES (?, ?)";
    final String UPDATEconvocatoriaExamen = "UPDATE ConvocatoriaExamen SET enunciado_id = ? WHERE convocatoria = ?";
    final String UPDATEconvocatoriaExamenSetEnunciado = "UPDATE ConvocatoriaExamen SET enunciado_id = ? WHERE convocatoria = ?";
    
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

            while (rs.next()) {
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
            stmt = con.prepareStatement(SELECTconvocatoria);

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
    
    @Override
    public ArrayList<Enunciado> getAllEnunciados() throws CreateException { 
        // Tenemos que definir el ResultSet para recoger el resultado de la consulta
        ResultSet rs = null;
        ArrayList<Enunciado> enunciadosList = new ArrayList<>();

        // Abro la conexióm
        con = conection.openConnection();

        try {
            stmt = con.prepareStatement(SELECTAllEnunciados);
            rs = stmt.executeQuery();

            // Iteramos sobre el ResultSet
            while (rs.next()) {
                Enunciado enunciado = new Enunciado();
                
                //recoger el resto de atributos de Enunciado
                enunciado.setId(rs.getInt("id"));
                enunciado.setDescripcion(rs.getString("descripcion"));

                enunciadosList.add(enunciado);
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
        return enunciadosList;
    }
    
    @Override
    public Enunciado consultarEnunciadoPorId(int enunciadoId) throws CreateException {
        Enunciado enunciado = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // Abro la conexión
        con = conection.openConnection();

        try {
            stmt = con.prepareStatement(SELECTIdEnunciado);
            stmt.setInt(1, enunciadoId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                enunciado = new Enunciado();
                enunciado.setId(enunciadoId);
                enunciado.setDescripcion(rs.getString("descripcion"));
                String nivelStr = rs.getString("nivel");

                // Manejo de posibles excepciones por nivel no válido
                try {
                    enunciado.setNivel(Dificultad.valueOf(nivelStr));
                } catch (IllegalArgumentException e) {
                    throw new CreateException("Dificultad no válida: " + nivelStr, e);
                }

                enunciado.setDisponible(rs.getBoolean("disponible"));
                enunciado.setRuta(rs.getString("ruta"));
            }
        } catch (SQLException e) {
            throw new CreateException("Error al consultar el enunciado con ID: " + enunciadoId, e);
        } finally {
            // Cierro ResultSet y PreparedStatement
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new CreateException("Error al cerrar los recursos", e);
            }
            // Cierro la conexión
            conection.closeConnection(stmt, con);
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
            stmt = con.prepareStatement(UPDATEconvocatoriaExamenSetEnunciado);

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

    @Override
    public boolean associateUDToEnunciado(UnidadDidactica unidadDidactica, Enunciado enunciado) throws CreateException {
        boolean cambios = false;
        // Abrimos la conexión
        con = conection.openConnection();
        try {
            stmt = con.prepareStatement(UPDATEud_e);

            stmt.setInt(1, enunciado.getId());
            stmt.setInt(2, unidadDidactica.getId());

            if (stmt.executeUpdate() == 1) {
                cambios = true;
            }
        } catch (SQLException e1) {
            String error = "Error al asociar unidad didactica";
            CreateException ex = new CreateException(error);
            throw ex;
        } finally {
            conection.closeConnection(stmt, con);
        }
        return cambios;       
    }

    @Override
    public boolean associateEnunciadoToConvocatoriaExamen(Enunciado enunciado, ConvocatoriaExamen convocatoriaExamen) throws CreateException {
        boolean cambios = false;
        // Abrimos la conexión
        con = conection.openConnection();
        try {
            stmt = con.prepareStatement(UPDATEconvocatoriaExamen);

            stmt.setInt(1, enunciado.getId());
            stmt.setString(2, convocatoriaExamen.getConvocatoria());

            if (stmt.executeUpdate() == 1) {
                cambios = true;
            }
        } catch (SQLException e1) {
            String error = "Error al asociar convocatoria";
            CreateException ex = new CreateException(error);
            throw ex;
        } finally {
            conection.closeConnection(stmt, con);
        }
        return cambios; 
    }

    @Override
    public ArrayList<UnidadDidactica> getAllUnidadesDidacticas() throws CreateException {
        // Tenemos que definir el ResultSet para recoger el resultado de la consulta
        ResultSet rs = null;
        ArrayList<UnidadDidactica> unidadesDidacticasList = new ArrayList<>();

        // Abro la conexióm
        con = conection.openConnection();

        try {
            stmt = con.prepareStatement(SELECTAllUnidadesDidacticas);
            rs = stmt.executeQuery();

            // Iteramos sobre el ResultSet
            while (rs.next()) {
                UnidadDidactica ud = new UnidadDidactica();
                
                //recoger el enunciados
                
                ud.setId(rs.getInt("id"));
                ud.setAcronimo(rs.getString("acronimo"));
                ud.setTitulo(rs.getString("titulo"));
                ud.setEvaluacion(rs.getString("evaluacion"));
                ud.setDescripcion(rs.getString("descripcion"));
               

                unidadesDidacticasList.add(ud);
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
                    String error = "Error al consultar unidades didacticas";
                    CreateException e1 = new CreateException(error);
                    throw e1;
                }
            }
            conection.closeConnection(stmt, con);
        }
        return unidadesDidacticasList;
    }

    @Override
    public ArrayList<ConvocatoriaExamen> getAllConvocatoriasExamen() throws CreateException {
         // Tenemos que definir el ResultSet para recoger el resultado de la consulta
        ResultSet rs = null;
        ArrayList<ConvocatoriaExamen> convocatoriasExamenList = new ArrayList<>();

        // Abro la conexióm
        con = conection.openConnection();

        try {
            stmt = con.prepareStatement(SELECTAllConvocatoriasExamen);
            rs = stmt.executeQuery();

            // Iteramos sobre el ResultSet
            while (rs.next()) {
                ConvocatoriaExamen ce = new ConvocatoriaExamen();
                
                //recoger el resto de atributos
                ce.setConvocatoria(rs.getString("convocatoria"));
                
                convocatoriasExamenList.add(ce);
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
                    String error = "Error al consultar convocatorias examen";
                    CreateException e1 = new CreateException(error);
                    throw e1;
                }
            }
            conection.closeConnection(stmt, con);
        }
        return convocatoriasExamenList;
    }

    @Override
    public Enunciado getEnunciadoByDescription(String description) throws CreateException {
        Enunciado enunciado = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // Abro la conexión
        con = conection.openConnection();

        try {
            stmt = con.prepareStatement(SELECTEnunciadoByDescription);
            stmt.setString(1, description);
            rs = stmt.executeQuery();

            if (rs.next()) {
                enunciado = new Enunciado();
                
                //recoger el resto de atributos
                enunciado.setId(rs.getInt("id"));
            
            }
        } catch (SQLException e) {
            throw new CreateException("Error al consultar el enunciado con descripcion: " + description, e);
        } finally {
            // Cierro ResultSet y PreparedStatement
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new CreateException("Error al cerrar los recursos", e);
            }
            // Cierro la conexión
            conection.closeConnection(stmt, con);
        }
        return enunciado;  
        
    }

}
