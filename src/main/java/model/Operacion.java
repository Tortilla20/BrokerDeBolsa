/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author idurfer
 */
//Esta clase realiza la orden de compra o de venta de uno o varios agentes
public class Operacion {

    private String tipo;
    private double limite;
    private int cantidad;
    private int idAgente;
    private transient Agente referenciaIdAgente;

    public Operacion(String tipo, double limite, int cantidad, Agente agente) {
        this.tipo = tipo;
        this.limite = limite;
        this.cantidad = cantidad;
        this.idAgente = agente.getId();
        this.referenciaIdAgente = agente;
    }

    // segundo constructor para cargar desde un fichero txt (sin agente todavia)
    public Operacion(String tipo, double limite, int cantidad, int idAgente) {
        this.tipo = tipo;
        this.limite = limite;
        this.cantidad = cantidad;
        this.idAgente = idAgente;
        this.referenciaIdAgente = null;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //metodo para que se usara para ordenar las comprar y ventas en el Broker
    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(int idAgente) {
        this.idAgente = idAgente;
    }

    public Agente getReferenciaIdAgente() {
        return referenciaIdAgente;
    }

    public void setReferenciaIdAgente(Agente referenciaIdAgente) {
        this.referenciaIdAgente = referenciaIdAgente;
    }

    @Override
    public String toString() {
        return referenciaIdAgente + " | " + tipo + " | " + limite + "€ | " + cantidad + " uds";
    }
}