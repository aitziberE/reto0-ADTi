/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import utilidades.Util;
import model.*;

/**
 *
 * @author inifr
 */
public class Main {

    /**
     * menu = Variable para estsablecer las opciones del menú.
     *
     * @param args
     */
    public static void main(String[] args) {
        int menu = 0;
        do {
            menu = menu();
            switch (menu) {
                case 1:
                    createUnidad();
                    break;
                case 2:
                    createEnunciado();
                    break;
                case 3:
                    checkEnunciado();
                    break;
                case 4:
                    checkConvocatoria();
                    break;
                case 5:
                    viewDocument();
                    break;
                case 6:
                    asignEnunciado();
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

    private static void createUnidad() {
        System.out.println("Introduzca los siguientes datos de la unidad didáctica a crear: \nTítulo:");
        String tituloUD = Util.introducirCadena();
        System.out.println("Acrónimo:");
        String acronimoUD = Util.introducirCadena();
        System.out.println("Evaluación:");
        String evaluacionUD = Util.introducirCadena();
        System.out.println("Descripción:");
        String descripcionUD = Util.introducirCadena();
        UnidadDidactica unidad = new UnidadDidactica(acronimoUD, tituloUD, evaluacionUD, descripcionUD);

    }

    private static void createEnunciado() {
        String nivelInput;
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

        Enunciado enunciado = new Enunciado(descripcionInput, nivel, disponibleInput, rutaInput);
    }

    private static void checkEnunciado() {

    }

    private static void checkConvocatoria() {

    }

    private static void viewDocument() {

    }

    private static void asignEnunciado() {

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
