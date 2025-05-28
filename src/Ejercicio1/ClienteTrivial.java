package Ejercicio1;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteTrivial {

    public static void main(String[] args) {

        String host = "localhost";
        int puerto = 6000;

        try (Socket cliente = new Socket(host, puerto)) {

            try (DataInputStream entradaDatos = new DataInputStream(cliente.getInputStream()); DataOutputStream salidaDatos = new DataOutputStream(cliente.getOutputStream())) {

                Scanner scan = new Scanner(System.in);

                boolean esVerdad = true;
                String mensajeServidor = "";
                String respuesta = "";
                int contador = 0;
                while (esVerdad & !respuesta.equals("salir".trim()) & contador < 4) {

                    mensajeServidor = entradaDatos.readUTF();
                    System.out.println(mensajeServidor);

                    System.out.print("Tu respuesta (formato: respuesta:xxx): ");
                    respuesta = scan.nextLine();

                    salidaDatos.writeUTF(respuesta);

                    if (respuesta.equals("salir")) {
                        esVerdad = false;
                        System.out.println(entradaDatos.readUTF());
                    } else {
                        System.out.println(entradaDatos.readUTF());
                    }

                    contador++;

                }

                // Si no he escrito salir se muestra el mensaje de fin del juego, sino, se muestra arriba
                if (contador == 4 && !respuesta.equals("salir")) {
                    System.out.println(entradaDatos.readUTF());
                }

            } catch (IOException ex) {
                System.err.println("Error. No se ha podido leer o enviar datos al servidor. " + ex.getMessage());
            }

        } catch (IOException ex) {
            System.err.println("Error. No se ha podido realizar la conexión. " + ex.getMessage());
        }

    }
}
