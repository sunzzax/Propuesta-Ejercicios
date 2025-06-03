/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Ejercicio3;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {

        int puerto = 6001;
        String host = "localhost";
        String palabra = "";
        
        try (Socket cliente = new Socket(host, puerto)) {
            Scanner scan = new Scanner(System.in);

            try (DataInputStream entradaDatos = new DataInputStream(cliente.getInputStream()); 
                    DataOutputStream salidaDatos = new DataOutputStream(cliente.getOutputStream())) {

                
                // Hago un bucle para que mientras la palabra que yo escriba no sea salir ejecute lo de dentro
                while (!palabra.equals("salir")) {
                    System.out.println("Introduce una palabra para hacer uso del Cifrado César:");

                    // Almaceno la palabra en una variable
                    palabra = scan.nextLine();
                    
                    // Le paso la palabra al servidor
                    salidaDatos.writeUTF(palabra);
                    // Muestro el mensaje recibido del servidor
                    System.out.println("Mensaje recibido del Servidor: " + entradaDatos.readUTF());
                }
                
            }

        } catch (IOException ex) {
            System.err.println("Error. No se ha poddio realizar la conexión con el servidor. " + ex.getMessage());
        }
    }

}
