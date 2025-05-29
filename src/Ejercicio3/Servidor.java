/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Ejercicio3;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {

        int puerto = 6001;
        String mensaje = "";
        try (ServerSocket servidor = new ServerSocket(puerto)) {

            try (Socket clienteConectado = servidor.accept()) {

                try (DataInputStream entradaDatos = new DataInputStream(clienteConectado.getInputStream()); DataOutputStream salidaDatos = new DataOutputStream(clienteConectado.getOutputStream())) {

                    System.out.println("Cliente conectado.");

                    while (!mensaje.equals("salir")) {
                        System.out.print("Dato recibido del cliente:");
                        mensaje = entradaDatos.readUTF().toLowerCase();
                        System.out.println(mensaje);

                        if (mensaje.isEmpty()) {
                            salidaDatos.writeUTF("No has introducido nada. Se necesita una letra...");
                        } else {

                            char letra = mensaje.charAt(0);

                            if (Character.isLetter(letra) && mensaje.length() == 1) {
                                letra += 3;

                                if (letra > 'z') {
                                    letra -= 26; // resto 26 porque son los caracteres que hay en el abecedario.
                                    // Vuelve para atras y se pone en la posición que le corresponde.
                                    // si es x pasa a -> a. (120 + 3 = 123) - 26 = 97 (el 97 en la tabla ascii es 'a')
                                    // si es y pasa a -> b (121 + 3 = 124)  - 26 = 98 (el 98 es 'b')
                                    // si es z pasa a -> c  (122 + 3 = 125) - 26 = 99 (el 99 es 'c)
                                }

                                String enviar = Character.toString(letra);
                                salidaDatos.writeUTF(enviar);
                            } else if (mensaje.length() > 1) {
                                salidaDatos.writeUTF("Debes introducir una sola letra.");
                            } else if (!Character.isLetter(letra)) {
                                salidaDatos.writeUTF("No has introducido una letra, solo se aceptan letras.");
                            }

                        }

                    }

                    salidaDatos.writeUTF("Fin del Programa.");

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
