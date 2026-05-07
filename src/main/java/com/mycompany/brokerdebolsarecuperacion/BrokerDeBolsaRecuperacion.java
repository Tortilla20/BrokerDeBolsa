/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.brokerdebolsarecuperacion;

import controller.Broker;
import controller.FrontController;
import view.MainFrame;

/**
 *
 * @author idurfer
 */
public class BrokerDeBolsaRecuperacion {

    public static void main(String[] args) {
        Broker broker = new Broker(100.0);
        MainFrame ventana = new MainFrame();
        FrontController fc = new FrontController(ventana, broker);
        ventana.setVisible(true);
    }
}