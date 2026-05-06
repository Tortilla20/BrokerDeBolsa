/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
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
        configurarAgentes();
        configurarOperaciones();
        actualizarListaAgentes();
    }

    // Configura el boton de añadir agente
    private void configurarAgentes() {
        ventana.addAñadirAgenteButtonListener(e -> {
            String nombre = ventana.getNombreTextField();
            double saldo = ventana.getSaldoSpinner();

            if (nombre.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(ventana, "El nombre no puede estar vacío");
                return;
            }
            if (saldo <= 0) {
                javax.swing.JOptionPane.showMessageDialog(ventana, "El saldo debe ser mayor que 0");
                return;
            }
            for (Agente a : broker.getMapaAgentes().values()) {
                if (a.getNombre().equalsIgnoreCase(nombre)) {
                    javax.swing.JOptionPane.showMessageDialog(ventana, "Ya existe un agente con ese nombre");
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
        });
    }

    // Configura el boton de guardar operacion
    private void configurarOperaciones() {
        // Actualizar comboBox al cambiar a la pestaña de operaciones
        ventana.getTabbedPane().addChangeListener(e -> {
            if (ventana.getTabbedPane().getSelectedIndex() == 2) {
                actualizarComboBoxAgentes();
            }
        });

        ventana.addGuardarOperacionButtonListener(e -> {
            int index = ventana.getIndexAgente();
            if (index < 0) {
                javax.swing.JOptionPane.showMessageDialog(ventana, "Selecciona un agente");
                return;
            }

            ArrayList<Agente> lista = new ArrayList<>(broker.getMapaAgentes().values());
            Agente agente = lista.get(index);
            String tipo = ventana.getTipoSeleccionado();
            double precio = ventana.getPrecioSpinner();
            int cantidad = ventana.getCantidadSpinner();

            if (precio <= 0 || cantidad <= 0) {
                javax.swing.JOptionPane.showMessageDialog(ventana, "Precio y cantidad deben ser mayores que 0");
                return;
            }
            if (tipo.equals("compra") && agente.getOperacionCompra() != null) {
                javax.swing.JOptionPane.showMessageDialog(ventana, "Este agente ya tiene una orden de compra activa");
                return;
            }
            if (tipo.equals("venta") && agente.getOperacionVenta() != null) {
                javax.swing.JOptionPane.showMessageDialog(ventana, "Este agente ya tiene una orden de venta activa");
                return;
            }
            if (tipo.equals("compra") && !agente.comprobarSiPuedeComprar(precio, cantidad)) {
                javax.swing.JOptionPane.showMessageDialog(ventana, "Saldo insuficiente para esta compra");
                return;
            }

            Operacion op = new Operacion(tipo, precio, cantidad, agente);
            broker.anhadirOperacion(op);
            if (tipo.equals("compra")) {
                agente.setOperacionCompra(op);
                ventana.setInfoCompraLabel("Compra: " + precio + "€ x " + cantidad);
            } else {
                agente.setOperacionVenta(op);
                ventana.setInfoVentaLabel("Venta: " + precio + "€ x " + cantidad);
            }

            ArrayList<Operacion> todasOps = new ArrayList<>();
            todasOps.addAll(broker.getOperacionCompra());
            todasOps.addAll(broker.getOperacionVenta());
            GuardarOperacion.guardarOperaciones(todasOps);

            javax.swing.JOptionPane.showMessageDialog(ventana, "Operacion creada con éxito");
        });
    }

    private void actualizarListaAgentes() {
        ventana.limpiarListaAgentes();
        for (Agente a : broker.getMapaAgentes().values()) {
            ventana.añadirAgenteALista(a.toString());
        }
    }

    private void actualizarComboBoxAgentes() {
        ventana.limpiarComboBoxAgentes();
        for (Agente a : broker.getMapaAgentes().values()) {
            ventana.añadirItemComboBoxAgentes(a.toString());
        }
    }
}