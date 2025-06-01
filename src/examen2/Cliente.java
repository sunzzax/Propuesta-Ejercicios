/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examen2;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    public static void main(String[] args) {
        
        String host = "localhost";
        int puerto = 6000;
        
        System.out.println("PROGRAMA CLIENTE INICIADO.");
        
        try(Socket cliente = new Socket(host, puerto)){
            System.out.println("Vamos a hacer la conversión.");
            try(DataInputStream entradaDatos = new DataInputStream(cliente.getInputStream());
                    DataOutputStream salidaDatos = new DataOutputStream(cliente.getOutputStream())){
                
                System.out.println("Conectado al servidor. Escribe una conversión o 'salir' para finalizar.");
                System.out.println("Ejemplo: 'C a F:numero o 'F a C:numero' ");
                String salir = "".toLowerCase().trim();
                Scanner scan = new Scanner(System.in);
                String escrito = "".trim();
                
                while (!salir.equals("salir")) {                    
                    System.out.print("Ingrese conversión: ");
                    escrito = scan.nextLine().trim();
                    salir = escrito;
                    salidaDatos.writeUTF(escrito);
                    
                    System.out.println("Servidor: " + entradaDatos.readUTF());
                }
                
                System.out.println("Desconectado del servidor.");
                System.out.println("Cerrando conexión...");
                salidaDatos.writeUTF("Desconectado.");
                
            } catch (IOException ex) {
                System.err.println("Error. No se ha podido enviar/recibir datos. " + ex.getMessage());
            }
            
        } catch (IOException ex) {
            System.err.println("Error. No se ha podido realizar la conexión con el servidor. " + ex.getMessage());
        }
        
    }   
    
}
 