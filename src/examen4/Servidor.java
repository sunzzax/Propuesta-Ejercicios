/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examen4;

import java.io.*;
import java.net.*;

/**
 *
 * @author kira
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int puerto = 7000;
        int numClientes = 0;
        
        try (ServerSocket servidor = new ServerSocket(puerto)) {

            while (numClientes < 5) {
                System.out.println("Esperando al Cliente...");
                try {
                    Socket cliente = servidor.accept();
                    System.out.println("Cliente conectado por " + cliente.getInetAddress());
                    numClientes++;
                    
                     new ClienteHandler(cliente).start();
                        
                } catch (IOException ex) {
                    System.err.println("Error. No se ha podido realizar la conexión con el cliente. " + ex.getMessage());
                }
            }
            
        } catch (IOException ex) {
            System.err.println("Error. No se ha podido realizar la conexión por el puerto " + puerto + ". "
                    + ex.getMessage());
        }

    }

}
