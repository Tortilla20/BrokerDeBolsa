/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.brokerdebolsarecuperacion;

import controller.Broker;
import controller.FrontController;
import controller.GraficaBolsa;
import controller.TareaBolsa;
import java.awt.BorderLayout;
import java.util.ArrayList;
import model.Agente;
import model.Operacion;
import org.jfree.chart.ChartPanel;
import persistencia.GuardarAgente;
import persistencia.GuardarOperacion;
import view.MainFrame;

/**
 *
 * @author idurfer
 */
public class BrokerDeBolsaRecuperacion {
    
    // REVISAR CLASES MAIN Y FRONTCONTROLLER

    public static void main(String[] args) {

        // Crear el broker con precio inicial
        Broker broker = new Broker(100.0);

        // Cargar agentes y añadirlos al broker
        ArrayList<Agente> agentes = GuardarAgente.cargarAgentes();
        for (Agente agente : agentes) {
            broker.anhadirAgente(agente);
        }

        // Cargar operaciones, reconstruir referencia al agente y añadirlas al broker
        ArrayList<Operacion> operaciones = GuardarOperacion.cargarOperaciones();
        for (Operacion op : operaciones) {
            Agente agente = broker.getAgente(op.getIdAgente());
            if (agente != null) {
                op.setReferenciaIdAgente(agente);
                broker.anhadirOperacion(op);
                if (op.getTipo().equals("compra")) {
                    agente.setOperacionCompra(op);
                } else {
                    agente.setOperacionVenta(op);
                }
            }
        }

        // Crear la ventana y el controlador
        MainFrame ventana = new MainFrame();
        FrontController fc = new FrontController(ventana, broker);

        // Añadir la grafica
        GraficaBolsa graficaBolsa = new GraficaBolsa(broker);
        ChartPanel chartPanel = graficaBolsa.getChartPanel();
        ventana.getGraficaPanel().setLayout(new BorderLayout());
        ventana.getGraficaPanel().add(chartPanel, BorderLayout.CENTER);

        // Arrancar hilos
        new Thread(graficaBolsa).start();
        new Thread(new TareaBolsa(broker)).start();

        ventana.setVisible(true);
    }
}