/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Ejercicio3;

import java.io.*;
import java.net.*;

public class Servidor {

    public static void main(String[] args) {

        int puerto = 6001;
        String mensaje = "";

        try (ServerSocket servidor = new ServerSocket(puerto)) {

            try (Socket clienteConectado = servidor.accept()) {

                try (DataInputStream entradaDatos = new DataInputStream(clienteConectado.getInputStream()); 
                        DataOutputStream salidaDatos = new DataOutputStream(clienteConectado.getOutputStream())) {

                    System.out.println("Cliente conectado.");

                    while (!mensaje.equals("salir")) {
                        mensaje = entradaDatos.readUTF();
                        System.out.print("Dato recibido del cliente:");
                        System.out.println(mensaje);

                        if (mensaje.isEmpty()) {
                            salidaDatos.writeUTF("No has introducido nada. Se necesita una letra...");
                        } else {

                            String palabra = "";

                            for (int i = 0; i < mensaje.length(); i++) {

                                char letra = mensaje.charAt(i);

                                if (Character.isLetter(letra)) {
                                    letra += 3;

                                    if (Character.isLowerCase(mensaje.charAt(i)) && letra > 'z') {
                                        letra -= 26;
                                    } else if (Character.isUpperCase(mensaje.charAt(i)) && letra > 'Z') {
                                        letra -= 26;
                                    }

                                    palabra += letra;

                                } else {
                                    palabra += "";
                                }

                            }

                            if (mensaje.equals("salir")) {
                                salidaDatos.writeUTF("Fin del Programa.");
                            } else {
                                salidaDatos.writeUTF(palabra);
                            }

                        }

                    }

                } catch (IOException ex) {
                    System.err.println("Error. No se ha podido conectar con el cliente. " + ex.getMessage());
                }
            } catch (IOException ex) {
                System.err.println("Error. No se ha podido realizar la conexión por el puerto: " + puerto + ". " + ex.getMessage());
            } catch (StringIndexOutOfBoundsException ex) {
                System.err.println("Error: No se ha recibido ningún carácter. Puede que el mensaje esté vacío.");
            }

        } catch (IOException ex) {
            System.err.println("Error. Nos se ha podido iniciar el servidor. " + ex.getMessage());
        }
    }
}
