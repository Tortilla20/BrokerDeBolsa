    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.Operacion;

/**
 *
 * @author idurfer
 */

//Gardar operaciones en un fichero txt
public class GuardarOperacion {
    
    private static String fichero = "operaciones.txt";

    // Guarda todas las operaciones en el fichero
    public static void guardarOperaciones(ArrayList<Operacion> operaciones) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {
            for (Operacion operacion : operaciones) {
                bw.write(operacion.getIdAgente() + " - " + operacion.getTipo() + " - " + operacion.getLimite() + " - " + operacion.getCantidad());
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Carga todas las operaciones del fichero
    public static ArrayList<Operacion> cargarOperaciones() {
        ArrayList<Operacion> lista = new ArrayList<>();
        File file = new File(fichero);
        if (!file.exists()) {
            return lista;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(" - ");
                int idAgente = Integer.parseInt(datos[0]);
                String tipo = datos[1];
                double limite = Double.parseDouble(datos[2]);
                int cantidad = Integer.parseInt(datos[3]);
                // Acordarse de que la referencia al agente ira en el main despues de cargar
                lista.add(new Operacion(tipo, limite, cantidad, idAgente));
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return lista;
    }
}