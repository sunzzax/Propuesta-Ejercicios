/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examen1;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author kira
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String host = "localhost";
        int puerto = 8000;
        
        System.out.println("PROGRAMA CLIENTE INICIADO.");
        
        try(Socket cliente = new Socket(host, puerto)){
            try(DataInputStream entradaDatos = new DataInputStream(cliente.getInputStream());
                    DataOutputStream salidaDatos = new DataOutputStream(cliente.getOutputStream())){
                
                
                
            }
        } catch (IOException ex) {
            System.err.println("Error. No se ha podido conectar por el puerto " + puerto + "." + ex.getMessage());
        }
        
    }

}
