/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.Agente;
import model.Operacion;
import persistencia.GuardarAgente;
import persistencia.GuardarOperacion;
import view.MainFrame;

/**
 *
 * @author idurfer
 */
public class FrontController {

    private MainFrame ventana;
    private Broker broker;

    public FrontController(MainFrame ventana, Broker broker) {
        this.ventana = ventana;
        this.broker = broker;

        // Cargar datos del archivo TXT de la sesion anterior
        cargarAgentes();
        cargarOperaciones();

        // Anhadir la grafica al panel
        GraficaBolsa graficaBolsa = new GraficaBolsa(broker);
        ventana.getGraficaPanel().setLayout(new java.awt.BorderLayout());
        ventana.getGraficaPanel().add(graficaBolsa.getChartPanel(), java.awt.BorderLayout.CENTER);

        // Arrancar los  hilos
        new Thread(graficaBolsa).start();
        new Thread(new TareaBolsa(broker)).start();

        ventana.addAnhadirAgenteButtonListener(configurarAgentes());
        ventana.addGuardarOperacionButtonListener(configurarOperaciones());
        ventana.getTabbedPane().addChangeListener(configurarCambiosPestanha());
        actualizarListaAgentes();
    }

    // Configura el boton de anhaadir agente
    private ActionListener configurarAgentes() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = ventana.getNombreTextField();
                double saldo = ventana.getSaldoSpinner();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(ventana, "El nombre no puede estar vacío");
                    return;
                }
                if (saldo <= 0) {
                    JOptionPane.showMessageDialog(ventana, "El saldo debe ser mayor que 0");
                    return;
                }
                for (Agente agente : broker.getMapaAgentes().values()) {
                    if (agente.getNombre().equalsIgnoreCase(nombre)) {
                        JOptionPane.showMessageDialog(ventana, "Ya existe un agente con ese nombre");
                        return;
                    }
                }

                int nuevoId = broker.getMapaAgentes().size();
                Agente agente = new Agente(nuevoId, nombre, saldo);
                broker.anhadirAgente(agente);

                ArrayList<Agente> todos = new ArrayList<>(broker.getMapaAgentes().values());
                GuardarAgente.guardarAgentes(todos);
                actualizarListaAgentes();
                actualizarComboBoxAgentes();
            }
        };
        return al;
    }

    //Cargar agentes
    private void cargarAgentes() {
        ArrayList<Agente> agentes = GuardarAgente.cargarAgentes();
        for (Agente agente : agentes) {
            broker.anhadirAgente(agente);
        }
    }

    // Configura el boton de guardar operacion
    private ActionListener configurarOperaciones() {
        // Actualizar comboBox al cambiar a la pestanha de operaciones
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = ventana.getIndexAgente();
                if (index < 0) {
                    JOptionPane.showMessageDialog(ventana, "Selecciona un agente");
                    return;
                }

                ArrayList<Agente> lista = new ArrayList<>(broker.getMapaAgentes().values());
                Agente agente = lista.get(index);
                String tipo = ventana.getTipoSeleccionado();
                double precio = ventana.getPrecioSpinner();
                int cantidad = ventana.getCantidadSpinner();

                if (precio <= 0 || cantidad <= 0) {
                    JOptionPane.showMessageDialog(ventana, "El precio y  la cantidad tienen que ser mayores que 0");
                    return;
                }
                if (tipo.equals("compra") && agente.getOperacionCompra() != null) {
                    JOptionPane.showMessageDialog(ventana, "El agente ya tiene una orden de compra activa");
                    return;
                }
                if (tipo.equals("venta") && agente.getOperacionVenta() != null) {
                    JOptionPane.showMessageDialog(ventana, "El agente ya tiene una orden de venta activa");
                    return;
                }
                if (tipo.equals("compra") && !agente.comprobarSiPuedeComprar(precio, cantidad)) {
                    JOptionPane.showMessageDialog(ventana, "Saldo insuficiente");
                    return;
                }

                Operacion operacion = new Operacion(tipo, precio, cantidad, agente);
                broker.anhadirOperacion(operacion);
                if (tipo.equals("compra")) {
                    agente.setOperacionCompra(operacion);
                    ventana.setInfoCompraLabel("Compra: " + precio + "€ x " + cantidad);
                } else {
                    agente.setOperacionVenta(operacion);
                    ventana.setInfoVentaLabel("Venta: " + precio + "€ x " + cantidad);
                }

                ArrayList<Operacion> todasOps = new ArrayList<>();
                todasOps.addAll(broker.getOperacionCompra());
                todasOps.addAll(broker.getOperacionVenta());
                GuardarOperacion.guardarOperaciones(todasOps);

                JOptionPane.showMessageDialog(ventana, "Operacion creada");
            }
        };
        return al;
    }

    //Cargar operaciones
    private void cargarOperaciones() {
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
    }

    private ChangeListener configurarCambiosPestanha() {
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (ventana.getTabbedPane().getSelectedIndex() == 2) {
                    actualizarComboBoxAgentes();
                }
            }
        };
        return changeListener;
    }

    private void actualizarListaAgentes() {
        ventana.limpiarListaAgentes();
        for (Agente agente : broker.getMapaAgentes().values()) {
            ventana.añadirAgenteALista(agente.toString());
        }
    }

    private void actualizarComboBoxAgentes() {
        ventana.limpiarComboBoxAgentes();
        for (Agente agente : broker.getMapaAgentes().values()) {
            ventana.addAnhadirItemComboBoxAgentes(agente.toString());
        }
    }
}