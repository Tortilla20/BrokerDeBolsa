/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Agente;
import model.Operacion;
import persistencia.GuardarAgente;

/**
 *
 * @author idurfer
 */

public class TareaBolsa implements Runnable{

    private Broker broker;

    public TareaBolsa(Broker broker) {
        this.broker = broker;
    }
    
    //hacer que compruebe si una mejor compra y una mejor venta se cruzan
    private void ejecutar() {
        synchronized (broker) {
            Operacion compra = broker.getCompraCarilla();
            Operacion venta = broker.getVentaBaratilla();
            
            //Si no hay operacioes en ninguno, no se deberian cruzar
            if(compra == null || venta == null) {
                return;
            }
            
            //hacer que se cruzen si el precio de compra es mayor o igual al de venta
            if(compra.getLimite() >= venta.getLimite()) {
                Agente comprador = compra.getReferenciaIdAgente();
                Agente vendedor = venta.getReferenciaIdAgente();
                
                //La cantidad en la que se inteercambia es el minimo de las 2 ordenes
                int cantidadTransaccion = Math.min(compra.getCantidad(), venta.getCantidad());
                double precioTransaccion = venta.getLimite();
                double totalDinero = precioTransaccion * cantidadTransaccion;
                
                //Actualizar saldos
                comprador.setSaldo(comprador.getSaldo() - totalDinero);
                vendedor.setSaldo(vendedor.getSaldo() + totalDinero);
                
                //Reducir las cantidades de esas ordenes
                compra.setCantidad(compra.getCantidad() - cantidadTransaccion);
                venta.setCantidad(venta.getCantidad() - cantidadTransaccion);
                
                //si una orden se queda en 0, eliminar esa orden
                if(compra.getCantidad() == 0) {
                    broker.eliminarOperacionCompra(compra);
                    comprador.setOperacionCompra(null);
                }
                if(venta.getCantidad() == 0) {
                    broker.eliminarOperacionVenta(venta);
                    vendedor.setOperacionVenta(null);
                }
                
                // Actualizar el precio del mercado
                broker.setPrecioActual(precioTransaccion);
                
                //guardar los agente actualizados
                ArrayList<Agente> todos = new ArrayList<>(broker.getMapaAgentes().values());
                GuardarAgente.guardarAgentes(todos);
            }
        }
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                if(broker.getOperacionCompra().size()> 0 && broker.getOperacionVenta().size() > 0) {
                    ejecutar();
                    Thread.sleep(100);
                } else {
                    Thread.sleep(300);
                }
            } catch(Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            } 
        }
    }
}