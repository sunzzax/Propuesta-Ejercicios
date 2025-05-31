package examen1;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {

        String host = "localhost";
        int puerto = 6000;

        System.out.println("PROGRAMA CLIENTE INICIADO.");

        try (Socket cliente = new Socket(host, puerto)) {

            try (DataInputStream entradaDatos = new DataInputStream(cliente.getInputStream()); 
                    DataOutputStream salidaDatos = new DataOutputStream(cliente.getOutputStream())) {

                String respuestaFinal = "";
                Scanner scan = new Scanner(System.in);

                System.out.println("Vamos a calcular la operación.");
                System.out.println("Resuelve: " + entradaDatos.readUTF());

                while (!respuestaFinal.contains("Correcto")) {

                    System.out.print("Tu respuesta: ");

                    String recibido = scan.nextLine();

                    try {
                        if (recibido.isBlank()) {
                            System.err.println("Error. No puedes dejar espacios en blanco... Intentelo de nuevo.");
                        } else {

                            int numero = Integer.parseInt(recibido);
                            System.out.println(numero);
                            salidaDatos.writeInt(numero);

                            respuestaFinal = entradaDatos.readUTF();
                            System.out.println(respuestaFinal);

                        }
                    } catch (NumberFormatException ex) {
                        System.err.println("Error. Debes introducir un número... Vuelve a intentarlo.");
                    }
                }

                System.out.println("Cerrando conexión...");

            } catch (IOException ex) {
                System.err.println("Error al intentar leer/enviar datos. " + ex.getMessage());
            }

        } catch (IOException ex) {
            System.err.println("Error al intentar conectarse al servidor. " + ex.getMessage());
        }

    }
}
