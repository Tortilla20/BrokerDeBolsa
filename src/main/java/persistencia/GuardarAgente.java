/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.Agente;

/**
 *
 * @author idurfer
 */

//Gardar agentes en un fichero txt
public class GuardarAgente {
    
    private static String fichero = "agentes.txt";
    
    //guardar los agentes en el fichero
    public static void guardarAgentes(ArrayList<Agente> agentes) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {
            for(Agente agente : agentes) {
                bw.write(agente.getId() + " - " + agente.getNombre() + " - " + agente.getSaldo());
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    //cargar los agentes del fichero
    public static ArrayList<Agente> cargarAgentes() {
        ArrayList<Agente> lista = new ArrayList<>();
        File file = new File(fichero);
        if(!file.exists()) {
            return lista;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while((linea = br.readLine()) != null) {
                String[] datos = linea.split(" - ");
                int id = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                double saldo = Double.parseDouble(datos[2]);
                lista.add(new Agente(id, nombre, saldo));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return lista;
    }
}