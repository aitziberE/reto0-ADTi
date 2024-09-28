package controller;

import excepciones.CreateException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.ConvocatoriaExamen;
import model.Enunciado;
import model.UnidadDidactica;

public interface IController {

    public void crearUnidadDidactica(String acronimo, String titulo, String evaluacion, String descripcion) throws CreateException;

    public void crearConvocatoria(String convocatoria, String descripcion, LocalDate fecha, String curso, int enunciadoId) throws CreateException;

    public void crearEnunciado(String descripcion, String nivel, boolean disponible, String ruta) throws CreateException;
    public boolean associateUDToEnunciado(UnidadDidactica unidadDidactica, Enunciado enunciado) throws CreateException;
    public boolean associateEnunciadoToConvocatoriaExamen(Enunciado enunciado, ConvocatoriaExamen convocatoriaExamen) throws CreateException;
    public ArrayList<UnidadDidactica> getAllUnidadesDidacticas() throws CreateException;
    public ArrayList<ConvocatoriaExamen> getAllConvocatoriasExamen() throws CreateException;
    
    public ArrayList<Enunciado> consultarEnunciado(String ud)  throws CreateException;
    
    public ArrayList<Enunciado> getAllEnunciados() throws CreateException;
    
    public ArrayList consultarConvocatoriaPorEnunciado(int enunciadoId)  throws CreateException;
    
    public Enunciado consultarEnunciadoPorId(int enunciadoId) throws CreateException;
    public Enunciado getEnunciadoByDescription(String description) throws CreateException;        
    public String consultarDescripcionEnunciado(int enunciadoId)  throws CreateException;

    public boolean asignarEnunciadoAConvocatoria(int enunciadoId, String convocatoria)  throws CreateException;
}
