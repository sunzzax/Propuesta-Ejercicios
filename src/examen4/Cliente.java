/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen4;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author kira
 */
public class Cliente {
    public static void main(String[] args) {
        
        int puerto = 7000;
        String host = "localhost";
        Scanner scan = new Scanner(System.in);
        
        try(Socket cliente = new Socket(host, puerto)){
            
            try(DataInputStream entradaDatos = new DataInputStream(cliente.getInputStream());
                    DataOutputStream salidaDatos = new DataOutputStream(cliente.getOutputStream())) {
                
                System.out.print("Introduce una fecha en formato dd/mm/aaaa: ");
                 String fecha = scan.nextLine();
                salidaDatos.writeUTF(fecha);
                
                System.out.println("Respuesta del servidor: " + entradaDatos.readUTF());
                
                System.out.println("Desconectado del servidor.");
                
            } catch (IOException ex) {
                System.err.println("Error no se ha podido leer/enviar datos. " + ex.getMessage());
            }
            
        } catch (IOException ex) {
            System.err.println("Error. No se ha podido realizar la conexión. " + ex.getMessage());
        }
        
    }
}
