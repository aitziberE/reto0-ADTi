/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import utilidades.Util;
import model.*;
import java.util.ArrayList;
import java.time.LocalDate;
import controller.Controller;
import excepciones.CreateException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aitziber, Iñigo
 */
public class Main {
     

    /**
     * menu = Variable para estsablecer las opciones del menú.
     *
     * @param args
     */
    public static void main(String[] args) {
        
        Controller c = new Controller();
        
        int menu = 0;
        do {
            menu = menu();
            switch (menu) {
                case 1:
                    createUnidad(c);
                    break;
                case 2:
                    createEnunciado(c);
                    break;
                case 3:
                    checkEnunciado(c);
                    break;
                case 4:
                    checkConvocatoria(c);
                    break;
                case 5:
                    viewDocument(c);
                    break;
                case 6:
                    asignEnunciado(c);
                    break;
                case 0:
                    System.out.println("Terminando ejecución del programa...");
                    break;
                default:
                    System.err.println("ERROR. Por favor, seleccione una opción  válida.");
                    break;
            }
        } while (menu != 0);
    }

    private static void createUnidad(Controller c) {
        try {
            System.out.println("Introduzca los siguientes datos de la unidad didáctica a crear:");
            String titulo = Util.introducirCadena("Título:");
            String acronimo = Util.introducirCadena("Acrónimo:");
            String evaluacion = Util.introducirCadena("Evaluación:");
            String descripcion = Util.introducirCadena("Descripción:");
            
            c.crearUnidadDidactica(acronimo, titulo, evaluacion, descripcion);
        } catch (CreateException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }   

    private static void createConvocatoriaExamen(Controller c){
        try {
            System.out.println("Introduzca los siguientes datos de la convocatoria de exámen:");
            String convocatoria = Util.introducirCadena("convocatoria");
            String descripcion = Util.introducirCadena("descripcion");
            System.out.println("fecha:");
            LocalDate fecha = Util.leerFechaAMD();
            String curso = Util.introducirCadena("Curso");
            System.out.println("enunciado:");
            
            int enunciado = Util.leerInt();
            
            c.crearConvocatoria(convocatoria, descripcion, fecha, curso, enunciado);
            //aqui no se le asigna enunciado
            
            // lo que habia hecho el iñigo
            /*      String nivelInput;
            Dificultad nivel = null;
            boolean disponibleInput;
            
            System.out.println("Introduzca los datos del enunciado a crear: \nDescripción:");
            String descripcionInput = Util.introducirCadena();
            do {
            System.out.println("Dificultad: (Baja, Media, Alta)");
            nivelInput = Util.introducirCadena();
            try {
            nivel = Dificultad.valueOf(nivelInput);
            
            } catch (IllegalArgumentException e) {
            System.out.println(e);
            }
            } while (nivelInput == null);
            
            System.out.println("Disponibilidad: (Activado / Desactivado)");
            if (Util.introducirCadena().equalsIgnoreCase("activado")) {
            disponibleInput = true;
            } else {
            disponibleInput = false;
            }
            System.out.println("Ruta:");
            //TODO buscar forma de hacer esto
            String rutaInput = "";
            
            Enunciado enunciado = new Enunciado(descripcionInput, nivel, disponibleInput, rutaInput); */
        } catch (CreateException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     

    private static void createEnunciado(Controller c){

        // private String descripcion;
        // private Dificultad nivel;
        // private boolean disponible;
        // private String ruta;
        // private ArrayList<ConvocatoriaExamen> convocatoriaExamenList;
        // private ArrayList<UnidadDidactica> unidadDidacticaList;
        //los dos arrays preguntar por id y añadir al arraylist el objeto con ese id
        //agregar las unidades didácticas
        //se asociará a este enunciado la convocatoria para la que se crea
    }
    
    private static void checkEnunciado(Controller c){
        // Consultar los enunciados de examen en los que se trata una unidad didáctica concreta.

        // convocatoriasList <ConvocatoriaExamen> () = metodoDelControlador(id enunciado)
        //pedir  moreno ese metodo, se lo cuentas
    }
    
    private static void checkConvocatoria(Controller c){
        // Consultar en que convocatorias se ha utilizado un enunciado concreto.
    }
    
    private static void viewDocument(Controller c){
        // Visualizar el documento de texto asociado a un enunciado.
        //aplicar lógica de las rutas.
        // dentro de resources meter algunos .txt con info del enunciado 
        //que el usuario de el id y moreno nos devuelva la ruta que tiene ese enunciado
        // desde aqui se abre esa ruta que corresponde a un archivo de resources
    }
    
    private static void asignEnunciado(Controller c){
        // pedir id de convocatoria e id de enunciado
        // sobreescribir el enunciado que tenia esa convocatoria o asignarselo sin más.
        // boolean añadidoConExito = metodMOreno(id conv, id enunc)

        //UnidadDidactica unidad = new UnidadDidactica(acronimoUD, tituloUD, evaluacionUD, descripcionUD);

    }



    private static int menu() {
        System.out.println("Seleccione una opción:\n"
                + "1. Crear unidad didáctica y convocatoria nuevas. \n"
                + "2. Crear y asociar enunciado\n"
                + "3. Consultar los enunciados de examen en los que se trata una unidad didáctica concreta. \n"
                + "4. Consultar en que convocatorias se ha utilizado un enunciado concreto. \n"
                + "5. Visualizar el documento de texto asociado a un enunciado. \n"
                + "6. Asignar un enunciado a una convocatoria. \n"
                + "0. Salir.");
        return Util.leerInt(0, 6);
    }

}
