package examen1;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Servidor {

    public static void main(String[] args) {

        int puerto = 6000;

        System.out.println("SERVIDOR GENERADOR DE OPERACIONES ARITMÉTICAS.");

        try (ServerSocket servidor = new ServerSocket(puerto)) {

            System.out.println("Esperando al cliente...");

            try (Socket clienteConectado = servidor.accept()) {

                System.out.println("Cliente conectado desde " + clienteConectado.getInetAddress());

                try (DataInputStream entradaDatos = new DataInputStream(clienteConectado.getInputStream()); 
                        DataOutputStream salidaDatos = new DataOutputStream(clienteConectado.getOutputStream())) {

                    int numero1;
                    int numero2;
                    int posicion;
                    int resultado = 0;
                    String operacion;

                    String operaciones[] = {"+", "-", "x"};

                    Random generadorNumeros = new Random();
                    numero1 = generadorNumeros.nextInt(1, 101);
                    numero2 = generadorNumeros.nextInt(1, 101);
                    posicion = generadorNumeros.nextInt(operaciones.length);

                    operacion = operaciones[posicion];

                    if (numero1 > numero2) {
                        salidaDatos.writeUTF(numero1 + " " + operacion + " " + numero2);
                    } else {
                        salidaDatos.writeUTF(numero2 + " " + operacion + " " + numero1);
                    }

                    switch (operacion) {
                        case "+" ->
                            resultado = numero1 + numero2;

                        case "-" -> {
                            if (numero1 > numero2) {
                                resultado = numero1 - numero2;
                            } else {
                                resultado = numero2 - numero1;
                            }
                        }

                        case "x" ->
                            resultado = numero1 * numero2;
                    }

                    boolean quiereContinuar = true;

                    while (quiereContinuar) {

                        String respuesta = String.valueOf(entradaDatos.readInt());

                        if (respuesta.equals(String.valueOf(resultado))) {
                            quiereContinuar = false;
                            salidaDatos.writeUTF("¡Correcto! Has resuelto la operación.");
                        } else {
                            salidaDatos.writeUTF("Incorrecto. Intentalo de nuevo.");
                        }

                    }

                    System.out.println("Cliente resolvió la operación. Cerrando Conexión...");

                } catch (IOException ex) {
                    System.err.println("Error al intentar leer/enviar datos. " + ex.getMessage());
                }

            } catch (IOException ex) {
                System.err.println("Error al intentar que el cliente se conecte. " + ex.getMessage());
            }

        } catch (IOException ex) {
            System.err.println("Error al intentar conectarse por el puerto " + puerto);
        }

    }
}
