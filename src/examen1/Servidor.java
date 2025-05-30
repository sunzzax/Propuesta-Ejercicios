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
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int puerto = 8000;
        
        System.out.println("SERVIDOR GENERADOR DE OPERACIONES ARITMÉTICAS.");
        
        try(ServerSocket servidor = new ServerSocket(puerto)){
            
            System.out.println("Esperando al cliente...");
            
            try(Socket clienteConectado = servidor.accept()){
                
                System.out.println("Cliente conectado desde " + clienteConectado.getInetAddress());
                
                try(DataInputStream entradaDatos = new DataInputStream(clienteConectado.getInputStream());
                        DataOutputStream salidaDatos = new DataOutputStream(clienteConectado.getOutputStream())){
                    
                    
                    
                }
                
            }catch (IOException ex){
                System.err.println("Error no se ha podido conectar el cliente. " + ex.getMessage());
            }
            
        } catch (IOException ex) {
            System.err.println("Error al intentar conectarse por el puerto " + puerto + ". " + ex.getMessage());
        }
        
    }
    
}
