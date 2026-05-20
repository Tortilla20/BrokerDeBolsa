/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;



/**
 *
 * @author idurfer
 */

public class GraficaBolsa implements Runnable {

    private TimeSeries serie;
    private Broker broker;

    public GraficaBolsa(Broker broker) {
        this.serie = new TimeSeries("Precio");
        this.broker = broker;
    }

    // meotodo que devuelve el panel con la grafica para anhadirlo a la ventana
    public ChartPanel getChartPanel() {
        TimeSeriesCollection dataset = new TimeSeriesCollection(serie);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Precio", "Tiempo", "€", dataset);
        return new ChartPanel(chart);
    }

    @Override
    public void run() {
        while (true) {
            double precio = broker.getPrecioActual();
            // swingutilities.invokeLater para actualizar la grafica desde el hilo correcto
            SwingUtilities.invokeLater(() -> serie.addOrUpdate(new Millisecond(), precio));
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}