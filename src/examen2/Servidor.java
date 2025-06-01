package examen2;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {

        int puerto = 6000;

        System.out.println("SERVIDOR CONVERTIDOR DE UNIDADES.");

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Esperando al cliente...");
            try (Socket clienteConectado = servidor.accept()) {
                System.out.println("Cliente conectado desde " + clienteConectado.getInetAddress());
                try (DataInputStream entradaDatos = new DataInputStream(clienteConectado.getInputStream());
                        DataOutputStream salidaDatos = new DataOutputStream(clienteConectado.getOutputStream())) {

                    String formatoCaF = "C a F:";
                    String formatoFaC = "F a C:";
                    String salir = "".toLowerCase().trim();
                    int numero = 0;
                    double resultado = 0;

                    while (!salir.equals("salir")) {

                        String recibido = entradaDatos.readUTF();
                        salir = recibido;
                        if (salir.equals("salir")) {
                            salidaDatos.writeUTF("Conexión Cerrada.");
                        }

                        if (recibido.startsWith(formatoCaF)) {
                            try {
                                numero = Integer.parseInt(recibido.substring(formatoCaF.length()).trim());
                                resultado = (numero * 1.8) + 32;
                                salidaDatos.writeUTF(numero + "ºC son " + resultado + "ºF");
                            } catch (NumberFormatException ex) {
                                salidaDatos.writeUTF("Debes introducir el formato correcto... "
                                        + "'C a F:valor' o 'F a C: valor'. (Los espacios cuentan.)");
                            }

                        } else if (recibido.startsWith(formatoFaC)) {
                            try {
                                numero = Integer.parseInt(recibido.substring(formatoCaF.length()).trim());
                                resultado = (numero - 32) / 1.8;
                                salidaDatos.writeUTF(numero + "ºF son " + resultado + "ºC");
                            } catch (NumberFormatException ex) {
                                salidaDatos.writeUTF("Debes introducir el formato correcto... "
                                        + "'C a F:valor' o 'F a C: valor'. (Los espacios cuentan.)");
                            }

                        } else if (!recibido.startsWith(formatoCaF) && !recibido.startsWith(formatoFaC)) {
                            salidaDatos.writeUTF("Debes introducir el formato correcto... "
                                    + "'C a F:valor' o 'F a C:valor'. (Los espacios cuentan.)");
                        } else if (recibido.equals(salir)) {
                            salidaDatos.writeUTF("Conexión cerrada.");
                        } else {
                            salidaDatos.writeUTF("Debes introducir el formato correcto... "
                                    + "'C a F:valor' o 'F a C: valor'. (Los espacios cuentan.)");
                        }

                    }

                    if (entradaDatos.readUTF().equals("Desconectado.")) {
                        System.out.println("Cliente desconectado. Cerrando servidor...");
                    }

                } catch (IOException ex) {
                    System.err.println("Error. No se ha podido leer/enviar datos. " + ex.getMessage());
                }

            } catch (IOException ex) {
                System.err.println("Error. No se ha podido realizar la conexión con el cliente. "
                        + ex.getMessage());
            }

        } catch (IOException ex) {
            System.err.println("Error. No se ha podido realizar la conexión por el puerto: " + puerto + ". "
                    + ex.getMessage());
        }

    }

}
