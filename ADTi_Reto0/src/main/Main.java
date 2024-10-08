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
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ander
 * @author Aitziber
 */
public class Main {

    /**
     * menu = Variable para estsablecer las opciones del menú.
     *
     * @param args
     * @throws excepciones.CreateException
     */
    public static void main(String[] args) throws CreateException {

        Controller c = new Controller();

        int menu = 0;
        do {
            menu = menu();
            switch (menu) {

                case 1:
                    createUnidad(c);
                    break;
                case 2:
                    createConvocatoriaExamen(c);
                    break;
                case 3:
                    createEnunciado(c);
                    break;
                case 4:
                    checkEnunciado(c);
                    break;
                case 5:
                    checkConvocatoria(c);
                    break;
                case 6:
                    viewDocument(c);
                    break;
                case 7:
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

    private static int menu() {
        System.out.println("Seleccione una opción:\n"
                + "1. Crear unidad didáctica\n"
                + "2. Crear convocatoria\n"
                + "3. Crear enunciado agregando unidades didácticas y asociando convocatoria\n"
                + "4. Consultar los enunciados de examen en los que se trata una unidad didáctica concreta. \n"
                + "5. Consultar en qué convocatorias se ha utilizado un enunciado concreto. \n"
                + "6. Visualizar el documento de texto asociado a un enunciado. \n"
                + "7. Asignar un enunciado a una convocatoria. \n"
                + "0. Salir.");
        return Util.leerInt(0, 7);
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

    private static void createConvocatoriaExamen(Controller c) {
        try {
            System.out.println("Introduzca los siguientes datos de la convocatoria de exámen:");
            String convocatoria = Util.introducirCadena("Nombre: ");
            String descripcion = Util.introducirCadena("Descripcion: ");
            System.out.println("Fecha (aaaa/MM/dd): ");
            LocalDate fecha = Util.leerFechaAMD();
            String curso = Util.introducirCadena("Curso: ");
            System.out.println("Enunciado: ");
            int enunciado = 0;

            ArrayList<Enunciado> enunciados;

            enunciados = c.getAllEnunciados();

            if (enunciados.size() > 0) {
                int cantidadEnus = 0;
                System.out.println("Seleccione id referente al enunciado que desea asociar:");
                for (int i = 0; enunciados.size() > i; i++) {
                    System.out.println(enunciados.get(i).getId() + ". " + enunciados.get(i).getDescripcion());
                    cantidadEnus = i + 1;
                }
                boolean error;
                do {
                    enunciado = Util.leerInt();
                    if (enunciado <= cantidadEnus && enunciado > 0) {
                        error = false;
                    } else {
                        System.out.println("No existe ese enunciado, elige una de los " + cantidadEnus);
                        error = true;
                    }
                } while (error);
            } else {
                System.out.println("No se encontraron enunciados");
            }

            c.crearConvocatoria(convocatoria, descripcion, fecha, curso, enunciado);
        } catch (CreateException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void createEnunciado(Controller c) {
        try {
            Dificultad nivel = Dificultad.BAJA;
            ArrayList<ConvocatoriaExamen> convocatoriaExamenList;
            ArrayList<UnidadDidactica> unidadDidacticaList;
            boolean associateUDToEnunciadoSuccess = false;
            boolean associateEnunciadoToConvocatoriaExamenSuccess = false;

            System.out.println("Introduzca los siguientes datos del enunciado:");
            String descripcion = Util.introducirCadena("Descripcion:");

            nivel = nivel.validarDificultad();
            String nivelString = nivel.name();

            System.out.println("¿Está disponible el enunciado? (si/no):");
            boolean disponible = Util.isBoolean();
            String ruta = Util.introducirCadena("Ruta:");

            c.crearEnunciado(descripcion, nivelString, disponible, ruta);

            Enunciado enunciado = c.getEnunciadoByDescription(descripcion);

            unidadDidacticaList = c.getAllUnidadesDidacticas();

            if (unidadDidacticaList.size() > 0) {
                int cantidadUds = 0;
                System.out.println("Seleccione num referente a la unidad didactica que desea asociar:");
                for (int i = 0; unidadDidacticaList.size() > i; i++) {
                    System.out.println(i + 1 + ". " + unidadDidacticaList.get(i).getAcronimo());
                    cantidadUds = i + 1;
                }
                int userSelection = 0;
                boolean error;
                do {
                    userSelection = Util.leerInt();
                    if (userSelection <= cantidadUds && userSelection > 0) {
                        error = false;
                    } else {
                        System.out.println("No existe esa UD, elige una de las " + cantidadUds);
                        error = true;
                    }
                } while (error);
                UnidadDidactica ud = unidadDidacticaList.get(userSelection - 1);

                associateUDToEnunciadoSuccess = c.associateUDToEnunciado(ud, enunciado);

            } else {
                System.out.println("No se encontraron unidades didacticas");
            }
            //visualizar convocatorias
            convocatoriaExamenList = c.getAllConvocatoriasExamen();

            if (convocatoriaExamenList.size() > 0) {
                int cantidadConvocatorias = 0;
                System.out.println("Seleccione num referente a la unidad didactica que desea asociar:");
                for (int j = 0; convocatoriaExamenList.size() > j; j++) {
                    System.out.println(j + 1 + ". " + convocatoriaExamenList.get(j).getConvocatoria());
                    cantidadConvocatorias = j + 1;
                }
                int userSelection = 0;
                boolean error;
                do {
                    userSelection = Util.leerInt();
                    if (userSelection <= cantidadConvocatorias && userSelection > 0) {
                        error = false;
                    } else {
                        System.out.println("No existe esa UD, elige una de las " + cantidadConvocatorias);
                        error = true;
                    }
                } while (error);
                ConvocatoriaExamen ce = convocatoriaExamenList.get(userSelection - 1);

                associateEnunciadoToConvocatoriaExamenSuccess = c.associateEnunciadoToConvocatoriaExamen(enunciado, ce);

            } else {
                System.out.println("No se encontraron convocatorias");
            }

            if (associateUDToEnunciadoSuccess && associateEnunciadoToConvocatoriaExamenSuccess) {
                System.out.println("operación realizada");
            } else {
                System.out.println("hubo algun error");
            }

        } catch (CreateException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void checkEnunciado(Controller c) {
        // Consultar los enunciados de examen en los que se trata una unidad didáctica concreta.
        try {
            ArrayList<UnidadDidactica> unidadDidacticaList = c.getAllUnidadesDidacticas();
                          
            if (unidadDidacticaList.size() > 0) {
                int cantidadUds = 0;
                System.out.println("Seleccione num referente a la unidad didactica que desea consultar:");
                for (int i = 0; unidadDidacticaList.size() > i; i++) {
                    System.out.println(i + 1 + ". " + unidadDidacticaList.get(i).getAcronimo());
                    cantidadUds = i + 1;
                }
                int userSelection = 0;
                boolean error;
                do {
                    userSelection = Util.leerInt();
                    if (userSelection <= cantidadUds && userSelection > 0) {
                        error = false;
                    } else {
                        System.out.println("No existe esa UD, elige una de las " + cantidadUds);
                        error = true;
                    }
                } while (error);
                UnidadDidactica ud = unidadDidacticaList.get(userSelection - 1);
                
                ArrayList<Enunciado> enunciadoList = c.consultarEnunciado(ud.getAcronimo());
                 
                if (enunciadoList.size() > 0) {
                    System.out.println("La unidad didáctica '" + ud.getAcronimo() + "' se trata en los siguientes enunciados:");
                    enunciadoList.forEach((enunciado) -> {
                        System.out.println("\t" + "- "+ enunciado.getDescripcion());
                     });
                } else {
                    System.out.println("No se encontraron enunciados");
                }
            } else {
                System.out.println("No se encontraron unidades didacticas");
            }
        } catch (CreateException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    private static void checkConvocatoria(Controller c) throws CreateException {
        // Consultar en que convocatorias se ha utilizado un enunciado concreto.
        ArrayList<ConvocatoriaExamen> convocatorias;
        ArrayList<Enunciado> enunciados;
        int enunciado = 0;
        enunciados = c.getAllEnunciados();

        if (enunciados.size() > 0) {
            int cantidadEnus = 0;
            System.out.println("Seleccione el numero de enunciado: ");
                for (int i = 0; enunciados.size() > i; i++) {
                    System.out.println(enunciados.get(i).getId() + ". " + enunciados.get(i).getDescripcion());
                    cantidadEnus = i + 1;
                }
            boolean error;
            do {
                enunciado = Util.leerInt();
                if (enunciado <= cantidadEnus && enunciado > 0) {
                    error = false;
                } else {
                    System.out.println("No existe ese enunciado, elige una de los " + cantidadEnus);
                    error = true;
                }
            } while (error);
        } else {
            System.out.println("No se encontraron enunciados");
        }
        convocatorias = c.consultarConvocatoriaPorEnunciado(enunciado);
        if (convocatorias.size() > 0) {
            System.out.println("El enunciado seleccionado se ha utilizado en "+ convocatorias.size() +" convocatorias");
            for (int i = 0; i < convocatorias.size(); i++) {
                System.out.println("\t" + "- "+ convocatorias.get(i).getConvocatoria());
            }
        } else {
            System.out.println("No se encontraron convocatorias para ese enunciado");
        }
    }

    private static void viewDocument(Controller c) throws CreateException {
        try {
            ArrayList<Enunciado> enunciadosList = c.getAllEnunciados();
            System.out.println("Introduce el numero que corresponda al enunciado que quieres visualizar:");

            for (int i = 0; enunciadosList.size() > i; i++) {
                System.out.println(i + 1 + ". " + enunciadosList.get(i).getDescripcion());
            }
            int userSelection = Util.leerInt();
            int enunciadoId = enunciadosList.get(userSelection - 1).getId();
            Enunciado enunciado = c.consultarEnunciadoPorId(enunciadoId);

            File archivo = new File(enunciado.getRuta());
            // Usar Runtime para ejecutar el Bloc de notas
            if (archivo.exists()) {
                String command = "notepad.exe " + archivo.getAbsolutePath();
                Runtime.getRuntime().exec(command);
            } else {
                System.out.println("El archivo no existe en la ruta especificada: " + enunciado.getRuta());
            }
        } catch (IOException e) {
            System.out.println("Error al intentar abrir el archivo: " + e.getMessage());
        }
    }

    private static void asignEnunciado(Controller c) {
        try {
            // Asignar un enunciado a una convocatoria
            boolean asignarEnunciadoAConvocatoriaSuccess= false;
            ArrayList<ConvocatoriaExamen> convocatorias;
            ArrayList<Enunciado> enunciados;
            int enunciado = 0;
            int conv = 0;
            enunciados = c.getAllEnunciados();
            convocatorias=c.getAllConvocatoriasExamen();
            
            if (enunciados.size() > 0) {
                int cantidadEnus = 0;
                System.out.println("Seleccione el numero de enunciado: ");
                for (int i = 0; enunciados.size() > i; i++) {
                    System.out.println(enunciados.get(i).getId() + ". " + enunciados.get(i).getDescripcion());
                    cantidadEnus = i + 1;
                }
                boolean error;
                do {
                    enunciado = Util.leerInt();
                    if (enunciado <= cantidadEnus && enunciado > 0) {
                        error = false;
                    } else {
                        System.out.println("No existe ese enunciado, elige uno de los " + cantidadEnus);
                        error = true;
                    }
                } while (error);
            } else {
                System.out.println("No se encontraron enunciados");
            }
            
            if (convocatorias.size() > 0) {
                int cantidadConv = 0;
                System.out.println("Seleccione el numero de convocatoria: ");
                for (int i = 0; convocatorias.size() > i; i++) {
                    System.out.println(i+1 +". " + convocatorias.get(i).getConvocatoria());
                    cantidadConv = i + 1;
                }
                boolean error;
                do {
                    conv = Util.leerInt();
                    if (conv <= cantidadConv && conv > 0) {
                        error = false;
                    } else {
                        System.out.println("No existe esa convocatorias, elige una de las " + cantidadConv);
                        error = true;
                    }
                } while (error);
            } else {
                System.out.println("No se encontraron convocatorias");
            }
            
            asignarEnunciadoAConvocatoriaSuccess =  c.asignarEnunciadoAConvocatoria(enunciados.get(enunciado-1).getId(), convocatorias.get(conv-1).getConvocatoria());
            
            if (asignarEnunciadoAConvocatoriaSuccess) {
                System.out.println("operación realizada");
            } else {
                System.out.println("hubo algun error");
            }
        } catch (CreateException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
