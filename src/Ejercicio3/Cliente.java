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
        String letra = "";
        
        try (Socket cliente = new Socket(host, puerto)) {
            Scanner scan = new Scanner(System.in);

            try (DataInputStream entradaDatos = new DataInputStream(cliente.getInputStream()); 
                    DataOutputStream salidaDatos = new DataOutputStream(cliente.getOutputStream())) {

                while (!letra.contains("salir")) {
                    System.out.println("Introduce una letra para hacer uso del Cifrado César:");
                    letra = scan.nextLine();

                    salidaDatos.writeUTF(letra);

                    System.out.println("Mensaje recibido del Servidor: " + entradaDatos.readUTF());
                }

                System.out.println(entradaDatos.readUTF());
                
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
