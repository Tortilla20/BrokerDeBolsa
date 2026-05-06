/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import model.Agente;
import model.Operacion;

/**
 *
 * @author idurfer
 */
// Clase del estado del mercado 
// Guarda el precio actual con los agentes y las listas de operaciones pendientes
// Introducir metodos con synchronized para evitar problemas con los hilos
public class Broker {

    private double precioActual;

    private HashMap<Integer, Agente> mapaAgentes = new HashMap<>();
    private List<Operacion> operacionCompra = new ArrayList<>();
    private List<Operacion> operacionVenta = new ArrayList<>();

    public Broker(double precioActual) {
        this.precioActual = precioActual;
    }

    //metodo que anhade una operacion a la lista y que la ordene
    public synchronized void anhadirOperacion(Operacion operacion) {
        if (operacion.getTipo().equals("compra")) {
            operacionCompra.add(operacion);
            //Las comprar se ordenan de mayor a menor precio
            operacionCompra.sort(Comparator.comparingDouble(Operacion::getLimite).reversed());
        }
        if (operacion.getTipo().equals("venta")) {
            operacionVenta.add(operacion);
            //Las ventas se ordenan de menor a mayor precio
            operacionVenta.sort(Comparator.comparingDouble(Operacion::getLimite));
        }
    }

    // Anhadir agente
    public synchronized void anhadirAgente(Agente agente) {
        mapaAgentes.put(agente.getId(), agente);
    }

    // metodos que devuelven la compra con el precio mas caro y la venta con el precio mas bajo
    public synchronized Operacion getCompraCarilla() {
        if (operacionCompra.isEmpty()) {
            return null;
        } else {
            return operacionCompra.get(0);
        }
    }

    public synchronized Operacion getVentaBaratilla() {
        if (operacionVenta.isEmpty()) {
            return null;
        } else {
            return operacionVenta.get(0);
        }
    }

    //metodos que eliminan las operaciones de compra y de venta
    public synchronized void eliminarOperacionCompra(Operacion operacion) {
        operacionCompra.remove(operacion);
    }

    public synchronized void eliminarOperacionVenta(Operacion operacion) {
        operacionVenta.remove(operacion);
    }

    public double getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(double precioActual) {
        this.precioActual = precioActual;
    }

    public HashMap<Integer, Agente> getMapaAgentes() {
        return mapaAgentes;
    }

    public void setMapaAgentes(HashMap<Integer, Agente> mapaAgentes) {
        this.mapaAgentes = mapaAgentes;
    }

    public List<Operacion> getOperacionCompra() {
        return operacionCompra;
    }

    public void setOperacionCompra(List<Operacion> operacionCompra) {
        this.operacionCompra = operacionCompra;
    }

    public List<Operacion> getOperacionVenta() {
        return operacionVenta;
    }

    public void setOperacionVenta(List<Operacion> operacionVenta) {
        this.operacionVenta = operacionVenta;
    }

    // Devuelve un agente por su id
    public synchronized Agente getAgente(int id) {
        return mapaAgentes.get(id);
    }
}