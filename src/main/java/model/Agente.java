/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author idurfer
 */

// Agente que participa en las negociaciones

public class Agente {
    
    private int id;
    private String nombre;
    private double saldo;
    private Operacion operacionCompra;
    private Operacion operacionVenta;

    public Agente(int id, String nombre, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.operacionCompra = null;
        this.operacionVenta = null;
    }
    
    //Metodo que comprueba si el agente tiene el dinero suficiente para comprar
    public boolean comprobarSiPuedeComprar(double precio, int cantidad) {
        return saldo >= precio * cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Operacion getOperacionCompra() {
        return operacionCompra;
    }

    public void setOperacionCompra(Operacion operacionCompra) {
        this.operacionCompra = operacionCompra;
    }

    public Operacion getOperacionVenta() {
        return operacionVenta;
    }

    public void setOperacionVenta(Operacion operacionVenta) {
        this.operacionVenta = operacionVenta;
    }

    @Override
    public String toString() {
        return id + " | " + nombre + " | " + saldo + "€";
    }
}